# ����student.info��������t_u_student��ṹ����Ҫ����
redis-cli -a chelizi keys student.*|xargs redis-cli -a chelizi del;

# ����coach.vo coach.dto��������t_u_coach��ṹ����Ҫ����
redis-cli -a chelizi keys coach.*|xargs redis-cli -a chelizi del;


