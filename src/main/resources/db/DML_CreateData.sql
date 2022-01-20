
--- Room Categories
insert into room_category(domain_id, name, description)
VALUES ('1', 'Single Room', 'You love spending the whole day in nature, exploring the mountain world extensively and are a fan of beautiful rooms with a casual stylish look, without wanting too much of a good thing. Our lovely double room Casual, with approx. 25 m², offers all the amenities that mountain enthusiasts like after a full day: comfortable box spring bed for a wonderful night`s sleep,
        beautiful bathroom with large rain shower, mini - fridge(not filled), Nespresso machine, kettle,
        balcony and much more. The \"Casual\" can also be booked for single use');

insert into room_category(domain_id, name, description)
VALUES ('2', 'Double Room', 'In our double room \"Stylish\" you will be pampered with a king-size box-spring bed. You will sleep pleasantly quiet and fully relax on holiday - like a prince and princess. With approx. 30 m², the two of you have plenty of space. You will enjoy using the stylish sofa or the recliner to relax, while your wife occupies the bathroom for evening styling. The balcony with its magnificent view will tempt you out in the morning to enjoy a private cup of Nespresso coffee together.');

insert into room_category(domain_id, name, description)
VALUES ('3', 'Junior Suite', 'In our junior suite \"Lifestyle\", we placed great emphasis on the balance between the living and sleeping areas. Spaciously divided, the sofa bed corner invites you to relax during the day. In the box spring bed you will wake up the next morning fully rested and relaxed. The bathroom with integrated infrared cabin, two washbasins, separately integrated WC and a large rain shower is already a great feel-good bathroom. Flat screen TV, Nespresso machine, mini-fridge (not filled), kettle and many other small details are part of the standard equipment');

insert into room_category(domain_id, name, description)
VALUES ('4', 'Suite', 'You will reside in our WOW suite as if in your own little flat. Step inside - you will be amazed. You will find everything you need for a perfect wellness holiday with lots of privacy and amenities that have been thought out down to the smallest detail. The separate living room with open fireplace is your personal retreat with a glass of good wine in the evening. In the bathroom you are welcome to use your own sauna and then cool off in the outdoor jacuzzi on the secluded terrace. If you have visitors, there is a guest toilet available.');

--- Seasons
insert into season(start_date, end_date, domain_id, name)
VALUES('2021-12-01', '2022-01-31', '1', 'Winter 2021/2022');

insert into season(start_date, end_date, domain_id, name)
VALUES('2022-02-01', '2022-05-31', '2', 'Spring 2022');

insert into season(start_date, end_date, domain_id, name)
VALUES('2022-06-01', '2022-09-30', '3', 'Summer 2022');

insert into season(start_date, end_date, domain_id, name)
VALUES('2022-10-01', '2022-11-30', '4', 'Fall 2022');

insert into season(start_date, end_date, domain_id, name)
VALUES('2022-12-01', '2023-01-31', '5', 'Winter 2022/2023');

insert into season(start_date, end_date, domain_id, name)
VALUES('2023-02-01', '2023-05-31', '6', 'Spring 2023');

insert into season(start_date, end_date, domain_id, name)
VALUES('2023-06-01', '2023-09-30', '7', 'Summer 2023');

insert into season(start_date, end_date, domain_id, name)
VALUES('2023-10-01', '2023-11-30', '8', 'Fall 2023');

insert into season(start_date, end_date, domain_id, name)
VALUES('2023-12-01', '2024-01-31', '9', 'Winter 2023/2024');

--- Category Prices
--- Single Room
insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('1', '1', '1', 50);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('2', '2', '1', 30);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('3', '3', '1', 40);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('4', '4', '1', 20);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('5', '5', '1', 50);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('6', '6', '1', 30);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('7', '7', '1', 40);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('8', '8', '1', 20);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('9', '9', '1', 50);

--- Double Room
insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('10', '1', '2', 90);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('11', '2', '2', 60);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('12', '3', '2', 80);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('13', '4', '2', 50);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('14', '5', '2', 90);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('15', '6', '2', 60);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('16', '7', '2', 80);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('17', '8', '2', 50);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('18', '9', '2', 90);

--- Junior Suite
insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('19', '1', '3', 120);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('20', '2', '3', 90);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('21', '3', '3', 110);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('22', '4', '3', 80);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('23', '5', '3', 120);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('24', '6', '3', 90);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('25', '7', '3', 110);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('26', '8', '3', 80);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('27', '9', '3', 120);

--- Suite
insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('28', '1', '4', 150);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('29', '2', '4', 120);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('30', '3', '4', 140);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('31', '4', '4', 110);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('32', '5', '4', 150);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('33', '6', '4', 120);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('34', '7', '4', 140);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('35', '8', '4', 110);

insert into room_category_price(domain_id, season_id, roomcategory_id, price)
VALUES ('36', '9', '4', 150);

--- Guests
insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1999-01-02', '+43 680 7994750', 'ali.cinar@gmail.at', 20, 0, '1', 'Ali', 'Cinar', 'Hauptplatz', '73', 'Oberkulm', '4210', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1999-01-02', '+43 650 7131945', 'michael.spiegel@gmx.at', 30, 0, '2', 'Michael', 'Spiegel', 'Polletstraße', '26', 'Wien', '1220', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1999-01-01', '+43 660 2648080', 'johannes.moosbrugger@gmx.at', 20, 0, '3', 'Johannes', 'Moosbrugger', 'Birkenweg', '9', 'Radstadt', '5550', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1999-01-01', '+43 676 4679464', 'dario.birbamer@gmail.at', 20, 0, '4', 'Dario', 'Birbarmer', 'Forststraße', '8', 'Kirchbichl', '6322', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1999-01-01', '+43 680 4589452', 'umut.caglayan@gmx.at', 20, 0, '5', 'Umut', 'Caglayan', 'Maternaweg', '18', 'Wien', '1160', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1985-03-08', '+43 680 1593698', 'rainer.zufall@students.fhv.at', 0, 0, '6', 'Rainer', 'Zufall', 'Floridusgasse', '96', 'Wiederndorf', '9150', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1989-08-12', '+43 680 9534658', 'klara.fall@students.fhv.at', 0, 1, '7', 'Klara', 'Fall', 'Pazmaniteng', '25', 'Aham', '4800', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1975-03-20', '+43 680 3576523', 'ernst.haft@students.fhv.at', 0, 0, '8', 'Ernst', 'Haft', 'Stubengraben', '47', 'Ebersdorf', '8342', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1950-09-23', '+43 7633331220389', 'thomas.pfeifer@gmx.at', 0, 0, '9', 'Thomas', 'Pfeifer', 'Enzingerstr.', '8', 'Perg', '6298', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1954-07-25', '+43 6280644508803', 'mateo.weninger@ddd.at', 0, 0, '10', 'Mateo', 'Weninger', 'Krausplatz', '3', 'Sankt Johann im Pongau', '7403', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1933-06-14', '+43 7692974705660', 'marvin.froschauer@gmx.at', 0, 2, '11', 'Marvin', 'Froschauer', 'Cornelius-Koch-Straße', '8', 'Leoben', '1908', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1937-05-11', '+43 3815405747977', 'selena.heinzl@yahoo.com', 0, 1, '12', 'Selena', 'Heinzl', 'Bachlerweg', '9/1', 'Zistersdorf', '8230', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('2000-03-09', '+43 2906032928062', 'jannis.zechner@outlook.de', 0, 0, '13', 'Jannis', 'Zechner', 'Grünwaldgasse', '0/5', 'Leoben', '5842', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1976-04-19', '+43 4523507680223', 'julia.probst@ddd.at', 0, 1, '14', 'Julia', 'Probst', 'Rachel-Fankhauser-Gasse', '806', 'Marchtrenk', '5544', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1994-06-17', '+43 3950357810429', 'natan.loibl@outlook.de', 0, 0, '15', 'Natan', 'Loibl', 'Adamgasse', '73', 'Grieskirchen', '8540', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1962-02-10', '+43 4658770110593', 'lukas.kastner@yahoo.com', 0, 0, '16', 'Lukas', 'Kastner', 'Gschaiderring', '6/0', 'Dürnstein', '5697', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1930-04-07', '+43 0444413756617', 'igor.paul@tdd.at', 0, 0, '17', 'Igor', 'Paul', 'Rosa-Kopp-Ring', '5/2', 'Marchegg', '3765', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1963-03-01', '+43 5530530488509', 'anja.neumann@ddd.at', 0, 1, '18', 'Anja', 'Neumann', 'Madeleine-Brandl-Gasse', '82', 'Stockerau', '3773', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1972-08-16', '+43 0716275539087', 'matteo.zettl@outlook.de', 0, 2, '19', 'Matteo', 'Zettl', 'Faschingweg', '18', 'Mautern an der Donau', '8180', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1941-07-19', '+43 8636272687471', 'david.klinger@ddd.at', 0, 0, '20', 'David', 'Klinger', 'Luana-Geiger-Weg', '51', 'Ybbs an der Donau', '8910', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1961-11-02', '+43 4416281800124', 'constanze.krainz@tdd.at', 0, 1, '21', 'Constanze', 'Krainz', 'Lucia-Köhler-Ring', '245', 'Radenthein', '6045', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1946-11-01', '+43 2983915208049', 'nele.steinkellner@tdd.at', 0, 1, '22', 'Nele', 'Steinkellner', 'Robert-Trimmel-Gasse', '1/1', 'Rattenberg', '8717', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('2003-12-19', '+43 9052354113676', 'sandro.wöhrer@gmx.at', 0, 0, '23', 'Sandro', 'Wöhrer', 'Leonie-Deutschmann-Platz', '28', 'Purbach am Neusiedler See', '7273', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1976-12-30', '+43 5993884607636', 'josef.lagler@tdd.at', 0, 0, '24', 'Josef', 'Lagler', 'Lucia-Wolf-Gasse', '50', 'Fehring', '8950', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1990-01-27', '+43 5209125220099', 'elsa.adler@outlook.de', 0, 1, '25', 'Elsa', 'Adler', 'Aylin-Wagner-Straße', '3/7', 'Zeltweg', '3360', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1956-02-19', '+43 2506943446949', 'franz.macher@tdd.at', 0, 0, '26', 'Franz', 'Macher', 'Stolzweg', '6', 'Gföhl', '7280', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1953-11-13', '+43 9565324776770', 'felix.hirschmann@tdd.at', 0, 0, '27', 'Felix', 'Hirschmann', 'Verena-Wurzinger-Straße', '809', 'Güssing', '6243', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1932-06-19', '+43 2753910325075', 'ilias.köhler@gmx.at', 0, 0, '28', 'Ilias', 'Köhler', 'Bertholdgasse', '1/5', 'Salzburg', '9868', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1995-02-14', '+43 7590596973157', 'lionel.hausberger@outlook.de', 0, 0, '29', 'Lionel', 'Hausberger', 'Hinterbergergasse', '8', 'Ebreichsdorf', '6835', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1975-06-13', '+43 6343305560269', 'jan.koch@gmail.com', 0, 0, '30', 'Jan', 'Koch', 'Dünserstr.', '5', 'Friedberg', '9921', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1996-02-15', '+43 0824776320970', 'raphael.bernhard@tdd.at', 0, 0, '31', 'Raphael', 'Bernhard', 'Elona-Pfister-Gasse', '8', 'Sankt Andrä im Lavanttal', '7202', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('2000-01-31', '+43 5880979916541', 'stefan.frisch@tdd.at', 0, 0, '32', 'Stefan', 'Frisch', 'Boris-Teufl-Ring', '10', 'Wien', '7484', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1941-01-01', '+43 1794031018893', 'amelia.hahn@gmail.com', 0, 1, '33', 'Amelia', 'Hahn', 'Schinaglstraße', '6', 'Lienz', '3818', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1963-04-11', '+43 6625086665358', 'charlotte.denk@outlook.de', 0, 1, '34', 'Charlotte', 'Denk', 'Wurmring', '2', 'Ternitz', '6734', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1970-01-22', '+43 4159350342871', 'luka.stummer@gmail.com', 0, 0, '35', 'Luka', 'Stummer', 'Haiderstraße', '2', 'Steyr', '9962', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1973-08-28', '+43 7900077434320', 'anna-lena.schlögl@yahoo.com', 0, 1, '36', 'Anna-Lena', 'Schlögl', 'Krainerweg', '280', 'Wolkersdorf', '2077', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1926-03-19', '+43 4383680981043', 'raffael.hofinger@outlook.de', 0, 0, '37', 'Raffael', 'Hofinger', 'Wandlweg', '965', 'Imst', '6480', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1933-05-22', '+43 1442191223500', 'fernando.neuner@gmail.com', 0, 0, '38', 'Fernando', 'Neuner', 'Tanja-Klein-Platz', '574', 'Tulln an der Donau', '4055', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1954-07-02', '+43 0521671396561', 'christian.lammer@gmx.at', 0, 0, '39', 'Christian', 'Lammer', 'Antonio-Kraft-Weg', '2/7', 'Bleiburg', '8646', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1924-05-22', '+43 6037141021798', 'damian.wallner@gmx.at', 0, 0, '40', 'Damian', 'Wallner', 'Josefine-Holzmann-Platz', '08', 'Gmunden', '7622', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1938-05-27', '+43 2783823488152', 'lucia.jöbstl@gmx.at', 0, 1, '41', 'Lucia', 'Jöbstl', 'Kummerstraße', '0/7', 'Spittal an der Drau', '8730', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1948-09-13', '+43 3055445543242', 'leander.konrad@yahoo.com', 0, 0, '42', 'Leander', 'Konrad', 'Ackerlplatz', '68', 'Attnang-Puchheim', '5262', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1972-07-20', '+43 2478489204221', 'lino.burtscher@yahoo.com', 0, 0, '43', 'Lino', 'Burtscher', 'Hornring', '4/9', 'Tulln an der Donau', '6800', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1967-08-15', '+43 4035557814764', 'dana.vogt@gmx.at', 0, 2, '44', 'Dana', 'Vogt', 'Schwendingerweg', '2/4', 'Scheibbs', '2096', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1939-09-07', '+43 4079464633366', 'emanuel.hausberger@tdd.at', 0, 2, '45', 'Emanuel', 'Hausberger', 'Laurenz-Kraxner-Platz', '531', 'Ebreichsdorf', '7978', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1990-08-07', '+43 4198295885627', 'alara.grabner@yahoo.com', 0, 1, '46', 'Alara', 'Grabner', 'Melisa-Spitzer-Gasse', '11', 'Oberwart', '5450', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1980-10-04', '+43 3993330843446', 'jasmine.heller@outlook.de', 0, 1, '47', 'Jasmine', 'Heller', 'Pröllgasse', '3/9', 'Raabs an der Thaya', '3115', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1973-06-12', '+43 4463525565106', 'miriam.böhm@gmx.at', 0, 1, '48', 'Miriam', 'Böhm', 'Brandnerring', '1/3', 'Gmünd in Kärnten', '1858', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1928-11-19', '+43 9792623973258', 'eva.mader@gmail.com', 0, 1, '49', 'Eva', 'Mader', 'Nageleweg', '75', 'Wörgl', '4105', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1995-08-06', '+43 6382346448636', 'linus.sommer@gmail.com', 0, 0, '50', 'Linus', 'Sommer', 'Glaserweg', '141', 'Wolkersdorf', '6939', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1998-02-11', '+43 2100658683227', 'daniela.hofbauer@yahoo.com', 0, 1, '51', 'Daniela', 'Hofbauer', 'John-Pöll-Platz', '78', 'Ansfelden', '4921', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1955-11-28', '+43 9863790868436', 'leonie.hofmann@outlook.de', 0, 1, '52', 'Leonie', 'Hofmann', 'Schimplweg', '35', 'Pöchlarn', '8740', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('2000-07-29', '+43 7200712700521', 'anna-lena.brandstätter@tdd.at', 0, 1, '53', 'Anna-Lena', 'Brandstätter', 'Petzstraße', '4', 'Landeck', '2311', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1978-02-28', '+43 4428076627844', 'leon.ebner@outlook.de', 0, 0, '54', 'Leon', 'Ebner', 'Marxplatz', '6', 'Neunkirchen', '6865', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1956-09-13', '+43 1975281232340', 'dora.konrad@outlook.de', 0, 1, '55', 'Dora', 'Konrad', 'Seilerstr.', '0/8', 'Altheim', '3839', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1995-11-15', '+43 6333854768367', 'jasmina.pieber@gmx.at', 0, 1, '56', 'Jasmina', 'Pieber', 'Karina-Zauner-Platz', '04', 'Oberndorf bei Salzburg', '4096', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1965-03-07', '+43 3569474773282', 'benjamin.gratzl@tdd.at', 0, 0, '57', 'Benjamin', 'Gratzl', 'Marie-Krall-Weg', '35', 'Waidhofen an der Ybbs', '3945', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1936-01-27', '+43 5689462996604', 'sonja.neuner@ddd.at', 0, 1, '58', 'Sonja', 'Neuner', 'Mayrstr.', '2', 'Neusiedl am See', '7402', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1978-12-02', '+43 8450558299273', 'dennis.ritter@tdd.at', 0, 0, '59', 'Dennis', 'Ritter', 'Lilly-Hochreiter-Gasse', '84', 'Purbach am Neusiedler See', '1620', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1985-01-01', '+43 1234567891', 'jonathan.thaler@tdd.fhv.at', 100, 0, '60', 'Jonathan', 'Thaler', 'Hochschulstraße', '1', 'Dornbirn', '6850', 'Austria');

--- Services
insert into service(domain_id, name, price)
VALUES('1', 'WLAN', 5);

insert into service(domain_id, name, price)
VALUES('2', 'Swimming Pool', 12);

insert into service(domain_id, name, price)
VALUES('3', 'Sauna', 7);

insert into service(domain_id, name, price)
VALUES('4', 'Fitness Room', 8);

insert into service(domain_id, name, price)
VALUES('5', 'News Paper', 2);

insert into service(domain_id, name, price)
VALUES('6', 'Parking Place', 10);

insert into service(domain_id, name, price)
VALUES('7', 'Minibar', 15);

--- Rooms
--- Single Rooms
insert into room(room_name, status, roomcategory_id)
VALUES('S101', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S102', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S103', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S104', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S105', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S201', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S202', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S203', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S204', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S205', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S301', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S302', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S303', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S304', 0, '1');

insert into room(room_name, status, roomcategory_id)
VALUES('S305', 0, '1');

--- Double Rooms
insert into room(room_name, status, roomcategory_id)
VALUES('D106', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D107', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D108', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D109', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D110', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D111', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D112', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D113', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D114', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D115', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D116', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D117', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D118', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D119', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D120', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D206', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D207', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D208', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D209', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D210', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D211', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D212', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D213', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D214', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D215', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D216', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D217', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D218', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D219', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D220', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D306', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D307', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D308', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D309', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D310', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D311', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D312', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D313', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D314', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D315', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D316', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D317', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D318', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D319', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D320', 0, '2');

--- Junior Suite
insert into room(room_name, status, roomcategory_id)
VALUES('J401', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J402', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J403', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J404', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J405', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J406', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J407', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J408', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J409', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J410', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J411', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J412', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J413', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J414', 0, '3');

--- Suite
insert into room(room_name, status, roomcategory_id)
VALUES('SU415', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU416', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU417', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU418', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU419', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU420', 0, '4');