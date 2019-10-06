package adapter

import resource.ErrorResource

/**
 * レスポンスを表す
 */
sealed class Response<Resource> {
    data class Success<Resource>(val resource: Resource) : Response<Resource>()
    data class Failure<Resource>(val resource: ErrorResource) : Response<Resource>()
}
