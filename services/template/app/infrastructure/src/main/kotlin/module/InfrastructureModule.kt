package module

import io.ktor.config.ApplicationConfig
import org.koin.dsl.module.Module

object InfrastructureModule {
    @io.ktor.util.KtorExperimentalAPI
    fun module(config: ApplicationConfig): Module = org.koin.dsl.module.module {
        single("database.master.url") { config.property("database.master.url").getString() }
        single("database.master.driver") { config.property("database.master.driver").getString() }
        single("database.master.user") { config.property("database.master.user").getString() }
        single("database.master.password") { config.property("database.master.password").getString() }
        single("database.master.connectionTimeout") { config.property("database.master.connectionTimeout").getString().toLong() }
        single("database.master.idleTimeout") { config.property("database.master.idleTimeout").getString().toLong() }
        single("database.master.maxLifetime") { config.property("database.master.maxLifetime").getString().toLong() }
        single("database.master.minimumIdle") { config.property("database.master.minimumIdle").getString().toInt() }
        single("database.master.maximumPoolSize") { config.property("database.master.maximumPoolSize").getString().toInt() }
        single("database.master.prepStmtCacheSize") { config.property("database.master.prepStmtCacheSize").getString().toInt() }
        single("database.master.prepStmtCacheSqlLimit") { config.property("database.master.prepStmtCacheSqlLimit").getString().toInt() }
        single("database.slave.url") { config.property("database.slave.url").getString() }
        single("database.slave.driver") { config.property("database.slave.driver").getString() }
        single("database.slave.user") { config.property("database.slave.user").getString() }
        single("database.slave.password") { config.property("database.slave.password").getString() }
        single("database.slave.connectionTimeout") { config.property("database.slave.connectionTimeout").getString().toLong() }
        single("database.slave.idleTimeout") { config.property("database.slave.idleTimeout").getString().toLong() }
        single("database.slave.maxLifetime") { config.property("database.slave.maxLifetime").getString().toLong() }
        single("database.slave.minimumIdle") { config.property("database.slave.minimumIdle").getString().toInt() }
        single("database.slave.maximumPoolSize") { config.property("database.slave.maximumPoolSize").getString().toInt() }
        single("database.slave.prepStmtCacheSize") { config.property("database.slave.prepStmtCacheSize").getString().toInt() }
        single("database.slave.prepStmtCacheSqlLimit") { config.property("database.slave.prepStmtCacheSqlLimit").getString().toInt() }
    }
}
