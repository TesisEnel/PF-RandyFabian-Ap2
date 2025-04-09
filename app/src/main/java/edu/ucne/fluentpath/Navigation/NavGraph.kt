package edu.ucne.fluentpath.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import edu.ucne.fluentpath.Presentation.Menu.MainScreen
import edu.ucne.fluentpath.Presentation.screens.GramaticaScreens.GramaticaDetailScreen
import edu.ucne.fluentpath.Presentation.screens.GramaticaScreens.GramaticaListScreen
import edu.ucne.fluentpath.Presentation.screens.PalabraScreens.PalabraListScreen
import edu.ucne.fluentpath.Presentation.screens.ProfesoresScreens.ProfesorDetailScreen
import edu.ucne.fluentpath.Presentation.screens.ProfesoresScreens.ProfesorListScreen
import edu.ucne.fluentpath.Presentation.screens.VocabularioScreens.VocabularioListScreen

sealed class Screen(val route: String) {
    object Main : Screen("main")

    object Vocabulario : Screen("vocabulario_list")
    object PalabraList : Screen("palabra_list/{vocabularioId}") {
        fun createRoute(vocabularioId: Int) = "palabra_list/$vocabularioId"
    }

    object ProfesorList : Screen("profesor_list")
    object ProfesorDetail : Screen("profesor_detail/{profesorId}") {
        fun createRoute(profesorId: Int) = "profesor_detail/$profesorId"
    }

    object GramaticaList : Screen("gramatica_list")
    object GramaticaDetail : Screen("gramatica_detail/{gramaticaId}") {
        fun createRoute(gramaticaId: Int) = "gramatica_detail/$gramaticaId"
    }
    object EnhanceRoom : Screen("enhance_room")
}

/*
@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        modifier = modifier
    ) {
        composable(Screen.Main.route) {
            MainScreen(navController)
        }

        composable(Screen.Vocabulario.route) {
            VocabularioListScreen(navController = navController)
        }

        composable(Screen.GramaticaList.route) {
            GramaticaListScreen(navController)
        }

        composable(
            route = Screen.GramaticaDetail.route,
            arguments = listOf(navArgument("gramaticaId") { type = NavType.IntType })
        ) { backStackEntry ->
            GramaticaDetailScreen(
                navController = navController
            )
        }

        composable(Screen.ProfesorList.route) {
            ProfesorListScreen(navController = navController)
        }



        composable(Screen.EnhanceRoom.route) {
            // EnhanceRoomScreen()
        }

        composable(
            route = Screen.PalabraList.route,
            arguments = listOf(
                navArgument("vocabularioId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val vocabularioId = backStackEntry.arguments?.getInt("vocabularioId") ?: 0
            PalabraListScreen(
                navController = navController,
                vocabularioId = vocabularioId
            )
        }
    }
}
*/


@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route,
        modifier = modifier
    ) {
        composable(Screen.Main.route) {
            MainScreen(navController)
        }

        composable(Screen.Vocabulario.route) {
            VocabularioListScreen(navController = navController)
        }

        composable(Screen.GramaticaList.route) {
            GramaticaListScreen(navController)
        }

        composable(
            route = Screen.GramaticaDetail.route,
            arguments = listOf(navArgument("gramaticaId") { type = NavType.IntType })
        ) { backStackEntry ->
            GramaticaDetailScreen(navController = navController)
        }

        composable(Screen.EnhanceRoom.route) {
            // EnhanceRoomScreen()
        }

        composable(
            route = Screen.PalabraList.route,
            arguments = listOf(navArgument("vocabularioId") { type = NavType.IntType })
        ) { backStackEntry ->
            val vocabularioId = backStackEntry.arguments?.getInt("vocabularioId") ?: 0
            PalabraListScreen(
                navController = navController,
                vocabularioId = vocabularioId
            )
        }

        // Nuevas rutas para Profesores
        composable(Screen.ProfesorList.route) {
            ProfesorListScreen(navController = navController)
        }

        composable(
            route = Screen.ProfesorDetail.route,
            arguments = listOf(navArgument("profesorId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            val profesorId = backStackEntry.arguments?.getInt("profesorId") ?: 0
            ProfesorDetailScreen(
                navController = navController,
                profesorId = profesorId
            )
        }
    }
}
