#!/bin/sh

set -e

IP=$(hostname -i)

/opt/spark/sbin/start-worker.sh spark://spark-master-server:9011 -c 3 -m 12G -p 9021 --webui-port 9022 -h $IP

sleep 86400