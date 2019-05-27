package calculator

import scala.io._
import java.io.FileNotFoundException

object calc extends App {
  // Types, values and methods for parsing arguments
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

  // Parse arguments
  val options: Options = parseOptions(Map("-v" -> false, "-i" -> false, "-e" -> false, "-f" -> false), args.toList)

  // Proceed expressions from arguments
  if (options("-e") && expressionsList.nonEmpty) {
    if (options("-v")) println("Proceed expressions")
    expressionsList.foreach { s =>
      if (options("-v")) println(s)
      val tokens: List[Token] = Lexer().parse(s)
      tokens.foreach(t => println(t.toString))
    }
  }

  // Proceed file specified in arguments
  if (options("-f")) {
    if (options("-v")) println(s"Proceed file $expressionsFile")
    val fileSource: BufferedSource = Source.fromFile(expressionsFile)
    try {
      fileSource.getLines.foreach { s =>
        if (options("-v")) println(s)
        val tokens: List[Token] = Lexer().parse(s)
        tokens.foreach(t => println(t.toString))
      }
    } catch {
      case e: FileNotFoundException => println(e.getLocalizedMessage)
    } finally
      fileSource.close()
  }

  // Proceed in interactive mode
  if (options("-i") || (!options("-e") && !options("-f"))) {
    if (options("-v")) println("Proceed from input")
    while (true) {
      val s: String = StdIn.readLine
      if (s != null) {
        println(s)
        val tokens: List[Token] = Lexer().parse(s)
        tokens.foreach(t => println(t.toString))
        if (tokens.contains(Command("quit")) ||
          tokens.contains(Command("exit"))) sys.exit(0)
      } else
        sys.exit(-1)
    }
  }
  sys.exit(0)
}
