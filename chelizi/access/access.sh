#!/bin/sh
#fightserver.sh
#To start or stop fightserver.

#base dir of the application
APP_BASE=`pwd`
echo $APP_BASE

#name of the application
APP_NAME=com.lili.LiliAccess
echo $APP_NAME

#name of the config file
CONFIG_FILE=$APP_BASE/access.properties
echo $CONFIG_FILE

#name of the file record the process id of the application
PROCESS_ID_FILE=$APP_BASE/access.pid
echo $PROCESS_ID_FILE	

#process id of the application
PROCESS_ID=`cat $PROCESS_ID_FILE`
echo $PROCESS_ID

case "$1" in
    start)
        if [ "$PROCESS_ID" ]; then
            echo "PID file ($PROCESS_ID) found. Is $APP_NAME still running? Start aborted."
            exit 1
        fi
        
        PATH=$CLASSPATH
        for i in $APP_BASE/*.jar;
        do
            PATH="$PATH":$i
        done
        
        for i in $APP_BASE/lib/*.jar;
        do
            PATH="$PATH":$i
        done
		# 加载logback.xml
		PATH="$PATH":$APP_BASE
        echo $PATH
        
        $JAVA_HOME/bin/java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=7778 -Xms4096m -Xmx6144m -Xmn1600m   -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath="./OutOfMemory.hprof"  -Dcom.sun.management.jmxremote.port=1111 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Djava.rmi.server.hostname=120.24.152.229 -cp "$PATH" "$APP_NAME" "$CONFIG_FILE" &
        echo $! > $PROCESS_ID_FILE
        
        echo "$APP_NAME started!"
    ;;
    stop)
        if [ "$PROCESS_ID" ]; then
            kill "$PROCESS_ID"
            rm -rf $PROCESS_ID_FILE
            echo "----------------------the $APP_NAME been killed------------------"
        else
            echo "----------------------the $APP_NAME is not running----------------"
        fi
    ;;
    *)
        echo "Usage: $0 start|stop"
    ;;
esac
exit 0
