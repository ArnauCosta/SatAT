object TestALK {
  def main(args: Array[String]): Unit = {
    // Create a new ScalAT instance
    val solver = new ScalAT()
    val nliterals = 3

    // Define literals
    val literals = solver.newVarArray(nliterals) // Variables: a, b, c, d
    println(s"Literals: ${literals.mkString(", ")}")

//    val literalToFix1 = literals(2) // Fixem un altre literal a true
//    solver.addClause(List(-literalToFix1))

//    val literalToFix2 = literals(1) // Fixem un altre literal a true
//    solver.addClause(List(-literalToFix2))

    val literalToFix2 = literals(2) // Fixem un altre literal a true
    solver.addClause(List(literalToFix2))

    val literalToFix = literals(1) // Fixem un altre literal a true
    solver.addClause(List(literalToFix))

    val literalToFix3 = literals(0) // Fixem un altre literal a true
    solver.addClause(List(literalToFix3))

    solver.addALK(literals.toList, 2)

    val result = solver.solve()

    println(result)

    for (v <- literals) {
      print(if (solver.getValue(v)) "1 " else "0 ")
    }

  }
}
