package calculator

abstract class Token
case class Constant(name: String, value: Double) extends Token
case class Operator(symbol: Char, name: String) extends Token
case class Symbol(symbol: Char, name: String) extends Token
case class Identifier(name: String) extends Token
case class Number(value: Double) extends Token
case class Function(name: String) extends Token
case class Command(name: String) extends Token
case class Continue() extends Token
case class Unknown() extends Token
case class End() extends Token
