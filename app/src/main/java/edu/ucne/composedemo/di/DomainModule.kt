package edu.ucne.composedemo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.composedemo.data.repository.ClienteRepository
import edu.ucne.composedemo.data.repository.CobroRepository
import edu.ucne.composedemo.data.repository.CxcRepository
import edu.ucne.composedemo.data.repository.GastosRepository
import edu.ucne.composedemo.data.repository.SistemaRepository
import edu.ucne.composedemo.data.repository.SuplidorGastoRepository
import edu.ucne.composedemo.domain.usecase.ClienteUseCase
import edu.ucne.composedemo.domain.usecase.CobroUseCase
import edu.ucne.composedemo.domain.usecase.CxcUseCase
import edu.ucne.composedemo.domain.usecase.GastosUseCase
import edu.ucne.composedemo.domain.usecase.SistemaUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DomainModule {
    
    @Provides
    @Singleton
    fun provideSistemaUseCase(sistemaRepository: SistemaRepository): SistemaUseCase {
        return SistemaUseCase(sistemaRepository)
    }
    
    @Provides
    @Singleton
    fun provideClienteUseCase(clienteRepository: ClienteRepository): ClienteUseCase {
        return ClienteUseCase(clienteRepository)
    }
    
    @Provides
    @Singleton
    fun provideCxcUseCase(cxcRepository: CxcRepository): CxcUseCase {
        return CxcUseCase(cxcRepository)
    }
    
    @Provides
    @Singleton
    fun provideCobroUseCase(cobroRepository: CobroRepository): CobroUseCase {
        return CobroUseCase(cobroRepository)
    }
    
    @Provides
    @Singleton
    fun provideGastosUseCase(
        gastosRepository: GastosRepository,
        suplidorGastoRepository: SuplidorGastoRepository
    ): GastosUseCase {
        return GastosUseCase(gastosRepository, suplidorGastoRepository)
    }
}