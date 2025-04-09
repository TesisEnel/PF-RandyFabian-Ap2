package edu.ucne.fluentpath.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Expand
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Rule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Spellcheck
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import edu.ucne.fluentpath.Navigation.Screen
import edu.ucne.fluentpath.R

@Composable
fun AppDrawer(
    currentRoute: String,
    navigateTo: (String) -> Unit,
    closeDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.white))
            .padding(start = 24.dp, top = 48.dp)
    ) {
        Text(
            "Menú Principal",
            style = MaterialTheme.typography.headlineSmall,
            color = colorResource(id = R.color.dark_text),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Items del menú
        DrawerMenuItem(
            icon = Icons.Default.Home,
            label = "Inicio",
            route = Screen.Main.route,
            currentRoute = currentRoute,
            onItemClick = {
                navigateTo(Screen.Main.route)
                closeDrawer()
            }
        )

        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = colorResource(id = R.color.dark_text)
        )

        Text(
            "Menu",
            style = MaterialTheme.typography.labelMedium,
            color = colorResource(id = R.color.dark_text),
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        DrawerMenuItem(
            icon = Icons.Default.LibraryBooks,
            label = "Vocabulario",
            route = Screen.Vocabulario.route,
            currentRoute = currentRoute,
            onItemClick = {
                navigateTo(Screen.Vocabulario.route)
                closeDrawer()
            }
        )

        DrawerMenuItem(
            icon = Icons.Default.MenuBook,
            label = "Gramáticas",
            route = Screen.GramaticaList.route,
            currentRoute = currentRoute,
            onItemClick = {
                navigateTo(Screen.GramaticaList.route)
                closeDrawer()
            }
        )

        DrawerMenuItem(
            icon = Icons.Default.School,
            label = "Profesores",
            route = Screen.ProfesorList.route,
            currentRoute = currentRoute,
            onItemClick = {
                navigateTo(Screen.ProfesorList.route)
                closeDrawer()
            }
        )

        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = colorResource(id = R.color.dark_text)
        )

    }
}

@Composable
fun DrawerMenuItem(
    icon: ImageVector,
    label: String,
    route: String,
    currentRoute: String,
    onItemClick: () -> Unit
) {
    val selected = currentRoute == route
    val backgroundColor = if (selected) {
        colorResource(id = R.color.dark_text).copy(alpha = 0.15f)
    } else {
        Color.Transparent
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick)
            .background(backgroundColor)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colorResource(id = R.color.dark_text)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.dark_text)
        )
    }
}