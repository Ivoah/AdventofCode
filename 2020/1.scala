#!/bin/sh
exec scala "$0" "$@"
!#

import scala.io.Source

val report = Source.fromFile("1.in").getLines().map(_.toInt).toSeq

for (c <- 2 to 3) {
  val sum = report.combinations(c).filter(_.sum == 2020).next()
  println(sum.product)
}
