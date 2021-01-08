#!/bin/sh

set -e


/opt/spark/sbin/start-master.sh -p 9011 --webui-port 9012

sleep 86400