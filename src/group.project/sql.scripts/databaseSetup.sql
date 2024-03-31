DROP TABLE Server CASCADE CONSTRAINTS;
DROP TABLE NPC CASCADE CONSTRAINTS ;
DROP TABLE Quest CASCADE CONSTRAINTS;
DROP TABLE Shop CASCADE CONSTRAINTS;
DROP TABLE Guild_2 CASCADE CONSTRAINTS;
DROP TABLE Guild_3 CASCADE CONSTRAINTS;
DROP TABLE Item CASCADE CONSTRAINTS;
DROP TABLE QuestItem CASCADE CONSTRAINTS;
DROP TABLE Potion_2 CASCADE CONSTRAINTS;
DROP TABLE Potion_3 CASCADE CONSTRAINTS;
DROP TABLE Weapon_2 CASCADE CONSTRAINTS;
DROP TABLE Weapon_3 CASCADE CONSTRAINTS;
DROP TABLE Player_2 CASCADE CONSTRAINTS;
DROP TABLE Player_4 CASCADE CONSTRAINTS;
DROP TABLE Player_6 CASCADE CONSTRAINTS;
DROP TABLE Player_7 CASCADE CONSTRAINTS;
DROP TABLE Inventory CASCADE CONSTRAINTS;
DROP TABLE Does CASCADE CONSTRAINTS;

CREATE TABLE Server(sid INT PRIMARY KEY, sname VARCHAR(50), region VARCHAR(50));

CREATE TABLE NPC(
                    nid INT PRIMARY KEY,
                    nname VARCHAR(50) NOT NULL
);

CREATE TABLE Quest(
                      qname VARCHAR(50) PRIMARY KEY,
                      giverid INT NOT NULL,
                      exp INT NOT NULL,
                      minlevel INT NOT NULL,
                      objectives VARCHAR(50),
                      UNIQUE(objectives),
                      FOREIGN KEY (giverid) REFERENCES NPC(nid)
                        ON DELETE CASCADE
);

CREATE TABLE Shop(
                     shopid INT PRIMARY KEY,
                     ownerid INT NOT NULL,
                     status VARCHAR(50) NOT NULL,
                     UNIQUE (ownerid),
                     FOREIGN KEY (ownerid) REFERENCES NPC (nid)
                    ON DELETE CASCADE
);

CREATE TABLE Guild_2(rank INT PRIMARY KEY, goldBonus INT);

CREATE TABLE Guild_3(gname VARCHAR(50) PRIMARY KEY, rank INT, FOREIGN KEY (rank) REFERENCES Guild_2 (rank) ON DELETE CASCADE);


CREATE TABLE Potion_2(type VARCHAR(50), sz VARCHAR(50), effect VARCHAR(50) NOT NULL, PRIMARY KEY(type, sz));

CREATE TABLE Weapon_2(type VARCHAR(50) NOT NULL, rarity VARCHAR(50) NOT NULL, damage INT NOT NULL,
                      PRIMARY KEY(type, rarity));

CREATE TABLE Player_2(exp INT PRIMARY KEY, lvl INT NOT NULL);

CREATE TABLE Player_4(exp INT PRIMARY KEY, mana INT NOT NULL, FOREIGN KEY (exp) REFERENCES Player_2 (exp) ON DELETE CASCADE);

CREATE TABLE Player_6(exp INT PRIMARY KEY, health INT NOT NULL, FOREIGN KEY (exp) REFERENCES Player_4 (exp) ON DELETE CASCADE);
CREATE TABLE Inventory(
                          invid INT PRIMARY KEY,
                          pname VARCHAR(50) NOT NULL,
                          sid INT NOT NULL,
                          sz INT NOT NULL,
                          UNIQUE(pname, sid)
);
CREATE TABLE Item(
                     iname VARCHAR(50),
                     iid INT,
                     invid INT,
                     questname VARCHAR(50),
                     value INT NOT NULL,
                     PRIMARY KEY (iname, iid),
                     FOREIGN KEY (questname) REFERENCES Quest (qname) ON DELETE SET NULL,
                     FOREIGN KEY (invid) REFERENCES Inventory (invid) ON DELETE SET NULL
);
CREATE TABLE Potion_3(ptname VARCHAR(50), ptid INT, type VARCHAR(50) NOT NULL, sz VARCHAR(50) NOT NULL, shopid INT, price INT,
                      PRIMARY KEY (ptname, ptid), FOREIGN KEY (ptname, ptid) REFERENCES Item (iname, iid),
                      FOREIGN KEY (type, sz) REFERENCES Potion_2 (type, sz),
                      FOREIGN KEY (shopid) REFERENCES Shop (shopid) ON DELETE SET NULL);



CREATE TABLE Weapon_3(wname VARCHAR(50), wid INT, type VARCHAR(50) NOT NULL, rarity VARCHAR(50) NOT NULL,
                      PRIMARY KEY (wname, wid), FOREIGN KEY (wname, wid) REFERENCES Item (iname, iid),
                      FOREIGN KEY (type, rarity) REFERENCES Weapon_2 (type, rarity));

CREATE TABLE QuestItem(
                          qiname VARCHAR(50),
                          qiid INT,
                          type VARCHAR(50) NOT NULL,
                          description VARCHAR(50) NOT NULL,
                          PRIMARY KEY (qiname, qiid),
                          FOREIGN KEY (qiname, qiid) REFERENCES Item (iname, iid)
);

CREATE TABLE Player_7(
                         pname VARCHAR(50), sid INT, wname VARCHAR(50), wid INT, exp INT NOT NULL,
                         gname VARCHAR(50), role VARCHAR(50), PRIMARY KEY(pname, sid), FOREIGN KEY (sid) REFERENCES Server (sid),
                         FOREIGN KEY (exp) REFERENCES Player_6 (exp) ON DELETE CASCADE, FOREIGN KEY (wid, wname) REFERENCES Weapon_3 (wid, wname) ON DELETE SET NULL,
                         FOREIGN KEY (gname) REFERENCES Guild_3 (gname) ON DELETE SET NULL
);

CREATE TABLE Does (qname VARCHAR(50), pname VARCHAR(50), sid INT, progress INT NOT NULL, PRIMARY KEY(qname, pname, sid),
                   FOREIGN KEY (qname) REFERENCES Quest (qname) ON DELETE CASCADE,
                   FOREIGN KEY (pname, sid) REFERENCES Player_7 (pname, sid) ON DELETE CASCADE);

ALTER TABLE Inventory ADD FOREIGN KEY (pname, sid) REFERENCES Player_7 (pname, sid);

INSERT INTO Server VALUES (1, 'ServerCA', 'Canada');
INSERT INTO Server VALUES (2, 'ServerUS', 'USA');
INSERT INTO Server VALUES (3, 'ServerSA', 'South America');
INSERT INTO Server VALUES (4, 'ServerAS', 'Asia');
INSERT INTO Server VALUES (5, 'ServerEU', 'Europe');


INSERT INTO NPC VALUES (1, 'Alice');
INSERT INTO NPC VALUES (2, 'Bob');
INSERT INTO NPC VALUES (3, 'Charles');
INSERT INTO NPC VALUES (4, 'David');
INSERT INTO NPC VALUES (5, 'Emily');
INSERT INTO NPC VALUES (6, 'Frank');
INSERT INTO NPC VALUES (7, 'John McTemporary');

INSERT INTO Quest VALUES ('Buy a Weapon', 1, 100, 1, 'Buy your first weapon');
INSERT INTO Quest VALUES ('Buy a Potion', 2, 150, 5, 'Buy your first potion');
INSERT INTO Quest VALUES ('Slay a Monster', 3, 500, 3, 'Defeat one slime');
INSERT INTO Quest VALUES ('Join a Guild', 4, 1000, 10, 'Join the Beginners Guild');
INSERT INTO Quest VALUES ('Deliver a Letter', 5, 300, 8, 'Deliver the letter to Bob');

INSERT INTO Shop VALUES(1, 1, 'open');
INSERT INTO Shop VALUES(2, 2, 'open');
INSERT INTO Shop VALUES(3, 3, 'closed');
INSERT INTO Shop VALUES(4, 5, 'closed');
INSERT INTO Shop VALUES(5, 6, 'open');
INSERT INTO Shop VALUES(6, 7, 'open');

INSERT INTO Guild_2 VALUES (1, 500);
INSERT INTO Guild_2 VALUES (2, 1000);
INSERT INTO Guild_2 VALUES (3, 1500);
INSERT INTO Guild_2 VALUES (4, 2000);
INSERT INTO Guild_2 VALUES (5, 2500);

INSERT INTO Guild_3 VALUES ('Beginner Guild', 1);
INSERT INTO Guild_3 VALUES ('Intermediate Guild', 2);
INSERT INTO Guild_3 VALUES ('Advanced Guild', 3);
INSERT INTO Guild_3 VALUES ('Expert Guild', 4);
INSERT INTO Guild_3 VALUES ('Pals for Life', 5);
INSERT INTO Guild_3 VALUES ('Rivals for Life', 5);
INSERT INTO Guild_3 VALUES ('Acquaintances for Life', 5);
INSERT INTO Guild_3 VALUES ('Old Hunters', 3);
INSERT INTO Guild_3 VALUES ('Byrgenwerth College', 3);
INSERT INTO Guild_3 VALUES ('Healing Church', 3);
INSERT INTO Guild_3 VALUES ('The Choir', 3);

INSERT INTO Player_2 VALUES (100, 1);
INSERT INTO Player_2 VALUES (500, 3);
INSERT INTO Player_2 VALUES (1000, 7);
INSERT INTO Player_2 VALUES (10000, 20);
INSERT INTO Player_2 VALUES (100000, 50);

INSERT INTO Player_4 Values (100, 50);
INSERT INTO Player_4 Values (500, 100);
INSERT INTO Player_4 Values (1000, 200);
INSERT INTO Player_4 Values (10000, 500);
INSERT INTO Player_4 Values (100000, 800);

INSERT INTO Player_6 VALUES (100, 50);
INSERT INTO Player_6 VALUES (500, 90);
INSERT INTO Player_6 VALUES (1000, 180);
INSERT INTO Player_6 VALUES (10000, 400);
INSERT INTO Player_6 VALUES (100000, 750);

INSERT INTO Player_7 Values ('Player1', 1, NULL, NULL, 100, NULL, NULL);
INSERT INTO Player_7 Values ('Leeroy Jenkins', 1, NULL, NULL, 500, 'Pals for Life', 'officer');
INSERT INTO Player_7 Values ('Chuck E. Cheese', 2, NULL, NULL, 1000, 'Beginner Guild', 'recruit');
INSERT INTO Player_7 Values ('Homer Simpsonn', 3, NULL, NULL, 100000, NULL, NULL);
INSERT INTO Player_7 Values ('GandalfTheCool', 5, NULL, NULL, 100000, 'Advanced Guild', 3);

INSERT INTO Inventory VALUES (1, 'Player1', 1, 10);
INSERT INTO Inventory VALUES (2, 'Leeroy Jenkins', 1, 20);
INSERT INTO Inventory VALUES (3, 'Chuck E. Cheese', 2, 25);
INSERT INTO Inventory VALUES (4, 'Homer Simpsonn', 3, 30);
INSERT INTO Inventory VALUES (5, 'GandalfTheCool', 5, 50);

INSERT INTO Item VALUES('Wooden Sword', 16, 1, 'Buy a Weapon', 5);
INSERT INTO Item VALUES('Stone Hammer', 17, NULL, NULL, 10);
INSERT INTO Item VALUES('Quality Bow', 18, NULL, NULL, 100);
INSERT INTO Item VALUES('Iron Sword', 19, NULL, NULL, 1000);
INSERT INTO Item VALUES('Diamond Hammer', 20, 2, NULL, 10000);

INSERT INTO Weapon_2 VALUES ('sword', 'common', 5);
INSERT INTO Weapon_2 VALUES ('hammer', 'common', 10);
INSERT INTO Weapon_2 VALUES ('bow', 'uncommon', 15);
INSERT INTO Weapon_2 VALUES ('sword', 'rare', 25);
INSERT INTO Weapon_2 VALUES ('hammer', 'legendary', 100);

INSERT INTO Weapon_3 VALUES ('Wooden Sword', 16, 'sword', 'common');
INSERT INTO Weapon_3 VALUES ('Stone Hammer', 17, 'hammer', 'common');
INSERT INTO Weapon_3 VALUES ('Quality Bow', 18, 'bow', 'uncommon');
INSERT INTO Weapon_3 VALUES ('Iron Sword', 19, 'sword', 'rare');
INSERT INTO Weapon_3 VALUES ('Diamond Hammer', 20, 'hammer', 'legendary');

UPDATE PLAYER_7 SET wid = 20, wname='Diamond Hammer' WHERE pname='Leeroy Jenkins' AND sid=1;
UPDATE PLAYER_7 SET wid = 16, wname='Wooden Sword' WHERE pname='Homer Simpsonn' AND sid=3;
UPDATE PLAYER_7 SET wid = 19, wname='Iron Sword' WHERE pname='GandalfTheCool' AND sid=5;

INSERT INTO Item VALUES('iron ingot', 1, 1, NULL, 5);
INSERT INTO Item VALUES('bronze ingot', 2, 1, NULL, 3);
INSERT INTO Item VALUES('tuna', 3, 2, NULL, 2);
INSERT INTO Item VALUES('blue hat', 4, 1, NULL, 10);
INSERT INTO Item VALUES('dandelion', 5, 1, NULL, 1);
INSERT INTO Item VALUES('Small Potion of Health', 6, 3, 'Deliver a Letter', 10);
INSERT INTO Item VALUES('Medium Potion of Speed', 7, 5, NULL, 20);
INSERT INTO Item VALUES('Large Potion of Strength', 8, 1, NULL, 30);
INSERT INTO Item VALUES('Small Potion of Defense', 9, NULL, NULL, 10);
INSERT INTO Item VALUES('Extra-Large Potion of Evasion', 10, NULL, NULL, 40);
INSERT INTO Item VALUES('Steamy Love Letter', 11, NULL, 'Deliver a Letter', -1);
INSERT INTO Item VALUES('Boar Tusk', 12, NULL, 'Slay a Monster', -1);
INSERT INTO Item VALUES('Poisoned Vial', 13, NULL, 'Buy a Potion', -1);
INSERT INTO Item VALUES('Heroes Sword', 14, NULL, 'Buy a Weapon', -1);
INSERT INTO Item VALUES('Quil of Destiny', 15, NULL, 'Join a Guild', -1);

INSERT INTO QuestItem VALUES('Steamy Love Letter', 11, 'readable', 'A poorly written love letter - blech!');
INSERT INTO QuestItem VALUES('Boar Tusk', 12, 'useable', 'The tusk of a Boar');
INSERT INTO QuestItem VALUES('Poisoned Vial', 13, 'useable', 'With or without ice?');
INSERT INTO QuestItem VALUES('Heroes Sword', 14, 'useable', 'The stuff of Legends!');
INSERT INTO QuestItem VALUES('Quil of Destiny', 15, 'useable', 'Cue the Epic Music');

INSERT INTO Potion_2 VALUES ('health', 'small', '+10 health');
INSERT INTO Potion_2 VALUES ('speed', 'medium', '+20 speed');
INSERT INTO Potion_2 VALUES ('strength', 'large', '+50 strength');
INSERT INTO Potion_2 VALUES ('defense', 'small', '+10 defense');
INSERT INTO Potion_2 VALUES ('evasion', 'extra-large', '+75 evasion');

INSERT INTO Potion_3 VALUES ('Small Potion of Health', 6 ,'health', 'small', 1, 50);
INSERT INTO Potion_3 VALUES ('Medium Potion of Speed', 7, 'speed', 'medium', 1, 75);
INSERT INTO Potion_3 VALUES ('Large Potion of Strength', 8, 'strength', 'large', 1, 100);
INSERT INTO Potion_3 VALUES ('Small Potion of Defense', 9, 'defense', 'small', 1, 50);
INSERT INTO Potion_3 VALUES ('Extra-Large Potion of Evasion', 10, 'evasion', 'extra-large', 1, 125);

INSERT INTO Does VALUES('Buy a Weapon', 'Player1', 1, 50);
INSERT INTO Does VALUES('Slay a Monster', 'Leeroy Jenkins', 1, 100);
INSERT INTO Does VALUES('Deliver a Letter', 'Chuck E. Cheese', 2, 90);
INSERT INTO Does VALUES('Buy a Potion', 'Homer Simpsonn', 3, 80);
INSERT INTO Does VALUES('Join a Guild', 'GandalfTheCool', 5, 2);

