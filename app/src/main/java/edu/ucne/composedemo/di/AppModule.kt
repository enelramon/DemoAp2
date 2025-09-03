package edu.ucne.composedemo.di

import android.content.Context
import androidx.room.Room
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.composedemo.data.local.database.TicketDb
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideTicketDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
                appContext,
                TicketDb::class.java,
                "Ticket.db"
            ).fallbackToDestructiveMigration(false)
            .build()

    @Provides
    fun provideTicketDao(ticketDb: TicketDb) = ticketDb.ticketDao()

    @Provides
    fun provideTareaDao(ticketDb: TicketDb) = ticketDb.tareaDao()
}