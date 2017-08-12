.PHONY: start
start:
	target/universal/stage/bin/scully

.PHONY: deploy
deploy:
	git push heroku master

.PHONY: build
build:
	sbt compile stage
