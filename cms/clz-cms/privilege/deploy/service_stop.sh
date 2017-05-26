ps -ef | grep com.lili.privilege.PrivilegeDubboApplication | grep -v grep | awk '{print $2}' | xargs kill -9
