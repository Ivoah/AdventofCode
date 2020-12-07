import scala.io.Source

val masses = Source.fromFile("1.txt").getLines().map(_.toInt).toSeq

def fuel_for_mass(mass: Int): Int = {
  val fuel = mass/3 - 2
  if (fuel < 0) 0
  else fuel + fuel_for_mass(fuel)
}

//print(sum(int(m)//3 - 2 for m in open('1.txt').read().split()))
println(masses.map(_/3 - 2).sum)
println(masses.map(fuel_for_mass).sum)
