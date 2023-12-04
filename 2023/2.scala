import scala.io.Source

val REDS = 12
val GREENS = 13
val BLUES = 14

@main
def main() = {
    println(Source.fromFile("2.txt").getLines.flatMap {
        case s"Game $id: $draws" =>
            if(draws.split("; ").forall { draw =>
                draw.split(", ").forall {
                    case s"$n red" => n.toInt <= REDS
                    case s"$n green" => n.toInt <= GREENS
                    case s"$n blue" => n.toInt <= BLUES
                }
            }) Some(id.toInt)
            else None
    }.sum)

    println(Source.fromFile("2.txt").getLines.map {
        case s"Game $id: $draws" =>
            draws.split("; ").foldLeft(Map("red" -> 0, "green" -> 0, "blue" -> 0)) { (counts, draw) =>
                counts ++ draw.split(", ").map { case s"$n $c" => c -> math.max(n.toInt, counts(c)) }.toMap
            }.values.product
    }.sum)
}
