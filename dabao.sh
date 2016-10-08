#!/bin/bash

#rm -rf target
mvn package -DskipTest
./bin/ModifyQueueACL.sh restart
#rm -rf /Users/It_Ds_N.cpp/Desktop/work/apache-tomcat-7.0.70/webapps/Mod*                
#cp ./target/ModifyQueueACL.war /Users/It_Ds_N.cpp/Desktop/work/apache-tomcat-7.0.70/webapps
