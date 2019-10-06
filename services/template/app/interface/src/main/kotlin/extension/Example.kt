package extension

import entity.Example
import resource.ExampleResource
import utility.formatToISO

/**
 * エンティティをリソースの形式に変換する
 */
fun Example.toResource(): ExampleResource {
    return ExampleResource(
        this.testKey,
        this.nameJa,
        this.nameEn,
        this.nameKo,
        this.nameZh,
        this.enabled,
        this.createdAt.formatToISO(),
        this.updatedAt.formatToISO()
    )
}
