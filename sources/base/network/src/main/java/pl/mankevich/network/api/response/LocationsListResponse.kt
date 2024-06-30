package pl.mankevich.network.api.response

import com.google.gson.annotations.SerializedName

data class LocationsListResponse(
    @SerializedName("results")
    val locationsResponse: List<LocationResponse>
)