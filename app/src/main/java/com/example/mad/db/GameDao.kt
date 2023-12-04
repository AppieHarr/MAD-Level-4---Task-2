package com.example.mad.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mad.data.Game

@Dao
interface GameDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(game: Game)

    @Query("SELECT * FROM game")
    fun getAllGames(): LiveData<List<Game>>

    @Query("DELETE FROM game")
    suspend fun deleteAll()

}

