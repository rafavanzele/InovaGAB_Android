package br.com.fiap.inovagab.ui.screens.leader

import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.fiap.inovagab.ui.viewmodel.StrategicGuidanceViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import br.com.fiap.inovagab.ui.theme.InovaGABTheme
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import br.com.fiap.inovagab.ui.components.InovaDrawer
import br.com.fiap.inovagab.ui.components.InovaTopBar
import kotlinx.coroutines.launch
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import br.com.fiap.inovagab.data.remote.model.StrategicGuidance
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState

@Composable
fun StrategicGuidanceScreen(
    navController: NavController,
    viewModel: StrategicGuidanceViewModel
) {
    val guidances by viewModel.guidances.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    var showForm by remember { mutableStateOf(false) }
    var editingGuidance by remember { mutableStateOf<StrategicGuidance?>(null) }

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    var guidanceToDelete by remember { mutableStateOf<StrategicGuidance?>(null) }

    val snackbarHostState = remember { SnackbarHostState() }

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
                    title = "Orientações",
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

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                when {
                    isLoading -> {
                        CircularProgressIndicator()
                    }

                    errorMessage != null -> {
                        Text(text = errorMessage ?: "")
                    }

                    guidances.isEmpty() -> {
                        Text(text = "Nenhuma orientação estratégica encontrada.")
                    }

                    else -> {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Button(
                                onClick = {
                                    showForm = true
                                    editingGuidance = null
                                    title = ""
                                    description = ""
                                    category = ""
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = null
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Nova orientação")
                            }

                            if (showForm) {
                                StrategicGuidanceForm(
                                    title = title,
                                    description = description,
                                    category = category,
                                    onTitleChange = { title = it },
                                    onDescriptionChange = { description = it },
                                    onCategoryChange = { category = it },
                                    onSaveClick = {
                                        val guidance = StrategicGuidance(
                                            id = editingGuidance?.id ?: "",
                                            titulo = title,
                                            descricao = description,
                                            categoria = category
                                        )

                                        if (editingGuidance == null) {

                                            viewModel.createGuidance(guidance)

                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    "Orientação cadastrada com sucesso"
                                                )
                                            }

                                        } else {

                                            viewModel.updateGuidance(guidance)

                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    "Orientação atualizada com sucesso"
                                                )
                                            }
                                        }

                                        showForm = false
                                        editingGuidance = null
                                        title = ""
                                        description = ""
                                        category = ""
                                    },
                                    onCancelClick = {
                                        showForm = false
                                        editingGuidance = null
                                        title = ""
                                        description = ""
                                        category = ""
                                    }
                                )
                            }

                            guidances.forEach { guidance ->
                                StrategicGuidanceCard(
                                    title = guidance.titulo,
                                    description = guidance.descricao,
                                    category = guidance.categoria,
                                    onEditClick = {
                                        showForm = true
                                        editingGuidance = guidance
                                        title = guidance.titulo
                                        description = guidance.descricao
                                        category = guidance.categoria
                                    },
                                    onDeleteClick = {
                                        if (guidance.id.isNullOrBlank() || guidance.id.length < 3) {
                                            scope.launch {
                                                snackbarHostState.showSnackbar(
                                                    "Não foi possível excluir esta orientação."
                                                )
                                            }
                                        } else {
                                            guidanceToDelete = guidance
                                        }
                                    }
                                )
                            }
                        }
                    }
                }

                if (guidanceToDelete != null) {
                    AlertDialog(
                        onDismissRequest = {
                            guidanceToDelete = null
                        },
                        title = {
                            Text("Excluir orientação")
                        },
                        text = {
                            Text(
                                "Deseja realmente excluir esta orientação estratégica?"
                            )
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    guidanceToDelete?.let { guidance ->

                                        viewModel.deleteGuidance(guidance.id)

                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "Orientação excluída com sucesso"
                                            )
                                        }
                                    }

                                    guidanceToDelete = null
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFFD32F2F)
                                )
                            ) {
                                Text("Excluir")
                            }
                        },
                        dismissButton = {
                            TextButton(
                                onClick = {
                                    guidanceToDelete = null
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
}

@Composable
fun StrategicGuidanceCard(
    title: String,
    description: String,
    category: String,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = Color(0xFF2E5AAC)
                )

                Spacer(modifier = Modifier.width(16.dp))

                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(text = description)

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = category,
                        color = Color(0xFF2E5AAC),
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Editar")
                }

                TextButton(onClick = onDeleteClick) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Excluir")
                }
            }
        }
    }
}

@Composable
fun StrategicGuidanceForm(
    title: String,
    description: String,
    category: String,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Gerenciar orientação estratégica",
                fontWeight = FontWeight.Bold
            )

            OutlinedTextField(
                value = title,
                onValueChange = onTitleChange,
                label = { Text("Título") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = onDescriptionChange,
                label = { Text("Descrição") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = category,
                onValueChange = onCategoryChange,
                label = { Text("Categoria") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = onCancelClick) {
                    Text("Cancelar")
                }

                Spacer(modifier = Modifier.width(8.dp))

                Button(onClick = onSaveClick) {
                    Text("Salvar")
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun StrategicGuidanceScreenPreview() {
//
//    InovaGABTheme {
//
//        StrategicGuidanceScreen(
//            viewModel = StrategicGuidanceViewModel()
//        )
//    }
//}