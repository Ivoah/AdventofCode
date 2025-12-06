import scala.io.Source

extension [T](s: Seq[T]) {
  def split(on: T): Seq[Seq[T]] = Iterator.unfold(0) { p =>
    if (p < s.length) {
      val i = s.indexOf(on, p)
      val ii = if (i == -1) s.length else i
      Some(s.slice(p, ii), ii + 1)
    } else None
  }.toSeq
}

val input = Source.fromFile("6.txt").getLines.toSeq
val operators = input.last.split("\\s+").map(Map("+" -> ((a: Long, b: Long) => a + b), "*" -> ((a: Long, b: Long) => a * b))).toSeq
val operands1 = input.init.map(_.trim.split("\\s+").map(_.toLong).toSeq).transpose
val operands2 = input.init.transpose.map(_.mkString.trim.toLongOption).split(None).map(_.flatten)

println(operators.zip(operands1).map{case (op, args) => args.reduce(op)}.sum)
println(operators.zip(operands2).map{case (op, args) => args.reduce(op)}.sum)
