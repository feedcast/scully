.PHONY: start
start:
	target/universal/stage/bin/scully

.PHONY: build
build:
	sbt compile stage

.PHONY: compose
compose:
	docker-compose up -d
