package calculator

import org.scalatest.FunSuite

class LexerTest extends FunSuite {
  test("Lexer.parse simple expression") {
    assert(Lexer().parse("x = y * 2") === List(
      Identifier("x"), Operator('=', "assign"), Identifier("y"), Operator('*', "multiply"), Number(2.0), End()
    ))
  }

  test("Lexer.parse multiline expression") {
    assert(Lexer().parse("y = 2 * Pi\n x = y + 2") === List(
      Identifier("y"), Operator('=', "assign"), Number(2.0), Operator('*', "multiply"), Constant("Pi", math.Pi), End(),
      Identifier("x"), Operator('=', "assign"), Identifier("y"), Operator('+', "plus"), Number(2.0), End()
    ))
  }

  test("Lexer.parse multiline expression with comments") {
    assert(Lexer().parse("y = 2 * Pi # first line \n x = y + 2# second line") === List(
      Identifier("y"), Operator('=', "assign"), Number(2.0), Operator('*', "multiply"), Constant("Pi", math.Pi), End(),
      Identifier("x"), Operator('=', "assign"), Identifier("y"), Operator('+', "plus"), Number(2.0), End()
    ))
  }

  test("Lexer.parse multiline expression with continue") {
    assert(Lexer().parse("y = 2 * Pi \\ \n + 2") === List(
      Identifier("y"), Operator('=', "assign"), Number(2.0), Operator('*', "multiply"), Constant("Pi", math.Pi),
      Continue(), Operator('+', "plus"), Number(2.0), End()
    ))
  }

  test("Lexer.parse multiline expression with continue and comment") {
    assert(Lexer().parse("y = 2 * Pi \\# first line\\\n + 2 # second line") === List(
      Identifier("y"), Operator('=', "assign"), Number(2.0), Operator('*', "multiply"), Constant("Pi", math.Pi),
      Continue(), Operator('+', "plus"), Number(2.0), End()
    ))
  }
}
