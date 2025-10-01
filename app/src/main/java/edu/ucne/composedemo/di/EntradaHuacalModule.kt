package edu.ucne.composedemo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.composedemo.data.local.dao.EntradaHuacalDao
import edu.ucne.composedemo.data.local.repository.EntradaHuacalRepositoryImpl
import edu.ucne.composedemo.domain.entradashuacales.repository.EntradaHuacalRepository
import edu.ucne.composedemo.domain.entradashuacales.usecase.DeleteEntradaHuacalUseCase
import edu.ucne.composedemo.domain.entradashuacales.usecase.GetEntradaHuacalUseCase
import edu.ucne.composedemo.domain.entradashuacales.usecase.ObserveEntradasHuacalesUseCase
import edu.ucne.composedemo.domain.entradashuacales.usecase.UpsertEntradaHuacalUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object EntradaHuacalModule {
    @Provides
    @Singleton
    fun provideGetEntradaHuacalUseCase(repository: EntradaHuacalRepository): GetEntradaHuacalUseCase {
        return GetEntradaHuacalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideObserveEntradasHuacalesUseCase(repository: EntradaHuacalRepository): ObserveEntradasHuacalesUseCase {
        return ObserveEntradasHuacalesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpsertEntradaHuacalUseCase(repository: EntradaHuacalRepository): UpsertEntradaHuacalUseCase {
        return UpsertEntradaHuacalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteEntradaHuacalUseCase(repository: EntradaHuacalRepository): DeleteEntradaHuacalUseCase {
        return DeleteEntradaHuacalUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideEntradaHuacalRepository(dao: EntradaHuacalDao): EntradaHuacalRepository {
        return EntradaHuacalRepositoryImpl(dao)
    }
}
