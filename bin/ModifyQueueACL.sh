#!/bin/bash

#*****************Configuration*******************

## Common
HOME="$(cd `dirname $0`;cd ..;pwd)"
CONF=$HOME/conf
LOG_DIR=$HOME/logs
PID_DIR=$LOG_DIR/PID
PID=$PID_DIR/ModifyQueueACL.pid
START_OUT_FILE=$LOG_DIR/modify-queue-acl-launch.out

## Log4j
LOG_ROOT=INFO,RFA
LOG_FILE=modify-queue-acl-$(hostname).log

## Java environment
JAVA_HOME=$JAVA_HOME
#JAVA_OPTS=""

## ModifyQueueACL setup
IMPL_CLASS=com.chinatelecom.server.ProxyServer

#*************************************************


if [ -z $JAVA_HOME ]; then
	echo "Need Java environment, you should set JAVA_HOME."
	exit 1
fi

JAVA=$JAVA_HOME/bin/java

if [ ! -f $JAVA ]; then
	echo "Can't find java in JAVA_HMOE=$JAVA_HOME"
	exit 1
fi

mkdir -p $LOG_DIR >/dev/null 2>&1
mkdir -p $PID_DIR >/dev/null 2>&1

CLASSPATH=$CONF:$JAVA_HOME/lib/tools.jar:$HOME/bin

in_dev_env=false
if [ -d $HOME/target ]; then
	in_dev_env=true
fi

if $in_dev_env; then
	for f in $HOME/target/ModifyQueueACL*.jar; do
		CLASSPATH=$CLASSPATH:$f
	done
	CLASSPATH=$CLASSPATH:`cat $HOME/target/cached_classpath.txt`
fi

for f in $HOME/lib/*.jar
do
	CLASSPATH=$CLASSPATH:$f
done

export CLASSPATH

JVM="-Xms8g -Xmx8g -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=80 \
-XX:+CMSParallelRemarkEnabled"

JAVA_OPTS="$JAVA_OPTS $JVM -Dproxy.log.dir=$LOG_DIR -Dproxy.log.file=$LOG_FILE -Dproxy.log.root=$LOG_ROOT"


print_help() {
	echo "Usage: $0 {start|stop|restart|status|classpath}"
	exit 1
}

check_start() {
	if [ -f $PID ]; then
		pid=$(head -n1 $PID)
		if kill -0 $pid >/dev/null 2>&1; then
			echo "ModifyQueueACL has already started. Stop it first."
			exit 1
		fi
	fi
}

check_stop() {
	if [ -f $PID ]; then
		pid=$(head -n1 $PID)
		if kill -0 $pid >/dev/null 2>&1; then
			echo -n "ModifyQueueACL run as process $pid, stop it."
		fi
	else
		echo "No such file $PID"
	fi
}

do_start() {
	nohup $JAVA $JAVA_OPTS -XX:OnOutOfMemoryError="kill -9 %p" $IMPL_CLASS "$@" >$START_OUT_FILE 2>&1 &
	echo $! > $PID
	echo "ModifyQueueACL has started. Process id is "$(head -n1 $PID)"."
}

do_stop() {
	kill $pid > /dev/null 2>&1
	while kill -0 $pid > /dev/null 2>&1;
	do
		echo -n "."
		sleep 1;
	done
	echo ""
	rm -f $PID
}

check_status(){
	if [ -f $PID ]; then
		pid=$(head -n1 $PID)
		if kill -0 $pid >/dev/null 2>&1; then
			echo "ModifyQueueACL is running on $pid.     [OK]"
		else
			echo "ModifyQueueACL had stopped.     [FAILED]"
			exit 1
		fi
	else
		echo "Can't find pid file.     [FAILED]"
		exit 1
	fi
}


CMD=$1
shift

case $CMD in
	'start')
		check_start
		do_start
	;;
	'stop')
		check_stop
		do_stop
	;;
	'restart')
		check_stop
		do_stop
		check_start
		do_start
	;;
	'status')
		check_status
	;;
	'classpath')
		echo $CLASSPATH
	;;
	*)
		print_help
	;;
esac
