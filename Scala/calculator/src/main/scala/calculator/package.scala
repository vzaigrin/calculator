package object calculator {
  // Token types
  object TokenType extends Enumeration {
    val Constant, Operator, Symbol, Identifier, Number, Function, Command, Unknown, End = Value
  }

  // Table for known Constants
  val constants: Map[String, Double] = Map("Pi" -> 3.141592653589793, "E" -> 2.718281828459045)

  // Check list of Tokens for Unknown tokens
  def checkTokens(tokens: List[Token]): Unit =
    if (tokens.exists(_.isUnknown)) {
      println("Error: unknown tokens")
      tokens.filter(_.isUnknown).foreach(t => println(t.value))
      sys.exit(-1)
    }
}
