#!/bin/bash
CLASS_PATH=.:%CLASSPATH%:./ChatServer-0.0.1-SNAPSHOT.jar 
#命令结尾的`&`表示后台运行程序
java -jar ChatServer-0.0.1-SNAPSHOT.jar& 