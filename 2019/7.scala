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

case class Intcode(mem: mutable.Seq[Int], var pc: Int = 0) {
  def digit(n: Int, i: Int): Int = n/math.pow(10, i - 1).toInt % 10

  val lengths = Map(1 -> 3, 2 -> 3, 3 -> 1, 4 -> 1, 5 -> 2, 6 -> 2, 7 -> 3, 8 -> 3, 99 -> 0)
  var next_input = 0
  var running = true

  def run(inputs: Seq[Int] = Seq()): Seq[Int] = {
    next_input = 0
    var outputs = Seq[Int]()
    while (running) {
      step(inputs) match {
        case Some(n) => outputs :+= n
        case None =>
      }
    }
    outputs
  }

  def run_until_output(inputs: Seq[Int] = Seq()): Option[Int] = {
    next_input = 0
    var output: Option[Int] = None
    while (running && output.isEmpty) {
      output = step(inputs)
    }
    output
  }

  def run_until_input(input: Int): Seq[Int] = {
    next_input = 0
    var outputs = Seq[Int]()
    while (running && next_input == 0) {
      step(Seq(input)) match {
        case Some(n) => outputs :+= n
        case None =>
      }
    }
    outputs
  }

  def step(inputs: Seq[Int]): Option[Int] = {
    implicit val _mem: mutable.Seq[Int] = mem
    val instr = mem(pc)
    val op = instr % 100
    val params = mem.slice(pc + 1, pc + 1 + lengths(op)).zipWithIndex.map {
      case (v, i) => Parameter(v, digit(instr, i + 3))
    }.toSeq
    var output: Option[Int] = None
    (op, params) match {
      case (1, Seq(p1, p2, dest)) => dest.set(p1.get + p2.get)
      case (2, Seq(p1, p2, dest)) => dest.set(p1.get * p2.get)
      case (3, Seq(dest)) => dest.set(inputs(next_input)); next_input += 1
      case (4, Seq(addr)) => output = Some(addr.get)
      case (5, Seq(test, dest)) => if (test.get != 0) pc = dest.get - (params.length + 1)
      case (6, Seq(test, dest)) => if (test.get == 0) pc = dest.get - (params.length + 1)
      case (7, Seq(p1, p2, dest)) => dest.set(if (p1.get < p2.get) 1 else 0)
      case (8, Seq(p1, p2, dest)) => dest.set(if (p1.get == p2.get) 1 else 0)
      case (99, Seq()) => running = false
    }
    pc += params.length + 1
    output
  }
}

val program = Intcode.load("7.txt")
println((0 to 4).permutations.map {
  _.foldLeft(Seq(0)) { (prev, phase) =>
    val amp = Intcode(program)
    val inputs = Seq(phase, prev.last)
    amp.run(inputs)
  }.last
}.max)

println((5 to 9).permutations.map { phases =>
  val amps = (0 to 4).map(_ => Intcode(program))
  for ((phase, i) <- phases.zipWithIndex) amps(i).run_until_input(phase)
  var last_output = 0
  var amp_e = 0
  var i = 0
  while (amps.exists(_.running)) {
    last_output = amps(i).run_until_output(Seq(last_output)) match {
      case Some(n) => n
      case None => last_output
    }
    if (i == 4) amp_e = last_output
    i = (i + 1)%5
  }
  amp_e
}.max)
