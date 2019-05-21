import scala.util.matching.Regex

package object calculator {

  // Token types
  object Token extends  Enumeration {
    val Constant, Operator, Symbol, Identifier, Number, Function, Unknown, End = Value
  }

  // Types of char in the input
  object Symbol extends Enumeration {
    val Space, End, SimpleToken, ComplexToken, Unknown = Value
  }

  // States od Finite-state Machine
  object State extends Enumeration {
    val Ready, Proceed = Value
  }

  def lex(input: String): List[(Token.Value, String)] = {
    // One-symbol tokens
    val simple: Map[Char, (Token.Value, String)] = Map (
      '\'' -> (Token.Operator, "derivative"),
      '^' -> (Token.Operator, "power"),
      '*' -> (Token.Operator, "multiply"),
      '/' -> (Token.Operator, "divide"),
      '+' -> (Token.Operator, "plus"),
      '-' -> (Token.Operator, "minus"),
      '=' -> (Token.Operator, "assign"),
      '(' -> (Token.Symbol, "left_par"),
      ')' -> (Token.Symbol, "right_par"),
      ';' -> (Token.Symbol, "semi-colon")
    )

    // Known tokens: constants and function
    val constants: List[String] = List ("Pi", "E")
    val functions: List[String] = List ("exp", "log", "log10", "pow", "acos", "asin", "atan", "cos", "sin", "tan", "cosh", "sinh", "tanh")

    // Check char to be in known arrays
    def checkChar(c: Char): Symbol.Value = {
      // Chars for separate, start and continue tokens
      val space: Array[Char] = Array (' ', '\t')
      val end: Array[Char] = Array ('\n', '\r')
      val simpleToken: Array[Char] = simple.keys.toArray
      val complexToken: Array[Char] = ('a' to 'z').toArray ++ ('A' to 'Z') ++ ('0' to '9') ++ Array('_', '.')

      if (space.contains(c)) Symbol.Space
      else if (end.contains(c)) Symbol.End
      else if (simpleToken.contains(c)) Symbol.SimpleToken
      else if (complexToken.contains(c)) Symbol.ComplexToken
      else Symbol.Unknown
    }

    // Check string for token
    def checkString(s: String): Token.Value = {
      // Identifiers and Numbers
      val identifier: Regex = "[_a-zA-Z][_a-zA-Z0-9]*".r
      val number: Regex = """\d+|\d+\.\d*|\.\d+""".r

      s match {
        case identifier(_*) =>
          if (constants.contains(s)) Token.Constant
          else if (functions.contains(s)) Token.Function
          else Token.Identifier
        case number(_*) => Token.Number
        case _ => Token.Unknown
      }
    }

    // Proceed chars from the input string one by one
    // Initialize State, Buffer and Result
    var state: State.Value = State.Ready // current state of Finite-state Machine
    var buffer: Array[Char] = Array() // buffer for "long" token
    var result: List[(Token.Value, String)] = List() // list with parsed tokens

    // Proceed chars from the input string one by one
    input.toArray.foreach { c =>
      state match {
        case State.Ready => checkChar(c) match {
          case Symbol.Space =>
          case Symbol.End => result = result ++ List((Token.End, ""))
          case Symbol.SimpleToken => result = result ++ List(simple(c))
          case Symbol.ComplexToken => state = State.Proceed
            buffer = Array(c)
          case Symbol.Unknown => result = result ++ List((Token.Unknown, c.toString))
        }
        case State.Proceed => checkChar(c) match {
          case Symbol.Space => state = State.Ready
            val s = buffer.mkString ("")
            buffer = Array()
            result = result ++ List((checkString(s), s))
          case Symbol.End => state = State.Ready
            val s = buffer.mkString("")
            buffer = Array()
            result = result ++ List((checkString(s), s), (Token.End, ""))
          case Symbol.SimpleToken => state = State.Ready
            val s = buffer.mkString("")
            buffer = Array()
            result = result ++ List((checkString(s), s), simple(c))
          case Symbol.ComplexToken => buffer = buffer ++ Array(c)
          case Symbol.Unknown => state = State.Ready
            val s = buffer.mkString("")
            buffer = Array()
            result = result ++ List((checkString(s), s), (Token.Unknown, c.toString))
        }
      }
    }
    // Return list of tokens
    if (state == State.Ready) result ++ List((Token.End, "") )
    else {
      val s = buffer.mkString("")
      result ++ List((checkString(s), s), (Token.End, ""))
    }
  }

}