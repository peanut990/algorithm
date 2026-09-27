-- 코드를 입력하세요
SELECT substring(PRODUCT_CODE,1,2) as category, count(*) as products
from product
group by substring(PRODUCT_CODE,1,2)
order by PRODUCT_CODE;