package pl.mankevich.network.retrofit.response

import com.google.gson.annotations.SerializedName

data class LocationsListResponse(
    val info: InfoResponse,
    @SerializedName("results") val locationsResponse: List<LocationResponse>
)