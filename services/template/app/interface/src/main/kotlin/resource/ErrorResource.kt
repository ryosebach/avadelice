package resource

data class ErrorResource(
    val type: ErrorType,
    val message: String
) {
    enum class ErrorType {
        MALFORMED_INPUT,
        VALIDATION_ERROR,
        NOT_FOUND,
        SYSTEM_ERROR
    }
}
