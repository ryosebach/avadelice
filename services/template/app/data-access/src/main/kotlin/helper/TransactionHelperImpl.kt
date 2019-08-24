package helper

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.transaction

class TransactionHelperImpl(
    masterDatabaseConfig: DatabaseConfig,
    slaveDatabaseConfig: DatabaseConfig
) : TransactionHelper {
    /**
     * masterに向けたコネクションの確立
     */
    private val masterDatabase = connect(masterDatabaseConfig)

    /**
     * slaveに向けたコネクションの確立
     */
    private val slaveDatabase = connect(slaveDatabaseConfig)

    /**
     * masterに向いたトランザクションを張る
     */
    override fun <T> masterTransaction(function: Transaction.() -> T): T {
        return transaction(masterDatabase, function)
    }

    /**
     * slaveに向いたトランザクションを張る
     */
    override fun <T> slaveTransaction(function: Transaction.() -> T): T {
        return transaction(slaveDatabase, function)
    }

    companion object {
        /**
         * configurationから接続を確立する
         */
        fun connect(config: DatabaseConfig): Database {
            return Database.connect(
                HikariDataSource(
                    HikariConfig().also {
                        it.driverClassName = config.driver
                        it.jdbcUrl = config.url
                        it.connectionTimeout = config.connectionTimeout
                        it.idleTimeout = config.idleTimeout
                        it.maxLifetime = config.maxLifetime
                        it.minimumIdle = config.minimumIdle
                        it.maximumPoolSize = config.maximumPoolSize
                        it.connectionInitSql = "SELECT 1;"
                        it.addDataSourceProperty("user", config.user)
                        it.addDataSourceProperty("password", config.password)
                        it.addDataSourceProperty("useServerPrepStmts", "true")
                        it.addDataSourceProperty("cachePrepStmts", "true")
                        it.addDataSourceProperty("prepStmtCacheSize", config.prepStmtCacheSize)
                        it.addDataSourceProperty("prepStmtCacheSqlLimit", config.prepStmtCacheSqlLimit)
                    }
                )
            )
        }
    }
}
