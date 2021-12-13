package com.mudita.mail.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mudita.mail.ui.usecase.email.view.EmailScreen
import com.mudita.mail.ui.usecase.signIn.view.SignInScreen
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun MuditaMailNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = SignInDestination.toRoute(),
    ) {
        composable(SignInDestination.toRoute()) {
            SignInScreen(
                getViewModel {
                    parametersOf(navController)
                }
            )
        }

        composable(EmailDestination.toRoute()) {
            val context = LocalContext.current
            EmailScreen(
                getViewModel {
                    parametersOf(context)
                }
            )
        }
    }
}
