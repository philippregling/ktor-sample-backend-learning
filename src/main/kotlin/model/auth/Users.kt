package main.kotlin.model.auth

import org.jetbrains.exposed.sql.Table


object Users : Table() {
    val id = integer("id").primaryKey().autoIncrement()
    val userName = varchar("name", 255)
    val hash = varchar("hash", 1000)
    val token = varchar("token", 1000)
}