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
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.PieChart
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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import br.com.fiap.inovagab.ui.components.InovaDrawer
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.fiap.inovagab.ui.viewmodel.ExecutiveReportViewModel
import br.com.fiap.inovagab.viewmodel.AuthViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import java.text.NumberFormat
import java.util.Locale
import br.com.fiap.inovagab.data.remote.model.ProjectReportResult
import br.com.fiap.inovagab.data.remote.model.StrategyReportResult

@Composable
fun ExecutiveReportsScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val viewModel: ExecutiveReportViewModel = viewModel()

    val executiveReport by viewModel.executiveReport.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    val token = authViewModel.currentUser?.token

    val currencyFormatter = NumberFormat.getCurrencyInstance(
        Locale("pt", "BR")
    )

    LaunchedEffect(token) {
        if (!token.isNullOrBlank()) {
            viewModel.loadExecutiveReport(token)
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
                    title = "Relatórios",
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
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                when {
                    isLoading -> {
                        Text("Carregando relatório executivo...")
                    }

                    errorMessage != null -> {
                        Text(errorMessage ?: "")
                    }

                    executiveReport == null -> {
                        Text("Nenhum relatório disponível.")
                    }

                    else -> {
                        val report = executiveReport!!

                        ExecutiveReportCard(
                            title = "Projetos",
                            value = report.totalProjetos.toString(),
                            description = "${report.projetosEmAndamento} em andamento • ${report.projetosConcluidos} concluídos",
                            icon = Icons.Default.Assessment
                        )

                        ExecutiveReportCard(
                            title = "Investimento total",
                            value = currencyFormatter.format(report.investimentoTotalProjetos),
                            description = "Investimento acumulado nos projetos.",
                            icon = Icons.Default.Description
                        )

                        ExecutiveReportCard(
                            title = "Retorno financeiro",
                            value = currencyFormatter.format(report.retornoFinanceiroTotal),
                            description = "Retorno financeiro total dos projetos.",
                            icon = Icons.Default.BarChart
                        )

                        ExecutiveReportCard(
                            title = "Lucro obtido",
                            value = currencyFormatter.format(report.lucroObtido),
                            description = "Diferença consolidada entre retorno e investimento.",
                            icon = Icons.Default.BarChart
                        )

                        ExecutiveReportCard(
                            title = "ROI",
                            value = String.format(Locale("pt", "BR"),"%.2f%%",report.roiPercentual ?: 0.0),
                            description = "Retorno sobre o investimento.",
                            icon = Icons.Default.PieChart
                        )

                        ExecutiveReportCard(
                            title = "Produtividade",
                            value = String.format(Locale("pt", "BR"),"%.2f%%",report.mediaAumentoProdutividadePercentual ?: 0.0),
                            description = "Média de aumento de produtividade registrada.",
                            icon = Icons.Default.Assessment
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = "Resultados por projeto",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F3F66)
                        )

                        if (report.resultadosPorProjeto.isEmpty()) {

                            Text(
                                text = "Nenhum resultado por projeto disponível.",
                                color = Color(0xFF6B7280)
                            )

                        } else {

                            report.resultadosPorProjeto.forEach { project ->

                                ProjectResultCard(
                                    project = project
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Resultados por estratégia",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF1F3F66)
                        )

                        if (report.resultadosPorEstrategia.isEmpty()) {

                            Text(
                                text = "Nenhum resultado por estratégia disponível.",
                                color = Color(0xFF6B7280)
                            )

                        } else {

                            report.resultadosPorEstrategia.forEach { strategy ->

                                StrategyResultCard(
                                    strategy = strategy
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ExecutiveReportCard(
    title: String,
    value: String,
    description: String,
    icon: ImageVector
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

            Spacer(modifier = Modifier.width(16.dp))

            Column {

                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF2E5AAC),
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = description
                )
            }
        }
    }
}

fun getExecutiveReportIcon(iconName: String): ImageVector {
    return when(iconName) {

        "growth" -> Icons.Default.BarChart
        "approval" -> Icons.Default.Assessment
        "savings" -> Icons.Default.Description
        "satisfaction" -> Icons.Default.PieChart

        else -> Icons.Default.Description
    }
}

@Composable
fun ProjectResultCard(
    project: ProjectReportResult
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = project.projetoTitulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F3F66)
            )

            Text(
                text = "Status: ${project.status}",
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Progresso: ${project.progresso}%",
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

            if (project.resultados.isNotEmpty()) {

                Spacer(
                    modifier = Modifier.height(4.dp)
                )

                Text(
                    text = "Resultados alcançados",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1F3F66)
                )

                project.resultados.forEach { result ->

                    Text(
                        text = "• ${result.titulo}: ${result.valorAlcancado} ${result.unidade}",
                        color = Color(0xFF6B7280)
                    )
                }
            }
        }
    }
}

@Composable
fun StrategyResultCard(
    strategy: StrategyReportResult
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Text(
                text = strategy.diretrizTitulo,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1F3F66)
            )

            if (strategy.categoria.isNotBlank()) {
                Text(
                    text = "Categoria: ${strategy.categoria}",
                    color = Color(0xFF6B7280)
                )
            }

            if (strategy.campanha.isNotBlank()) {
                Text(
                    text = "Campanha: ${strategy.campanha}",
                    color = Color(0xFF6B7280)
                )
            }

            Text(
                text = "Projetos vinculados: ${strategy.totalProjetos}",
                color = Color(0xFF6B7280)
            )

            Text(
                text = "Resultados registrados: ${strategy.totalResultados}",
                color = Color(0xFF6B7280)
            )
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ExecutiveReportsScreenPreview() {
//    InovaGABTheme {
//        ExecutiveReportsScreen()
//    }
//}

