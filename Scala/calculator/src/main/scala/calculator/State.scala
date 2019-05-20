package calculator

// Finite-state Machine states
object State extends Enumeration {
  type State = Value
  val Ready, Proceed = Value
}
