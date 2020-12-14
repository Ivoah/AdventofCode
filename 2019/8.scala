import scala.io.Source

val (width, height) = (25, 6)
val layers = Source.fromFile("8.txt").mkString.strip.map(_.asDigit).grouped(width).grouped(height).toSeq

val layer = layers.minBy(_.map(_.count(_ == 0)).sum)
println(layer.map(_.count(_ == 1)).sum * layer.map(_.count(_ == 2)).sum)

val picture = layers.transpose.map(_.transpose.map(_.foldRight(2)((p, s) => if (p == 2) s else p)))
println(picture.map(_.map(Seq(' ', '#')).mkString).mkString("\n"))
