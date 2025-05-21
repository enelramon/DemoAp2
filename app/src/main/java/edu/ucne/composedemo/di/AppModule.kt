package edu.ucne.composedemo.di

import edu.ucne.composedemo.data.local.database.TicketDb
import edu.ucne.composedemo.data.repository.TicketRepository
import edu.ucne.composedemo.presentation.ticket.TicketViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val databaseModule = module {
    single {
        androidx.room.Room.databaseBuilder(
            androidContext(),
            TicketDb::class.java,
            "Ticket.db"
        ).fallbackToDestructiveMigration()
            .build()
    }

    single { get<TicketDb>().ticketDao() }
}

val repositoryModule = module {
    single { TicketRepository(get()) } // get() resuelve ticketDao automáticamente
}

val viewModelModule = module {
    viewModel { TicketViewModel(get()) }
}