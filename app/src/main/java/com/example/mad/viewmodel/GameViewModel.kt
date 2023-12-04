package com.example.mad.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.mad.data.Game
import com.example.mad.repository.GameRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mad.db.GameRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class GameViewModel(application: Application) : AndroidViewModel(application) {
    private val ioScope = CoroutineScope(Dispatchers.IO)
    private val gameRepository = GameRepository(application.applicationContext)

    val gameHistory: LiveData<List<Game>> = gameRepository.getAllGames()

    var playerMove: Game.Move? = null

    // Properties to hold the current values of wins, draws, and losses
    private var wins = 0
    private var draws = 0
    private var losses = 0

    fun saveGame(game: Game) {
        ioScope.launch {
            gameRepository.insertGame(game)
        }
    }

    fun getGameResult(playerMove: Game.Move, computerMove: Game.Move): Game.Result {
        return when {
            //each case explicitly states the result of the game
            playerMove == computerMove -> Game.Result.DRAW
            playerMove == Game.Move.ROCK && computerMove == Game.Move.SCISSORS -> Game.Result.WIN
            playerMove == Game.Move.PAPER && computerMove == Game.Move.ROCK -> Game.Result.WIN
            playerMove == Game.Move.SCISSORS && computerMove == Game.Move.PAPER -> Game.Result.WIN
            else -> Game.Result.LOSE
        }
    }

    fun deleteAllGames() {
        ioScope.launch {
            gameRepository.deleteAllGames()
        }
    }

}

