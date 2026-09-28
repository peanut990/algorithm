-- 코드를 작성해주세요
with recursive gt as (
    select 
        id, parent_id, 1 as generation
    from ecoli_data
    where parent_id is null
    
    union all
    
    select
        c.id, c.parent_id, gt.generation + 1
    from ecoli_data c
    join gt
        on c.parent_id = gt.id 
)
select count(*), generation
from gt c
where not exists (select * from gt p where p.parent_id = c.id)
group by c.generation
order by c.generation;