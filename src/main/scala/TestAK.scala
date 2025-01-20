object TestAK {
  def main(args: Array[String]): Unit = {
    // Create a new ScalAT instance
    val solver = new ScalAT()
    val nliterals = 10

    // Define literals
    val literals = solver.newVarArray(nliterals) // Variables: a, b, c, d
    println(s"Literals: ${literals.mkString(", ")}")

    // Add Exactly-K constraint with K = 2
    solver.addEK(literals.toList, 5)

    // Solve the problem
    val result = solver.solve()

    // Print the result
    println(result)

    for (v <- literals) {
      print(if (solver.getValue(v)) "1 " else "0 ")
    }
  }
}
