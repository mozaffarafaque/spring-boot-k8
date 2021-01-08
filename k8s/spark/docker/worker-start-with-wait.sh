#!/bin/sh

set -e


#echo "USERNAME: $USER \n" >> /opt/spark/logs/boot.txt

#echo "USERNAME: $USER \n" >> /opt/spark/logs/boot.txt



/opt/spark/sbin/start-worker.sh spark://spark-master:9011 -c 2 -m 2G -p 9021 --webui-port 9022

sleep 900