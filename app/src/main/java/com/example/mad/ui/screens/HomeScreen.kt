package com.example.mad.ui.screens

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.materialIcon
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.example.mad.R
import com.example.mad.Utils
import com.example.mad.data.Game
import com.example.mad.db.GameDao
import com.example.mad.db.GameRoomDatabase
import com.example.mad.repository.GameRepository

import com.example.mad.ui.theme.MADTheme
import com.example.mad.viewmodel.GameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(navController: NavController, viewModel: GameViewModel) {

    val context = LocalContext.current

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Rock Paper Scissors") },
        )
    }, bottomBar = {
        BottomNavigation {
            BottomNavigationItem(icon = {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Play",
                )
            },
                label = { Text(text = "Play") },
                selected = true,
                onClick = { /* Nothing to do, already on the play screen */ })
            BottomNavigationItem(icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "History",
                )
            },
                label = { Text(text = "History") },
                selected = false,
                onClick = { navController.navigate(Screen.History.route) })
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.app_name),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.h4
            )
            Text(
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = stringResource(R.string.statistics),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
            Text(
                text = stringResource(
                    R.string.wins,
                ), textAlign = TextAlign.Center, modifier = Modifier.padding(16.dp)
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val myMove = remember { mutableStateOf(0) }
                val computerMove2 = remember { mutableStateOf(0) }
                val computerMove = Game.Move.values().random()
                viewModel.playerMove?.let { playerMove ->
                    computerMove2.value = 1
                    when (viewModel.getGameResult(playerMove, computerMove)) {
                        Game.Result.WIN -> {
                            Text(
                                text = stringResource(R.string.win),
                                style = MaterialTheme.typography.h4
                            )

                        }
                        Game.Result.LOSE -> {
                            Text(
                                text = stringResource(R.string.lose),
                                style = MaterialTheme.typography.h4
                            )
                        }
                        Game.Result.DRAW -> {
                            Text(
                                text = stringResource(R.string.draw),
                                style = MaterialTheme.typography.h4
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    if (myMove.value != 0) {
                        Image(
                            painter = painterResource(
                                Utils.getMoveImage(viewModel.playerMove!!),
                            ),
                            modifier = Modifier
                                .size(100.dp)
                                .padding(16.dp)
                                .weight(1f),
                            contentDescription = "Rock"
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.you),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }


                    Text(
                        text = stringResource(R.string.vs),
                        style = MaterialTheme.typography.h4,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f)
                    )

                    if (computerMove2.value != 0) {
                        Image(
                            painter = painterResource(
                                Utils.getMoveImage(computerMove)
                            ),
                            modifier = Modifier
                                .size(100.dp)
                                .padding(16.dp)
                                .weight(1f),
                            contentDescription = "Rock"
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.computer),
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    fun gameResult(playerMove: Game.Move, computerMove: Game.Move): Game.Result {
                        return when (playerMove) {
                            Game.Move.ROCK -> {
                                when (computerMove) {
                                    Game.Move.ROCK -> Game.Result.DRAW
                                    Game.Move.PAPER -> Game.Result.LOSE
                                    Game.Move.SCISSORS -> Game.Result.WIN
                                }
                            }
                            Game.Move.PAPER -> {
                                when (computerMove) {
                                    Game.Move.ROCK -> Game.Result.WIN
                                    Game.Move.PAPER -> Game.Result.DRAW
                                    Game.Move.SCISSORS -> Game.Result.LOSE
                                }
                            }
                            Game.Move.SCISSORS -> {
                                when (computerMove) {
                                    Game.Move.ROCK -> Game.Result.LOSE
                                    Game.Move.PAPER -> Game.Result.WIN
                                    Game.Move.SCISSORS -> Game.Result.DRAW
                                }
                            }
                        }
                    }
                    Box(modifier = Modifier
                        .padding(16.dp)
                        .clickable(onClick = {
                            myMove.value = 0
                            viewModel.playerMove = Game.Move.ROCK
                            myMove.value = 1
                            gameResult(Game.Move.ROCK, computerMove)
                            val game = Game(
                                date = Date(),
                                playerMove = Game.Move.ROCK,
                                computerMove = computerMove,
                                result = gameResult(Game.Move.ROCK, computerMove),
                            )
                            viewModel.saveGame(game)
                        })
                        .background(
                            color = colorResource(id = R.color.grey),
                            shape = RoundedCornerShape(16.dp)
                        )) {
                        Image(
                            painter = painterResource(id = R.drawable.rock),
                            contentDescription = "Rock",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(16.dp)
                        )
                    }

                    Box(modifier = Modifier
                        .padding(16.dp)
                        .clickable(onClick = {
                            myMove.value = 0
                            viewModel.playerMove = Game.Move.PAPER
                            myMove.value = 1
                            gameResult(Game.Move.PAPER, computerMove)
                            val game = Game(
                                date = Date(),
                                playerMove = Game.Move.PAPER,
                                computerMove = computerMove,
                                result = gameResult(Game.Move.PAPER, computerMove),
                            )
                            viewModel.saveGame(game)

                        })
                        .background(
                            color = colorResource(id = R.color.grey),
                            shape = RoundedCornerShape(16.dp)
                        )) {
                        Image(
                            painter = painterResource(id = R.drawable.paper),
                            contentDescription = "Paper",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(16.dp)
                        )
                    }

                    Box(modifier = Modifier
                        .padding(16.dp)
                        .clickable(onClick = {
                            myMove.value = 0
                            viewModel.playerMove = Game.Move.SCISSORS
                            myMove.value = 1
                            gameResult(Game.Move.SCISSORS, computerMove)
                            val game = Game(
                                date = Date(),
                                playerMove = Game.Move.SCISSORS,
                                computerMove = computerMove,
                                result = gameResult(Game.Move.SCISSORS, computerMove),
                            )
                            viewModel.saveGame(game)
                        })
                        .background(
                            color = colorResource(id = R.color.grey),
                            shape = RoundedCornerShape(16.dp)
                        )) {
                        Image(
                            painter = painterResource(id = R.drawable.scissors),
                            contentDescription = "Scissors",
                            modifier = Modifier
                                .size(80.dp)
                                .padding(16.dp)
                        )
                    }

                }
            }
        }
    }
}