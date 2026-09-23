-- 코드를 입력하세요
SELECT BOOK_ID, PUBLISHED_DATE from book where PUBLISHED_DATE >= '2021-01-01'
and PUBLISHED_DATE <= '2021-12-31'
and category = '인문' order by PUBLISHED_DATE asc;