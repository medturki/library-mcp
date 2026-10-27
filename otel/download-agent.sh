#!/usr/bin/env sh
# Downloads the OpenTelemetry Java agent next to this script (the jar is git-ignored).
set -e
cd "$(dirname "$0")"
curl -fL -o opentelemetry-javaagent.jar \
  https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar
echo "Downloaded $(pwd)/opentelemetry-javaagent.jar"
