package com.example.mad.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "game")
data class Game(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "date")
    val date: Date,

    @ColumnInfo(name = "playerMove")
    val playerMove: Move,

    @ColumnInfo(name = "computerMove")
    val computerMove: Move,

    @ColumnInfo(name = "result")
    val result: Result

) {
    enum class Move {
        ROCK, PAPER, SCISSORS
    }

    enum class Result {
        WIN, LOSE, DRAW
    }
}




