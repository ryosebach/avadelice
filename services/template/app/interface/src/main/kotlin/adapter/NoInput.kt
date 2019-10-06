package adapter

import resource.ErrorResource

class NoInput private constructor() : Input {
    override fun validate(): Violations {
        return object : Violations {
            override fun isValid(): Boolean {
                return true
            }

            override fun toResource(): ErrorResource {
                throw Exception("No input data.")
            }
        }
    }
}
