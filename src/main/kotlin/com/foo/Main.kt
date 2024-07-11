package com.foo

import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import com.foo.sqldelight.Database
import com.zaxxer.hikari.HikariDataSource

fun main() {
    val dataSource = HikariDataSource().apply {
        jdbcUrl = "jdbc:postgresql://127.0.0.1:5432/postgres"
    }
    val db = Database(dataSource.asJdbcDriver())
    println(db.studentQueries.getAllStudents().executeAsList())
}
