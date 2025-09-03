package edu.ucne.composedemo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.composedemo.data.local.dao.TareaDao
import edu.ucne.composedemo.data.local.dao.TicketDao
import edu.ucne.composedemo.data.local.entities.TareaEntity
import edu.ucne.composedemo.data.local.entities.TicketEntity

@Database(
    entities = [
        TicketEntity::class,
        TareaEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class TicketDb : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
    abstract fun tareaDao(): TareaDao
}