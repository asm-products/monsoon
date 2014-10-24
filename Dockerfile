FROM clojure
MAINTAINER Dave Newman <dave@assembly.com> @whatupdave

ADD . /app
WORKDIR /app
RUN ls -la
RUN lein deps
