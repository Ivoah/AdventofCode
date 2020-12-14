import scala.io._
import scala.collection.mutable

object Day9 extends App {
  case class Parameter(value: Long, mode: Long = 0) {
    def get(implicit cpu: Longcode): Long = {
      mode match {
        case 0 => cpu.mem.getOrElse(value, 0)
        case 1 => value
        case 2 => cpu.mem.getOrElse(value + cpu.base, 0)
        case _ => throw new Exception("Invalid parameter mode")
      }
    }

    def set(new_value: Long)(implicit cpu: Longcode): Unit = {
      mode match {
        case 0 => cpu.mem(value) = new_value
        case 2 => cpu.mem(value + cpu.base) = new_value
        case _ => throw new Exception("Invalid parameter mode")
      }
    }
  }

  object Longcode {
    def load(filename: String): Seq[Long] = {
      val source = Source.fromFile(filename)
      val program = source.mkString.strip.split(",").map(_.toLong).toSeq
      source.close()
      program
    }

    def apply(program: Seq[Long]): Longcode = Longcode(program.indices.map(_.toLong).zip(program).to(mutable.Map))
  }

  case class Longcode(mem: mutable.Map[Long, Long], var pc: Long = 0, var base: Long = 0) {
    def digit(n: Long, i: Long): Long = n / math.pow(10, i.toDouble - 1).toLong % 10

    val lengths = Map(1L -> 3, 2L -> 3, 3L -> 1, 4L -> 1, 5L -> 2, 6L -> 2, 7L -> 3, 8L -> 3, 9L -> 1, 99L -> 0)
    var next_input = 0
    var running = true

    def run(inputs: Seq[Long] = Seq()): Seq[Long] = {
      next_input = 0
      var outputs = Seq[Long]()
      while (running) {
        step(inputs) match {
          case Some(n) => outputs :+= n
          case None =>
        }
      }
      outputs
    }

    def run_until_output(inputs: Seq[Long] = Seq()): Option[Long] = {
      next_input = 0
      var output: Option[Long] = None
      while (running && output.isEmpty) {
        output = step(inputs)
      }
      output
    }

    def run_until_input(input: Long): Seq[Long] = {
      next_input = 0
      var outputs = Seq[Long]()
      while (running && next_input == 0) {
        step(Seq(input)) match {
          case Some(n) => outputs :+= n
          case None =>
        }
      }
      outputs
    }

    def step(inputs: Seq[Long]): Option[Long] = {
      implicit val cpu = this
      val instr = mem(pc)
      val op = instr % 100
      val params = (1 to lengths(op)).map { i =>
        Parameter(mem(pc + i), digit(instr, i + 2))
      }
      var output: Option[Long] = None
      (op, params) match {
        case (1, Seq(p1, p2, dest)) => dest.set(p1.get + p2.get)
        case (2, Seq(p1, p2, dest)) => dest.set(p1.get * p2.get)
        case (3, Seq(dest)) => dest.set(inputs(next_input)); next_input += 1
        case (4, Seq(addr)) => output = Some(addr.get)
        case (5, Seq(test, dest)) => if (test.get != 0) pc = dest.get - (params.length + 1)
        case (6, Seq(test, dest)) => if (test.get == 0) pc = dest.get - (params.length + 1)
        case (7, Seq(p1, p2, dest)) => dest.set(if (p1.get < p2.get) 1 else 0)
        case (8, Seq(p1, p2, dest)) => dest.set(if (p1.get == p2.get) 1 else 0)
        case (9, Seq(offset)) => base += offset.get
        case (99, Seq()) => running = false
      }
      pc += params.length + 1
      output
    }
  }

  val program = Longcode.load("9.txt")
  for (i <- 1 to 2) {
    val cpu = Longcode(program)
    println(cpu.run(Seq(i)).mkString("\n"))
  }
}
