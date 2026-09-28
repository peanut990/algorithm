-- 코드를 입력하세요
SELECT animal_type, count(*)
from ANIMAL_INS
where animal_type = 'cat' or animal_type = 'dog'
group by animal_type
order by animal_type;