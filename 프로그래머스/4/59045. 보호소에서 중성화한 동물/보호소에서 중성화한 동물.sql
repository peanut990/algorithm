-- 코드를 입력하세요
SELECT ao.animal_id, ao.animal_type, ao.name
from animal_ins ai
join animal_outs ao
on ai.animal_id = ao.animal_id
where ai.SEX_UPON_INTAKE like '%intact%' and (ao.SEX_UPON_OUTCOME like '%spayed%' or ao.SEX_UPON_OUTCOME like '%neutered%')
order by ao.animal_id;