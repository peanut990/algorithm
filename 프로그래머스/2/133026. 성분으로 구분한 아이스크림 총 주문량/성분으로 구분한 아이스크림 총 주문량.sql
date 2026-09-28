-- 코드를 입력하세요
SELECT INGREDIENT_TYPE, sum(total_order) as 'TOTAL_ORDER'
from first_half f
join icecream_info i
on f.flavor = i.flavor
group by i.INGREDIENT_TYPE
order by total_order;
