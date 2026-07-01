package br.com.fiap.inovagab.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.fiap.inovagab.ui.screens.auth.LoginScreen
import br.com.fiap.inovagab.ui.screens.leader.AchievedResultsScreen
import br.com.fiap.inovagab.ui.screens.leader.ExecutiveReportsScreen
import br.com.fiap.inovagab.ui.screens.leader.LeaderHomeScreen
import br.com.fiap.inovagab.ui.screens.leader.StrategicGuidanceScreen
import br.com.fiap.inovagab.ui.screens.leader.StrategicIndicatorsScreen
import br.com.fiap.inovagab.ui.screens.leader.TeamEngagementScreen
import br.com.fiap.inovagab.ui.screens.manager.ApprovalsScreen
import br.com.fiap.inovagab.ui.screens.manager.ManagerHomeScreen
import br.com.fiap.inovagab.ui.screens.manager.PendingIdeasScreen
import br.com.fiap.inovagab.ui.screens.manager.ProjectsScreen
import br.com.fiap.inovagab.ui.screens.manager.TeamsScreen
import br.com.fiap.inovagab.ui.screens.operator.CreateIdeaScreen
import br.com.fiap.inovagab.ui.screens.operator.IdeaStatusScreen
import br.com.fiap.inovagab.ui.screens.operator.MyIdeasScreen
import br.com.fiap.inovagab.ui.screens.operator.OperatorHomeScreen
import br.com.fiap.inovagab.ui.viewmodel.StrategicGuidanceViewModel
import br.com.fiap.inovagab.viewmodel.IdeaViewModel
import br.com.fiap.inovagab.ui.screens.leader.ContributorRankingScreen
import br.com.fiap.inovagab.ui.viewmodel.ContributorRankingViewModel
import br.com.fiap.inovagab.viewmodel.AuthViewModel
import br.com.fiap.inovagab.ui.screens.common.ProfileScreen
import br.com.fiap.inovagab.ui.screens.common.NotificationsScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val ideaViewModel: IdeaViewModel = viewModel()
    val authViewModel: AuthViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            LoginScreen(
                navController = navController,
                authViewModel = authViewModel
                )
        }

        composable("profile") {
            ProfileScreen(
                navController = navController,
                authViewModel = authViewModel
            )
        }

        composable("notifications") {
            NotificationsScreen(
                navController = navController
            )
        }

        composable("operatorHome") {
            OperatorHomeScreen(navController = navController)
        }

        composable("managerHome") {
            ManagerHomeScreen(navController = navController)
        }

        composable("leaderHome") {
            LeaderHomeScreen(navController = navController)
        }

        composable("createIdea") {
            CreateIdeaScreen(
                navController = navController,
                ideaViewModel = ideaViewModel
            )
        }

        composable("myIdeas") {
            MyIdeasScreen(
                navController = navController,
                ideaViewModel = ideaViewModel
            )
        }

        composable("ideaStatus") {
            IdeaStatusScreen(
                navController = navController,
                ideaViewModel = ideaViewModel
            )
        }

        composable("pendingIdeas") {
            PendingIdeasScreen(
                navController = navController,
                ideaViewModel = ideaViewModel
            )
        }

        composable("projects") {
            ProjectsScreen(navController = navController)
        }

        composable("approvals") {
            ApprovalsScreen(
                navController = navController,
                ideaViewModel = ideaViewModel
            )
        }

        composable("teams") {
            TeamsScreen(navController = navController)
        }

        composable("strategicIndicators") {
            StrategicIndicatorsScreen(navController = navController)
        }

        composable("achievedResults") {
            AchievedResultsScreen(navController = navController)
        }

        composable("teamEngagement") {
            TeamEngagementScreen(navController = navController)
        }

        composable("contributorRanking") {

            val contributorRankingViewModel: ContributorRankingViewModel =
                viewModel()

            ContributorRankingScreen(
                navController = navController,
                viewModel = contributorRankingViewModel
            )
        }

        composable("executiveReports") {
            ExecutiveReportsScreen(navController = navController)
        }

        composable("strategicGuidance") {

            val strategicViewModel: StrategicGuidanceViewModel =
                viewModel()

            StrategicGuidanceScreen(
                navController = navController,
                viewModel = strategicViewModel
            )
        }
    }
}

