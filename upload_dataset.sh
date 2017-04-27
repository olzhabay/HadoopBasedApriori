#!/usr/bin/env bash

start-all.sh
hdfs dfs -mkdir -p .
hdfs dfs -put dataSet/sample
hdfs dfs -put dataSet/connect.dat
hdfs dfs -put dataSet/mushroom.dat
hdfs dfs -put dataSet/retail.dat
