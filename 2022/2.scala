object Shape extends Enumeration {
  protected case class ShapeVal(score: Int) extends super.Val {
    def outcome(other: ShapeVal): Outcome.Value = {
      if (this == other) Outcome.Draw
      else if (this == Rock && other == Sissors) Outcome.Win
      else if (this == Sissors && other == Rock) Outcome.Loss
      else if (this > other) Outcome.Win
      else Outcome.Loss
    }
  }
  import scala.language.implicitConversions
  implicit def valueToShapeVal(x: Value): ShapeVal = x.asInstanceOf[ShapeVal]

  val Rock = ShapeVal(1)
  val Paper = ShapeVal(2)
  val Sissors = ShapeVal(3)
}

object Outcome extends Enumeration {
  protected case class OutcomeVal(score: Int) extends super.Val
  import scala.language.implicitConversions
  implicit def valueToOutcomeVal(x: Value): OutcomeVal = x.asInstanceOf[OutcomeVal]

  val Win = OutcomeVal(6)
  val Loss = OutcomeVal(0)
  val Draw = OutcomeVal(3)
}

@main
def main() = {
  val moves = scala.io.Source.fromFile("2.txt").getLines().map {
    case s"$opponent $me" => (
      Map("A" -> Shape.Rock, "B" -> Shape.Paper, "C" -> Shape.Sissors)(opponent),
      Map("X" -> Shape.Rock, "Y" -> Shape.Paper, "Z" -> Shape.Sissors)(me),
    )
  }.toSeq
  println(moves.map { case (opponent, me) => me.score + me.outcome(opponent).score }.sum)

  val moves2 = scala.io.Source.fromFile("2.txt").getLines().map {
    case s"$opponent $me" =>
      val opponentShape = Map("A" -> Shape.Rock, "B" -> Shape.Paper, "C" -> Shape.Sissors)(opponent)
      (
        opponentShape,
        Shape.values.find(_.outcome(opponentShape) == Map("X" -> Outcome.Loss, "Y" -> Outcome.Draw, "Z" -> Outcome.Win)(me)).get
      )
  }.toSeq
  println(moves2.map { case (opponent, me) => me.score + me.outcome(opponent).score }.sum)
  
}
