case class CPU(x: Int = 1) {
  def addx(V: Int) = {
    Seq(CPU(x), CPU(x + V))
  }

  def noop() = {
    Seq(CPU(x))
  }
}

@main
def main() = {
  val instructions = io.Source.fromFile("10.txt").getLines()

  val cpu_states = instructions.scanLeft(Seq(CPU())) {
    case (cpus, s"addx $v") => cpus.last.addx(v.toInt)
    case (cpus, "noop") => cpus.last.noop()
  }.flatten.toSeq

  println((for (cycle <- 20 until cpu_states.length by 40) yield {
    cycle*cpu_states(cycle - 1).x
  }).sum)

  val screen = cpu_states.dropRight(1).grouped(40).map { row =>
    row.zipWithIndex.map { case (state, x) =>
      if ((state.x - x).abs <= 1) "#" else " "
    }.mkString
  }.mkString("\n")

  println(screen)
}
