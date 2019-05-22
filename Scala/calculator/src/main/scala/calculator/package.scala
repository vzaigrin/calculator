import scala.util.matching.Regex

package object calculator {

  // Token
  case class Token(tt: TokenType.Value, value: Any)

  // Token types
  object TokenType extends Enumeration {
    val Constant, Operator, Symbol, Identifier, Number, Function, Command, Unknown, End = Value
  }

  // Table for known Constants
  val constants: Map[String, Double] = Map("Pi" -> 3.141592653589793, "E" -> 2.718281828459045)
}
