package aoc202407

import scala.io.Source

def product[A](pool: Seq[A], n: Int): Seq[Seq[A]] = {
  if (n == 0) Seq(Seq())
  else pool.flatMap(a => product(pool, n - 1).map(a +: _))
}

def valid[A](value: A, equation: Seq[A], operators: Seq[(A, A) => A]): Boolean = {
  product(operators, equation.length - 1).exists { ops =>
    value == equation.tail.foldLeft((equation.head, 0)) { (a, b) =>
      (a, b) match {
        case ((l, i), r) => (ops(i)(l, r), i + 1)
      }
    }._1
  }
}

@main
def main() = {
  val tests = Source.fromFile("7.txt")
    .getLines
    .map {
      case s"$value: $equation" => (value.toLong, equation.split(" ").map(_.toLong).toSeq)
    }.toSeq
  
  val operators = Seq(
    (l: Long, r: Long) => l + r,
    (l: Long, r: Long) => l * r,
    (l: Long, r: Long) => s"$l$r".toLong
  )

  println(tests.collect {
    case (value, equation) if valid(value, equation, operators.init) => value
  }.sum)

  println(tests.collect {
    case (value, equation) if valid(value, equation, operators) => value
  }.sum)
}
