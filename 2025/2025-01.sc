import scala.io.Source

val instructions = Source.fromFile("1.txt")
  .getLines
  .map(_.replace("L", "-").replace("R", "").toInt)
  .toSeq

val positions = instructions
  .scanLeft(51) {
    case (dial, r) => Math.floorMod(dial + r, 100)
  }

val allPositions = instructions
  .flatMap(r => Seq.fill(math.abs(r))(math.signum(r)))
  .scanLeft(51) {
    case (dial, r) => Math.floorMod(dial + r, 100)
  }

println(positions.count(_ == 1))
println(allPositions.count(_ == 1))
