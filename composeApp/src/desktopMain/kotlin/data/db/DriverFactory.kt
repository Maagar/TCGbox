package data.db

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.tcgbox.database.AppDatabase
import java.util.Properties

class DriverFactory {
    fun createDriver(): SqlDriver {
        val databasePath = "jdbc:tcgbox:database.db"
        val driver: SqlDriver = JdbcSqliteDriver("jdbc:sqlite:test.db", Properties(), AppDatabase.Schema)

        return driver
    }
}

fun createDatabase(driver: SqlDriver): AppDatabase {
    return AppDatabase(driver)
}