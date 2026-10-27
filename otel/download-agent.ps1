# Downloads the OpenTelemetry Java agent next to this script (the jar is git-ignored).
$ErrorActionPreference = "Stop"
$target = Join-Path $PSScriptRoot "opentelemetry-javaagent.jar"
Invoke-WebRequest -Uri "https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/latest/download/opentelemetry-javaagent.jar" -OutFile $target
Write-Host "Downloaded $target"
