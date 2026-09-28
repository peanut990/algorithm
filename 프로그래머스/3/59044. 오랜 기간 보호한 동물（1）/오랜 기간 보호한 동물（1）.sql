-- 코드를 입력하세요
SELECT `in`.name, `in`.datetime
from animal_ins `in`
left join animal_outs `out`
on `in`.animal_id = `out`.animal_id
where `out`.datetime is null
order by `in`.datetime
limit 3;