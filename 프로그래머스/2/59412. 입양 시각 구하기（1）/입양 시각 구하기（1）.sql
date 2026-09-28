-- 코드를 입력하세요
SELECT hour(datetime), count(*)
from animal_outs
where time(datetime) >= '09:00' and time(datetime) < '20:00'
group by hour(datetime)
order by hour(datetime);