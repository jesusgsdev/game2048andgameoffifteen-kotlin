package jesusgsdev.games.game2048

import jesusgsdev.board.GameBoard
import jesusgsdev.board.createGameBoard
import org.junit.Assert.*
import org.junit.Test

class Test2048Initializer {
    private val initializer = RandomGame2048Initializer

    @Test
    fun initializer_cell_is_not_null() {
        val board = TestBed.emptyBoard
        val result = initializer.nextValue(board)
        val expected = result?.first
        assertNotNull(expected)
    }

    @Test
    fun initializer_value_is_correct() {
        val board = TestBed.emptyBoard
        val result = initializer.nextValue(board)
        val expected = TestBed.validNumbers(listOf(result?.second))
        assertTrue(expected)
    }

    @Test
    fun empty_board_values_count_is_correct() {
        val board = TestBed.emptyBoard
        val pair = initializer.nextValue(board)
        pair?.run {
            board[pair.first] = pair.second
        }

        val values = board.values.filterNotNull()
        val expected = 1
        assertEquals(expected, values.size)
    }

    @Test
    fun empty_board_value_is_correct() {
        val board = TestBed.emptyBoard
        val pair = initializer.nextValue(board)
        pair?.run {
            board[pair.first] = pair.second
        }

        val value = board.values.filterNotNull().first()
        val expected = TestBed.validNumbers(listOf(value))
        assertTrue(expected)
    }

    @Test
    fun full_board_values_count_is_correct() {
        val board = TestBed.fullBoard
        val values = board.values.filterNotNull()
        val expected = board.expectedListSize
        assertEquals(expected, values.size)
    }

    @Test
    fun full_board_values_are_correct() {
        val board = TestBed.fullBoard
        val expected = board.validNumbers
        assertTrue(expected)
    }

    object TestBed {
        val emptyBoard: GameBoard<Int?>
            get() = createGameBoard(4)

        val fullBoard: GameBoard<Int?>
            get() {
                val board = emptyBoard
                val initializer = RandomGame2048Initializer
                for (i in 1..board.expectedListSize) {
                    val pair = initializer.nextValue(board)
                    pair?.run {
                        val cell = board.getCell(this.first.i, this.first.j)
                        board[cell] = second
                    }
                }
                return board
            }

        fun validNumbers(values: List<Int?>): Boolean {
            values.forEach {
                if (
                    it == null ||
                    it !in listOf(2, 4)
                ) return false
            }
            return true
        }

    }
}

private val GameBoard<Int?>.values: List<Int?>
    get() {
        val cells = this.getAllCells()
        val values = mutableListOf<Int?>()
        cells.forEach { values.add(this[it]) }
        return values
    }

private val GameBoard<Int?>.validNumbers: Boolean
    get() {
        values.forEach { if (it == null || it !in listOf(2, 4)) return false }
        return true
    }

private val GameBoard<Int?>.expectedListSize get() = width * width