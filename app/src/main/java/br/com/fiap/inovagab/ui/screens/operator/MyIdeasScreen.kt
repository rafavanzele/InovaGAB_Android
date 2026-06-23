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


@Composable
fun MyIdeasScreen(
    navController: NavController? = null,
    ideaViewModel: IdeaViewModel
    ) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val ideas by ideaViewModel.ideas.collectAsState()

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

                items(ideas) { idea ->
                    IdeaCard(idea = idea)
                }
            }
        }
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
