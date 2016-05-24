# TTC'16 The Class Responsibility Assignment Case - SIGMA solution

## Prerequisites

- [SBT - simple build tool](http://www.scala-sbt.org/download.html)

## Running the solution

```sh
$ ./build.sh
$ java [<optional parameters>] -jar target/scala-2.11/ttc16-cra-sigma_2.11-1.0-one-jar.jar <input> <output>
or
$ ./run.sh <model directory> [<optional parameters>] # to run multiple models capturing some statistics
eg.
$ ./run.sh models # runs CRA on all models in models directory and
```

where optional parameters are:
- `-Dalgorithm=<value>` (default = NSGAIII)
- `-DnumRuns=<value>` (default = 10)
- `-DpopulationSize=<value>` (default = 64)
- `-DmaxEvaluations=<value>` (default = 10000)

The result of the `run.sh` is generated in `outputs` directory. Next to the models, it contains a CSV file (result.csv) which for each input model outputs:

- model name
- cohesion ration
- coupling ration
- CRA index
- execution time

There is also an `.out` file per model which records the standard output of the transformation process.

## Re-generating SIGMA support for the EMF model

Shall the EMF model change, these are the steps to regenerate the EMF support for SIGMA:

```sh
$ sbt
...
> consoleQuick
> :load generate-sigma-support.scala-console
```

