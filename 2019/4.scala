println((165432 to 707912).count { pw =>
  pw.toString.toSeq.sliding(2).forall{case Seq(a, b) => a.toInt <= b.toInt} &&
    pw.toString.toSeq.groupBy(c => c).values.map(_.length).toSeq.exists(_ >= 2)
})
println((165432 to 707912).count { pw =>
  pw.toString.toSeq.sliding(2).forall{case Seq(a, b) => a.toInt <= b.toInt} &&
    pw.toString.toSeq.groupBy(c => c).values.map(_.length).toSeq.contains(2)
})
