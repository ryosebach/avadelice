package helper

data class DatabaseConfig(
    val url: String,
    val driver: String,
    val user: String,
    val password: String,
    val connectionTimeout: Long,
    val idleTimeout: Long,
    val maxLifetime: Long,
    val minimumIdle: Int,
    val maximumPoolSize: Int,
    val prepStmtCacheSize: Int,
    val prepStmtCacheSqlLimit: Int
)
