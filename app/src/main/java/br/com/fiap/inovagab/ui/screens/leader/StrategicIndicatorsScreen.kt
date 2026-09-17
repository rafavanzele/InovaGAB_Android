package br.com.fiap.inovagab.ui.screens.leader

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.TrendingUp
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.fiap.inovagab.ui.components.InovaTopBar
import br.com.fiap.inovagab.ui.theme.InovaGABTheme
import androidx.navigation.NavController
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import br.com.fiap.inovagab.ui.components.InovaDrawer
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.inovagab.data.remote.model.StrategicIndicator
import br.com.fiap.inovagab.ui.viewmodel.StrategicIndicatorViewModel
import br.com.fiap.inovagab.viewmodel.AuthViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ButtonDefaults
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import br.com.fiap.inovagab.data.remote.model.CreateStrategicIndicatorRequest
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.TextButton

@Composable
fun StrategicIndicatorsScreen(
    navController: NavController,
    viewModel: StrategicIndicatorViewModel = viewModel(),
    authViewModel: AuthViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val indicators by viewModel.indicators.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val token = authViewModel.currentUser?.token

    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    var valorAtual by remember { mutableStateOf("") }
    var meta by remember { mutableStateOf("") }
    var unidade by remember { mutableStateOf("") }

    var editingIndicator by remember {
        mutableStateOf<StrategicIndicator?>(null)
    }

    var indicatorToDelete by remember {
        mutableStateOf<StrategicIndicator?>(null)
    }

    LaunchedEffect(token) {
        if (!token.isNullOrBlank()) {
            viewModel.loadIndicators(token)
        }
    }

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
                    title = "Indicadores",
                    onMenuClick = {
                        scope.launch {
                            drawerState.open()
                        }
                    }
                )
            },
            containerColor = Color(0xFFF5F7FB)
        ) { paddingValues ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Text(
                    text = "Novo indicador estratégico",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66)
                )

                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = descricao,
                    onValueChange = { descricao = it },
                    label = { Text("Descrição") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = valorAtual,
                    onValueChange = { valorAtual = it },
                    label = { Text("Valor atual") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = meta,
                    onValueChange = { meta = it },
                    label = { Text("Meta") },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal
                    ),
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = unidade,
                    onValueChange = { unidade = it },
                    label = { Text("Unidade") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {

                        val valorAtualDouble =
                            valorAtual.replace(",", ".").toDoubleOrNull()

                        val metaDouble =
                            meta.replace(",", ".").toDoubleOrNull()

                        if (
                            titulo.length >= 3 &&
                            descricao.length >= 10 &&
                            valorAtualDouble != null &&
                            valorAtualDouble >= 0 &&
                            metaDouble != null &&
                            metaDouble > 0 &&
                            unidade.isNotBlank() &&
                            !token.isNullOrBlank()
                        ) {

                            val request = CreateStrategicIndicatorRequest(
                                titulo = titulo,
                                descricao = descricao,
                                valorAtual = valorAtualDouble,
                                meta = metaDouble,
                                unidade = unidade
                            )

                            if (editingIndicator == null) {

                                viewModel.createIndicator(
                                    token = token,
                                    request = request,
                                    onSuccess = {
                                        titulo = ""
                                        descricao = ""
                                        valorAtual = ""
                                        meta = ""
                                        unidade = ""
                                    },
                                    onError = { error ->
                                        println(error)
                                    }
                                )

                            } else {

                                viewModel.updateIndicator(
                                    token = token,
                                    id = editingIndicator!!.id,
                                    request = request,
                                    onSuccess = {
                                        editingIndicator = null
                                        titulo = ""
                                        descricao = ""
                                        valorAtual = ""
                                        meta = ""
                                        unidade = ""
                                    },
                                    onError = { error ->
                                        println(error)
                                    }
                                )
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1F3F66)
                    )
                ) {
                    Text(
                        if (editingIndicator == null) {
                            "Salvar indicador"
                        } else {
                            "Atualizar indicador"
                        }
                    )
                }

                when {
                    isLoading -> {
                        Text(text = "Carregando indicadores...")
                    }

                    errorMessage != null -> {
                        Text(text = errorMessage ?: "")
                    }

                    indicators.isEmpty() -> {
                        Text(text = "Nenhum indicador encontrado.")
                    }

                    else -> {
                        indicators.forEach { indicator ->

                            StrategicIndicatorCard(
                                title = indicator.titulo,
                                value = "${indicator.valorAtual} ${indicator.unidade}",
                                description = indicator.descricao,
                                icon = Icons.Default.Analytics,
                                onEditClick = {
                                    editingIndicator = indicator

                                    titulo = indicator.titulo
                                    descricao = indicator.descricao
                                    valorAtual = indicator.valorAtual.toString()
                                    meta = indicator.meta.toString()
                                    unidade = indicator.unidade
                                },
                                onDeleteClick = {
                                    indicatorToDelete = indicator
                                }
                            )
                        }
                    }
                }
            }
        }

        indicatorToDelete?.let { indicator ->

            AlertDialog(
                onDismissRequest = {
                    indicatorToDelete = null
                },
                title = {
                    Text("Excluir indicador")
                },
                text = {
                    Text(
                        "Tem certeza que deseja excluir o indicador \"${indicator.titulo}\"?"
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (!token.isNullOrBlank()) {
                                viewModel.deleteIndicator(
                                    token = token,
                                    id = indicator.id,
                                    onSuccess = {
                                        indicatorToDelete = null
                                    },
                                    onError = { error ->
                                        println(error)
                                        indicatorToDelete = null
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
                            indicatorToDelete = null
                        }
                    ) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@Composable
fun StrategicIndicatorCard(
    title: String,
    value: String,
    description: String,
    icon: ImageVector,
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

        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = Color(0xFF2E5AAC)
            )

            Spacer(
                modifier = Modifier.width(16.dp)
            )

            Column {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF2E5AAC),
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = description
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
                ) {
                    Button(
                        onClick = onEditClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1F3F66)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Editar indicador"
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text("Editar")
                    }

                    Button(
                        onClick = onDeleteClick,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1F3F66)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Excluir indicador"
                        )

                        Spacer(
                            modifier = Modifier.width(8.dp)
                        )

                        Text("Excluir")
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun StrategicIndicatorsScreenPreview() {
//
//    InovaGABTheme {
//        StrategicIndicatorsScreen()
//    }
//}