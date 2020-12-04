import scala.io.Source

val kvpair = raw"(\S+):(\S+)".r
val cm = raw"(\d+)cm".r
val in = raw"(\d+)in".r

val validators = Map(
  // (Birth Year) - four digits; at least 1920 and at most 2002.
  "byr" -> ((byr: String) => byr.toIntOption.exists(y => 1920 <= y && y <= 2002)),
  // (Issue Year) - four digits; at least 2010 and at most 2020.
  "iyr" -> ((iyr: String) => iyr.toIntOption.exists(y => 2010 <= y && y <= 2020)),
  // (Expiration Year) - four digits; at least 2020 and at most 2030.
  "eyr" -> ((eyr: String) => eyr.toIntOption.exists(y => 2020 <= y && y <= 2030)),
  // (Height) - a number followed by either cm or in:
  // If cm, the number must be at least 150 and at most 193.
  // If in, the number must be at least 59 and at most 76.
  "hgt" -> ((hgt: String) => hgt match {
    case cm(h) => 150 <= h.toInt && h.toInt <= 193
    case in(h) => 59 <= h.toInt && h.toInt <= 76
    case _ => false
  }),
  // (Hair Color) - a # followed by exactly six characters 0-9 or a-f.
  "hcl" -> ((hcl: String) => hcl.matches("#[0-9a-f]{6}")),
  // (Eye Color) - exactly one of: amb blu brn gry grn hzl oth.
  "ecl" -> ((ecl: String) => Set("amb", "blu", "brn", "gry", "grn", "hzl", "oth").contains(ecl)),
  // (Passport ID) - a nine-digit number, including leading zeroes.
  "pid" -> ((pid: String) => pid.matches("\\d{9}")),
)

val passports = Source.fromFile("4.in")
  .mkString
  .split("\n\n")
  .map(_.split('\n').mkString(" "))
  .map(_.split(' ').map{case kvpair(k, v) => (k, v)}.toMap)

def valid(passport: Map[String, String]) = validators.keySet subsetOf passport.keySet
def valid_strict(passport: Map[String, String]) = validators.forall{case (k, v) => passport.get(k).exists(v)}

println(passports.count(valid))
println(passports.count(valid_strict))
