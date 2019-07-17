
/factory/GetWorker2StepMappingForTheUserTaskHost.php?factoryHostId=?


select step.app_id, step.recipe_index, step.step_index
from team_factory_worker tfw, team, step_team st, user, step, recipe, app
where tfw.factory_host_id = 701 and tfw.s1 = 1 
and tfw.team_id = team.id and team.status = 'ACTIVE'
and tfw.team_id = st.team_id and st.user_id = 201
and st.user_id = user.id and user.status = 'ACTIVE'
and st.app_id = step.app_id and st.recipe_index = step.recipe_index and st.step_index = step.step_index and step.status = 'ACTIVE'
and st.app_id = recipe.app_id and st.recipe_index = recipe.recipe_index and recipe.status = 'ACTIVE'
and st.app_id = app.id and app.status = 'ACTIVE'
and app.task_host_id = 301
group by step.app_id, step.recipe_index, step.step_index
order by step.app_id, step.recipe_index, step.step_index;





select step.app_id, step.recipe_index, step.step_index
from team_factory_worker tfw, team, step_team st, user, step, recipe, app
where tfw.factory_host_id = <701> and tfw.s1 = <1> 
and tfw.team_id = team.id and team.status = 'ACTIVE'
and tfw.team_id = st.team_id and st.user_id = <?>
and st.user_id = user.id and user.status = 'ACTIVE'
and st.app_id = step.app_id and st.recipe_index = step.recipe_index and st.step_index = step.step_index and step.status = 'ACTIVE'
and st.app_id = recipe.app_id and st.recipe_index = recipe.recipe_index and recipe.status = 'ACTIVE'
and st.app_id = app.id and app.status = 'ACTIVE'
and app.task_host_id = <?>
group by step.app_id, step.recipe_index, step.step_index
order by step.app_id, step.recipe_index, step.step_index;





select step.app_id, step.recipe_index, step.step_index 
order by step.app_id, step.recipe_index, step.step_index
group by step.app_id, step.recipe_index, step.step_index

team_factory_worker -> team -> user_team -> step_team -> step -> user_task_host
factory_host_id=?              一个team可能		        可能由任务   user_id=?
s1 = 1                         存在两条记录                 处理快关    task_host_id=?

以上SQL, 执行 10 + 50 次

s1 -> 1-2-3,1-2-4,1-2-5,1-2-6
s2 -> 1-2-3,1-2-4,1-2-5
s3 -> 1-2-3,1-2-4,1-2-5,1-2-6
s4 -> 1-2-3,1-2-4,1-2-5
s5 -> 1-2-3,1-2-4,1-2-5,1-2-6

转化为以下形式：
key -> values
1-2-3,1-2-4,1-2-5,1-2-6 -> 1,3,5
1-2-3,1-2-4,1-2-5 -> 2,4

j1 -> 1-2-3,1-2-4,1-2-5,1-2-6
j2 -> 1-2-3,1-2-4,1-2-5
j3 -> 1-2-3,1-2-4,1-2-5,1-2-6
j4 -> 1-2-3,1-2-4,1-2-5
j5 -> 1-2-3,1-2-4,1-2-5,1-2-6

转化为以下形式：
key -> values
1-2-3,1-2-4,1-2-5,1-2-6 -> 1,3,5
1-2-3,1-2-4,1-2-5 -> 2,4

