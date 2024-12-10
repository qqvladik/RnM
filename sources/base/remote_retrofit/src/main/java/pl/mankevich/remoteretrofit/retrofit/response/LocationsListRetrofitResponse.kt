package pl.mankevich.remoteretrofit.retrofit.response

import com.google.gson.annotations.SerializedName

data class LocationsListRetrofitResponse(
    val info: InfoRetrofitResponse,
    @SerializedName("results") val locationsResponse: List<LocationRetrofitResponse>
)