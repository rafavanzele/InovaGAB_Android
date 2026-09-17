package br.com.fiap.inovagab.ui.screens.manager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.inovagab.ui.components.InovaTopBar
import br.com.fiap.inovagab.ui.theme.InovaGABTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import br.com.fiap.inovagab.ui.components.InovaDrawer
import br.com.fiap.inovagab.data.mock.TeamMock
import br.com.fiap.inovagab.data.model.Team
import br.com.fiap.inovagab.viewmodel.TeamViewModel
import br.com.fiap.inovagab.viewmodel.AuthViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import br.com.fiap.inovagab.data.remote.model.TeamResponse
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.OutlinedTextField
import br.com.fiap.inovagab.data.remote.model.CreateTeamRequest
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton

@Composable
fun TeamsScreen(
    navController: NavController? = null,
    teamViewModel: TeamViewModel,
    authViewModel: AuthViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var showForm by remember {
        mutableStateOf(false)
    }

    var name by remember {
        mutableStateOf("")
    }

    var description by remember {
        mutableStateOf("")
    }

    var responsible by remember {
        mutableStateOf("")
    }

    var members by remember {
        mutableStateOf("")
    }

    var projectId by remember {
        mutableStateOf("")
    }

    var showError by remember {
        mutableStateOf(false)
    }

    var editingTeam by remember {
        mutableStateOf<TeamResponse?>(null)
    }

    var teamToDelete by remember {
        mutableStateOf<TeamResponse?>(null)
    }

    val teams by teamViewModel.teams.collectAsState()

    val token = authViewModel.currentUser?.token

    LaunchedEffect(token) {
        if (!token.isNullOrBlank()) {
            teamViewModel.loadTeams(
                token = token,
                onError = { error ->
                    println(error)
                }
            )
        }
    }


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
                    title = "Equipes",
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
                        text = "Equipes e colaboradores",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F3F66)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Acompanhe participação e desempenho das equipes.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280)
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color(0xFF1F3F66)
                        ),
                        onClick = {
                            showForm = true
                        }
                    ) {
                        Text(
                            text = "Nova equipe",
                            modifier = Modifier.padding(16.dp),
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                if (showForm) {

                    item {

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
                                    value = name,
                                    onValueChange = {
                                        name = it
                                    },
                                    label = {
                                        Text("Nome da equipe")
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
                                    value = members,
                                    onValueChange = {
                                        members = it
                                    },
                                    label = {
                                        Text("Membros")
                                    },
                                    placeholder = {
                                        Text("Ex.: Operador A, Operador B")
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )

                                OutlinedTextField(
                                    value = projectId,
                                    onValueChange = {
                                        projectId = it
                                    },
                                    label = {
                                        Text("ID do projeto (opcional)")
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

                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color(0xFF1F3F66)
                                    ),
                                    onClick = {
                                        if (
                                            name.isBlank() ||
                                            description.isBlank() ||
                                            responsible.isBlank() ||
                                            members.isBlank()
                                        ) {
                                            showError = true
                                        } else {
                                            showError = false

                                            val currentToken = token

                                            if (!currentToken.isNullOrBlank()) {

                                                val memberList = members
                                                    .split(",")
                                                    .map { it.trim() }
                                                    .filter { it.isNotBlank() }

                                                val request = CreateTeamRequest(
                                                    nome = name,
                                                    descricao = description,
                                                    responsavel = responsible,
                                                    membros = memberList,
                                                    projetoId = projectId.ifBlank { null }
                                                )

                                                val currentEditingTeam = editingTeam

                                                if (currentEditingTeam == null) {

                                                    teamViewModel.createTeam(
                                                        token = currentToken,
                                                        request = request,
                                                        onSuccess = {

                                                            teamViewModel.loadTeams(
                                                                token = currentToken,
                                                                onError = { error ->
                                                                    println(error)
                                                                }
                                                            )

                                                            name = ""
                                                            description = ""
                                                            responsible = ""
                                                            members = ""
                                                            projectId = ""
                                                            editingTeam = null

                                                            showForm = false
                                                        },
                                                        onError = { error ->
                                                            println(error)
                                                        }
                                                    )

                                                } else {

                                                    teamViewModel.updateTeam(
                                                        token = currentToken,
                                                        id = currentEditingTeam.id,
                                                        request = request,
                                                        onSuccess = {

                                                            teamViewModel.loadTeams(
                                                                token = currentToken,
                                                                onError = { error ->
                                                                    println(error)
                                                                }
                                                            )

                                                            name = ""
                                                            description = ""
                                                            responsible = ""
                                                            members = ""
                                                            projectId = ""
                                                            editingTeam = null

                                                            showForm = false
                                                        },
                                                        onError = { error ->
                                                            println(error)
                                                        }
                                                    )
                                                }
                                            }
                                        }
                                    }
                                ) {
                                    Text(
                                        text = if (editingTeam == null) {
                                            "Cadastrar equipe"
                                        } else {
                                            "Salvar alterações"
                                        },
                                        modifier = Modifier.padding(16.dp),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }

                items(teams) { team ->
                    TeamCard(
                        team = team,
                        onEdit = { selectedTeam ->

                            editingTeam = selectedTeam

                            name = selectedTeam.nome
                            description = selectedTeam.descricao
                            responsible = selectedTeam.responsavel
                            members = selectedTeam.membros.joinToString(", ")
                            projectId = selectedTeam.projetoId ?: ""

                            showForm = true
                        },
                        onDelete = { selectedTeam ->
                            teamToDelete = selectedTeam
                        }
                    )
                }
            }

            if (teamToDelete != null) {

                AlertDialog(
                    onDismissRequest = {
                        teamToDelete = null
                    },
                    title = {
                        Text("Confirmar exclusão")
                    },
                    text = {
                        Text(
                            "Deseja realmente excluir a equipe \"${teamToDelete?.nome}\"?"
                        )
                    },
                    confirmButton = {
                        TextButton(
                            onClick = {
                                val currentTeam = teamToDelete
                                val currentToken = token

                                if (
                                    currentTeam != null &&
                                    !currentToken.isNullOrBlank()
                                ) {

                                    teamViewModel.deleteTeam(
                                        token = currentToken,
                                        id = currentTeam.id,
                                        onSuccess = {

                                            teamViewModel.loadTeams(
                                                token = currentToken,
                                                onError = { error ->
                                                    println(error)
                                                }
                                            )

                                            teamToDelete = null
                                        },
                                        onError = { error ->
                                            println(error)
                                        }
                                    )
                                }
                            }
                        ) {
                            Text("Excluir")
                        }
                    },
                    dismissButton = {
                        TextButton(
                            onClick = {
                                teamToDelete = null
                            }
                        ) {
                            Text("Cancelar")
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun TeamCard(
    team: TeamResponse,
    onEdit: (TeamResponse) -> Unit,
    onDelete: (TeamResponse) -> Unit
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
                    imageVector = Icons.Default.Groups,
                    contentDescription = null,
                    tint = Color(0xFF1F3F66),
                    modifier = Modifier.size(30.dp)
                )

                Text(
                    text = team.nome,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Colaboradores: ${team.membros.joinToString(", ")}",
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                    text = "Responsável: ${team.responsavel}",
                    color = Color(0xFF6B7280)
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF1F3F66)
                ),
                onClick = {
                    onEdit(team)
                }
            ) {
                Text(
                    text = "Editar equipe",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFD32F2F)
                ),
                onClick = {
                    onDelete(team)
                }
            ) {
                Text(
                    text = "Excluir equipe",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun TeamsScreenPreview() {
//    InovaGABTheme {
//        TeamsScreen()
//    }
//}

