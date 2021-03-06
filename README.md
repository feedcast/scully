![Dana](https://github.com/feedcast/scully/raw/master/docs/logo.png "Dana")

# Scully [![Build Status](https://travis-ci.org/feedcast/scully.svg?branch=master)](https://travis-ci.org/feedcast/scully)
> :mag: The Podcast Detective

## Introduction

Scully is a low-latency search engine for [feedcast](https://feedcast.io) around [ElasticSearch](https://github.com/elastic/elasticsearch).

The name, Scully, is a reference of the X-Files character [Dana Scully](https://en.wikipedia.org/wiki/Dana_Scully), a **detective** played by the amazing [Gillian Anderson](https://en.wikipedia.org/wiki/Gillian_Anderson).

## Endpoints

#### PUT /index/episode
> Index an episode

body:

```javascript
{
  "uuid": "a23b7-1238d-92kdj7",
  "path": "/x-files/01-pilot",
  "title": "I want to believe",
  "summary": "Foo",
  "description": "Bar",
}
```

where:
* uuid - Unique identifier of the episode
* path - URL path for the episode on the front-end
* title - Title of the episode
* summary - Summary of the episode
* description - Description of the episode

responses:
* 202 - Accepted with an empty body if everything goes well
* 400 - Bad request if the given attributes are not properly given

#### GET /?query=:query
> Search for episodes

params:
* query - Search query

response:
* 200 - OK with an array of episodes in the response body
body:

```javascript
[
  {
    "uuid": "a23b7-1238d-92kdj7",
    "path": "/x-files/01-pilot",
    "title": "I want to believe",
    "summary": "Foo",
    "description": "Bar",
  },
  ...
]
```

## Setup

### Dependencies

To setup external dependencies use: `make compose`.

That will run Elastic Search on `localhost:9200` with Docker Compose.

### Build

To install the libraries and compile: `make build`.

That will run SBT compile stage.

### Test

To run the tests: `make test`.

### Start

To start the application run: `make start`.

That will start the server at `localhost:2024` by default.

#### Environment
> Environment variables available to configure

* **PORT** - HTTP server PORT, default: `2024`.
* **ELASTIC_SEARCH_URL** - The Elastic Search URL, default: `elasticsearch://elastic:changeme@localhost:9200/`.
