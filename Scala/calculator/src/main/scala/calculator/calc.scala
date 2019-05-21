package calculator

object calc extends App {
  args.foreach { s =>
    println(s)
    lexer(s).foreach(l => println(l._1.toString, l._2))
  }
}
