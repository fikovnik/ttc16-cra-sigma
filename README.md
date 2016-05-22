# TTC'16 The Class Responsibility Assignment Case - SIGMA solution

## Prerequisites

- [SBT - simple build tool](http://www.scala-sbt.org/download.html)

## Running the solution

```sh
$ ./build.sh
$ java [<optional parameters>] -jar target/scala-2.11/ttc16-cra-sigma_2.11-1.0-one-jar.jar <input> <output>
or
$ ./run.sh [<optional parameters>] | tee output.txt # runs CRA on all models in models directory
```

where optional parameters are:
- `-Dalgorithm=<value>` (default = NSGAIII)
- `-DnumRuns=<value>` (default = 10)
- `-DpopulationSize=<value>` (default = 64)
- `-DmaxEvaluations=<value>` (default = 10000)

## Re-generating SIGMA support for the EMF model

Shall the EMF model change, these are the steps to regenerate the EMF support for SIGMA:

```sh
$ sbt
...
> consoleQuick
> :load generate-sigma-support.scala-console
```

