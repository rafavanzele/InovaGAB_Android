package br.com.fiap.inovagab.ui.screens.leader

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.fiap.inovagab.data.model.ContributorRanking
import br.com.fiap.inovagab.ui.viewmodel.ContributorRankingViewModel
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import br.com.fiap.inovagab.ui.components.InovaDrawer
import br.com.fiap.inovagab.ui.components.InovaTopBar
import kotlinx.coroutines.launch
import androidx.compose.material.icons.filled.TrendingUp

@Composable
fun ContributorRankingScreen(
    navController: NavController,
    viewModel: ContributorRankingViewModel
) {
    val ranking by viewModel.ranking.collectAsState()

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
                homeRoute = "leaderHome",
                profileRoute = "leaderProfile",
                notificationsRoute = "leaderNotifications"
            )
        }
    ) {
        Scaffold(
            topBar = {
                InovaTopBar(
                    title = "Ranking de Inovadores",
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
                    .verticalScroll(rememberScrollState())
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Ranking de Inovadores",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66)
                )

                Text(
                    text = "Reconhecimento dos colaboradores que mais contribuíram com ideias aprovadas.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF6B7280)
                )

                RankingSummaryCard(ranking = ranking)

                ranking.forEachIndexed { index, contributor ->
                    ContributorRankingCard(
                        position = index + 1,
                        contributor = contributor
                    )
                }
            }
        }
    }
}

@Composable
fun ContributorRankingCard(
    position: Int,
    contributor: ContributorRanking
) {
    val medalColor = when (position) {
        1 -> Color(0xFFFFC107)
        2 -> Color(0xFFB0BEC5)
        3 -> Color(0xFFCD7F32)
        else -> Color(0xFF2E5AAC)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(medalColor.copy(alpha = 0.18f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = medalColor,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column {
                    Text(
                        text = "${position}º lugar",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = medalColor
                    )

                    Text(
                        text = contributor.nome,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF1F3F66)
                    )

                    Text(
                        text = contributor.cargo,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color(0xFF6B7280)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                RankingMetric(
                    icon = Icons.Default.Lightbulb,
                    label = "Enviadas",
                    value = contributor.ideiasEnviadas.toString(),
                    color = Color(0xFF2E5AAC)
                )

                RankingMetric(
                    icon = Icons.Default.Star,
                    label = "Aprovadas",
                    value = contributor.ideiasAprovadas.toString(),
                    color = Color(0xFF4CAF50)
                )
            }

            BadgeChip(contributor.badge)
        }
    }
}

@Composable
fun RankingMetric(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(22.dp)
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F3F66)
            )

            Text(
                text = label,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF6B7280)
            )
        }
    }
}

@Composable
fun RankingSummaryCard(
    ranking: List<ContributorRanking>
) {
    val totalIdeiasEnviadas = ranking.sumOf { it.ideiasEnviadas }
    val totalIdeiasAprovadas = ranking.sumOf { it.ideiasAprovadas }

    val taxaAprovacao = if (totalIdeiasEnviadas > 0) {
        (totalIdeiasAprovadas * 100) / totalIdeiasEnviadas
    } else {
        0
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF1F3F66)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Inovação em números",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SummaryMetric(
                    icon = Icons.Default.Lightbulb,
                    value = totalIdeiasEnviadas.toString(),
                    label = "Enviadas"
                )

                SummaryMetric(
                    icon = Icons.Default.Star,
                    value = totalIdeiasAprovadas.toString(),
                    label = "Aprovadas"
                )

                SummaryMetric(
                    icon = Icons.Default.TrendingUp,
                    value = "$taxaAprovacao%",
                    label = "Aprovação"
                )
            }
        }
    }
}

@Composable
fun SummaryMetric(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(26.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = Color.White.copy(alpha = 0.75f)
        )
    }
}

@Composable
fun BadgeChip(
    badge: String
) {

    val (emoji, backgroundColor) = when (badge) {

        "Top Inovadora" ->
            Pair("🏆", Color(0xFFFFF3CD))

        "Ideias de Impacto" ->
            Pair("🚀", Color(0xFFD6ECFF))

        else ->
            Pair("🤝", Color(0xFFE8F5E9))
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50.dp))
            .background(backgroundColor)
            .padding(
                horizontal = 14.dp,
                vertical = 8.dp
            )
    ) {

        Text(
            text = "$emoji $badge",
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFF1F3F66)
        )
    }
}