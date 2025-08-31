package com.tareasapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tareasapp.di.ServiceLocator
import com.tareasapp.feature.edit.EditEffect
import com.tareasapp.feature.edit.EditIntent
import com.tareasapp.feature.edit.EditScreen
import com.tareasapp.feature.edit.EditViewModel
import com.tareasapp.feature.list.ListEffect
import com.tareasapp.feature.list.ListIntent
import com.tareasapp.feature.list.ListScreen
import com.tareasapp.feature.list.ListViewModel
import com.tareasapp.ui.theme.TareasTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			TareasTheme {
				App()
			}
		}
	}
}

@Composable
fun App() {
	val navController = rememberNavController()
	val snackbarHostState = remember { SnackbarHostState() }

	Scaffold(snackbarHost = { SnackbarHost(hostState = snackbarHostState) }) { padding ->
		NavHost(navController = navController, startDestination = "list") {
			composable("list") {
				val vm: ListViewModel = viewModel(factory = object : ViewModelProvider.Factory {
					override fun <T : ViewModel> create(modelClass: Class<T>): T {
						val ctx = navController.context
						return ListViewModel(
							ServiceLocator.provideObserveTasks(ctx),
							ServiceLocator.provideDeleteTask(ctx)
						) as T
					}
				})
				val state by vm.state.collectAsState()

				LaunchedEffect(Unit) {
					for (effect in vm.effects) {
						when (effect) {
							is ListEffect.ShowMessage -> snackbarHostState.showSnackbar(effect.message)
						}
					}
				}

				ListScreen(
					state = state,
					onIntent = {
						when (it) {
							ListIntent.CreateNew -> navController.navigate("edit/0")
							is ListIntent.Edit -> navController.navigate("edit/${it.id}")
							else -> vm.dispatch(it)
						}
					}
				)
			}

			composable(
				"edit/{taskId}",
				arguments = listOf(navArgument("taskId") { type = NavType.IntType })
			) { backStackEntry ->
				val id = backStackEntry.arguments?.getInt("taskId") ?: 0
				val vm: EditViewModel = viewModel(factory = object : ViewModelProvider.Factory {
					override fun <T : ViewModel> create(modelClass: Class<T>): T {
						val ctx = navController.context
						return EditViewModel(
							ServiceLocator.provideGetTask(ctx),
							ServiceLocator.provideUpsertTask(ctx),
							ServiceLocator.provideDeleteTask(ctx)
						) as T
					}
				})
				val state by vm.state.collectAsState()

				LaunchedEffect(Unit) {
					for (effect in vm.effects) {
						when (effect) {
							is EditEffect.ShowMessage -> snackbarHostState.showSnackbar(effect.message)
						}
					}
				}

				EditScreen(
					state = state,
					onIntent = { intent ->
						when (intent) {
							is EditIntent.Load -> vm.dispatch(EditIntent.Load(id))
							else -> vm.dispatch(intent)
						}
					}
				)

				LaunchedEffect(state.saved || state.deleted) {
					if (state.saved || state.deleted) {
						navController.popBackStack()
					}
				}
			}
		}
	}
}