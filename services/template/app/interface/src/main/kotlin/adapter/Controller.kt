package adapter

/**
 * リクエストを処理してレスポンスを返すコントローラーを表す
 *
 */
interface Controller<Params : Input, Content : Input, Resource : Any> {
    fun proceed(request: Request<Params, Content>): Response<Resource>
}
