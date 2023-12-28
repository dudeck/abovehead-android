package pl.abovehead

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

class NavControllerViewModel : ViewModel() {
    lateinit var navControllerState: MutableState<NavController>
    fun storeNavController(navController: NavController) {
        navControllerState = mutableStateOf(navController);
    }
}