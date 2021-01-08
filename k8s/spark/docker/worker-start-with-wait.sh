#!/bin/sh

set -e


/opt/spark/sbin/start-worker.sh spark://spark-master-server:9011 -c 2 -m 2G -p 9021 --webui-port 9022

sleep 86400