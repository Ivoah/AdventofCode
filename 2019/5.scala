import scala.math
import scala.io._
import scala.collection.mutable

case class Parameter(value: Int, mode: Int = 0) {
  def get(implicit mem: mutable.Seq[Int]): Int = {
    mode match {
      case 0 => mem(value)
      case 1 => value
    }
  }
  def set(new_value: Int)(implicit mem: mutable.Seq[Int]): Unit = {
    mode match {
      case 0 => mem(value) = new_value
      case 1 => throw new Exception("Invalid parameter mode")
    }
  }
}

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
  def digit(n: Int, i: Int): Int = n/math.pow(10, i - 1).toInt % 10

  val lengths = Map(1 -> 3, 2 -> 3, 3 -> 1, 4 -> 1, 5 -> 2, 6 -> 2, 7 -> 3, 8 -> 3, 99 -> 0)

  def run(): Unit = while (running) step()
  def step(): Unit = {
    implicit val _mem = mem
    val instr = mem(pc)
    val op = instr % 100
    val params = mem.slice(pc + 1, pc + 1 + lengths(op)).zipWithIndex.map {
      case (v, i) => Parameter(v, digit(instr, i + 3))
    }.toSeq
    (op, params) match {
      case (1, Seq(p1, p2, dest)) => dest.set(p1.get + p2.get)
      case (2, Seq(p1, p2, dest)) => dest.set(p1.get * p2.get)
      case (3, Seq(dest)) => print("> "); dest.set(StdIn.readInt())
      case (4, Seq(addr)) => println(addr.get)
      case (5, Seq(test, dest)) => if (test.get != 0) pc = dest.get - (params.length + 1)
      case (6, Seq(test, dest)) => if (test.get == 0) pc = dest.get - (params.length + 1)
      case (7, Seq(p1, p2, dest)) => dest.set(if (p1.get < p2.get) 1 else 0)
      case (8, Seq(p1, p2, dest)) => dest.set(if (p1.get == p2.get) 1 else 0)
      case (99, Seq()) => running = false
    }
    pc += params.length + 1
  }
}

val cpu = Intcode(Intcode.load("5.txt"))
cpu.run()
