package main.kotlin.auth

import main.kotlin.DatabaseFactory.dbQuery
import main.kotlin.model.auth.User
import main.kotlin.model.auth.Users
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class AuthService {


    //    suspend fun getAllWidgets(): List<Widget> = dbQuery {
//        Widgets.selectAll().map { toWidget(it) }
//    }
//
    suspend fun getUser(id: Int? = null, userName: String? = null): User? = dbQuery {
        when {
            id != null -> Users.select { (Users.id eq id) }.mapNotNull { toUser(it) }.singleOrNull()
            userName != null -> Users.select { (Users.userName eq userName) }.mapNotNull { toUser(it) }.singleOrNull()
            else -> null
        }
    }

    //
//    suspend fun updateWidget(widget: NewWidget): Widget? {
//        val id = widget.id
//        return if (id == null) {
//            addWidget(widget)
//        } else {
//            dbQuery {
//                Widgets.update({ Widgets.id eq id }) {
//                    it[name] = widget.name
//                    it[quantity] = widget.quantity
//                    it[dateUpdated] = System.currentTimeMillis()
//                }
//            }
//            getWidget(id).also {
//                onChange(ChangeType.UPDATE, id, it)
//            }
//        }
//    }
//
    suspend fun addUser(username: String, password: String): User? {
        var key = 0
        dbQuery {
            key = (Users.insert {
                it[userName] = username
                it[hash] = password
            } get Users.id)!!
        }
        return getUser(key)
    }

    //
//    suspend fun deleteWidget(id: Int): Boolean {
//        return dbQuery {
//            Widgets.deleteWhere { Widgets.id eq id } > 0
//        }.also {
//            if(it) onChange(ChangeType.DELETE, id)
//        }
//    }
//
    private fun toUser(row: ResultRow): User = User(id = row[Users.id], userName = row[Users.userName], passwordHash = row[Users.hash])

}