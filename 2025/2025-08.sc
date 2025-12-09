import scala.io.Source

case class Junction(x: Int, y: Int, z: Int) {
  def distance(other: Junction): Double = math.sqrt(math.pow(other.x - x, 2) + math.pow(other.y - y, 2) + math.pow(other.z - z, 2))
}

val boxes = Source.fromFile("8.txt")
  .getLines
  .map{case s"$x,$y,$z" => Junction(x.toInt, y.toInt, z.toInt)}
  .toSeq

val dists = boxes.combinations(2).toSeq.sortBy{case Seq(j1, j2) => j1.distance(j2)}

val nets = dists.scanLeft((boxes.map(Set(_)).toSet, (Junction(0, 0, 0), Junction(0, 0, 0)))) { case ((circuits, (_, _)), Seq(j1, j2)) =>
  val c1 = circuits.find(_.contains(j1)).get
  val c2 = circuits.find(_.contains(j2)).get
  if (c1 == c2) (circuits, (j1, j2))
  else (circuits - c1 - c2 + (c1 ++ c2), (j1, j2))
}.toSeq

println(nets(1000)._1.toSeq.map(_.size.toInt).sortBy(-_).take(3).reduce(_ * _))
println(nets.find(_._1.size == 1).get._2 match {case (j1, j2) => j2.x * j1.x})
