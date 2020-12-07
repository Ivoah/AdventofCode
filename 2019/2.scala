import scala.io.Source
import scala.collection.mutable

object Intcode {
  def load(filename: String): Seq[Int] = {
    val source = Source.fromFile(filename)
    val program = source.mkString.strip.split(",").map(_.toInt).toSeq
    source.close()
    program
  }
  def apply(program: Seq[Int]): Intcode = Intcode(mutable.Seq(program: _*))
}

case class Intcode(val mem: mutable.Seq[Int], var pc: Int = 0, var running: Boolean = true) {
  def run(): Unit = while (running) step()
  def step(): Unit = {
    mem(pc) match {
      case 1 => mem(mem(pc + 3)) = mem(mem(pc + 1)) + mem(mem(pc + 2))
      case 2 => mem(mem(pc + 3)) = mem(mem(pc + 1)) * mem(mem(pc + 2))
      case 99 => running = false
    }
    pc += 4
  }
}

val program = Intcode.load("2.txt")

val cpu = Intcode(program)
cpu.mem(1) = 12
cpu.mem(2) = 2

cpu.run()
println(cpu.mem.head)

val answer = for (noun <- 0 to 999; verb <- 0 to 999 if ({
  val cpu = Intcode(program)
  cpu.mem(1) = noun
  cpu.mem(2) = verb

  try {
    cpu.run()
    cpu.mem.head == 19690720
  } catch { case _: Throwable => false}
})) yield 100*noun + verb
println(answer.head)
