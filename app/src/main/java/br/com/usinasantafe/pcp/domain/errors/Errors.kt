package br.com.usinasantafe.pcp.domain.errors

//class UsecaseException(
//    message: String = "Failure Usecase",
//    function: String = "",
//    cause: Throwable? = null
//) : Exception("$message -> $function", cause)
//
//class RepositoryException(
//    message: String = "Failure Repository",
//    function: String = "",
//    cause: Throwable? = null
//) : Exception("$message -> $function", cause)
//
//class DatasourceException(
//    message: String = "Failure Datasource",
//    function: String = "",
//    cause: Throwable? = null
//) : Exception("$message -> $function", cause)


fun resultFailure(
    context: String,
    message: String?,
    cause: Throwable? = null
): Result<Nothing>  {
    return Result.failure(
        AppError(
            context = context,
            message = message,
            cause = cause
        )
    )
}

class AppError(
    context: String,
    message: String?,
    cause: Throwable? = null
) : Exception("$context -> ${message ?: "Unknown Error"}", cause)