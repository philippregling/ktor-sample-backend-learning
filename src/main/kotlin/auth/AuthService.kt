package main.kotlin.service

class AuthService {


//    suspend fun getAllWidgets(): List<Widget> = dbQuery {
//        Widgets.selectAll().map { toWidget(it) }
//    }
//
//    suspend fun getWidget(id: Int): Widget? = dbQuery {
//        Widgets.select {
//            (Widgets.id eq id)
//        }.mapNotNull { toWidget(it) }
//                .singleOrNull()
//    }
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
//    suspend fun addWidget(widget: NewWidget): Widget {
//        var key = 0
//        dbQuery {
//            key = (Widgets.insert {
//                it[name] = widget.name
//                it[quantity] = widget.quantity
//                it[dateUpdated] = System.currentTimeMillis()
//            } get Widgets.id)!!
//        }
//        return getWidget(key)!!.also {
//            onChange(ChangeType.CREATE, key, it)
//        }
//    }
//
//    suspend fun deleteWidget(id: Int): Boolean {
//        return dbQuery {
//            Widgets.deleteWhere { Widgets.id eq id } > 0
//        }.also {
//            if(it) onChange(ChangeType.DELETE, id)
//        }
//    }
//
//    private fun toWidget(row: ResultRow): Widget =
//            Widget(
//                    id = row[Widgets.id],
//                    name = row[Widgets.name],
//                    quantity = row[Widgets.quantity],
//                    dateUpdated = row[Widgets.dateUpdated]
//            )

}