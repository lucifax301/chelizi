# 版本发布时，因直接修改了数据库数据，需要清理相关的缓存！
# 注意：理论上不应该清理缓存中的用户登录凭证！

# 清理学员
redis-cli -a chelizi keys *student.* |xargs redis-cli -a chelizi del;

# 清理教练
redis-cli -a chelizi keys *coach.* |xargs redis-cli -a chelizi del;

# 清理学员进度模板
redis-cli -a chelizi keys *template* |xargs redis-cli -a chelizi del;

# 清理课程模板
redis-cli -a chelizi keys *courses* |xargs redis-cli -a chelizi del;

# 清理邮寄资料信息
redis-cli -a chelizi keys "enroll.mail*" | xargs redis-cli -a chelizi del;