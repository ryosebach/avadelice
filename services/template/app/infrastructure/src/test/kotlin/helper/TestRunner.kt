package helper

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.ninja_squad.dbsetup.DbSetup
import com.ninja_squad.dbsetup.Operations
import com.ninja_squad.dbsetup.destination.DriverManagerDestination
import com.ninja_squad.dbsetup.operation.Operation
import com.typesafe.config.ConfigFactory
import config.Config
import io.ktor.config.HoconApplicationConfig
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.createTestEnvironment
import io.ktor.util.KtorExperimentalAPI
import org.koin.ktor.ext.installKoin

@KtorExperimentalAPI
class TestRunner(
    module: org.koin.dsl.module.Module
) {
    private val testApplicationEngine: TestApplicationEngine

    init {
        val applicationConfig = HoconApplicationConfig(testConfig)

        testApplicationEngine = TestApplicationEngine(
            createTestEnvironment {
                config = applicationConfig
            }
        )
        val modules = listOf(
            module,
            Config(testApplicationEngine.environment).module()
        )

        testApplicationEngine.start(true)

        testApplicationEngine.application.installKoin(modules)
    }

    /**
     * テストを用意したアプリケーションで実行する
     */
    fun withApplicationEngine(block: TestApplicationEngine.() -> Unit) {
        with(testApplicationEngine, block)
    }

    companion object {
        private val testConfig = ConfigFactory.load("application-test.conf")!!
        val objectMapper = jacksonObjectMapper().apply {
            configure(SerializationFeature.INDENT_OUTPUT, true)
            configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
        }

        /**
         * DBの準備をする
         */
        fun setupDatabase(vararg operations: Operation) {
            DbSetup(
                DriverManagerDestination(
                    testConfig.getString("database.master.url"),
                    testConfig.getString("database.master.user"),
                    testConfig.getString("database.master.password")
                ),
                Operations.sequenceOf(operations.asList())
            ).launch()
        }
    }
}
