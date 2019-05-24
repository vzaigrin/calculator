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
    Function("exp", math.exp),
    Function("log", math.log),
    Function("log10", math.log10),
    Function("acos", math.acos),
    Function("asin", math.asin),
    Function("atan", math.atan),
    Function("cos", math.cos),
    Function("sin", math.sin),
    Function("tan", math.tan),
    Function("cosh", math.cosh),
    Function("sinh", math.sinh),
    Function("tanh", math.tanh)
  )
  val functionsMap: Map[String, Function] = functions.map(c => c.name -> c).toMap

  val commands = List(Command("quit"), Command("exit"))
}
