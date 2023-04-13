#!/bin/bash
cd -- "$(dirname -- "$BASH_SOURCE")"
cd bin
./java -m org.example/org.example.ShrimpGameAppLauncher
exit 0
