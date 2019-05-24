package calculator

import scala.util.matching.Regex

// Lexical Analyzer
class Lexer {
  // Types of chars in the input
  private object Symbol extends Enumeration {
    val Space, End, Operator, Symbol, Complex, Unknown = Value
  }

  // States of Finite-state Machine
  private object State extends Enumeration {
    val Ready, Proceed, Skip = Value
  }

  // Check char for type
  private def checkChar(c: Char): Symbol.Value = {
    // Chars for separate tokens, one-symbol tokens and symbol for start, continue and stop token
    val space: Array[Char] = Array (' ', '\t')
    val end: Array[Char] = Array ('\n', '\r')
    val operator: List[Char] = operators.map(_.symbol)
    val symbol: List[Char] = symbols.map(_.symbol)
    val complex: List[Char] = (('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')).toList ++ List('_', '.')

    if (space.contains(c)) Symbol.Space
    else if (end.contains(c)) Symbol.End
    else if (operator.contains(c)) Symbol.Operator
    else if (symbol.contains(c)) Symbol.Symbol
    else if (complex.contains(c)) Symbol.Complex
    else Symbol.Unknown
  }

  // Check string for token
  private def checkToken(buffer: Array[Char]): Token = {
    // Identifiers and Numbers
    val identifier: Regex = "[_a-zA-Z][_a-zA-Z0-9]*".r
    val number: Regex = """\d+|\d+\.\d*|\.\d+""".r
    val s = buffer.mkString ("")

    s match {
      case identifier(_*) =>
        if (constants.map(_.name).contains(s)) constants.find(_.name == s).get
        else if (functions.map(_.name).contains(s)) functions.find(_.name == s).get
        else if (commands.map(_.name).contains(s)) commands.find(_.name == s).get
        else Identifier(s)
      case number(_*) => Number(s.toDouble)
      case _ =>
        println(s"Warning: unknown token: $s")
        Unknown()
    }
  }

  def parse(input: String): List[Token] = {
    // Initialize State, Buffer and Result
    var state: State.Value = State.Ready // current state of Finite-state Machine
    var buffer: Array[Char] = Array() // buffer for "long" token
    var result: List[Token] = List() // list with parsed tokens
    
    // Char for 'continue' symbol or '/' by default and 'comment'
    val continue: Char = symbols.find(_.name == "continue") match {
      case Some(c) => c.symbol
      case None => '/'
    }
    // Char for 'comment' symbol or '#' by default
    val comment: Char = symbols.find(_.name == "comment") match {
      case Some(c) => c.symbol
      case None => '#'
    }

    // Proceed chars from the input string one by one
    input.toArray.foreach { c =>
      state match {
        case State.Ready => checkChar(c) match {
          case Symbol.Space =>
          case Symbol.End => if (result.last != Continue()) result :+= End()
          case Symbol.Operator => result :+= operators.find(_.symbol == c).get
          case Symbol.Symbol =>
            if (c == comment) state = State.Skip
            else if (c == continue) result :+= Continue()
            else result :+= symbols.find(_.symbol == c).get
          case Symbol.Complex => state = State.Proceed
            buffer = Array(c)
          case Symbol.Unknown => println(s"Warning: unknown token: $c")
        }
        case State.Proceed => checkChar(c) match {
          case Symbol.Space => state = State.Ready
            val token = checkToken(buffer)
            buffer = Array()
            if (token != Unknown()) result :+= token
          case Symbol.End => state = State.Ready
            val token = checkToken(buffer)
            buffer = Array()
            if (token != Unknown()) result ++= List(token, End())
          case Symbol.Operator => state = State.Ready
            val token = checkToken(buffer)
            buffer = Array()
            if (token != Unknown()) result ++= List(token, operators.find(_.symbol == c).get)
          case Symbol.Symbol => state = State.Ready
            val token = checkToken(buffer)
            buffer = Array()
            if (token != Unknown()) result :+= token
            if (c == comment) state = State.Skip
            else if (c == continue) result :+= Continue()
            else result :+= symbols.find(_.symbol == c).get
          case Symbol.Complex => buffer :+= c
          case Symbol.Unknown => println(s"Warning: unknown token: $c")
        }
        case State.Skip => checkChar(c) match {
          case Symbol.End => state = State.Ready
            if (result.last != Continue()) result :+= End()
            buffer = Array()
          case _ =>
        }
      }
    }

    // No more chars in the input
    // Check buffer and return final list of tokens
    state match {
      case State.Skip => result
      case State.Ready => result :+ End()
      case State.Proceed =>
        val token = checkToken(buffer)
        if (token != Unknown()) result ++ List(token, End())
        else result :+ End()
    }
  }
}

object Lexer {
  def apply() = new Lexer()
}
