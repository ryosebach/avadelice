package module

import controller.ExampleController
import helper.DatabaseConfig
import helper.TransactionHelper
import helper.TransactionHelperImpl
import org.koin.dsl.module.Module
import org.koin.dsl.module.module
import rdb.ExampleRepositoryImpl
import repository.ExampleRepository
import service.ExampleService
import service.ExampleServiceImpl

object ModuleBuilder {
    fun modules(infrastructureModules: Module): List<Module> = listOf(
        module {
            // data stores
            single("masterDatabaseConfig") {
                DatabaseConfig(
                    url = get("database.master.url"),
                    driver = get("database.master.driver"),
                    user = get("database.master.user"),
                    password = get("database.master.password"),
                    connectionTimeout = get("database.master.connectionTimeout"),
                    idleTimeout = get("database.master.idleTimeout"),
                    maxLifetime = get("database.master.maxLifetime"),
                    minimumIdle = get("database.master.minimumIdle"),
                    maximumPoolSize = get("database.master.maximumPoolSize"),
                    prepStmtCacheSize = get("database.master.prepStmtCacheSize"),
                    prepStmtCacheSqlLimit = get("database.master.prepStmtCacheSqlLimit")
                )
            }
            single("slaveDatabaseConfig") {
                DatabaseConfig(
                    url = get("database.slave.url"),
                    driver = get("database.slave.driver"),
                    user = get("database.slave.user"),
                    password = get("database.slave.password"),
                    connectionTimeout = get("database.slave.connectionTimeout"),
                    idleTimeout = get("database.slave.idleTimeout"),
                    maxLifetime = get("database.slave.maxLifetime"),
                    minimumIdle = get("database.slave.minimumIdle"),
                    maximumPoolSize = get("database.slave.maximumPoolSize"),
                    prepStmtCacheSize = get("database.slave.prepStmtCacheSize"),
                    prepStmtCacheSqlLimit = get("database.slave.prepStmtCacheSqlLimit")
                )
            }
            single<TransactionHelper> {
                TransactionHelperImpl(
                    masterDatabaseConfig = get("masterDatabaseConfig"),
                    slaveDatabaseConfig = get("slaveDatabaseConfig")
                )
            }

            // repositories
            single<ExampleRepository> { ExampleRepositoryImpl() }

            // controllers
            single { ExampleController(get()) }

            // services
            single<ExampleService> { ExampleServiceImpl(get(), get()) }
        },
        infrastructureModules
    )
}
