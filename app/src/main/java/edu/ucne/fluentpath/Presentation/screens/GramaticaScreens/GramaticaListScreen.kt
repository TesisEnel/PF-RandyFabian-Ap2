package edu.ucne.fluentpath.Presentation.screens.GramaticaScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import edu.ucne.fluentpath.R
import edu.ucne.fluentpath.components.*
import edu.ucne.fluentpath.data.local.entities.GramaticaEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GramaticaListScreen(
    navController: NavController,
    viewModel: GramaticaViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    var searchQuery by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }
    val backgroundColor = Color(0xFF38CC74)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (!isSearchActive) {
                        Text(stringResource(R.string.gramatica_list_title))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Row {
                        if (isSearchActive) {
                            SearchTextField(
                                searchQuery = searchQuery,
                                onSearchQueryChange = { searchQuery = it },
                                onCloseSearch = {
                                    searchQuery = ""
                                    isSearchActive = false
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(end = 8.dp)
                            )
                        } else {
                            IconButton(
                                onClick = { isSearchActive = true },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Icon(Icons.Default.Search, contentDescription = "Search")
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            when {
                state.isLoading -> LoadingState(modifier = Modifier.fillMaxSize())
                state.error != null -> ErrorState(
                    error = state.error!!,
                    onRetry = { viewModel.loadGramaticas() },
                    modifier = Modifier.fillMaxSize()
                )
                state.gramaticas.isEmpty() -> EmptyState(
                    message = stringResource(R.string.no_gramaticas_found),
                    onRefresh = { viewModel.loadGramaticas() },
                    modifier = Modifier.fillMaxSize()
                )
                else -> {
                    val filteredGramaticas = state.gramaticas.filter {
                        it.titulo.contains(searchQuery, ignoreCase = true)
                    }

                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(filteredGramaticas) { gramatica ->
                            GramaticaItem(
                                gramatica = gramatica,
                                onClick = {
                                    navController.navigate(
                                        "gramatica_detail/${gramatica.gramaticaId}"
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SearchTextField(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onCloseSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = modifier
            .clip(RoundedCornerShape(20.dp)),
        placeholder = { Text("Buscar gramáticas...") },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (searchQuery.isNotEmpty()) {
                IconButton(onClick = {
                    onSearchQueryChange("")
                    onCloseSearch()
                }) {
                    Icon(Icons.Default.Close, contentDescription = "Clear")
                }
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun GramaticaItem(
    gramatica: GramaticaEntity,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = gramatica.titulo,
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

