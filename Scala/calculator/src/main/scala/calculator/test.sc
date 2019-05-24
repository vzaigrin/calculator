val my_sin: Double => Double = (x: Double) => math.sin(x)
my_sin(0)
my_sin(3.141592653589793/2)
my_sin(3.141592653589793)

abstract class Token
case class Constant(name: String, value: Double) extends Token
case class Operator(symbol: Char, name: String) extends Token
case class Symbol(symbol: Char, name: String) extends Token
case class Identifier(name: String) extends Token
case class Number(value: Double) extends Token
case class Function(name: String, fun: Double => Double) extends Token

val constants: List[Constant] = List(
  Constant("Pi", math.Pi),  // 3.141592653589793
  Constant("E", math.E)     // 2.718281828459045
)
val constantsMap = constants.map(c => c.name -> c).toMap
constantsMap.keySet.contains("E")
constantsMap("E")
