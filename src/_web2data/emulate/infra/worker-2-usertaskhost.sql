

/factory/GetWorker2UserTaskHostMapping.php?factoryHostId=?



select tfw.worker_type, tfw.worker_index, app.user_id, app.task_host_id
from team_factory_worker tfw, team, step_team st, user, step, recipe, app, org_task_host oth
where tfw.factory_host_id = 701
and tfw.team_id = team.id and team.status = 'ACTIVE'
and tfw.team_id = st.team_id 
and st.user_id = user.id and user.status = 'ACTIVE'
and st.app_id = step.app_id and st.recipe_index = step.recipe_index and st.step_index = step.step_index and step.status = 'ACTIVE'
and st.app_id = recipe.app_id and st.recipe_index = recipe.recipe_index and recipe.status = 'ACTIVE'
and st.app_id = app.id and app.status = 'ACTIVE'
and app.task_host_id = oth.id and oth.status = 'ACTIVE'
group by tfw.worker_type, tfw.worker_index, app.user_id, app.task_host_id
order by tfw.worker_type, tfw.worker_index, app.user_id, app.task_host_id;


select c.user_id, c.task_host_id
from team_factory_worker a, step_team b, app c, user d
where a.factory_host_id = 701 and s1 = 1 
and a.team_id = b.team_id 
and b.app_id = c.id
and c.user_id = d.id and d.is_used = 'Y'
group by c.user_id, c.task_host_id
order by c.user_id, c.task_host_id;



( select x.user_id, x.task_host_id order by x.user_id, x.task_host_id group by x.user_id, x.task_host_id ) 

team_factory_worker -> team -> user_team -> step_team -> step -> user_task_host x
factory_host_id=?              一个team可能		        可能由任务
s1 = 1                         存在多条记录                 处理快关 

简化：
team_factory_worker -> user_team -> step_team -> user_task_host x
factory_host_id=?      一个team可能		        
s1 = 1                 存在多条记录                 


以上SQL, 执行 10 + 50 次

s1 -> 1-2,1-3,2-1,2-2,3-1,3-2,3-3
s2 -> 1-2,1-3,2-1,2-2,3-1
s3 -> 1-2,1-3,2-1,2-2,3-1,3-2,3-3
s4 -> 1-2,1-3,2-1,2-2,3-1
s5 -> 1-2,1-3,2-1,2-2,3-1,3-2,3-3

转化为以下形式：
key -> values
1-2,1-3,2-1,2-2,3-1,3-2,3-3 <- s1,s3,s5
1-2,1-3,2-1,2-2,3-1 -> s2,s4



j1 -> 1-2,1-3,2-1,2-2,3-1,3-2,3-3
j2 -> 1-2,1-3,2-1,2-2,3-1
j3 -> 1-2,1-3,2-1,2-2,3-1,3-2,3-3
j4 -> 1-2,1-3,2-1,2-2,3-1
j5 -> 1-2,1-3,2-1,2-2,3-1,3-2,3-3


转化为以下形式：
key -> values
1-2,1-3,2-1,2-2,3-1,3-2,3-3 -> j1,j3,j5
1-2,1-3,2-1,2-2,3-1 -> j2,j4

