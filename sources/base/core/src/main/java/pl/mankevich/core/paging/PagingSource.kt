package pl.mankevich.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingSource.LoadParams.Append
import androidx.paging.PagingSource.LoadParams.Prepend
import androidx.paging.PagingSource.LoadParams.Refresh
import androidx.paging.PagingSourceFactory
import androidx.paging.PagingState

typealias PagingSourceFactory<Value> = PagingSourceFactory<Int, Value>

abstract class PagingSource<Value : Any> : PagingSource<Int, Value>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Value> {
        return try {
            withTransaction {
                val tempCount = getCount()

                val key = params.key ?: 0
                val limit: Int = getLimit(params, key)
                val offset: Int = getOffset(params, key, tempCount)

                val data = getData(limit, offset)
                val nextPosToLoad = offset + data.size
                val nextKey =
                    if (data.isEmpty() || data.size < limit || nextPosToLoad >= tempCount) {
                        null
                    } else {
                        nextPosToLoad
                    }
                val prevKey = if (offset <= 0 || data.isEmpty()) null else offset
                LoadResult.Page(
                    data = data,
                    prevKey = prevKey,
                    nextKey = nextKey,
                    itemsBefore = offset,
                    itemsAfter = maxOf(0, tempCount - nextPosToLoad)
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Value>): Int? {
        return state.getClippedRefreshKey()
    }

    abstract suspend fun getCount(): Int

    abstract suspend fun getData(limit: Int, offset: Int): List<Value>

    abstract suspend fun <R> withTransaction(block: suspend () -> R): R

    /**
     * Calculates query limit based on LoadType.
     *
     * Prepend: If requested loadSize is larger than available number of items to prepend, it will
     * query with OFFSET = 0, LIMIT = prevKey
     */
    private fun getLimit(params: LoadParams<Int>, key: Int): Int {
        return when (params) {
            is Prepend ->
                if (key < params.loadSize) {
                    key
                } else {
                    params.loadSize
                }

            else -> params.loadSize
        }
    }

    /**
     * calculates query offset amount based on loadtype
     *
     * Prepend: OFFSET is calculated by counting backwards the number of items that needs to be
     * loaded before [key]. For example, if key = 30 and loadSize = 5, then offset = 25 and items
     * in db position 26-30 are loaded.
     * If requested loadSize is larger than the number of available items to
     * prepend, OFFSET clips to 0 to prevent negative OFFSET.
     *
     * Refresh:
     * If initialKey is supplied through Pager, Paging 3 will now start loading from
     * initialKey with initialKey being the first item.
     * If key is supplied by [getClippedRefreshKey], the key has already been adjusted to load half
     * of the requested items before anchorPosition and the other half after anchorPosition. See
     * comments on [getClippedRefreshKey] for more details.
     * If key (regardless if from initialKey or [getClippedRefreshKey]) is larger than available items,
     * the last page will be loaded by counting backwards the loadSize before last item in
     * database. For example, this can happen if invalidation came from a large number of items
     * dropped. i.e. in items 0 - 100, items 41-80 are dropped. Depending on last
     * viewed item, hypothetically [getClippedRefreshKey] may return key = 60. If loadSize = 10, then items
     * 31-40 will be loaded.
     */
    private fun getOffset(params: LoadParams<Int>, key: Int, itemCount: Int): Int {
        return when (params) {
            is Prepend ->
                if (key < params.loadSize) {
                    0
                } else {
                    key - params.loadSize
                }

            is Append -> key
            is Refresh ->
                if (key >= itemCount) {
                    maxOf(0, itemCount - params.loadSize)
                } else {
                    key
                }
        }
    }

    /**
     * Returns the key for [PagingSource] for a non-initial REFRESH load.
     *
     * To prevent a negative key, key is clipped to 0 when the number of items available before
     * anchorPosition is less than the requested amount of initialLoadSize / 2.
     */
    private fun <Value : Any> PagingState<Int, Value>.getClippedRefreshKey(): Int? {
        return when (val anchorPosition = anchorPosition) {
            null -> null
            /**
             *  It is unknown whether anchorPosition represents the item at the top of the screen or item at
             *  the bottom of the screen. To ensure the number of items loaded is enough to fill up the
             *  screen, half of loadSize is loaded before the anchorPosition and the other half is
             *  loaded after the anchorPosition -- anchorPosition becomes the middle item.
             */
            else -> maxOf(0, anchorPosition - (config.initialLoadSize / 2))
        }
    }
}