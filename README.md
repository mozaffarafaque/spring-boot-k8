# Exploring fluent

Sample application to explore fluentd and fluent bit
## Build current project 

 - From the root of the project
 `./gradlew clean build`

 - Build image
  `sh deployment/docker/image_fluentd_uma.sh`

 - To start tghe application
  `sh deployment/run/fluent-uma-start.sh`

## Installation using td-agent

### Install command

`curl -L https://toolbelt.treasuredata.com/sh/install-redhat-td-agent3.sh | sh`

### Start | Stop
`sudo launchctl [load|unload] /Library/LaunchDaemons/td-agent.plist`

### Configurations tio supply

`sudo vim  /etc/td-agent/td-agent.conf`

### To see the log
` tailf  /var/log/td-agent/td-agent.log`

## Command to start log collector  
