![Dana](https://github.com/feedcast/scully/raw/master/docs/logo.png "Dana")

# Scully [![Build Status](https://travis-ci.org/feedcast/scully.svg?branch=master)](https://travis-ci.org/feedcast/scully)
> :mag: The Podcast Detective

## Introduction

Scully is a low-latency search engine for [feedcast](feedcast.io) around [ElasticSearch](https://github.com/elastic/elasticsearch).

The name, Scully, is a reference of the X-Files character [Dana Scully](https://en.wikipedia.org/wiki/Dana_Scully), a **detective** played by the amazing [Gillian Anderson](https://en.wikipedia.org/wiki/Gillian_Anderson).

## Endpoints

#### PUT /index/episode
> Index an episode

body:
```
{
  "uuid": "a23b7-1238d-92kdj7",
  "title": "I want to believe"
}
```

where:
* uuid - Unique identifier of the document
* title - Title of the document

responses:
* 202 - Accepted with an empty body if everything goes well
* 400 - Bad request if the given attributes are not properly given
