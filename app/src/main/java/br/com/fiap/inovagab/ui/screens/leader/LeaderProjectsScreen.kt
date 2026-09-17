package br.com.fiap.inovagab.ui.screens.leader

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.inovagab.ui.viewmodel.ProjectViewModel
import br.com.fiap.inovagab.viewmodel.AuthViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.fiap.inovagab.ui.components.InovaDrawer
import br.com.fiap.inovagab.ui.components.InovaTopBar
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import br.com.fiap.inovagab.data.remote.model.ProjectResponse
import androidx.compose.foundation.lazy.items

@Composable
fun LeaderProjectsScreen(
    navController: NavController,
    projectViewModel: ProjectViewModel = viewModel(),
    authViewModel: AuthViewModel
) {

    val projects by projectViewModel.remoteProjects.collectAsState()

    val token = authViewModel.currentUser?.token

    LaunchedEffect(token) {
        if (!token.isNullOrBlank()) {
            projectViewModel.loadProjects(
                token = token,
                onError = { error ->
                    println(error)
                }
            )
        }
    }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            InovaDrawer(
                navController = navController,
                drawerState = drawerState,
                homeRoute = "leaderHome",
                profileRoute = "leaderProfile",
                notificationsRoute = "leaderNotifications"
            )
        }
    ) {

        Scaffold(
            topBar = {
                InovaTopBar(
                    title = "Projetos",
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            containerColor = Color(0xFFF5F7FB)
        ) { paddingValues ->

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                item {
                    Text(
                        text = "Andamento dos projetos",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F3F66)
                    )

                    Text(
                        text = "Acompanhe o progresso e os resultados dos projetos de inovação.",
                        color = Color(0xFF6B7280),
                        modifier = Modifier.padding(top = 6.dp)
                    )
                }

                items(projects) { project ->
                    LeaderProjectCard(
                        project = project
                    )
                }
            }
        }
    }
}

@Composable
fun LeaderProjectCard(
    project: ProjectResponse
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        )
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Assignment,
                    contentDescription = null,
                    tint = Color(0xFF1F3F66)
                )

                Spacer(
                    modifier = Modifier.width(12.dp)
                )

                Text(
                    text = project.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66)
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = project.descricao,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            LinearProgressIndicator(
                progress = { project.progresso / 100f },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            Text(
                text = "${project.progresso.toInt()}% concluído",
                color = Color(0xFF1F3F66),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Text(
                text = "Status: ${project.status}",
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Etapa: ${project.etapa}",
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Responsável: ${project.responsavel}",
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Prazo: ${project.prazo}",
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Investimento: ${project.investimento}",
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Retorno previsto: ${project.retornoPrevisto}",
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Resultado: ${project.resultado}",
                color = Color(0xFF6B7280)
            )
        }
    }
}