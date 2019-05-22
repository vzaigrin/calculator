package calculator

import scala.io._

object calc extends App {
  type Options = Map[String, Boolean]
  var expressionsList: List[String] = List()
  var expressionsFile: String = ""

  @scala.annotation.tailrec
  def parseOptions(options: Options, args: List[String]): Options = {
    args match {
      case "-v" :: tail => parseOptions(options + ("-v" -> true), tail)
      case "-i" :: tail => parseOptions(options + ("-i" -> true), tail)
      case "-e" :: expr :: tail => expressionsList :+= expr
        parseOptions(options + ("-e" -> true), tail)
      case "-f" :: file :: tail => expressionsFile = file
        parseOptions(options + ("-f" -> true), tail)
      case _ => options
    }
  }

  val options: Options = parseOptions(Map("-v" -> false, "-i" -> false, "-e" -> false, "-f" -> false), args.toList)

  if (options("-e") && expressionsList.nonEmpty) {
    if (options("-v")) println("Proceed expressions")
    expressionsList.foreach { s =>
      if (options("-v")) println(s)
      lexer(s).foreach(t => println(t.tt, t.value))
    }
  }

  if (options("-f")) {
    if (options("-v")) println(s"Proceed file $expressionsFile")
    //noinspection SourceNotClosed
    Source.fromFile(expressionsFile).getLines.foreach{ s =>
      if (options("-v")) println(s)
      lexer(s).foreach(t => println(t.tt, t.value))
    }
  }

  sys.exit(0)
}
