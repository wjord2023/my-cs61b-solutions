compile:
	javac -cp "./javalib/*" ./game2048/*.java -d out

run:
	java -cp "./javalib/*:./out" game2048.Main

testEmptySpace: compile
	java -cp "./javalib/*:./out" org.junit.runner.JUnitCore game2048.TestEmptySpace

testMaxTileExists: compile
	java -cp "./javalib/*:./out" org.junit.runner.JUnitCore game2048.TestMaxTileExists

testAtLeastOneMoveExists: compile
	java -cp "./javalib/*:./out" org.junit.runner.JUnitCore game2048.TestAtLeastOneMoveExists

testUpOnly: compile
	java -cp "./javalib/*:./out" org.junit.runner.JUnitCore game2048.TestUpOnly
