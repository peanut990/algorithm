-- 코드를 입력하세요
SELECT b.title, b.board_id, r.reply_id, r.writer_id, r.contents, r.created_date
from USED_GOODS_BOARD b join USED_GOODS_REPLY r on b.board_id = r.board_id 
where year(b.created_date) = 2022 and month(b.created_date) = 10
order by r.created_date asc, b.title asc;
