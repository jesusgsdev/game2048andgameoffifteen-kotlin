package jesusgsdev.board

import jesusgsdev.board.Direction.*

open class SquareBoardImpl(override val width: Int) : SquareBoard {

    var cells : Array<Array<Cell>> = arrayOf(arrayOf())

    override fun getCellOrNull(i: Int, j: Int): Cell? =
            when {
                i > width || j > width || i == 0 || j == 0 -> null
                else -> getCell(i, j)
            }

    override fun getCell(i: Int, j: Int): Cell = cells[i - 1][j - 1]

    override fun getAllCells(): Collection<Cell> = IntRange(1, width).flatMap { i: Int -> IntRange(1, width)
            .map{ j:Int -> getCell(i,j) } }
            .toList()

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
            when {
                jRange.last > width -> IntRange(jRange.first, width).map { j:Int -> getCell(i, j) }.toList()
                else -> jRange.map { j: Int -> getCell(i, j) }.toList()
            }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
            when {
                iRange.last > width -> IntRange(iRange.first, width).map { i:Int -> getCell(i, j) }.toList()
                else -> iRange.map { i: Int -> getCell(i, j) }.toList()
            }

    override fun Cell.getNeighbour(direction: Direction): Cell? =
            when (direction) {
                UP -> getCellOrNull(i - 1, j)
                LEFT -> getCellOrNull(i, j - 1)
                DOWN -> getCellOrNull(i + 1, j)
                RIGHT -> getCellOrNull(i, j + 1)
            }
}

fun createSquareBoard(width: Int): SquareBoard {
    val sq = SquareBoardImpl(width)

    sq.cells = createEmptyBoard(width)

    return sq
}

fun <T> createGameBoard(width: Int): GameBoard<T> {
    val gb = GameBoardImpl<T>(width)

    gb.cells = createEmptyBoard(width)
    gb.cells.forEach { it.forEach { cell: Cell -> gb.cellValues += cell to null } }

    return gb
}


class GameBoardImpl<T>(override val width: Int) : SquareBoardImpl(width), GameBoard<T> {

    val cellValues = mutableMapOf<Cell, T?>()

    override fun get(cell: Cell): T? = cellValues.get(cell)

    override fun set(cell: Cell, value: T?) {
        cellValues += cell to value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = cellValues.filterValues { predicate.invoke(it) }.keys

    override fun find(predicate: (T?) -> Boolean): Cell? = cellValues.filter{ predicate.invoke(it.value) }.keys.first()

    override fun any(predicate: (T?) -> Boolean): Boolean = cellValues.values.any(predicate)

    override fun all(predicate: (T?) -> Boolean): Boolean = cellValues.values.all(predicate)

}

data class CellImpl(override val i: Int, override val j: Int) : Cell

private fun createEmptyBoard(width: Int): Array<Array<Cell>> {
    var board = arrayOf<Array<Cell>>()

    for (i in 1..width) {
        var array = arrayOf<Cell>()
        for (j in 1..width) {
            array += CellImpl(i, j)
        }
        board += array
    }
    return board
}