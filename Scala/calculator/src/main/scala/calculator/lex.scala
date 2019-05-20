package calculator

import State._
import scala.util.matching.Regex

// Lexical Analyzer

class lex {
  // Current state of Finite-state Machine
  var state: Value = Ready

  // Token: type and name
  type Token = (String, String)

  var buffer: Array[Char] = Array() // buffer for "long" token
  var result: List[Token] = List() // list with tokens

  // Known tokens
  val constants: List[String] = List("Pi", "E")
  val functions: List[String] = List("exp", "log", "log10", "pow", "acos", "asin", "atan", "cos", "sin", "tan", "cosh", "sinh", "tanh")
  val simple: Map[Char, Token] = Map(
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

  // Variable and Numbers
  val variable: Regex = "[_a-zA-Z][_a-zA-Z0-9]*".r
  val number: Regex = "[0-9]*".r
  val numberDot: Regex = "([0-9]*)\\.([0-9]*)".r

  // Chars for separate, start and continue tokens
  val space: Array[Char] = Array(' ', '\t', '\n', '\r')
  val simpleToken: Array[Char] = Array('\'', '^', '*', '/', '+', '-', '=', '(', ')', ';', '(', ')', ';')
  val startToken: Array[Char] = ('a' to 'z').toArray ++ ('A' to 'Z') ++ Array('_')
  val continueToken: Array[Char] = startToken ++ ('0' to '9') ++ Array('.')

  // Check char to be in known arrays
  def checkChar(c: Char): String = {
    if (space.contains(c)) "space"
    else if (simpleToken.contains(c)) "simpleToken"
    else if (startToken.contains(c)) "startToken"
    else if (continueToken.contains(c)) "continueToken"
    else "unknown"
  }

  // Parse input string to List of tokens
  def parse(input: String): List[Token] = {
    // Initialize State, Buffer and Result
    state = Ready
    buffer = Array()
    result = List()

    // Proceed chars from the input string one by one
    input.toArray.foreach { c =>
      state match {
        case Ready => checkChar(c) match {
          case "simpleToken" => result = result ++ List(simple(c))
          case "startToken" => state = Proceed
            buffer = Array(c)
          case "unknown" => result = result ++ List(("unknown", c.toString))
        }
        case Proceed => println("Ready")
      }
    }
    // Return list of tokens
    result
  }
}
