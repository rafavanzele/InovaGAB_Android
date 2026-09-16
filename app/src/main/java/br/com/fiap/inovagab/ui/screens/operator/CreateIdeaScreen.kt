package br.com.fiap.inovagab.ui.screens.operator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.fiap.inovagab.ui.components.InovaTopBar
import br.com.fiap.inovagab.ui.theme.InovaGABTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import br.com.fiap.inovagab.ui.components.InovaDrawer
import br.com.fiap.inovagab.viewmodel.IdeaViewModel
import br.com.fiap.inovagab.data.model.Idea
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import br.com.fiap.inovagab.viewmodel.AuthViewModel
import androidx.compose.runtime.LaunchedEffect
import br.com.fiap.inovagab.data.remote.model.StrategicGuidance
import br.com.fiap.inovagab.data.remote.network.RetrofitInstance
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateIdeaScreen(
    navController: NavController? = null,
    ideaViewModel: IdeaViewModel,
    authViewModel: AuthViewModel
) {

    var ideaTitle by remember { mutableStateOf("") }
    var ideaDescription by remember { mutableStateOf("") }
    var ideaCategory by remember { mutableStateOf("") }

    var strategicGuidances by remember {
        mutableStateOf<List<StrategicGuidance>>(emptyList())
    }

    var selectedGuidance by remember {
        mutableStateOf<StrategicGuidance?>(null)
    }

    var guidanceMenuExpanded by remember {
        mutableStateOf(false)
    }

    val snackbarHostState = remember { SnackbarHostState() }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    val token = authViewModel.currentUser?.token

    LaunchedEffect(token) {
        if (!token.isNullOrBlank()) {
            try {
                strategicGuidances =
                    RetrofitInstance.api.getStrategicGuidances(
                        authorization = "Bearer $token"
                    )
            } catch (e: Exception) {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = e.message
                            ?: "Não foi possível carregar as diretrizes estratégicas."
                    )
                }
            }
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
                    title = "Nova Ideia",
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

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
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
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = Color(0xFF1F3F66)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "Cadastrar nova ideia",
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F3F66)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Descreva uma sugestão de melhoria, inovação ou oportunidade para a organização.",
                            color = Color(0xFF6B7280)
                        )
                    }
                }

                OutlinedTextField(
                    value = ideaTitle,
                    onValueChange = { ideaTitle = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Título da ideia")
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,

                        focusedLabelColor = Color(0xFF1F3F66),
                        unfocusedLabelColor = Color(0xFF6B7280),

                        cursorColor = Color(0xFF1F3F66),

                        focusedIndicatorColor = Color(0xFF1F3F66),
                        unfocusedIndicatorColor = Color.LightGray,

                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                OutlinedTextField(
                    value = ideaDescription,
                    onValueChange = { ideaDescription = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    label = {
                        Text(text = "Descrição da ideia")
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,

                        focusedLabelColor = Color(0xFF1F3F66),
                        unfocusedLabelColor = Color(0xFF6B7280),

                        cursorColor = Color(0xFF1F3F66),

                        focusedIndicatorColor = Color(0xFF1F3F66),
                        unfocusedIndicatorColor = Color.LightGray,

                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                OutlinedTextField(
                    value = ideaCategory,
                    onValueChange = { ideaCategory = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Categoria")
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color(0xFF1F3F66),
                        unfocusedLabelColor = Color(0xFF6B7280),
                        cursorColor = Color(0xFF1F3F66),
                        focusedIndicatorColor = Color(0xFF1F3F66),
                        unfocusedIndicatorColor = Color.LightGray,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent
                    )
                )

                ExposedDropdownMenuBox(
                    expanded = guidanceMenuExpanded,
                    onExpandedChange = {
                        guidanceMenuExpanded = !guidanceMenuExpanded
                    }
                ) {
                    OutlinedTextField(
                        value = selectedGuidance?.titulo ?: "",
                        onValueChange = {},
                        readOnly = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        label = {
                            Text(text = "Diretriz estratégica")
                        },
                        placeholder = {
                            Text(text = "Selecione uma diretriz")
                        },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = guidanceMenuExpanded
                            )
                        },
                        shape = RoundedCornerShape(16.dp),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            focusedLabelColor = Color(0xFF1F3F66),
                            unfocusedLabelColor = Color(0xFF6B7280),
                            focusedIndicatorColor = Color(0xFF1F3F66),
                            unfocusedIndicatorColor = Color.LightGray,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent
                        )
                    )

                    ExposedDropdownMenu(
                        expanded = guidanceMenuExpanded,
                        onDismissRequest = {
                            guidanceMenuExpanded = false
                        }
                    ) {
                        strategicGuidances.forEach { guidance ->
                            DropdownMenuItem(
                                text = {
                                    Text(text = guidance.titulo)
                                },
                                onClick = {
                                    selectedGuidance = guidance
                                    guidanceMenuExpanded = false
                                }
                            )
                        }
                    }
                }

                Button(
                    onClick = {
                        val currentToken = token
                        val currentGuidance = selectedGuidance

                        if (currentToken.isNullOrBlank()) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Sessão inválida. Faça login novamente."
                                )
                            }
                            return@Button
                        }

                        if (currentGuidance == null) {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Selecione uma diretriz estratégica."
                                )
                            }
                            return@Button
                        }

                        ideaViewModel.createIdea(
                            token = currentToken,
                            title = ideaTitle,
                            description = ideaDescription,
                            category = ideaCategory,
                            strategicGuidanceId = currentGuidance.id,
                            onSuccess = {

                                ideaTitle = ""
                                ideaDescription = ""
                                ideaCategory = ""
                                selectedGuidance = null

                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Ideia cadastrada com sucesso!"
                                    )
                                }
                            },
                            onError = { error ->

                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = error
                                    )
                                }
                            }
                        )
                    },
                    enabled =
                        ideaTitle.isNotBlank() &&
                                ideaDescription.isNotBlank() &&
                                ideaCategory.isNotBlank() &&
                                selectedGuidance != null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1F3F66),
                        disabledContainerColor = Color.LightGray
                    )
                ) {
                    Text(
                        text = "Enviar ideia",
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun CreateIdeaScreenPreview() {
//    InovaGABTheme {
//        CreateIdeaScreen()
//    }
//}
//
