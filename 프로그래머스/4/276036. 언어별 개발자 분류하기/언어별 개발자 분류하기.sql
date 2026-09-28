-- 코드를 작성해주세요
select 
    case
        when
            max(s.name = 'python') = 1 and max(s.category = 'front end') = 1
        then 'A'
        when
            max(s.name = 'c#') = 1
        then 'B'
        when
            max(s.category = 'front end') = 1
        then 'C'
    end as grade,
    d.id,
    d.email
from developers d
join
    skillcodes s
    on (d.skill_code & s.code) != 0
group by d.id
having grade is not null
order by grade, id;

