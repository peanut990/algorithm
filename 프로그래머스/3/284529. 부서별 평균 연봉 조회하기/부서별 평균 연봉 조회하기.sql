-- 코드를 작성해주세요
select d.dept_id, d.DEPT_NAME_EN, round(avg(sal)) as avg_sal
from HR_DEPARTMENT d
inner join HR_EMPLOYEES e
on d.dept_id = e.dept_id
group by d.dept_id, d.DEPT_NAME_EN
order by avg_sal desc;