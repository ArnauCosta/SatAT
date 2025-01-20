object TestAMK {
  def main(args: Array[String]): Unit = {
    // Create a new ScalAT instance
    val solver = new ScalAT()
    val nliterals = 10
    // Define literals
    val literals = solver.newVarArray(nliterals) // Variables a, b, c, d
    println(s"Literals: ${literals.mkString(", ")}")


    val literalToFix = literals(8) // Fixem un literal a true
    solver.addClause(List(literalToFix))

    val literalToFix2 = literals(2) // Fixem un altre literal a true
    solver.addClause(List(literalToFix2))

    // Si es descomenta passa a ser no satisfactible.
//    val literalToFixToUnsat = literals(9)
//    solver.addClause(List(literalToFixToUnsat))

    // Add At-Most-K constraint with K = 2
    solver.addAMK(literals.toList, 2)

    // Solve the problem
    val result = solver.solve()

    // Print the result
    println(result)

    for (v <- literals) {
      print(if (solver.getValue(v)) "1 " else "0 ")
    }
  }
}
