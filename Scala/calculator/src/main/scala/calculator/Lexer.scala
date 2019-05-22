package calculator

import scala.util.matching.Regex

// Lexical Analyzer
class Lexer {
  // Types of chars in the input
  object SymbolType extends Enumeration {
    val Space, End, SimpleToken, ComplexToken, Unknown = Value
  }

  // States of Finite-state Machine
  object State extends Enumeration {
    val Ready, Proceed = Value
  }

  // One-symbol tokens
  private val simple: Map[Char, Token] = Map (
    '\'' -> Token(TokenType.Operator, "derivative"),
    '^' -> Token(TokenType.Operator, "power"),
    '*' -> Token(TokenType.Operator, "multiply"),
    '/' -> Token(TokenType.Operator, "divide"),
    '+' -> Token(TokenType.Operator, "plus"),
    '-' -> Token(TokenType.Operator, "minus"),
    '=' -> Token(TokenType.Operator, "assign"),
    '(' -> Token(TokenType.Symbol, "left_parenthesis"),
    ')' -> Token(TokenType.Symbol, "right_parenthesis"),
    ';' -> Token(TokenType.Symbol, "semi-colon")
  )

  // Known tokens: function and commands
  private val functions: List[String] = List("exp", "log", "log10", "pow", "acos", "asin", "atan", "cos", "sin", "tan", "cosh", "sinh", "tanh")
  private val commands: List[String] = List("quit", "exit")

  // Check char for type
  private def checkChar(c: Char): SymbolType.Value = {
    // Chars for separate, start and continue tokens
    val space: Array[Char] = Array (' ', '\t')
    val end: Array[Char] = Array ('\n', '\r')
    val simpleToken: Array[Char] = simple.keys.toArray
    val complexToken: Array[Char] = ('a' to 'z').toArray ++ ('A' to 'Z') ++ ('0' to '9') ++ Array('_', '.')

    if (space.contains(c)) SymbolType.Space
    else if (end.contains(c)) SymbolType.End
    else if (simpleToken.contains(c)) SymbolType.SimpleToken
    else if (complexToken.contains(c)) SymbolType.ComplexToken
    else SymbolType.Unknown
  }

  // Check string for token
  private def checkToken(s: String): Token = {
    // Identifiers and Numbers
    val identifier: Regex = "[_a-zA-Z][_a-zA-Z0-9]*".r
    val number: Regex = """\d+|\d+\.\d*|\.\d+""".r

    s match {
      case identifier(_*) =>
        if (constants.keys.toList.contains(s)) Token(TokenType.Constant, s)
        else if (functions.contains(s)) Token(TokenType.Function, s)
        else if (commands.contains(s)) Token(TokenType.Command, s)
        else Token(TokenType.Identifier, s)
      case number(_*) => Token(TokenType.Number, s.toDouble)
      case _ => Token(TokenType.Unknown, s)
    }
  }

  def parse(input: String): List[Token] = {
    // Proceed chars from the input string one by one
    // Initialize State, Buffer and Result
    var state: State.Value = State.Ready // current state of Finite-state Machine
    var buffer: Array[Char] = Array() // buffer for "long" token
    var result: List[Token] = List() // list with parsed tokens
    val endToken: Token = Token(TokenType.End, "") // Token for end of line or input
    def unknownToken(s: String): Token = Token(TokenType.Unknown, s) // Token for unknown input

    // Proceed chars from the input string one by one
    input.toArray.foreach { c =>
      state match {
        case State.Ready => checkChar(c) match {
          case SymbolType.Space =>
          case SymbolType.End => result :+= endToken
          case SymbolType.SimpleToken => result :+= simple(c)
          case SymbolType.ComplexToken => state = State.Proceed
            buffer = Array(c)
          case SymbolType.Unknown => result :+= unknownToken(c.toString)
        }
        case State.Proceed => checkChar(c) match {
          case SymbolType.Space => state = State.Ready
            val s = buffer.mkString ("")
            buffer = Array()
            result :+= checkToken(s)
          case SymbolType.End => state = State.Ready
            val s = buffer.mkString("")
            buffer = Array()
            result ++= List(checkToken(s), endToken)
          case SymbolType.SimpleToken => state = State.Ready
            val s = buffer.mkString("")
            buffer = Array()
            result ++= List(checkToken(s), simple(c))
          case SymbolType.ComplexToken => buffer :+= c
          case SymbolType.Unknown => state = State.Ready
            val s = buffer.mkString("")
            buffer = Array()
            result ++= List(checkToken(s), unknownToken(c.toString))
        }
      }
    }
    // Return list of tokens
    if (state == State.Ready) result :+ endToken
    else {
      val s = buffer.mkString("")
      result ++ List(checkToken(s), endToken)
    }
  }
}

object Lexer {
  def apply() = new Lexer
}
