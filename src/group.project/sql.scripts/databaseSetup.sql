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





