import com.google.gson.annotations.SerializedName
data class DustBody(
    val totalCount: Int,
    @SerializedName("items")
    val dustItems: MutableList<DustItem>?,
    val pageNo: Int,
    val numOfRows: Int
)