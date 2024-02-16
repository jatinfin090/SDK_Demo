import com.example.basedemo.base.BaseErrorResponse

data class ServerException(
    val errors: List<BaseErrorResponse>? = null,
    val msg: String? = null,
    val requestTime: String? = null,
    val statusCode: Int? = null,
    val success: Boolean? = null,
) : Exception()
