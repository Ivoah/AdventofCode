import scala.io.Source

val ops = Map("+" -> ((a: Long, b: Long) => a + b), "*" -> ((a: Long, b: Long) => a * b))

val input = Source.fromFile("6.txt").getLines.toSeq
val operators = input.last.split("(?=[*+])").scanLeft((ops("+"), 0, 0)){case (last, s) => (ops(s.trim), last._2 + last._3, s.length)}.tail.toSeq
val operands1 = input.init.map(_.trim.split("\\s+").map(_.toLong).toSeq).transpose
val operands2 = operators.map{case (_, pos, len) => input.init.map(_.substring(pos, pos + len)).transpose.flatMap(_.mkString.trim.toLongOption)}

println(operators.zip(operands1).map{case ((op, _, _), args) => args.reduce(op)}.sum)
println(operators.zip(operands2).map{case ((op, _, _), args) => args.reduce(op)}.sum)
