package com.example.mad.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.mad.data.Game
import com.example.mad.db.GameDao
import com.example.mad.db.GameRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GameRepository(context: Context) {
    private val gameDao: GameDao

    init {
        val db = GameRoomDatabase.getDatabase(context)
        gameDao = db.gameDao()
    }

    fun getAllGames(): LiveData<List<Game>> {
        return gameDao.getAllGames()
    }

    suspend fun insertGame(game: Game) {
        gameDao.insert(game)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAll()
    }

}



