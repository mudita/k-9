package com.mudita.mail.ui.usecase

import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.mudita.mail.ui.navigation.MuditaMailNavGraph
import com.mudita.mail.ui.theme.MuditaTheme

@Composable
fun MuditaMailApp(

) {
    MuditaTheme {
        val navController = rememberNavController()
        val scaffoldState = rememberScaffoldState()
        Scaffold(
            scaffoldState = scaffoldState
        ) {
            MuditaMailNavGraph(navController)
        }
    }
}
