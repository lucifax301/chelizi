# 清理student.info，更改了t_u_student表结构后都需要清理
redis-cli -a chelizi keys student.*|xargs redis-cli -a chelizi del;

# 清理coach.vo coach.dto，更改了t_u_coach表结构后都需要清理
redis-cli -a chelizi keys coach.*|xargs redis-cli -a chelizi del;


