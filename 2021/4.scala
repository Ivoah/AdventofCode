import scala.io.AnsiColor._
import scala.io.Source

class Board(val board: Seq[Seq[(Int, Boolean)]]) {
    override def toString(): String = board.map(_.map(p => if (p._2) f"${GREEN}${p._1}%2d${RESET}" else f"${p._1}%2d").mkString(" ")).mkString("\n")
    def play(n: Int): Board = new Board(board.map(_.map(p => if (p._1 == n) (p._1, true) else p)))
    def won: Boolean = board.exists(_.forall(_._2)) || board.transpose.exists(_.forall(_._2))
    def score: Int = board.flatten.filter(!_._2).map(_._1).sum
}

object Bingo {
    def apply(board: Seq[String]): Board = new Board(board.map(_.trim.split("\\s+").map(n => (n.toInt, false))))
}

object Four extends App {
    val input = Source.fromFile("4.txt").getLines().toSeq
    val plays = input.head.split(",").map(_.toInt).toSeq
    val boards = input.tail.grouped(6).map(b => Bingo(b.tail)).toSeq

    def findWinner(plays: Seq[Int], boards: Seq[Board]): (Board, Int) = {
        val playedBoards = boards.map(_.play(plays.head))
        playedBoards.find(_.won) match {
            case Some(board) => (board, plays.head)
            case None => findWinner(plays.tail, playedBoards)
        }
    }

    def findLooser(plays: Seq[Int], boards: Seq[Board]): (Board, Int) = {
        val playedBoards = boards.map(_.play(plays.head))
        if (boards.exists(!_.won) && playedBoards.forall(_.won)) (boards.find(!_.won).get.play(plays.head), plays.head)
        else findLooser(plays.tail, playedBoards)
    }

    val (winner, lastW) = findWinner(plays, boards)
    println(winner.score*lastW)
    val (looser, lastL) = findLooser(plays, boards)
    println(looser.score*lastL)
}
