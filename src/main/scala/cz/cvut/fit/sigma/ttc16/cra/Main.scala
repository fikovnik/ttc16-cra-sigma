package cz.cvut.fit.sigma.ttc16.cra

import java.io.File

import at.ac.tuwien.big.momot.examples.modularization.ttc.CRAIndexCalculator
import at.ac.tuwien.big.momot.examples.modularization.ttc.architecture.support.Architecture
import at.ac.tuwien.big.momot.examples.modularization.ttc.architecture.support.Architecture._architecture._
import fr.unice.i3s.sigma.emf.util.EMFUtils

object Main extends App with Architecture {

  if (args.length != 3) {
    println(s"Missing required parameters: <algorithm> <path/to/input.xmi> <path/to/output.xmi>")
    sys.exit(1)
  }

  EMFUtils.IO.registerDefaultFactories()
  CRAIndexCalculator.registerPackage()

  val solver = args(0).trim.toUpperCase() match {
    case "NSGAIII" => Solvers.NSGAIII
    case "SPEA2" => Solvers.SPEA2
    case x => throw new IllegalArgumentException("Unsupported algorithm " + x)
  }
  val input = new File(args(1))
  val output = new File(args(2))
  val numRuns = Integer.valueOf(System.getProperty("numRuns", "10"))

  println(s"Solving ${input.getCanonicalPath} using $solver (running $numRuns)")

  val initModel = EMFUtils.IO.loadFromFile[ClassModel](input)
  val solution = profile(time => s"Solved all in $time ms\n", optimize(numRuns, initModel))

  CRAIndexCalculator.printGeneralInfo(solution)
  CRAIndexCalculator.printCorrectnessInfo(solution)
  CRAIndexCalculator.printOptimalityInfo(solution)

  EMFUtils.IO.saveToFile(output, Some(solution))

  println(s"Output written to ${output.getCanonicalPath}")

  private def optimize(numRuns: Int, initModel: ClassModel): ClassModel = {
    (1 to numRuns)
      .map (x => profile(time => s"Solved $x/$numRuns in $time ms", solver(initModel)))
      .maxBy(CRAIndexCalculator.calculateCRAIndex)
  }

  private def profile[A](message: Long => String, thunk: => A): A = {
    val time = System.currentTimeMillis()
    val ret = thunk

    println(message(System.currentTimeMillis() - time))

    ret
  }
}
