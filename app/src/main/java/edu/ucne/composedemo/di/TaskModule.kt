package edu.ucne.composedemo.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.composedemo.domain.tareas.repository.TaskRepository
import edu.ucne.composedemo.domain.tareas.usecase.DeleteTaskUseCase
import edu.ucne.composedemo.domain.tareas.usecase.GetTaskUseCase
import edu.ucne.composedemo.domain.tareas.usecase.ObserveTasksUseCase
import edu.ucne.composedemo.domain.tareas.usecase.UpsertTaskUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TaskModule {
    @Provides
    @Singleton
    fun provideGetTaskUseCase(taskRepository: TaskRepository): GetTaskUseCase {
        return GetTaskUseCase(taskRepository)
    }

    @Provides
    @Singleton
    fun provideObserveTasksUseCase(taskRepository: TaskRepository): ObserveTasksUseCase {
        return ObserveTasksUseCase(taskRepository)
    }

    @Provides
    @Singleton
    fun provideUpsertTaskUseCase(taskRepository: TaskRepository): UpsertTaskUseCase {
        return UpsertTaskUseCase(taskRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteTaskUseCase(taskRepository: TaskRepository): DeleteTaskUseCase {
        return DeleteTaskUseCase(taskRepository)
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskRepository: TaskRepository): TaskRepository {
        return taskRepository
    }

}