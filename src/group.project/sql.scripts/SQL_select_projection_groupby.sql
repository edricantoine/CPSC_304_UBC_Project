-- Query: Selection
-- Two examples of possible dropdowns in WHERE clause
-- 1) find all quests that a player can complete (meets min level)
SELECT *
FROM Quest q
JOIN Player_7 p7 ON (p7.pname = 'Player1' AND p7.sid = '1')
JOIN Player_6 p6 ON p6.exp = p7.exp
JOIN Player_4 p4 ON p4.exp = p6.exp
JOIN Player_2 p2 ON p2.exp = p4.exp
Where p2.level >= q.minlevel;

-- 2) find all players' names and roles in a given guild
SELECT p7.pname
FROM Player_7 p7, Guild_3 g3
WHERE p7.gname = g3.gname AND g3.gname = 'Rivals for Life';

-- Query: Aggregation with GROUP BY
-- Find total value of all items in an inventory
SELECT iname, COUNT(value) as total_items_value
FROM Inventory JOIN Item ON Inventory.invid = Item.invid
GROUP BY iname

