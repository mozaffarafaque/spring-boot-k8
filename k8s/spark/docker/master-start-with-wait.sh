#!/bin/sh

set -e

IP=$(hostname -i)

/opt/spark/sbin/start-master.sh -p 9011 --webui-port 9012 -h $IP

sleep 86400