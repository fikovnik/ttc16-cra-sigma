package cz.cvut.fit.sigma.ttc16.cra

import at.ac.tuwien.big.momot.examples.modularization.ttc.CRAIndexCalculator
import at.ac.tuwien.big.momot.examples.modularization.ttc.architecture.support.Architecture._architecture._
import org.moeaframework.Executor
import org.moeaframework.core.NondominatedPopulation

import scala.collection.JavaConversions._

object Solvers {

  trait Solver extends (ClassModel => ClassModel) {
    def apply(initModel: ClassModel): ClassModel = {
      run(initModel)
        .map(x => CRAProblem.solutionToClassModel(initModel, x))
        .maxBy(CRAIndexCalculator.calculateCRAIndex)
    }

    def run(initModel: ClassModel): NondominatedPopulation
  }

  object NSGAIII extends Solver {
    override def run(initModel: ClassModel): NondominatedPopulation = {
      new Executor()
        .withProblemClass(classOf[CRAProblem], initModel)
        .withAlgorithm("NSGAIII")
        .withProperty("populationSize", 64)
        .withMaxEvaluations(10000)
        .run()
    }

    override def toString = s"NSGAIII"
  }

  object SPEA2 extends Solver {
    override def run(initModel: ClassModel): NondominatedPopulation = {
      new Executor()
        .withProblemClass(classOf[CRAProblem], initModel)
        .withAlgorithm("SPEA2")
        .withProperty("populationSize", 64)
        .withProperty("archiveSize", 32)
        .withMaxEvaluations(10000)
        .run()
    }

    override def toString = s"SPEA2"
  }
}
