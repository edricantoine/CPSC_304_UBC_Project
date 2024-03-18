CREATE TABLE Server(sid INT PRIMARY KEY, sname VARCHAR(20), region VARCHAR(20));

CREATE TABLE Player_2(exp INT PRIMARY KEY, lvl INT NOT NULL);

CREATE TABLE Player_4(exp INT PRIMARY KEY, mana INT NOT NULL, FOREIGN KEY (exp) REFERENCES Player_2 (exp));

CREATE TABLE Player_6(exp INT PRIMARY KEY, health INT NOT NULL, FOREIGN KEY (exp) REFERENCES Player_4 (exp));

CREATE TABLE Player_7(
    pname VARCHAR(20), sid INT, wname VARCHAR(20), wid INT, exp INT NOT NULL,
    gname VARCHAR(20), role VARCHAR(20), PRIMARY KEY(pname, sid), FOREIGN KEY (sid) REFERENCES Server (sid),
    FOREIGN KEY (exp) REFERENCES Player_6 (exp), FOREIGN KEY (wid, wname) REFERENCES Weapon_3 (wid, wname),
    FOREIGN KEY (gname) REFERENCES Guild_3 (gname)
                     );

CREATE TABLE Guild_2(rank INT PRIMARY KEY, goldBonus INT);

CREATE TABLE Guild_3(gname VARCHAR(20) PRIMARY KEY, rank INT, FOREIGN KEY (rank) REFERENCES Guild_2 (rank));

CREATE TABLE Potion_2(type VARCHAR(20), size VARCHAR(20), effect VARCHAR(20) NOT NULL, PRIMARY KEY(type, size));

CREATE TABLE Potion_3(ptname VARCHAR, ptid INT, type VARCHAR NOT NULL, size VARCHAR NOT NULL, shopid INT, price INT,
                      PRIMARY KEY (ptname, ptid), FOREIGN KEY (ptname, ptid) REFERENCES Item (iname, iid),
                      FOREIGN KEY (type, size) REFERENCES Potion_2 (type, size),
                      FOREIGN KEY (shopid) REFERENCES Shop (shopid));

CREATE TABLE Weapon_2(type VARCHAR(20) NOT NULL, rarity VARCHAR NOT NULL, damage INT NOT NULL,
                      PRIMARY KEY(type, rarity));

CREATE TABLE Weapon_3(wname VARCHAR(20), wid INT, type VARCHAR(20) NOT NULL, rarity VARCHAR(20) NOT NULL,
                      PRIMARY KEY (wname, wid), FOREIGN KEY (wname, wid) REFERENCES Item (iname, iid),
                      FOREIGN KEY (type, rarity) REFERENCES Weapon_2 (type, rarity));

CREATE TABLE Does (qname VARCHAR(20), pname VARCHAR(20), sid INT, progress INT NOT NULL, PRIMARY KEY(qname, pname, sid),
                   FOREIGN KEY (qname) REFERENCES Quest (qname),
                   FOREIGN KEY (pname, sid) REFERENCES Player_7 (pname, sid));

INSERT INTO Server VALUES (1, 'ServerCA', 'Canada');
INSERT INTO Server VALUES (2, 'ServerUS', 'USA');
INSERT INTO Server VALUES (3, 'ServerSA', 'South America');
INSERT INTO Server VALUES (4, 'ServerAS', 'Asia');
INSERT INTO Server VALUES (5, 'ServerEU', 'Europe');

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
INSERT INTO Player_7 Values ('Leeroy Jenkins', 1, 'Diamond Hammer', 20, 500, 'Pals for Life', 'officer');
INSERT INTO Player_7 Values ('Chuck E. Cheese', 2, NULL, NULL, 1000, 'Beginner Guild', 'recruit');
INSERT INTO Player_7 Values ('Homer Simpsonn', 3, 'Wooden Sword', 16, 100000, NULL, NULL);
INSERT INTO Player_7 Values ('GandalfTheCool', 5, 'Iron Sword', 19, 100000, 'Advanced Guild', 3);

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

INSERT INTO Potion_2 VALUES ('health', 'small', '+10 health');
INSERT INTO Potion_2 VALUES ('speed', 'medium', '+20 speed');
INSERT INTO Potion_2 VALUES ('strength', 'large', '+50 strength');
INSERT INTO Potion_2 VALUES ('defense', 'small', '+10 defense');
INSERT INTO Potion_2 VALUES ('evasion', 'extra-large', '+75 evasion');

INSERT INTO Potion_3 VALUES ('Small Potion of Health', 6, 1, 'health', 'small');
INSERT INTO Potion_3 VALUES ('Medium Potion of Speed', 7, 1, 'speed', 'medium');
INSERT INTO Potion_3 VALUES ('Large Potion of Strength', 8, 1, 'strength', 'large');
INSERT INTO Potion_3 VALUES ('Small Potion of Defense', 9, 1, 'defense', 'small');
INSERT INTO Potion_3 VALUES ('Extra-Large Potion of Evasion', 10, 1, 'evasion', 'extra-large');

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

INSERT INTO Does VALUES('Buy a Weapon', 'Player1', 1, 50);
INSERT INTO Does VALUES('Slay a Monster', 'Leeroy Jenkins', 1, 100);
INSERT INTO Does VALUES('Deliver a Letter', 'Chuck E. Cheese', 2, 90);
INSERT INTO Does VALUES('Buy a Potion', 'Homer Simpson', 3, 80);
INSERT INTO Does VALUES('Join a Guild', 'GandalfTheCool', 5, 2);

