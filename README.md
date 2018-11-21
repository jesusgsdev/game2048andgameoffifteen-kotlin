## Game

Your task is to implement two games: [Game 2048](https://en.wikipedia.org/wiki/2048_(video_game)) and [Game of Fifteen](https://en.wikipedia.org/wiki/15_puzzle). Use your implementation of the GameBoard interface from the previous task.

After implementing the game you can play it yourself running main function in ui/PlayGame2048 or ui/PlayGameOfFifteen.

### Game 2048

First, complete the tasks in `Game2048Helper.kt` (implementing the function moveAndMergeEqual declared in `Game2048Helper.kt`) and in `Game2048Initializer.kt` (generating new values randomly). Then, implement the utility functions declared in `Game2048.kt.` The tests which you can run to check each function are specified in the comments next to the function.

### Game of Fifteen

Game of Fifteen is solvable only if the initial permutation of numbers is [even](https://en.wikipedia.org/wiki/Parity_of_a_permutation). Implement first the function `isEven` (declared in `GameOfFifteenHelper.kt`) checking whether a permutation is even or odd, and then use this function to produce only solvable permutations in `GameOfFifteenIntiializer.kt`.

You can use the following algorithm to check the given permutation. Let P is a permutation function on a range of numbers 1..n. For a `pair (i, j)` of elements such that `i < j , if P(i) > P(j)`, then the permutation is said to invert the order of `(i, j)`. The number of such inverted pairs is the parity of the permutation. If permutation inverts even number of such pairs it is an even permutation else it is an odd permutation.