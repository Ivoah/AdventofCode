import scala.io.Source
import scala.collection.mutable

val directions = Source.fromFile("3.txt").getLines().map(_.split(",").toSeq).toSeq
//val wires = directions.map(_.foldLeft(Seq((0, 0)))((path, dir) => path ++ (dir match {
//  case s"U$len" => (1 to len.toInt).map(i => (path.last._1, path.last._2 + i))
//  case s"D$len" => (1 to len.toInt).map(i => (path.last._1, path.last._2 - i))
//  case s"L$len" => (1 to len.toInt).map(i => (path.last._1 - i, path.last._2))
//  case s"R$len" => (1 to len.toInt).map(i => (path.last._1 + i, path.last._2))
//})))
val wires = directions.map { ds =>
  val wire = mutable.ListBuffer((0, 0))
  for (d <- ds) {
    val end = wire.last
    wire ++= (d match {
      case s"U$len" => (1 to len.toInt).map(i => (end._1, end._2 + i))
      case s"D$len" => (1 to len.toInt).map(i => (end._1, end._2 - i))
      case s"L$len" => (1 to len.toInt).map(i => (end._1 - i, end._2))
      case s"R$len" => (1 to len.toInt).map(i => (end._1 + i, end._2))
    })
  }
  wire
}

val crossings = wires.map(_.toSet).reduce(_ intersect _) diff Set((0, 0))

println(crossings.map{case (x, y) => x + y}.min)
println(crossings.map(p => wires.map(_.indexOf(p)).sum).min)
