import scala.util.matching.Regex
val variable: Regex = "[_a-zA-Z][_a-zA-Z0-9]*".r
val number: Regex = "[0-9]*".r
val numberDot: Regex = "([0-9]*)\\.([0-9]*)".r

"t" match {
  case variable(_*) => println("match variable")
  case number(_*) => println("match number")
  case numberDot(_*) => println("match number with dot")
  case _ => println("not match")
}

var result: List[String] = List()

result = result ++ List("1")
result = result ++ List("2")
result = List()

"String\tone more".toArray

val operators: Array[Char] = Array('\'', '^', '*', '/', '+', '-', '=', '(', ')', ';')
val symbols: Array[Char] = Array('(', ')', ';')
val space: Array[Char] = Array(' ', '\t', '\n', '\r')
val simpleToken: Array[Char] = operators ++ symbols
val startToken: Array[Char] = ('a' to 'z').toArray ++ ('A' to 'Z') ++ Array('_')
val continueToken: Array[Char] = startToken ++ ('0' to '9') ++ Array('.')

