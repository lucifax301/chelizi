export CLASSPATH=$CLASSPATH:./lib/*:./config
java -Xrunjdwp:transport=dt_socket,server=y,suspend=n  -Xmn128m -Xms256m -Xmx512m  com.lili.privilege.PrivilegeDubboApplication >/dev/null 1>output &

