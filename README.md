This is umbrella project for command-line calculator.  
I will use it for studying different computer languages.

Calculator should read input (and execute expressions) in the interactive mode or from the file.

**Command line arguments**:
- -i - interactive mode (default) - read and execute expressions until EOF or command 'quit' or 'exit'
- -e expression - execute expression and exit
- -f file - read and execute expressions from the file and exit or proceed to interactive mode if *-i* option is provided

## Input Specification

**Constants**
- Pi = 3.141592653589793
- E = 2.718281828459045

**Operators**
- ' - derivative
- ^ - power
- \* - multiply
- / - divide
- \+ - plus
- \- - minus
- = - assignment

**Symbols**
- ( - left parenthesis
- ) - right parenthesis
- ; - semi-colon

**Identifier**: [_a-zA-Z][_a-zA-Z0-9]\*

**Number**: [0-9][.0-9]\*

**Functions**
- exp - exponential
- log - natural logarithm
- log10 - base 10 logarithm 
- pow - power
- acos - arc cosine
- asin - arc sine
- atan - arc tangent
- cos - cosine
- sin - sine
- tan - tangent
- cosh - hyperbolic cosine
- sinh - hyperbolic sine
- tanh - hyperbolic tangent

**Commands**
- quit - stop and exit
- exit - stop and exit

# Tasks

To achieve its goals, the calculator should provide the following steps:
- Lexical analysis - convert input expression into list of tokens
- Syntax analysis - transform a list of tokens (from the Lexical analyzer) into a Syntax tree, based on a grammar
- Code generation - translate the output of the Syntax analyzer into lower level code (stack machine)


## Lexical Analyzer

For lexical analysis I will use **Finite-state machine** with two states:
- ready - ready for a new token, no symbols in cache
- process - in the process of identifying token (has symbols in cache)
