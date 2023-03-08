package com.mudita.mail.ui.navigation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mudita.mail.ui.usecase.add.view.AddAccountScreen
import com.mudita.mail.ui.usecase.email.view.EmailScreen
import com.mudita.mail.ui.usecase.signIn.view.SignInScreen
import com.mudita.mail.ui.util.BackPressHandler
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MuditaMailNavGraph(
    startDestination: Destination,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.toRoute(),
    ) {
        composable(SignInDestination.toRoute()) {
            val context = LocalContext.current
            SignInScreen(
                getViewModel { parametersOf(context, navController) }
            )
        }
        composable(EmailDestination.toRoute()) {
            val context = LocalContext.current
            EmailScreen(
                getViewModel { parametersOf(context, navController) }
            )
        }
        composable(AddAccountDestination.toRoute()) {
            val context = LocalContext.current
            AddAccountScreen(
                getViewModel {
                    parametersOf(
                        context,
                        navController,
                        BackPressHandler { (context as? Activity)?.onBackPressed() }
                    )
                }
            )
        }
    }
}
