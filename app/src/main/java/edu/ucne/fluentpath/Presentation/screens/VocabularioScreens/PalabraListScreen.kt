
package edu.ucne.fluentpath.Presentation.screens.PalabraScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import edu.ucne.fluentpath.R
import edu.ucne.fluentpath.components.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PalabraListScreen(
    viewModel: PalabraViewModel = hiltViewModel(),
    navController: NavController,
    vocabularioId: Int
) {
    val state by viewModel.state.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isLoading)

    LaunchedEffect(vocabularioId) {
        viewModel.handleIntent(PalabraIntent.LoadPalabras(vocabularioId))
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Words in Vocabulary",
                        color = colorResource(id = R.color.white))
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() },
                        colors = IconButtonDefaults.iconButtonColors(
                            contentColor = colorResource(id = R.color.white)
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF38CC74),
                    titleContentColor = colorResource(id = R.color.white),
                    navigationIconContentColor = colorResource(id = R.color.white)
                )
            )
        },

        containerColor = colorResource(id = R.color.light_blue)
    ) { paddingValues ->
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = { viewModel.handleIntent(PalabraIntent.Refresh) },
            indicator = { state, refreshTrigger ->
                SwipeRefreshIndicator(
                    state = state,
                    refreshTriggerDistance = refreshTrigger,
                    backgroundColor = colorResource(id = R.color.primary_blue),
                    contentColor = colorResource(id = R.color.white)
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFF38CC74))
            ) {
                when {
                    state.isLoading -> LoadingState()
                    state.error != null -> ErrorState(
                        error = state.error!!,
                        onRetry = { viewModel.handleIntent(PalabraIntent.LoadPalabras(vocabularioId)) }
                    )
                    state.palabras.isEmpty() -> EmptyState(
                        message = "No words found",
                        onRefresh = { viewModel.handleIntent(PalabraIntent.Refresh) }
                    )
                    else -> LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(state.palabras) { palabra ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = colorResource(id = R.color.white),
                                    contentColor = colorResource(id = R.color.dark_text)
                                )
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    palabra.imagenURL?.let { url ->
                                        AsyncImage(
                                            model = url,
                                            contentDescription = "Image for ${palabra.termino}",
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .height(200.dp)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                    }

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = palabra.termino ?: "No term",
                                            style = MaterialTheme.typography.headlineSmall,
                                            color = colorResource(id = R.color.dark_text)
                                        )
                                        IconButton(
                                            onClick = {
                                                palabra.termino?.let { term ->
                                                    palabra.definicion?.let { def ->
                                                        viewModel.speakWordAndDefinition(term, def)
                                                    }
                                                }
                                            },
                                            colors = IconButtonDefaults.iconButtonColors(
                                                contentColor = colorResource(id = R.color.primary_blue)
                                            )
                                        ) {
                                            Icon(
                                                Icons.Default.VolumeUp,
                                                contentDescription = "Speak"
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = palabra.definicion ?: "No definition",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = colorResource(id = R.color.dark_text)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}