-- 코드를 작성해주세요
with st as (
    select d.id, s.name, s.category
    from developers d
    join skillcodes s
        on (d.skill_code & s.code) != 0
), gd as(
    select 
        d.id,
        case when ('front end' in (select category from st where d.id = st.id) and 'python' in (select name from st where d.id = st.id)) > 0 then 'A'
            when ('c#' in (select name from st where d.id = st.id) ) > 0 then 'B'
            when ('front end' in (select category from st where d.id = st.id)) >0 then 'C'
        end as grade
    from DEVELOPERS d
    group by d.id
)
select gd.grade, d.id, d.email 
from gd
join developers d
    on gd.id = d.id
where gd.grade is not null
order by gd.grade, d.id;
