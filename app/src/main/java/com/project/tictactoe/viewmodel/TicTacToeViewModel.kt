package com.project.tictactoe.viewmodel

import android.arch.lifecycle.ViewModel

class TicTacToeViewModel : ViewModel() {
    var playBoard = Array(3) { IntArray(3) }
    val PLAYER_X = 1
    val PLAYER_O = 2
    var playerTurn = PLAYER_X
    private var GAME_MOVE_COUNTER = 0
    var isGameFinished: Boolean = false

    fun recordPlayerMove(position: Int, player: Int): Pair<Boolean, String> {
        if (!isGameFinished && GAME_MOVE_COUNTER <= 9) {
            when (position) {
                position -> if (playBoard[position / 3][position % 3] == 0) {
                    playBoard[position / 3][position % 3] = player
                    GAME_MOVE_COUNTER = GAME_MOVE_COUNTER.plus(1)
                    changePlayerTurn(player)
                    return Pair(true, "")
                }
            }
        } else {
            return Pair(false, "Game finished. No more moves allowed.")
        }
        return Pair(false, "A player has already played this position")
    }

    private fun changePlayerTurn(player: Int) {
        if (player == PLAYER_X) {
            playerTurn = PLAYER_O
        } else {
            playerTurn = PLAYER_X
        }
    }

    fun identifyWinnerByRow(): Pair<Boolean, String> {
        IntRange(0, 2).forEach { rowPosition ->
            if (playBoard[rowPosition][0] > 0 && compareUserMove(Pair(rowPosition, 0), Pair(rowPosition, 1)) &&
                compareUserMove(Pair(rowPosition, 0), Pair(rowPosition, 2))) {
                isGameFinished = true
                if (checkIfMoveMadeByPlayerX(Pair(rowPosition, 0))) {
                    return Pair(true, "Player X won by - row ${rowPosition + 1}")
                } else if (checkIfMoveMadeByPlayerO(Pair(rowPosition, 0))) {
                    return Pair(true, "Player O won by - row ${rowPosition + 1}")
                }
            }
        }
        return Pair(false, "")
    }

    fun identifyWinnerByColumn(): Pair<Boolean, String> {
        IntRange(0, 2).forEach { columnPosition ->
            if (playBoard[0][columnPosition] > 0 && compareUserMove(Pair(0, columnPosition), Pair(1, columnPosition))
                && compareUserMove(Pair(0, columnPosition), Pair(2, columnPosition))) {
                isGameFinished = true
                if (checkIfMoveMadeByPlayerX(Pair(0, columnPosition))) {
                    return Pair(true, "Player X won by - column ${columnPosition + 1}")
                } else if (checkIfMoveMadeByPlayerO(Pair(0, columnPosition))) {
                    return Pair(true, "Player O won by - column ${columnPosition + 1}")
                }
            }
        }
        return Pair(false, "")
    }

    fun identifyWinnerByDiagonal(): Pair<Boolean, String> {
        if (playBoard[0][0] > 0 && compareUserMove(Pair(0, 0), Pair(1, 1)) && compareUserMove(Pair(0, 0), Pair(2, 2))) {
            isGameFinished = true
            if (checkIfMoveMadeByPlayerX(Pair(0, 0))) {
                return Pair(true, "Player X won by - Left to Right diagonal")
            } else if (checkIfMoveMadeByPlayerO(Pair(0, 0))) {
                return Pair(true, "Player O won by - Left to Right diagonal")
            }
        } else if (playBoard[0][2] > 0 && compareUserMove(Pair(0, 2), Pair(1, 1)) &&
            compareUserMove(Pair(0, 2), Pair(2, 0))) {
            isGameFinished = true
            if (checkIfMoveMadeByPlayerX(Pair(0, 2))) {
                return Pair(true, "Player X won by - Right to Left diagonal")
            } else if (checkIfMoveMadeByPlayerO(Pair(0, 2))) {
                return Pair(true, "Player O won by - Right to Left diagonal")
            }
        }
        return Pair(false, "")
    }

    fun identifyIfMatchDrawn(): Pair<Boolean, String> {
        if (GAME_MOVE_COUNTER == 9) {
            isGameFinished = true
            return Pair(true, "Match drawn")
        } else {
            return Pair(false, "")
        }
    }

    private fun compareUserMove(firstPosition: Pair<Int, Int>, secondPosition: Pair<Int, Int>): Boolean {
        return playBoard[firstPosition.first][firstPosition.second] == playBoard[secondPosition.first][secondPosition.second]
    }

    private fun checkIfMoveMadeByPlayerX(movePosition: Pair<Int, Int>): Boolean {
        return playBoard[movePosition.first][movePosition.second] == PLAYER_X
    }

    private fun checkIfMoveMadeByPlayerO(movePosition: Pair<Int, Int>): Boolean {
        return playBoard[movePosition.first][movePosition.second] == PLAYER_O
    }
}