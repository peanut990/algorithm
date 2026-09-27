-- 코드를 입력하세요
# SELECT count(distinct name) from animal_ins where name is not null;
select count(*)
from (
select count(*)
from animal_ins
where name is not null
group by (name)
) c;