package object calculator {
  // Known tokens

  val constants: List[Constant] = List(
    Constant("Pi", math.Pi),  // 3.141592653589793
    Constant("E", math.E)     // 2.718281828459045
  )
  val constantsMap: Map[String, Constant] = constants.map(c => c.name -> c).toMap

  val operators: List[Operator] = List(
    Operator('\'', "derivative"),
    Operator('^', "power"),
    Operator('*', "multiply"),
    Operator('/', "divide"),
    Operator('+', "plus"),
    Operator('-', "minus"),
    Operator('=', "assign")
  )
  val operatorsMap: Map[Char, Operator] = operators.map(c => c.symbol -> c).toMap

  val symbols: List[Symbol] = List(
    Symbol('(', "left_parenthesis"),
    Symbol(')', "right_parenthesis"),
    Symbol(';', "separate"),
    Symbol('\\', "continue"),
    Symbol('#', "comment")
  )
  val symbolsMap: Map[Char, Symbol] = symbols.map(c => c.symbol -> c).toMap

  val functions: List[Function] = List(
    Function("exp", (x: Double) => math.exp(x)),
    Function("log", (x: Double) => math.log(x)),
    Function("log10", (x: Double) => math.log10(x)),
    Function("acos", (x: Double) => math.acos(x)),
    Function("asin", (x: Double) => math.asin(x)),
    Function("atan", (x: Double) => math.atan(x)),
    Function("cos", (x: Double) => math.cos(x)),
    Function("sin", (x: Double) => math.sin(x)),
    Function("tan", (x: Double) => math.tan(x)),
    Function("cosh", (x: Double) => math.cosh(x)),
    Function("sinh", (x: Double) => math.sinh(x)),
    Function("tanh", (x: Double) => math.tanh(x))
  )
  val functionsMap: Map[String, Function] = functions.map(c => c.name -> c).toMap

  val commands = List(Command("quit"), Command("exit"))
}
