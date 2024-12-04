import scala.io.Source

enum Instr {
  case Do()
  case `Don't`()
  case Mul(a: Int, b: Int)
}

@main
def main202403() = {
  val instructions = raw"mul\((\d+),(\d+)\)|do\(\)|don't\(\)".r.findAllMatchIn(
    Source.fromFile("3.txt")
      .getLines()
      .mkString("\n")
  ).map(m => m.matched match {
    case "do()" => Instr.Do()
    case "don't()" => Instr.`Don't`()
    case _ => Instr.Mul(m.group(1).toInt, m.group(2).toInt)
  }).toSeq

  println(instructions.collect{ case Instr.Mul(a, b) => a*b }.sum)
  println(instructions.foldLeft((0, true)) { (state, instr) =>
    (state, instr) match {
      case ((sum, _), Instr.Do()) => (sum, true)
      case ((sum, _), Instr.`Don't`()) => (sum, false)
      case ((sum, false), _) => (sum, false)
      case ((sum, true), Instr.Mul(a, b)) => (sum + a*b, true)
    }
  }._1)
}
