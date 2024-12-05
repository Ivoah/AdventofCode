import scala.io.Source

@main
def main202404() = {
  val dirs = Seq(
    (-1, 0),
    (-1, 1),
    (0, 1),
    (1, 1),
    (1, 0),
    (1, -1),
    (0, -1),
    (-1, -1)
  )
  val search = "XMAS"

  val grid = Source.fromFile("4.txt")
    .getLines
    .map(_.toSeq)
    .toSeq
  
  val matches = (for (r <- grid.indices; c <- grid(r).indices; dir <- dirs) yield {
    search.zipWithIndex.forall {
      case (char, i) => grid.lift(r + dir._1*i).flatMap(_.lift(c + dir._2*i)).map(_ == char).getOrElse(false)
    } match {
      case true => Some((r, c))
      case false => None
    }
  }).flatten
  
  val search2 = Seq(
    Map(
      (-1, -1) -> 'M',
      (-1, 1) -> 'S',
      (0, 0) -> 'A',
      (1, 1) -> 'S',
      (1, -1) -> 'M',
    ),
    Map(
      (-1, -1) -> 'M',
      (-1, 1) -> 'M',
      (0, 0) -> 'A',
      (1, 1) -> 'S',
      (1, -1) -> 'S',
    ),
    Map(
      (-1, -1) -> 'S',
      (-1, 1) -> 'S',
      (0, 0) -> 'A',
      (1, 1) -> 'M',
      (1, -1) -> 'M',
    ),
    Map(
      (-1, -1) -> 'S',
      (-1, 1) -> 'M',
      (0, 0) -> 'A',
      (1, 1) -> 'M',
      (1, -1) -> 'S',
    )
  )

  val matches2 = (for (r <- grid.indices; c <- grid(r).indices) yield {
    search2.exists(_.forall {
      case ((rr, cc), char) => grid.lift(r + rr).flatMap(_.lift(c + cc)).map(_ == char).getOrElse(false)
    }) match {
      case true => Some((r, c))
      case false => None
    }
  }).flatten

  println(matches.length)
  println(matches2.length)
}
