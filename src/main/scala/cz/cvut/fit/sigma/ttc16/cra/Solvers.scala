package cz.cvut.fit.sigma.ttc16.cra

import at.ac.tuwien.big.momot.examples.modularization.ttc.CRAIndexCalculator
import at.ac.tuwien.big.momot.examples.modularization.ttc.architecture.support.Architecture._architecture._
import org.moeaframework.Executor
import org.moeaframework.core.NondominatedPopulation

import scala.collection.JavaConversions._

object Solvers {

  trait Solver extends (ClassModel => ClassModel) {
    val populationSize = System.getProperty("populationSize", "64").toInt
    val maxEvaluations = System.getProperty("maxEvaluation", "10000").toInt

    def apply(initModel: ClassModel): ClassModel =
      run(initModel)
        .map(x => CRAProblem.solutionToClassModel(initModel, x))
        .maxBy(CRAIndexCalculator.calculateCRAIndex)

    def run(initModel: ClassModel): NondominatedPopulation


    protected def create(initModel: ClassModel): Executor =
      new Executor()
        .withProblemClass(classOf[CRAProblem], initModel)
        .withProperty("populationSize", populationSize)
        .withMaxEvaluations(maxEvaluations)
  }

  object NSGAIII extends Solver {
    override def run(initModel: ClassModel) =
      create(initModel)
        .withAlgorithm("NSGAIII")
        .run()

    override def toString = s"NSGAIII (pupulationSize=$populationSize)"
  }

  object SPEA2 extends Solver {
    val archiveSize = System.getProperty("archiveSize", "32").toInt

    override def run(initModel: ClassModel) =
      create(initModel)
        .withAlgorithm("SPEA2")
        .withProperty("archiveSize", archiveSize)
        .run()

    override def toString = s"SPEA2 (pupulationSize=$populationSize, archiveSize=$archiveSize)"
  }
}
