-- 코드를 작성해주세요


with st as (
    select d.id, s.name, s.category
    from developers d
    join skillcodes s
        on (d.skill_code & s.code) != 0
), gd as(
    select 
        d.id,
        d.email,
        case when ('front end' in (select category from st where d.id = st.id) and 'python' in (select name from st where d.id = st.id)) then 'A'
            when ('c#' in (select name from st where d.id = st.id) ) then 'B'
            when ('front end' in (select category from st where d.id = st.id)) then 'C'
        end as grade
    from DEVELOPERS d
)
select grade, id, email 
from gd
where grade is not null
order by grade, id;
