-- 코드를 입력하세요
SELECT user_id, nickname, sum(price) as total_sales
from USED_GOODS_BOARD gb
join USED_GOODS_USER gu
on gb.writer_id = gu.user_id
where status = 'done'
group by user_id
having sum(price) >= 700000
order by total_sales
;