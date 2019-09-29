package input.body

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class ExampleUpdateBody(
    val nameJa: String?,
    val nameEn: String?,
    val nameKo: String?,
    val nameZh: String?,
    val enabled: Boolean?
)
