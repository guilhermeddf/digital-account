/*package com.dock.bank.digitalaccount.config



import org.testcontainers.containers.PostgreSQLContainer

class DatabaseContainer(imageName: String) : PostgreSQLContainer<DatabaseContainer>(imageName) {

    companion object {
        const val PORT = 5432
        const val DATABASE = "digital-account-service"
        const val USER = "digital-account-service-app"
        const val PASSWORD = "digital-account-service-app"
    }

    init {
        withExposedPorts(PORT)
        withDatabaseName(DATABASE)
        withUsername(USER)
        withPassword(PASSWORD)
    }
}

 */

