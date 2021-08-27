package dali.hamza.tvshowapp.models

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

@Stable
class AppComposition<T : ViewModel>(
    private val viewModel: T,
    private val navController: NavController
) {
    fun getVM() = viewModel
    fun getController() = navController
}