-- 코드를 입력하세요
SELECT f.FLAVOR FROM FIRST_HALF f inner join ICECREAM_INFO i on f.FLAVOR = i.FLAVOR where total_order >= 3000 and INGREDIENT_TYPE = 'fruit_based' order by total_order desc;