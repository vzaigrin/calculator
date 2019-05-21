import scala.util.matching.Regex

val number: Regex = """\d+|\d+\.\d*|\.\d+""".r
".5" match {
  case number(_*) => println("match")
  case _ => println("not match")
}

".5".toDouble
