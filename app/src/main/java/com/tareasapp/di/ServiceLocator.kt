package com.tareasapp.di

import android.content.Context
import androidx.room.Room
import com.tareasapp.data.local.AppDatabase
import com.tareasapp.data.repository.TaskRepositoryImpl
import com.tareasapp.domain.repository.TaskRepository
import com.tareasapp.domain.usecase.DeleteTask
import com.tareasapp.domain.usecase.GetTask
import com.tareasapp.domain.usecase.ObserveTasks
import com.tareasapp.domain.usecase.UpsertTask

object ServiceLocator {
	@Volatile private var database: AppDatabase? = null
	@Volatile private var repository: TaskRepository? = null

	fun provideDatabase(context: Context): AppDatabase = database ?: synchronized(this) {
		val db = Room.databaseBuilder(
			context.applicationContext,
			AppDatabase::class.java,
			"tareas-db"
		).fallbackToDestructiveMigration().build()
		database = db
		db
	}

	fun provideRepository(context: Context): TaskRepository = repository ?: synchronized(this) {
		val repo = TaskRepositoryImpl(provideDatabase(context).taskDao())
		repository = repo
		repo
	}

	fun provideObserveTasks(context: Context) = ObserveTasks(provideRepository(context))
	fun provideGetTask(context: Context) = GetTask(provideRepository(context))
	fun provideUpsertTask(context: Context) = UpsertTask(provideRepository(context))
	fun provideDeleteTask(context: Context) = DeleteTask(provideRepository(context))
}