-- 코드를 입력하세요
select fp.category, mx_p.max_price, fp.PRODUCT_NAME
from FOOD_PRODUCT fp
    join
        (SELECT category, max(price) as max_price
        from FOOD_PRODUCT
        where category = '과자' or category = '국' or category = '김치' or  category = '식용유'
        group by category
        ) mx_p
    on fp.category = mx_p.category
where fp.price = mx_p.max_price
order by mx_p.max_price desc;


