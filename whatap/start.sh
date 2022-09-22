########## WHATAP START ############
sudo rpm --import http://repo.whatap.io/centos/release.gpg
echo "[whatap]" | sudo tee /etc/yum.repos.d/whatap.repo > /dev/null
echo "name=whatap packages for enterprise linux" | sudo tee -a /etc/yum.repos.d/whatap.repo > /dev/null
echo "baseurl=http://repo.whatap.io/centos/latest/\$basearch" | sudo tee -a /etc/yum.repos.d/whatap.repo > /dev/null
echo "enabled=1" | sudo tee -a /etc/yum.repos.d/whatap.repo > /dev/null
echo "gpgcheck=0" | sudo tee -a /etc/yum.repos.d/whatap.repo > /dev/null
sudo yum install whatap-infra
echo "license={x4oo220v9642i-x7vcur79tvhvbi-x52qfm12a5rgj2}" |sudo tee /usr/whatap/infra/conf/whatap.conf
echo "whatap.server.host={13.124.11.223/13.209.172.35}" |sudo tee -a /usr/whatap/infra/conf/whatap.conf
echo "createdtime=date +%s%N" |sudo tee -a /usr/whatap/infra/conf/whatap.conf
sudo service whatap-infra restart
########## WHATAP END ############
