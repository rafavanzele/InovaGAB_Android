package br.com.fiap.inovagab.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import br.com.fiap.inovagab.ui.theme.InovaGABTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InovaTopBar(
    title: String,
    onMenuClick: () -> Unit = {},
    onNotificationClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F3F66)
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onMenuClick
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Abrir menu",
                    tint = Color(0xFF1F3F66)
                )
            }
        },
        actions = {
            IconButton(
                onClick = onNotificationClick
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notificações",
                    tint = Color(0xFF1F3F66)
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color(0xFFF5F7FB)
        )
    )
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun InovaTopBarPreview(){
//
//    InovaGABTheme() {
//        InovaTopBar()
//    }
//}