package adapter

import resource.ErrorResource

interface Violations {
    fun isValid(): Boolean
    fun toResource(): ErrorResource
}
