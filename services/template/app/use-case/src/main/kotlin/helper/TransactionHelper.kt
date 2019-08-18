package helper

import org.jetbrains.exposed.sql.Transaction

interface TransactionHelper {
    /**
     * masterに向いたトランザクションを張る
     */
    fun <T> masterTransaction(function: Transaction.() -> T): T

    /**
     * slaveに向いたトランザクションを張る
     */
    fun <T> slaveTransaction(function: Transaction.() -> T): T
}
