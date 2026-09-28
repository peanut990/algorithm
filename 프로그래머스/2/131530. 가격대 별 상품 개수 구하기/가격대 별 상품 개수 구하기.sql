-- 코드를 입력하세요
SELECT 
    (truncate(price/10000,0) * 10000) as PRICE_GROUP,
    count(*)
from product
group by truncate(price/10000,0) * 10000
order by PRICE_GROUP;
