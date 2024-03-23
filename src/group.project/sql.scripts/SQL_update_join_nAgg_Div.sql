-- change the owner of a shop
 UPDATE Shop
 SET ownerid = 3, status = 'open'
 WHERE shopid = 1;

-- Get all items from an inventory that cost over 50 coins
-- a player might do this filter items to sell
 SELECT * 
 FROM Inventory JOIN Item ON Inventory.invid = Item.invid
 WHERE value > 50;

-- rationale: an achievement for a player to complete every quest
 SELECT DISTINCT pname
 FROM Does D
 GROUP BY pname 
 HAVING COUNT(DISTINCT D.pname) = (SELECT COUNT(Q.qname) FROM Quest Q);

 -- Average level of players in each guild
 SELECT gname, AVG(avg_level) AS avg_guild_level
 FROM (SELECT gname, AVG(level) AS avg_level
       FROM Player 
       GROUP BY gname, pname
 ) AS guild_player_average
 GROUP BY gname;
