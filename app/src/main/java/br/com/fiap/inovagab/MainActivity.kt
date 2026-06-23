package br.com.fiap.inovagab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.fiap.inovagab.navigation.AppNavigation
import br.com.fiap.inovagab.ui.screens.auth.LoginScreen
import br.com.fiap.inovagab.ui.theme.InovaGABTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            InovaGABTheme {

                AppNavigation()
            }
        }
    }
}