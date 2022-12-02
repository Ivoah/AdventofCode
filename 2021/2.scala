val course = scala.io.Source.fromFile("2.txt").getLines().toSeq

val loc1 = course.foldLeft((0, 0)) {
    case (p, s"forward $x") => (p._1 + x.toInt, p._2)
    case (p, s"down $x") => (p._1, p._2 + x.toInt)
    case (p, s"up $x") => (p._1, p._2 - x.toInt)
}
println(loc1._1 * loc1._2)

val loc2 = course.foldLeft((0, 0, 0)) {
    case (p, s"forward $x") => (p._1 + x.toInt, p._2 + p._3*x.toInt, p._3)
    case (p, s"down $x") => (p._1, p._2, p._3 + x.toInt)
    case (p, s"up $x") => (p._1, p._2, p._3 - x.toInt)
}
println(loc2._1 * loc2._2)
