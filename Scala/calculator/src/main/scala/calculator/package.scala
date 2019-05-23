package object calculator {
  // Known tokens
  val constants: List[Constant] = List(
    Constant("Pi", 3.141592653589793),
    Constant("E", 2.718281828459045)
  )
  val operators: List[Operator] = List(
    Operator('\'', "derivative"),
    Operator('^', "power"),
    Operator('*', "multiply"),
    Operator('/', "divide"),
    Operator('+', "plus"),
    Operator('-', "minus"),
    Operator('=', "assign")
  )
  val symbols: List[Symbol] = List(
    Symbol('(', "left_parenthesis"),
    Symbol(')', "right_parenthesis"),
    Symbol(';', "semi-colon"),
    Symbol('\\', "continue"),
    Symbol('#', "comment")
  )
  val functions: List[Function] = List(
    Function("exp"),
    Function("log"),
    Function("log10"),
    Function("pow"),
    Function("acos"),
    Function("asin"),
    Function("atan"),
    Function("cos"),
    Function("sin"),
    Function("tan"),
    Function("cosh"),
    Function("sinh"),
    Function("tanh")
  )
  val commands = List(Command("quit"), Command("exit"))
}
