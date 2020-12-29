# K8 Intsance Useful Commands


## Useful Links

###   Steps followed:
  - https://www.zekelabs.com/blog/how-to-install-kubernetes-cluster-on-aws-ec2-instances/
  - https://kubernetes.io/docs/reference/generated/kubectl/kubectl-commands

## ec2 Instance

 Security Groups
 -  One should be able to do ssh
 -  Hosts in the cluster should be able to interact with each other

Install Docker
 - On RHEL


If added file system EBS disc use followings to create the file system

```$xslt
 sudo useradd -d /home/k8user sudo useradd -d /home/afaque afaque    
 mkfs -t xfs /dev/xvdf && mkdir /local-ebs && mount /dev/xvdaf /local-ebs && mkdir /local-ebs/data  && chown k8user:k8user /local-ebs/data
```
 
 Installation has some error
 To fix it followings were done
  
 ```$xslt
  yum install -y docker-ce-stable
``` 
 Open file:   vim /etc/yum.repos.d/docker-ce.repo
 
 Change the content under `[docker-ce-stable]`
 
 ```
 [docker-ce-stable]
 name=Docker CE Stable - $basearch
 baseurl=https://download.docker.com/linux/centos/7/$basearch/stable
 enabled=1
 gpgcheck=1
 gpgkey=https://download.docker.com/linux/centos/gpg
 ```
 
Create user & Add user to Docker group

```$xslt
sudo useradd -d /home/afaque afaque  
sudo usermod -aG docker afaque
```
Sometimes additionally we have to execute followings

```jshelllanguage
sudo chmod 666 /var/run/docker.sock
```
To remove old package 
```jshelllanguage
sudo yum remove docker docker-common docker-selinux docker-engine-selinux docker-engine docker-ce
```

 Installing repo
```jshelllanguage
sudo yum install -y yum-utils device-mapper-persistent-data lvm2
```

Adding the repo

```$xslt
sudo yum-config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo
```


Fix extra packages

```$xslt
yum install http://ftp.riken.jp/Linux/cern/centos/7/extras/x86_64/Packages/container-selinux-2.74-1.el7.noarch.rpm
yum sudo yum install http://mirror.centos.org/centos/7/extras/x86_64/Packages/container-selinux-2.107-1.el7_6.noarch.rpm --skip-broken

```

Start docker

```$xslt
sudo service docker start
```


Docker pull
```$xslt
docker pull mozafaq/test-springboot-app:1.1
```

Docker start instance (Just for testing, Validate port and 
set APP_IMAGE variable before running the commend)
```$xslt
docker container run -it -d -p 8081:8081 --name container-sample-app ${APP_IMAGE}
```

To ssh into running container
```$xslt
 docker exec -it container-sample-app /bin/bash
```
 
- Master node init

```$xslt
sudo kubeadm init --pod-network-cidr=192.168.0.0/16 --ignore-preflight-errors=NumCPU

```

- Join command (on worker node). This wii come frommaster node command
```$xslt
kubeadm join 172.31.61.80:6443 --token <TOKEN> --discovery-token-ca-cert-hash <Certificate_Hash>

```
	
## Other kubctl commands

- Create deplyment

```$xslt
 kubectl apply -f <Deplyment file>
```


- For getting the nodes

```$xslt
kubectl get nodes
```
- Getting cluster info

```$xslt
kubectl cluster-info
```

### In case issue is encountered, reset with below commands 

```$xslt
kubeadm reset
rm -rf /var/lib/cni/
rm -rf /var/lib/cni/

systemctl daemon-reload

systemctl restart kubelet

iptables -F && sudo iptables -t nat -F && sudo iptables -t mangle -F && sudo iptables -X

```

- To make node ready

```$xslt
kubectl apply -f "https://cloud.weave.works/k8s/net?k8s-version=$(kubectl version | base64 | tr -d '\n')"
```

-  Server expose
```$xslt
sudo kubectl expose deployment test-sample-k8-app --type=NodePort --name=test-sample-k8-app  -n=expl-sample-namespace
```

- Delete deployment in specified namespace

```$xslt
 sudo kubectl delete deployment test-sample-k8-app -n=expl-sample-namespace
```

- Describe services
```$xslt
 kubectl describe services  test-sample-k8-app -n=expl-sample-namespace
```
- To get the application name
```$xslt
 sudo kubectl describe services  test-sample-k8-app  -n=expl-sample-namespace
```

- Delete service
```$xslt
 kubectl delete services test-sample-k8-app
```

- Restart service
```$xslt
 sudo kubectl rollout restart deployment test-sample-k8-app -n=expl-sample-namespace
``` 

- See all pods

```$xslt
 kubectl delete --all pods --namespace=default
```

- ssh into pod
```$xslt
 kubectl exec -t -i -n=<namespace> <pod name> bash

```

- Scaling in/out

```$xslt
kubectl scale deployment test-sample-k8-app -n=expl-sample-namespace  --replicas <count>
```