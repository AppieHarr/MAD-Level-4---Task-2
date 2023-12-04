package com.example.mad

import com.example.mad.data.Game
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {

        fun getMoveImage(move: Game.Move): Int {
            return when (move) {
                Game.Move.ROCK -> R.drawable.rock
                Game.Move.PAPER -> R.drawable.paper
                Game.Move.SCISSORS -> R.drawable.scissors
            }
        }
        // Converts a Date object into a String using dd MMM yyyy format (e.g. 12 Sep 2019)
        fun dateToString(date: Date): String =
            SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(date)

        // Converts a day, month and year into a Date object.
        fun dayMonthYearToDate(day: String, month: String, year: String): Date? {
            val dateString = StringBuilder()
            dateString
                .append(day)
                .append(month)
                .append(year)

            val format = SimpleDateFormat("ddMMyyyy", Locale.getDefault())
            return format.parse(dateString.toString())
        }
    }
}