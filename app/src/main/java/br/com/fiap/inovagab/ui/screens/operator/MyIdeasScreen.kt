package br.com.fiap.inovagab.ui.screens.operator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import br.com.fiap.inovagab.data.model.Idea
import br.com.fiap.inovagab.viewmodel.IdeaViewModel
import br.com.fiap.inovagab.data.model.IdeaStatus
import br.com.fiap.inovagab.viewmodel.AuthViewModel
import androidx.compose.runtime.LaunchedEffect
import br.com.fiap.inovagab.data.remote.model.IdeaResponse
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.OutlinedTextField

@Composable
fun MyIdeasScreen(
    navController: NavController? = null,
    ideaViewModel: IdeaViewModel,
    authViewModel: AuthViewModel
    ) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val ideas by ideaViewModel.ideas.collectAsState()

    val remoteIdeas by ideaViewModel.remoteIdeas.collectAsState()

    val token = authViewModel.currentUser?.token

    LaunchedEffect(token) {
        if (!token.isNullOrBlank()) {
            ideaViewModel.loadMyIdeas(
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
                homeRoute = "operatorHome",
                profileRoute = "operatorProfile"
            )
        }
    ) {
        Scaffold(
            topBar = {
                InovaTopBar(
                    title = "Minhas Ideias",
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
                        text = "Ideias cadastradas",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F3F66)
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Acompanhe o andamento das suas propostas de inovação.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF6B7280)
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }

                items(remoteIdeas) { idea ->
                    RemoteIdeaCard(
                        idea = idea,

                        onEdit = { selectedIdea ->

                            val currentToken = token

                            if (!currentToken.isNullOrBlank()) {
                                ideaViewModel.updateIdea(
                                    token = currentToken,
                                    id = selectedIdea.id,
                                    title = selectedIdea.titulo,
                                    description = selectedIdea.descricao,
                                    category = selectedIdea.categoria,

                                    onSuccess = {
                                        ideaViewModel.loadMyIdeas(
                                            token = currentToken,
                                            onError = { error ->
                                                println(error)
                                            }
                                        )
                                    },

                                    onError = { error ->
                                        println(error)
                                    }
                                )
                            }
                        },

                        onDelete = { selectedIdea ->

                            val currentToken = token

                            if (!currentToken.isNullOrBlank()) {
                                ideaViewModel.deleteIdea(
                                    token = currentToken,
                                    id = selectedIdea.id,
                                    onSuccess = {
                                        ideaViewModel.loadMyIdeas(
                                            token = currentToken,
                                            onError = { error ->
                                                println(error)
                                            }
                                        )
                                    },
                                    onError = { error ->
                                        println(error)
                                    }
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RemoteIdeaCard(
    idea: IdeaResponse,
    onEdit: (IdeaResponse) -> Unit,
    onDelete: (IdeaResponse) -> Unit) {

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    var showEditDialog by remember {
        mutableStateOf(false)
    }

    var editTitle by remember(idea.id) {
        mutableStateOf(idea.titulo)
    }

    var editDescription by remember(idea.id) {
        mutableStateOf(idea.descricao)
    }

    var editCategory by remember(idea.id) {
        mutableStateOf(idea.categoria)
    }

    val statusText = when (idea.status.lowercase()) {
        "pendente" -> "Em análise"
        "aprovada", "aprovado" -> "Aprovada"
        "rejeitada", "rejeitado" -> "Recusada"
        else -> idea.status
    }

    val statusColor = when (idea.status.lowercase()) {
        "pendente" -> Color(0xFFFF9800)
        "aprovada", "aprovado" -> Color(0xFF4CAF50)
        "rejeitada", "rejeitado" -> Color(0xFFF44336)
        else -> Color(0xFF6B7280)
    }

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
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = Color(0xFF1F3F66),
                    modifier = Modifier.size(28.dp)
                )

                Text(
                    text = idea.titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = idea.descricao,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Categoria: ${idea.categoria}",
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = statusColor,
                            shape = CircleShape
                        )
                )

                Text(
                    text = statusText,
                    color = statusColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Button(
                    onClick = {
                        editTitle = idea.titulo
                        editDescription = idea.descricao
                        editCategory = idea.categoria
                        showEditDialog = true
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1F3F66)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Editar",
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Button(
                    onClick = {
                        showDeleteDialog = true
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF44336)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(
                        text = "Excluir",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }

    if (showEditDialog) {
        AlertDialog(
            onDismissRequest = {
                showEditDialog = false
            },
            title = {
                Text(
                    text = "Editar ideia"
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    OutlinedTextField(
                        value = editTitle,
                        onValueChange = {
                            editTitle = it
                        },
                        label = {
                            Text(text = "Título")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = editDescription,
                        onValueChange = {
                            editDescription = it
                        },
                        label = {
                            Text(text = "Descrição")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = editCategory,
                        onValueChange = {
                            editCategory = it
                        },
                        label = {
                            Text(text = "Categoria")
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showEditDialog = false

                        onEdit(
                            idea.copy(
                                titulo = editTitle,
                                descricao = editDescription,
                                categoria = editCategory
                            )
                        )
                    },
                    enabled =
                        editTitle.isNotBlank() &&
                                editDescription.isNotBlank() &&
                                editCategory.isNotBlank()
                ) {
                    Text(
                        text = "Salvar",
                        color = Color(0xFF1F3F66)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showEditDialog = false
                    }
                ) {
                    Text(
                        text = "Cancelar"
                    )
                }
            }
        )
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = {
                showDeleteDialog = false
            },
            title = {
                Text(
                    text = "Excluir ideia?"
                )
            },
            text = {
                Text(
                    text = "Tem certeza de que deseja excluir esta ideia? Essa ação não poderá ser desfeita."
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                        onDelete(idea)
                    }
                ) {
                    Text(
                        text = "Excluir",
                        color = Color(0xFFF44336)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDeleteDialog = false
                    }
                ) {
                    Text(
                        text = "Cancelar"
                    )
                }
            }
        )
    }
}

@Composable
fun IdeaCard(idea: Idea) {

    val statusText = when (idea.status) {
        IdeaStatus.PENDING -> "Em análise"
        IdeaStatus.APPROVED -> "Aprovada"
        IdeaStatus.REJECTED -> "Recusada"

    }

    val statusColor = when (idea.status) {
        IdeaStatus.PENDING -> Color(0xFFFF9800)
        IdeaStatus.APPROVED -> Color(0xFF4CAF50)
        IdeaStatus.REJECTED -> Color(0xFFF44336)

    }

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
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = Color(0xFF1F3F66),
                    modifier = Modifier.size(28.dp)
                )

                Text(
                    text = idea.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = idea.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(
                            color = statusColor,
                            shape = CircleShape
                        )
                )

                Text(
                    text = statusText,
                    color = statusColor,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun MyIdeasScreenPreview() {
//
//    InovaGABTheme {
//        MyIdeasScreen()
//    }
//}
//
