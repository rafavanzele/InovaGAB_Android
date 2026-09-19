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
import br.com.fiap.inovagab.viewmodel.AuthViewModel
import androidx.compose.runtime.LaunchedEffect
import br.com.fiap.inovagab.data.remote.model.ProjectResponse
import br.com.fiap.inovagab.ui.viewmodel.StrategicGuidanceViewModel
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.IconButton
import br.com.fiap.inovagab.data.remote.model.CreateProjectRequest
import br.com.fiap.inovagab.data.remote.model.UpdateProjectRequest
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.TextButton
import br.com.fiap.inovagab.ui.viewmodel.RemoteAchievedResultViewModel
import br.com.fiap.inovagab.data.remote.model.CreateAchievedResultRequest
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ProjectsScreen(
    navController: NavController? = null,
    viewModel: ProjectViewModel = viewModel(),
    authViewModel: AuthViewModel,
    strategicGuidanceViewModel: StrategicGuidanceViewModel = viewModel(),
    achievedResultViewModel: RemoteAchievedResultViewModel = viewModel()
) {

    val projects by viewModel.projects.collectAsState()

    val remoteProjects by viewModel.remoteProjects.collectAsState()

    val guidances by strategicGuidanceViewModel.guidances.collectAsState()

    val token = authViewModel.currentUser?.token

    LaunchedEffect(token) {
        if (!token.isNullOrBlank()) {
            viewModel.loadProjects(
                token = token,
                onError = { error ->
                    println(error)
                }
            )

            strategicGuidanceViewModel.loadStrategicGuidances(token)
        }
    }

    var showForm by remember { mutableStateOf(false) }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var responsible by remember { mutableStateOf("") }
    var deadline by remember { mutableStateOf("") }
    var investment by remember { mutableStateOf("") }
    var expectedReturn by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }

    var selectedGuidanceId by remember {
        mutableStateOf("")
    }

    var selectedGuidanceTitle by remember {
        mutableStateOf("")
    }

    var guidanceMenuExpanded by remember {
        mutableStateOf(false)
    }

    var editingProject by remember {
        mutableStateOf<ProjectResponse?>(null)
    }

    var selectedProjectForResult by remember {
        mutableStateOf<ProjectResponse?>(null)
    }

    var resultTitle by remember { mutableStateOf("") }
    var resultDescription by remember { mutableStateOf("") }
    var resultCategory by remember { mutableStateOf("") }
    var resultValue by remember { mutableStateOf("") }
    var resultUnit by remember { mutableStateOf("") }
    var resultDate by remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val listState = rememberLazyListState()


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
        selectedProjectForResult?.let { selectedProject ->

            AlertDialog(
                onDismissRequest = {
                    selectedProjectForResult = null
                },

                title = {
                    Text("Registrar resultado")
                },

                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        Text(
                            text = "Projeto: ${selectedProject.titulo}",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F3F66)
                        )

                        OutlinedTextField(
                            value = resultTitle,
                            onValueChange = { resultTitle = it },
                            label = { Text("Título") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = resultDescription,
                            onValueChange = { resultDescription = it },
                            label = { Text("Descrição") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = resultCategory,
                            onValueChange = { resultCategory = it },
                            label = { Text("Categoria") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = resultValue,
                            onValueChange = { resultValue = it },
                            label = { Text("Valor alcançado") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = resultUnit,
                            onValueChange = { resultUnit = it },
                            label = { Text("Unidade") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = resultDate,
                            onValueChange = { resultDate = it },
                            label = { Text("Data do resultado") },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },

                confirmButton = {
                    Button(
                        onClick = {
                            val value = resultValue.toDoubleOrNull()

                            if (
                                resultTitle.isBlank() ||
                                resultDescription.isBlank() ||
                                resultCategory.isBlank() ||
                                value == null ||
                                resultUnit.isBlank() ||
                                resultDate.isBlank()
                            ) {
                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        "Preencha todos os campos corretamente."
                                    )
                                }
                            } else {
                                val request = CreateAchievedResultRequest(
                                    titulo = resultTitle.trim(),
                                    descricao = resultDescription.trim(),
                                    categoria = resultCategory.trim(),
                                    projetoId = selectedProject.id ?: "",
                                    valorAlcancado = value,
                                    unidade = resultUnit.trim(),
                                    dataResultado = resultDate.trim()
                                )

                                if (!token.isNullOrBlank()) {
                                    achievedResultViewModel.createAchievedResult(
                                        token = token,
                                        request = request,
                                        onSuccess = {
                                            selectedProjectForResult = null

                                            resultTitle = ""
                                            resultDescription = ""
                                            resultCategory = ""
                                            resultValue = ""
                                            resultUnit = ""
                                            resultDate = ""

                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    "Resultado registrado com sucesso."
                                                )
                                            }
                                        },
                                        onError = { error ->
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    error
                                                )
                                            }
                                        }
                                    )
                                }
                            }
                        },
                        enabled = true
                    ) {
                        Text("Registrar")
                    }
                },

                dismissButton = {
                    TextButton(
                        onClick = {
                            selectedProjectForResult = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }


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
                state = listState,
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(18.dp),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
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

                                Box(
                                    modifier = Modifier.fillMaxWidth()
                                ) {


                                    OutlinedTextField(
                                        value = selectedGuidanceTitle,
                                        onValueChange = {},
                                        readOnly = true,
                                        label = {
                                            Text("Diretriz estratégica")
                                        },
                                        placeholder = {
                                            Text("Selecione uma diretriz")
                                        },
                                        trailingIcon = {
                                            IconButton(
                                                onClick = {
                                                    guidanceMenuExpanded = true
                                                }
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Default.ArrowDropDown,
                                                    contentDescription = "Selecionar diretriz"
                                                )
                                            }
                                        },
                                        modifier = Modifier.fillMaxWidth()
                                    )

                                    DropdownMenu(
                                        expanded = guidanceMenuExpanded,
                                        onDismissRequest = {
                                            guidanceMenuExpanded = false
                                        }
                                    ) {

                                        guidances.forEach { guidance ->

                                            DropdownMenuItem(
                                                text = {
                                                    Text(guidance.titulo)
                                                },
                                                onClick = {
                                                    selectedGuidanceId = guidance.id
                                                    selectedGuidanceTitle = guidance.titulo
                                                    guidanceMenuExpanded = false
                                                }
                                            )
                                        }
                                    }
                                }


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
                                            selectedGuidanceId.isBlank() ||
                                            responsible.isBlank() ||
                                            deadline.isBlank() ||
                                            investment.isBlank() ||
                                            expectedReturn.isBlank()
                                        ) {
                                            showError = true
                                            return@Card
                                        }

                                        showError = false

                                        val currentToken = token

                                        if (!currentToken.isNullOrBlank()) {

                                            val currentEditingProject = editingProject

                                            if (currentEditingProject == null) {

                                                viewModel.createRemoteProject(
                                                    token = currentToken,
                                                    request = CreateProjectRequest(
                                                        titulo = title,
                                                        descricao = description,
                                                        diretrizId = selectedGuidanceId,
                                                        responsavel = responsible,
                                                        prazo = deadline,
                                                        investimento = investment,
                                                        investimentoValor = null,
                                                        retornoPrevisto = expectedReturn
                                                    ),
                                                    onSuccess = {

                                                        viewModel.loadProjects(
                                                            token = currentToken,
                                                            onError = { error ->
                                                                println(error)
                                                            }
                                                        )

                                                        title = ""
                                                        description = ""
                                                        responsible = ""
                                                        deadline = ""
                                                        investment = ""
                                                        expectedReturn = ""
                                                        selectedGuidanceId = ""
                                                        selectedGuidanceTitle = ""
                                                        editingProject = null

                                                        showForm = false

                                                        scope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Projeto cadastrado com sucesso!"
                                                            )
                                                        }
                                                    },
                                                    onError = { error ->
                                                        println(error)
                                                    }
                                                )

                                            } else {

                                                viewModel.updateRemoteProject(
                                                    token = currentToken,
                                                    id = currentEditingProject.id,
                                                    request = UpdateProjectRequest(
                                                        titulo = title,
                                                        descricao = description,
                                                        responsavel = responsible,
                                                        prazo = deadline,
                                                        investimento = investment,
                                                        investimentoValor = currentEditingProject.investimentoValor,
                                                        retornoPrevisto = expectedReturn,
                                                        status = currentEditingProject.status,
                                                        etapa = currentEditingProject.etapa,
                                                        progresso = currentEditingProject.progresso
                                                    ),
                                                    onSuccess = {

                                                        viewModel.loadProjects(
                                                            token = currentToken,
                                                            onError = { error ->
                                                                println(error)
                                                            }
                                                        )

                                                        title = ""
                                                        description = ""
                                                        responsible = ""
                                                        deadline = ""
                                                        investment = ""
                                                        expectedReturn = ""
                                                        selectedGuidanceId = ""
                                                        selectedGuidanceTitle = ""
                                                        editingProject = null

                                                        showForm = false

                                                        scope.launch {
                                                            snackbarHostState.showSnackbar(
                                                                message = "Projeto atualizado com sucesso!"
                                                            )
                                                        }
                                                    },
                                                    onError = { error ->
                                                        println(error)
                                                    }
                                                )
                                            }
                                        }
                                    }
                                ) {

                                    Text(
                                        text = if (editingProject == null) {
                                            "Cadastrar Projeto"
                                        } else {
                                            "Salvar alterações"
                                        },
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(18.dp),
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }

                items(remoteProjects) { project ->
                    ProjectCard(
                        project = project,
                        onEdit = { selectedProject ->

                            editingProject = selectedProject

                            title = selectedProject.titulo
                            description = selectedProject.descricao
                            responsible = selectedProject.responsavel
                            deadline = selectedProject.prazo
                            investment = selectedProject.investimento
                            expectedReturn = selectedProject.retornoPrevisto

                            selectedGuidanceId = selectedProject.diretrizId

                            selectedGuidanceTitle =
                                guidances.firstOrNull {
                                    it.id == selectedProject.diretrizId
                                }?.titulo ?: ""

                            showForm = true

                            scope.launch {
                                listState.animateScrollToItem(0)
                            }
                        },
                        onRegisterResult = { selectedProject ->
                            selectedProjectForResult = selectedProject
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ProjectCard(
    project: ProjectResponse,
    onEdit: (ProjectResponse) -> Unit,
    onRegisterResult: (ProjectResponse) -> Unit
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

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = project.descricao,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LinearProgressIndicator(
                progress = { project.progresso / 100f },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "${project.progresso.toInt()}% concluído",
                color = Color(0xFF1F3F66),
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Status: ${project.status}",
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
                    onEdit(project)
                }
            ) {
                Text(
                    text = "Editar projeto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFFE8EEF7)
                ),
                onClick = {
                    onRegisterResult(project)
                }
            ) {
                Text(
                    text = "Registrar resultado",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    color = Color(0xFF1F3F66),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

        }
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun ProjectsScreenPreview() {
//
//    InovaGABTheme {
//        ProjectsScreen()
//    }
//}
//
