-- 코드를 입력하세요
SELECT book_id, author_name, published_date
from book b
join author a
on b.author_id = a.author_id
where category = '경제'
order by published_date;