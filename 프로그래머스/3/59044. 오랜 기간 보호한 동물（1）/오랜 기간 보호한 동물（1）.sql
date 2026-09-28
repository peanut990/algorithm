-- 코드를 입력하세요
# SELECT `in`.name, `in`.datetime
# from animal_ins `in`
# left join animal_outs `out`
# on `in`.animal_id = `out`.animal_id
# where `out`.datetime is null
# order by `in`.datetime
# limit 3;

# SELECT `in`.name, `in`.datetime
# from animal_ins `in`
# where not exists ( select 1 from animal_outs `out` where `in`.animal_id = `out`.animal_id )
# order by `in`.datetime
# limit 3;

SELECT `in`.name, `in`.datetime
from animal_ins `in`
where `in`.animal_id not in ( select `out`.animal_id from animal_outs `out`)
order by `in`.datetime
limit 3;