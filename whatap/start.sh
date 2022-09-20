########## WHATAP START ############
WHATAP_HOME=./whatap
WHATAP_JAR=`ls ${WHATAP_HOME}/whatap.agent-2.1.3.jar | sort | tail -1`
JAVA_OPTS="${JAVA_OPTS} -javaagent:${WHATAP_JAR} "
########## WHATAP END ############
