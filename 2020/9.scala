import scala.io.Source

val numbers = Source.fromFile("9.txt").getLines().map(_.toLong).toSeq
val invalid = numbers.sliding(26).find { s =>
  !s.slice(0, 25).combinations(2).exists(p => s.last == p.head + p.last)
}.get.last
println(invalid)

var (low, high) = (0, 2)
var found = false
while (!found) {
  if (numbers.slice(low, high).sum == invalid) found = true
  else if (numbers.slice(low, high).sum < invalid) high += 1
  else if (numbers.slice(low, high).sum > invalid) {
    low += 1
    if (high - low == 1) high += 1
  }
}
val slice = numbers.slice(low, high)
println(slice.min + slice.max)
