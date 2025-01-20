object TestAMOLog {
  def main(args: Array[String]): Unit = {
    // Crear una instància de ScalAT
    val solver = new ScalAT()

    val nliterals = 10

    // Definir literals (variables booleans)
    val literals = solver.newVarArray(nliterals)

//    val literalToFix = literals(5) // Fixem un literal a true
//    solver.addClause(List(literalToFix))

     // Si es descomenta passa a ser no satisfactible.
//    val literalToFixToUnsat = literals(2) // Fixem un altre literal a true
//    solver.addClause(List(literalToFixToUnsat))
//
//    val literalToFixToUnsat2 = literals(6) // Fixem un altre literal a true
//    solver.addClause(List(literalToFixToUnsat2))

    // Imprimir literals per identificació
    println(s"Literals: ${literals.mkString(", ")}")

    // Afegir la restricció At-Most-One amb codificació logarítmica
    solver.addAMOLog(literals.toList)

    // Resoldre el problema
    val result = solver.solve()

    // Resultats
    println("---")
    println(result)
    println("---")
    for (v <- literals) {
      print(if (solver.getValue(v)) "1 " else "0 ")
    }
    println("\n---")



  }
}
