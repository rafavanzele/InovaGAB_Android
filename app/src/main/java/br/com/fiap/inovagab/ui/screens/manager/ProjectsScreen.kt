package br.com.fiap.inovagab.ui.screens.manager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.inovagab.data.model.Project
import br.com.fiap.inovagab.ui.components.InovaDrawer
import br.com.fiap.inovagab.ui.components.InovaTopBar
import br.com.fiap.inovagab.ui.theme.InovaGABTheme
import br.com.fiap.inovagab.ui.viewmodel.ProjectViewModel
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState

@Composable
fun ProjectsScreen(
    navController: NavController? = null,
    viewModel: ProjectViewModel = viewModel()
) {

    val projects by viewModel.projects.collectAsState()

    var showForm by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var responsible by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var investment by remember { mutableStateOf("") }
    var expectedReturn by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            InovaDrawer(
                navController = navController,
                drawerState = drawerState,
                homeRoute = "managerHome",
                profileRoute = "managerProfile",
                notificationsRoute = "managerNotifications"
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

            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
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
                        text = "Projetos em andamento",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F3F66)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Acompanhe a evolução dos projetos ativos.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),

                        shape = RoundedCornerShape(18.dp),

                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1F3F66)
                        ),

                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        ),

                        onClick = {
                            showForm = !showForm
                        }
                    ) {

                        Text(
                            text = if (showForm) "Fechar cadastro" else "Novo projeto",
                            modifier = Modifier.padding(18.dp),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    if (showForm) {

                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(22.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.White
                            )
                        ) {

                            Column(
                                modifier = Modifier.padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                OutlinedTextField(
                                    value = title,
                                    onValueChange = {
                                        title = it
                                    },
                                    label = {
                                        Text("Título")
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                OutlinedTextField(
                                    value = description,
                                    onValueChange = {
                                        description = it
                                    },
                                    label = {
                                        Text("Descrição")
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                OutlinedTextField(
                                    value = responsible,
                                    onValueChange = {
                                        responsible = it
                                    },
                                    label = {
                                        Text("Responsável")
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                OutlinedTextField(
                                    value = deadline,
                                    onValueChange = {
                                        deadline = it
                                    },
                                    label = {
                                        Text("Prazo")
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                OutlinedTextField(
                                    value = investment,
                                    onValueChange = {
                                        investment = it
                                    },
                                    label = {
                                        Text("Investimento")
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                OutlinedTextField(
                                    value = expectedReturn,
                                    onValueChange = {
                                        expectedReturn = it
                                    },
                                    label = {
                                        Text("Retorno previsto")
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                if (showError) {

                                    Text(
                                        text = "Preencha todos os campos obrigatórios.",
                                        color = Color.Red,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Spacer(
                                    modifier = Modifier.height(8.dp)
                                )

                                Card(
                                    modifier = Modifier.fillMaxWidth(),

                                    shape = RoundedCornerShape(16.dp),

                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFF2E7D32)
                                    ),

                                    onClick = {

                                        if (
                                            title.isBlank() ||
                                            description.isBlank() ||
                                            responsible.isBlank() ||
                                            deadline.isBlank() ||
                                            investment.isBlank() ||
                                            expectedReturn.isBlank()
                                        ) {
                                            showError = true
                                            return@Card
                                        }

                                        showError = false

                                        viewModel.addProject(

                                            Project(
                                                id = projects.size + 1,
                                                title = title,
                                                description = description,
                                                responsible = responsible,
                                                status = "Iniciado",
                                                deadline = deadline,
                                                investment = investment,
                                                expectedReturn = expectedReturn,
                                                result = "Em andamento",
                                                progress = 0f
                                            )
                                        )

                                        title = ""
                                        description = ""
                                        responsible = ""
                                        deadline = ""
                                        investment = ""
                                        expectedReturn = ""

                                        showForm = false

                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = "Projeto cadastrado com sucesso!"
                                            )
                                        }
                                    }
                                ) {

                                    Text(
                                        text = "Cadastrar Projeto",
                                        modifier = Modifier.padding(18.dp),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                items(projects) { project ->
                    ProjectCard(project = project)
                }
            }
        }
    }
}

@Composable
fun ProjectCard(
    project: Project
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
                    text = project.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = { project.progress },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "${(project.progress * 100).toInt()}% concluído",
                color = Color(0xFF1F3F66),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Status: ${project.status}",
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Responsável: ${project.responsible}",
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Prazo: ${project.deadline}",
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Investimento: ${project.investment}",
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Retorno previsto: ${project.expectedReturn}",
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Resultado: ${project.result}",
                color = Color(0xFF6B7280)
            )


        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProjectsScreenPreview() {

    InovaGABTheme {
        ProjectsScreen()
    }
}

