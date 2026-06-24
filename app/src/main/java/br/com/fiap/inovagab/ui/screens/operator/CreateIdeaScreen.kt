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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateIdeaScreen(
    navController: NavController? = null,
    ideaViewModel: IdeaViewModel
) {

    var ideaTitle by remember { mutableStateOf("") }
    var ideaDescription by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }

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
                homeRoute = "operatorHome"
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

                Button(
                    onClick = {
                        val newIdea = Idea(
                            id = (0..9999).random(),
                            title = ideaTitle,
                            description = ideaDescription
                        )

                        ideaViewModel.addIdea(newIdea)

                        ideaTitle = ""
                        ideaDescription = ""

                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Ideia cadastrada com sucesso!"
                            )
                        }
                    },
                    enabled = ideaTitle.isNotBlank() && ideaDescription.isNotBlank(),
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
