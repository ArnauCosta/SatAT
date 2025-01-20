object BuscaMinesQuad extends App {
  if (args.length != 1) {
    println("Ús: scala BuscaMines <nom_fitxer>")
    System.exit(1)
  }

  val filename = args(0)

  case class MinesweeperInstance(n: Int, m: Int, nmines: Int, board: Array[Array[String]])

  def parseFile(filename: String): MinesweeperInstance = {
    val lines = scala.io.Source.fromFile(filename).getLines().toList
    val Array(n, m, nmines) = lines.head.split(" ").map(_.toInt)
    val board = lines.tail.map(_.split(" ").toArray).toArray
    MinesweeperInstance(n, m, nmines, board)
  }

  val instance = parseFile(filename)
  println(s"Dimensions del tauler: ${instance.n} x ${instance.m}")
  println(s"Nombre de mines (nmines): ${instance.nmines}")
  println("Contingut del tauler:")
  instance.board.foreach(row => println(row.mkString(" ")))

  val e = new ScalAT("BuscaMines")
  val varsMine = e.newVar2DArray(instance.n, instance.m)
  val varsNoMine = e.newVar2DArray(instance.n, instance.m)

  for (i <- 0 until instance.n; j <- 0 until instance.m) {
    e.addEOQuad(List(varsMine(i)(j), varsNoMine(i)(j)))
  }

  def getAdjacents(i: Int, j: Int, vars: Array[Array[Int]], n: Int, m: Int): List[Int] = {
    val directions = List((-1, -1), (-1, 0), (-1, 1), (0, -1), (0, 1), (1, -1), (1, 0), (1, 1))
    directions.flatMap { case (di, dj) =>
      val ni = i + di
      val nj = j + dj
      if (ni >= 0 && ni < n && nj >= 0 && nj < m) Some(vars(ni)(nj)) else None
    }
  }

  for (i <- 0 until instance.n; j <- 0 until instance.m) {
    instance.board(i)(j) match {
      case "X" =>
        e.addClause(List(varsNoMine(i)(j))) // No pot ser mina
      case n if n.matches("\\d") =>
        e.addClause(List(varsNoMine(i)(j)))
        val adjacents = getAdjacents(i, j, varsMine, instance.n, instance.m)
        e.addEK(adjacents, n.toInt) // Exactament n adjacents amb mines
      case _ => // '-'
    }
  }

  if (instance.nmines >= 0) {
    val allMines = varsMine.flatten.toList
    e.addEK(allMines, instance.nmines)
  }

  val result = e.solve()
  if (result.satisfiable) {
    println("Solution found:")
    val solution = Array.ofDim[String](instance.n, instance.m)
    for (i <- 0 until instance.n; j <- 0 until instance.m) {
      solution(i)(j) = if (e.getValue(varsMine(i)(j))) "o" else "X"
    }
    solution.foreach(row => println(row.mkString(" ")))
  } else {
    println("No solution found.")
  }
}
