package edu.ucne.composedemo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.composedemo.data.local.dao.EntradaHuacalDao
import edu.ucne.composedemo.data.local.dao.TicketDao
import edu.ucne.composedemo.data.local.entities.EntradaHuacalEntity
import edu.ucne.composedemo.data.local.entities.TicketEntity
import edu.ucne.composedemo.data.tareas.local.TaskDao
import edu.ucne.composedemo.data.tareas.local.TaskEntity

@Database(
    entities = [
        TicketEntity::class,
        TaskEntity::class,
        EntradaHuacalEntity::class,
    ],
    version = 3,
    exportSchema = false
)
abstract class TicketDb : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
    abstract fun taskDao(): TaskDao
    abstract fun entradaHuacalDao(): EntradaHuacalDao
}
