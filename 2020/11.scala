import scala.annotation.tailrec
import scala.io.Source

type StateType = Seq[Seq[Option[Boolean]]]

val seats = Source.fromFile("11.txt").getLines().map(_.map(Map('L' -> Some(false), '.' -> None))).toSeq

def neighbors(pos: (Int, Int), state: StateType) = {
  (for (
    i <- pos._1 - 1 to pos._1 + 1;
    j <- pos._2 - 1 to pos._2 + 1
    if (i, j) != pos && i >= 0 && i < state.size && j >= 0 && j < state(i).size
  ) yield state(i)(j) match {
    case Some(true) => 1
    case Some(false) => 0
    case None => 0
  }).sum
}

def visible(pos: (Int, Int), dir: (Int, Int), state: StateType): Boolean = {
  val new_pos = (pos._1 + dir._1, pos._2 + dir._2)
  if (new_pos._1 < 0 || new_pos._1 >= state.size || new_pos._2 < 0 || new_pos._2 >= state(new_pos._1).size) false
  else state(new_pos._1)(new_pos._2).getOrElse(visible(new_pos, dir, state))
}

def visible_neighbors(pos: (Int, Int), state: StateType) = {
  (-1 to 1).flatMap(i => (-1 to 1).map(j => (i, j))).count(dir => dir != (0, 0) && visible(pos, dir, state))
}

def next_state(state: StateType, overcrowded: Int = 4)(implicit neighbors_function: ((Int, Int), StateType) => Int) = {
  state.zipWithIndex.map{case (row, i) => row.zipWithIndex.map{case (seat, j) => seat.map(s => neighbors_function((i, j), state) match {
    case n if n == 0 => true
    case n if n >= overcrowded => false
    case _ => s
  })}}
}

@tailrec
def stable_state(initial_state: StateType, overcrowded: Int = 4)(implicit neighbors_function: ((Int, Int), StateType) => Int): StateType = {
  val state = next_state(initial_state, overcrowded)
  if (state == initial_state) state
  else stable_state(state, overcrowded)
}

println(stable_state(seats)(neighbors).map(_.count(_.getOrElse(false))).sum)
println(stable_state(seats, 5)(visible_neighbors).map(_.count(_.getOrElse(false))).sum)
