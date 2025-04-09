package edu.ucne.fluentpath.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import edu.ucne.fluentpath.Navigation.AppNavHost
import edu.ucne.fluentpath.Navigation.Screen
import edu.ucne.fluentpath.R
import edu.ucne.fluentpath.components.AppDrawer
import kotlinx.coroutines.launch

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            AppDrawer(
                currentRoute = currentRoute ?: Screen.Main.route,
                navigateTo = { route ->
                    navController.navigate(route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                    scope.launch { drawerState.close() }
                },
                closeDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                AppTopBar(
                    currentRoute = currentRoute,
                    onMenuClick = { scope.launch { drawerState.open() } }
                )
            },
            containerColor = colorResource(id = R.color.light_blue)
        ) { padding ->
            Box(
                modifier = Modifier
                    .padding(padding)
                   // .background(colorResource(id = R.color.light_blue))
                   .background(Color(0xFF38CC74))
            ) {
                AppNavHost(navController = navController)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    currentRoute: String?,
    onMenuClick: () -> Unit
) {
    val title = when (currentRoute) {
        Screen.Main.route -> "Inicio"
        Screen.EnhanceRoom.route -> "Enhance Room"
        Screen.Vocabulario.route -> "Vocabulario"
        Screen.GramaticaList.route -> "Gramáticas"
        Screen.ProfesorList.route -> "Profesores"
        else -> "App"
    }

    TopAppBar(
        title = { Text(title, color = Color.White) },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Menú",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
        //    containerColor = colorResource(id = R.color.primary_blue),
            containerColor = Color(0xFF38CC74),
            titleContentColor = Color.White,
            navigationIconContentColor = Color.White
        )
    )
}