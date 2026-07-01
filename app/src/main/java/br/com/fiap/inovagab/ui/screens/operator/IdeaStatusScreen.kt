package br.com.fiap.inovagab.ui.screens.operator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Timeline
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
import br.com.fiap.inovagab.viewmodel.IdeaViewModel
import br.com.fiap.inovagab.data.model.Idea
import br.com.fiap.inovagab.data.model.IdeaStatus
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.offset


data class ProposalStatus(
    val title: String,
    val currentStep: String,
    val responsible: String
)

@Composable
fun IdeaStatusScreen(
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
                homeRoute = "operatorHome",
                profileRoute = "operatorProfile"
            )
        }
    ) {
        Scaffold(
            topBar = {
                InovaTopBar(
                    title = "Status",
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
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {

                Text(
                    text = "Status das propostas",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66)
                )

                Text(
                    text = "Acompanhe em qual etapa estão suas ideias enviadas.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )

                if (ideas.isEmpty()) {
                    EmptyIdeaStatusCard()
                } else {
                    ideas.forEach { idea ->
                        ProposalStatusCard(idea = idea)
                    }
                }
            }
        }
    }
}

@Composable
fun ProposalStatusCard(idea: Idea) {

    val currentStep = when (idea.status) {
        IdeaStatus.PENDING -> "Em análise"
        IdeaStatus.APPROVED -> "Aprovada"
        IdeaStatus.REJECTED -> "Recusada"
    }

    val responsible = when (idea.status) {
        IdeaStatus.PENDING -> "Gestor responsável"
        IdeaStatus.APPROVED -> "Comitê de inovação"
        IdeaStatus.REJECTED -> "Gestor responsável"
    }

    val statusColor = when (idea.status) {
        IdeaStatus.APPROVED -> Color(0xFF4CAF50)
        IdeaStatus.PENDING -> Color(0xFFFF9800)
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
                    imageVector = Icons.Default.Timeline,
                    contentDescription = null,
                    tint = Color(0xFF1F3F66),
                    modifier = Modifier.size(30.dp)
                )

                Text(
                    text = idea.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66),
                    modifier = Modifier.padding(start = 12.dp)
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Etapa atual:",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )

                Spacer(modifier = Modifier.size(8.dp))

                Row(
                    modifier = Modifier
                        .background(
                            color = statusColor.copy(alpha = 0.12f),
                            shape = RoundedCornerShape(50.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(
                        modifier = Modifier
                            .size(8.dp)
                            .background(statusColor, CircleShape)
                    )

                    Text(
                        text = currentStep,
                        color = statusColor,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 6.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Responsável: $responsible",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280)
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProposalTimeline(status = idea.status)
        }
    }
}


@Composable
fun ProposalTimeline(status: IdeaStatus) {

    val steps = when (status) {
        IdeaStatus.PENDING -> listOf(
            "Enviada" to true,
            "Em análise" to true,
            "Concluída" to false
        )

        IdeaStatus.APPROVED -> listOf(
            "Enviada" to true,
            "Em análise" to true,
            "Aprovada" to true
        )

        IdeaStatus.REJECTED -> listOf(
            "Enviada" to true,
            "Em análise" to true,
            "Recusada" to true
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            steps.forEachIndexed { index, step ->

                val isActive = step.second

                Spacer(
                    modifier = Modifier
                        .size(14.dp)
                        .background(
                            color = if (isActive) Color(0xFF1F3F66) else Color(0xFFD1D5DB),
                            shape = CircleShape
                        )
                )

                if (index < steps.lastIndex) {
                    Spacer(
                        modifier = Modifier
                            .height(3.dp)
                            .weight(1f)
                            .background(
                                color = if (isActive && steps[index + 1].second)
                                    Color(0xFF1F3F66)
                                else
                                    Color(0xFFD1D5DB)
                            )
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            steps.forEach { step ->

                val label = step.first
                val isActive = step.second

                Text(
                    text = label,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
                    color = if (isActive) Color(0xFF1F3F66) else Color(0xFF9CA3AF),
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}


@Composable
fun EmptyIdeaStatusCard() {
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Timeline,
                contentDescription = null,
                tint = Color(0xFF9CA3AF),
                modifier = Modifier.size(44.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Nenhuma proposta enviada",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F3F66)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Quando você enviar uma ideia, o status dela aparecerá aqui.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF6B7280)
            )
        }
    }
}

//@Preview(showBackground = true, showSystemUi = true)
//@Composable
//fun IdeaStatusScreenPreview() {
//    InovaGABTheme {
//        IdeaStatusScreen()
//    }
//}
//
