package com.example.mad.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.mad.R
import com.example.mad.Utils
import com.example.mad.viewmodel.GameViewModel
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SimpleDateFormat", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun History(viewModel: GameViewModel, navController: NavController) {
    val games by viewModel.gameHistory.observeAsState(emptyList())
    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Rock Paper Scissors") },
            )
        },
        bottomBar = {
            BottomNavigation {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play",
                        )
                    },
                    label = { Text(text = "Play") },
                    selected = true,
                    onClick = { navController.navigate(Screen.HomeScreen.route) }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.List,
                            contentDescription = "History",
                        )
                    },
                    label = { Text(text = "History") },
                    selected = false,
                    onClick = { }
                )
            }
        }
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "Game History",
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Button(
                onClick = { viewModel.deleteAllGames()
                },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ){
                Text(text = "Delete All Games")
            }
            if (games.isEmpty()) {
                Text(text = "No games played yet.")
            } else {
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(1),
                    content = {
                        games.forEach { game ->
                            item {
                    Card(Modifier.padding(vertical = 8.dp)) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                text = "Result: ${game.result}",
                                style = MaterialTheme.typography.h6,
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Text(
                                text = dateFormat.format(game.date),
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f),
                                modifier = Modifier.align(Alignment.CenterHorizontally)
                            )
                            Row(
                                modifier = Modifier
                    .fillMaxWidth()
                .padding(16.dp)
                        ) {
                        Image(
                            painter = painterResource(
                                Utils.getMoveImage(game.playerMove)
                            ),
                            modifier = Modifier
                                .size(100.dp)
                                .padding(16.dp)
                                .weight(1f),
                            contentDescription = "${game.playerMove}"
                        )
                        Text(
                            text = stringResource(R.string.vs),
                            style = MaterialTheme.typography.h4,
                            fontStyle = FontStyle.Italic,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(1f)
                        )
                        Image(
                            painter = painterResource(
                                Utils.getMoveImage(game.computerMove)
                            ),
                            modifier = Modifier
                                .size(100.dp)
                                .padding(16.dp)
                                .weight(1f),
                            contentDescription = "${game.computerMove}"
                        )
                    }
                            }
                }
            }
    }
                }
                )
        }
        }
    }
}





