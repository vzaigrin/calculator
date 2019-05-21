import calculator.State.{Proceed, Ready}
import scala.util.matching.Regex

// One-symbol tokens
val simple: Map[Char, (String, String)] = Map (
  '\'' -> ("op", "derivative"),
  '^' -> ("op", "power"),
  '*' -> ("op", "multiply"),
  '/' -> ("op", "divide"),
  '+' -> ("op", "plus"),
  '-' -> ("op", "minus"),
  '=' -> ("op", "assign"),
  '(' -> ("sym", "left_par"),
  ')' -> ("sym", "right_par"),
  ';' -> ("sym", "semi-colon")
)

// Check char to be in known arrays
def checkChar(c: Char): String = {
  // Chars for separate, start and continue tokens
  val space: Array[Char] = Array (' ', '\t')
  val end: Array[Char] = Array ('\n', '\r')
  val simpleToken: Array[Char] = simple.keys.toArray
  val complexToken: Array[Char] = ('a' to 'z').toArray ++ ('A' to 'Z') ++ ('0' to '9') ++ Array('_', '.')

  if (space.contains(c)) "space"
  else if (end.contains(c)) "end"
  else if (simpleToken.contains(c)) "simpleToken"
  else if (complexToken.contains(c)) "complexToken"
  else "unknown"
}

// Check string for token
def checkString(s: String): String = {
  // Variable and Numbers
  val variable: Regex = "[_a-zA-Z][_a-zA-Z0-9]*".r
  val number: Regex = "[0-9]+".r
  val numberDot: Regex = "([0-9]*)\\.([0-9]*)".r

  // Known tokens: constants and function
  val constants: List[String] = List ("Pi", "E")
  val functions: List[String] = List ("exp", "log", "log10", "pow", "acos", "asin", "atan", "cos", "sin", "tan", "cosh", "sinh", "tanh")

  s match {
    case variable(_*) =>
      if (constants.contains(s)) "constant"
      else if (functions.contains(s)) "function"
      else "variable"
    case number(_*) => "number"
    case numberDot(_*) => "number"
    case _ => "unknown"
  }
}

// Parse input string to List of tokens
def parse(input: String): List[(String, String)] = {
  // Initialize State, Buffer and Result
  var state = Ready // current state of Finite-state Machine
  var buffer: Array[Char] = Array() // buffer for "long" token
  var result: List[(String, String)] = List() // list with parsed tokens

  // Proceed chars from the input string one by one
  input.toArray.foreach { c =>
    state match {
      case Ready => checkChar(c) match {
        case "end" => result = result ++ List(("end", ""))
        case "simpleToken" => result = result ++ List(simple(c))
        case "complexToken" => state = Proceed
          buffer = Array(c)
        case "unknown" => result = result ++ List(("unknown", c.toString))
        case _ =>
      }
      case Proceed => checkChar(c) match {
        case "space" => state = Ready
          val s = buffer.mkString ("")
          buffer = Array()
          result = result ++ List((checkString(s), s))
        case "end" => state = Ready
          val s = buffer.mkString("")
          buffer = Array()
          result = result ++ List((checkString(s), s), ("end", ""))
        case "simpleToken" => state = Ready
          val s = buffer.mkString("")
          buffer = Array()
          result = result ++ List((checkString(s), s), simple(c))
        case "complexToken" => buffer = buffer ++ Array(c)
        case "unknown" => state = Ready
          val s = buffer.mkString("")
          buffer = Array()
          result = result ++ List((checkString(s), s), ("unknown", c.toString))
      }
    }
  }
  // Return list of tokens
  if (state == Ready) result ++ List (("end", "") )
  else {
    val s = buffer.mkString("")
    result ++ List((checkString(s), s), ("end", ""))
  }
}

val r = parse("y = 2*x+Pi^4 + z + 2")
r.foreach(tv => println(tv._1, tv._2))
