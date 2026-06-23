package br.com.fiap.inovagab.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.inovagab.ui.theme.InovaGABTheme
import kotlinx.coroutines.launch

@Composable
fun InovaDrawer(
    navController: NavController? = null,
    drawerState: DrawerState? = null,
    homeRoute: String = ""
) {

    val scope = rememberCoroutineScope()

    ModalDrawerSheet(
        modifier = Modifier.width(280.dp),
        drawerContainerColor = Color.White
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "InovaGAB",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1F3F66),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Text(
            text = "Plataforma corporativa",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF6B7280),
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        HorizontalDivider()

        DrawerItem(
            label = "Home",
            icon = Icons.Default.Home,
            onClick = {
                if (homeRoute.isNotBlank()) {
                    navController?.navigate(homeRoute)
                }

                scope.launch {
                    drawerState?.close()
                }
            }
        )

        DrawerItem(
            label = "Perfil",
            icon = Icons.Default.Person,
            onClick = {
                scope.launch {
                    drawerState?.close()
                }
            }
        )

        DrawerItem(
            label = "Notificações",
            icon = Icons.Default.Notifications,
            onClick = {
                scope.launch {
                    drawerState?.close()
                }
            }
        )

        DrawerItem(
            label = "Configurações",
            icon = Icons.Default.Settings,
            onClick = {
                scope.launch {
                    drawerState?.close()
                }
            }
        )

        DrawerItem(
            label = "Sair",
            icon = Icons.Default.ExitToApp,
            onClick = {
                navController?.navigate("login") {
                    popUpTo(0)
                }

                scope.launch {
                    drawerState?.close()
                }
            }
        )
    }
}

@Composable
fun DrawerItem(
    label: String,
    icon: ImageVector,
    onClick: () -> Unit
) {

    NavigationDrawerItem(
        label = {
            Text(
                text = label,
                color = Color(0xFF1F1F1F)
            )
        },
        selected = false,
        onClick = onClick,
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color(0xFF1F1F1F)
            )
        },
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Color(0xFFE8F5E9),
            unselectedContainerColor = Color.Transparent,
            selectedTextColor = Color(0xFF0B5D3B),
            unselectedTextColor = Color(0xFF1F1F1F),
            selectedIconColor = Color(0xFF0B5D3B),
            unselectedIconColor = Color(0xFF1F1F1F)
        ),
        modifier = Modifier.padding(horizontal = 12.dp)
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InovaDrawerPreview() {

    InovaGABTheme {
        InovaDrawer()
    }
}