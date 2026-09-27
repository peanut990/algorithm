-- 코드를 입력하세요
# SELECT animal_type, coalesce(name,'No name'), sex_upon_intake from animal_ins;
select animal_type, case when name is null then 'No name' else name end , sex_upon_intake from animal_ins;