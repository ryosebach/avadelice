package input

import input.body.ExampleCreateBody
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import request.ExampleCreateRequest

@KtorExperimentalLocationsAPI
@UseExperimental(io.ktor.util.InternalAPI::class)
@Location("/")
object ExampleCreateInput {
    fun toRequest(body: ExampleCreateBody): ExampleCreateRequest {
        return ExampleCreateRequest(
            exampleKey = body.exampleKey,
            nameJa = body.nameJa,
            nameEn = body.nameEn,
            nameKo = body.nameKo,
            nameZh = body.nameZh,
            enabled = body.enabled
        )
    }
}
