@echo off
echo Start running test

:: To run tests sequentially, use: RunSequentialRegression.xml
:: To run tests parallel,     use RunParallelRegression.xml

mvn clean test -D suiteXmlFile=RunParallelRegression.xml

echo end test


