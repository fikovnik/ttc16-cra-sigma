#!/bin/sh
set -e

input_dir="models"
output_dir="outputs"
algorithm=${algorithm:-"NSGAIII"}

for input in $input_dir/*.xmi; do
  output="$output_dir/${input/%.xmi/-result.xmi}"
  java -jar target/scala-2.11/ttc16-cra-sigma_2.11-1.0-one-jar.jar "$algorithm" "$input" "$output"
  echo
done
