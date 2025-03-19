package edu.ucne.composedemo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.composedemo.data.local.dao.DepositoDao
import edu.ucne.composedemo.data.local.dao.TicketDao
import edu.ucne.composedemo.data.local.entities.DepositoEntity
import edu.ucne.composedemo.data.local.entities.TicketEntity

@Database(
    entities = [
        TicketEntity::class,
        DepositoEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class TicketDb : RoomDatabase() {
    abstract fun ticketDao(): TicketDao
    abstract fun depositoDao(): DepositoDao
}