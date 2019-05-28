#!/bin/bash
# install updates
yum update -y

# install apache httpd, java and git
yum install httpd -y
yum install java -y
yum install git

# create the working directory
mkdir /var/www/b2bApplication

# download the jar file from S3, it has to be uploaded there before that.
aws s3 cp s3://atawakol/product-0.0.1-SNAPSHOT.jar /var/www/b2bApplication/ --region=ap-south-1

# create a springboot user to run the app as a service
useradd springboot
# springboot login shell disabled
chsh -s /sbin/nologin springboot
chown springboot:springboot /var/www/b2bApplication/product-0.0.1-SNAPSHOT.jar
chmod 500 /var/www/b2bApplication/product-0.0.1-SNAPSHOT.jar

# create a symbolic link
# https://www.baeldung.com/spring-boot-app-as-a-service
ln -s /var/www/b2bApplication/product-0.0.1-SNAPSHOT.jar /etc/init.d/b2bApplication

# forward port 80 to 8080
echo "<VirtualHost *:80>
  ProxyRequests Off
  ProxyPass / http://localhost:8080/
  ProxyPassReverse / http://localhost:8080/
</VirtualHost>" >> /etc/httpd/conf/httpd.conf

# start the httpd and b2bApplication
service httpd start
service b2bApplication start

# automatically start httpd and b2bApplication if this ec2 instance reboots
chkconfig httpd on
chkconfig b2bApplication on