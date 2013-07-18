#!/bin/bash

ID="51e7ee266924fe1e7bc9c711"

for h in 0 1 2 3 4
do
  line=""
  for w in 0 1 2 3 4 5
  do
    letter=`curl -X GET -s http://totalrecall.99cluster.com/games/$ID/cards/$w,$h`
    line="$line $letter"
  done
  echo $line
done
