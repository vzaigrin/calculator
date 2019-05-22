package calculator

class Token(val tt: TokenType.Value, val value: Any) {
  def isUnknown: Boolean = tt == TokenType.Unknown
}

object Token {
  def apply(tt: TokenType.Value, value: Any) = new Token(tt, value)
}
