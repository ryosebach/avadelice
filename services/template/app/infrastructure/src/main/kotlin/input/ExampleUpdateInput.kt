package input

import input.body.ExampleUpdateBody
import io.ktor.locations.KtorExperimentalLocationsAPI
import io.ktor.locations.Location
import request.ExampleUpdateRequest

@KtorExperimentalLocationsAPI
@UseExperimental(io.ktor.util.InternalAPI::class)
@Location("/{exampleKey}")
data class ExampleUpdateInput(
    val exampleKey: String
) {
    fun toRequest(body: ExampleUpdateBody): ExampleUpdateRequest {
        return ExampleUpdateRequest(
            exampleKey,
            nameJa = body.nameJa,
            nameEn = body.nameEn,
            nameKo = body.nameKo,
            nameZh = body.nameZh,
            enabled = body.enabled
        )
    }
}
