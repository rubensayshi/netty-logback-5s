install:
	mvn clean install

deps:
	mvn dependency:tree

run: install
	java -cp target/fatjar.jar io.netty.logback5s.Slow