import scala.annotation.tailrec
import scala.io.Source

@tailrec
def partition(seq: List[Boolean], min: Int, max: Int): Int = {
  seq match {
    case head::tail if head => partition(tail, min + (max - min)/2 + 1, max)
    case head::tail if !head => partition(tail, min, max - (max - min)/2 - 1)
    case Nil if min == max => min
  }
}

val passes = Source.fromFile("5.txt").getLines()
val ids = passes.map { pass =>
  val row = partition(pass.slice(0, 7).map(_ == 'B').toList, 0, 127)
  val col = partition(pass.slice(7, 10).map(_ == 'R').toList, 0, 7)
  row*8 + col
}.toSeq

println(ids.max)
println(ids.sorted.sliding(2).find(s => s.head + 1 != s.last).get.head + 1)
