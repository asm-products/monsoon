# Monsoon

[![Build Status](https://travis-ci.org/asm-products/monsoon.svg)](https://travis-ci.org/asm-products/monsoon)

A lightweight database wrapper and push mechanism for sundry activities &mdash; it makes it rain or something.

## Prerequisites

You will need [Leiningen][1] 2.0.0 or above installed.

[1]: https://github.com/technomancy/leiningen

## Running

To start a web server for the application, run:

    lein ring server

# Fig

You can use Docker and Fig to run an isolated environment. First install `boot2docker` and `fig`.

    fig up

in a second terminal, bootstrap the database:

    ./bootstrap.sh

## License

AGPL

Copyright © 2014 Assembly
