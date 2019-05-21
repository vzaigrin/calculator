package calculator

object calc extends App {
  args.foreach { s =>
    println(s)
    lex(s).foreach(l => println(l._1, l._2))
  }
}
