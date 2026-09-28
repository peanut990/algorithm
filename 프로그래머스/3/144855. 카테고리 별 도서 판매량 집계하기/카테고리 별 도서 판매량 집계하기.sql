-- 코드를 입력하세요
SELECT category, sum(sales) as total_sales
from book b
join book_sales bs
on b.book_id = bs.book_id
where sales_date >= '2022-01-01' and sales_date < '2022-02-01'
group by category
order by category
;