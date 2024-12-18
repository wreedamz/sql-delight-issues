package com.foo

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.foo.sqldelight.Database
import com.zaxxer.hikari.HikariDataSource
import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toKotlinInstant
import java.time.LocalDateTime
import java.time.ZoneOffset

fun main() {
    val dataSource = HikariDataSource().apply {
        jdbcUrl = "jdbc:postgresql://127.0.0.1:5432/postgres"
    }
    val db = Database(dataSource.asJdbcDriver(), SleepTracker.Adapter(SleepTrackerAdapter))
    println(db.sleepTrackerQueries.getAllTracker().executeAsList())
}

object SleepTrackerAdapter : ColumnAdapter<List<Instant>, Array<LocalDateTime>> {
    override fun decode(databaseValue: Array<LocalDateTime>) =
        databaseValue.map { it.toInstant(ZoneOffset.UTC).toKotlinInstant() }

    override fun encode(value: List<Instant>): Array<LocalDateTime> {
        return value.map { it.toJavaInstant() }
            .map { LocalDateTime.ofInstant(it, ZoneOffset.UTC) }
            .toTypedArray()
    }
}
