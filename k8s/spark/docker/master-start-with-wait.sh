#!/bin/sh

set -e


#echo "USERNAME: $USER \n" >> /opt/spark/logs/boot.txt

#echo "USERNAME: $USER \n" >> /opt/spark/logs/boot.txt



/opt/spark/sbin/start-master.sh -p 9011 --webui-port 9012

sleep 900