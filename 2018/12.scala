import scala.annotation.tailrec
import scala.io.Source

val input = Source.fromFile("12.txt").getLines().toSeq
val initial_state = input.head.slice("initial state: ".length, input.head.length).zipWithIndex.collect{case ('#', i) => i}.toSet
implicit val rules: Map[Seq[Boolean], Boolean] = input.slice(2, input.length).map(r => ((0 until 5).map(i => r(i) == '#'), r(9) == '#')).toMap[Seq[Boolean], Boolean]

@tailrec
def gen_states(prev_states: Seq[Set[Int]])(implicit rules: Map[Seq[Boolean], Boolean]): Seq[Set[Int]] = {
  val last_state = prev_states.last
  val next_state = (last_state.min - 2 to last_state.max + 2).filter(i => rules((i - 2 to i + 2).map(last_state))).toSet
  if ((-1 to 1).exists(i => last_state.map(_ + i) == next_state)) prev_states :+ next_state
  else gen_states(prev_states :+ next_state)
}

val states = gen_states(Seq(initial_state))
println(states(20).sum)
val shift = (-1 to 1).filter(i => states(states.length - 2).map(_ + i) == states.last).head
println(states.last.sum + shift*(50000000000L - states.length + 1)*states.last.size)
