.PHONY: build run clean

test:
        mvn clean test

build:
		mvn clean package

run:
		docker-compose up --build

clean:
		docker-compose down
		mvn clean