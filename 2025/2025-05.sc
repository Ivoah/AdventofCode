import scala.io.Source

case class Range(start: Long, end: Long) {
  def size: Long = end + 1 - start
  def contains(n: Long): Boolean = start <= n && n <= end
  def overlaps(other: Range): Boolean = contains(other.start) || contains(other.end)
  def merged(other: Range): Range = Range(math.min(start, other.start), math.max(end, other.end))
}

object Range {
  def parseOption(s: String): Option[Range] = {
    s match {
      case s"$start-$end" => start.toLongOption.flatMap(s => end.toLongOption.map(e => Range(s, e)))
      case _ => None
    }
  }
}

val (ranges, ids) = {
  val input = Source.fromFile("5.txt").getLines.toSeq
  (
    input.flatMap(Range.parseOption).sortBy(_.start).foldLeft(Seq(Range(0, -1))) { case (combined, next) =>
      if (combined.last overlaps next) combined.init :+ combined.last.merged(next)
      else combined :+ next
    },
    input.flatMap(_.toLongOption)
  )
}

println(ids.count(id => ranges.exists(_.contains(id))))
println(ranges.map(_.size).sum)

