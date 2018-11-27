package jesusgsdev.games.gameOfFifteen

import jesusgsdev.board.Cell
import jesusgsdev.board.Direction
import jesusgsdev.board.createGameBoard
import jesusgsdev.games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'
 * (or choosing the corresponding run configuration).
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        val values = initializer.initialPermutation

        var k = 0
        for (i in 1..board.width) {
            for (j in 1..board.width) {
                val value = if( k < values.size) values[k++] else null
                board[board.getCell(i, j)] = value
            }
        }
    }

    override fun canMove() = true

    override fun hasWon(): Boolean {
        var won = true
        var k = 1
        for (i in 1..board.width) {
            for (j in 1..board.width) {
                if(get(i, j) != k && k != board.width*board.width) {
                    won = false
                    break
                }
                k++
            }
        }

        return won && get(board.width, board.width) == null
    }

    override fun processMove(direction: Direction) {
        val zero = findNull()

        var nonZero : Cell? = null
        board.apply { nonZero = zero.getNeighbour(direction.opposite()) }
        if (nonZero == null) return

        board[zero] = board[nonZero!!]
        board[nonZero!!] = null
    }


    private fun Direction.opposite(): Direction = when(this) {
        Direction.LEFT -> Direction.RIGHT
        Direction.RIGHT -> Direction.LEFT
        Direction.UP -> Direction.DOWN
        Direction.DOWN -> Direction.UP
    }

    private fun findNull() : Cell = board.getAllCells().find{ get(it.i, it.j) == null }!!

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}