#!/bin/bash

# Launching fluent d container
docker run -it --ip 0.0.0.0 -p 9090:9091 mozafaq/fluent-test-app:1.1
