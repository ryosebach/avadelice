package adapter

/**
 * リクエストを表す
 */
data class Request<Params : Input, Content : Input>(
    val params: Params?,
    val content: Content?
)
