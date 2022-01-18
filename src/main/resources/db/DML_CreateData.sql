
--- Room Categories
insert into room_category(domain_id, name, description)
VALUES ('1', 'Single Room', 'A room for one person.');

insert into room_category(domain_id, name, description)
VALUES ('2', 'Double Room', 'A room for two persons.');

insert into room_category(domain_id, name, description)
VALUES ('3', 'Junior Suite', 'A luxury small suite.');

insert into room_category(domain_id, name, description)
VALUES ('4', 'Suite', 'A luxury big suite.');

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
VALUES('D306', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D307', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D308', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D309', 0, '2');

insert into room(room_name, status, roomcategory_id)
VALUES('D310', 0, '2');

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
VALUES('J501', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J502', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J503', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J504', 0, '3');

insert into room(room_name, status, roomcategory_id)
VALUES('J505', 0, '3');

    --- Suite
insert into room(room_name, status, roomcategory_id)
VALUES('SU406', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU407', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU408', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU409', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU410', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU506', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU507', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU508', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU509', 0, '4');

insert into room(room_name, status, roomcategory_id)
VALUES('SU510', 0, '4');

--- Services
insert into service(domain_id, name, price)
VALUES('1', 'TV', 10);

insert into service(domain_id, name, price)
VALUES('2', 'WLAN', 5);

insert into service(domain_id, name, price)
VALUES('3', 'Swimming Pool', 12);

insert into service(domain_id, name, price)
VALUES('4', 'Sauna', 7);

insert into service(domain_id, name, price)
VALUES('5', 'Bike', 6);

insert into service(domain_id, name, price)
VALUES('6', 'E-Bike', 10);

insert into service(domain_id, name, price)
VALUES('7', 'Fitness Room', 8);

insert into service(domain_id, name, price)
VALUES('8', 'News Paper', 2);

insert into service(domain_id, name, price)
VALUES('9', 'Parking Place', 10);

insert into service(domain_id, name, price)
VALUES('10', 'Minibar', 15);

--- Guests
insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1999-01-02', '+43 680 7994750', 'ali.cinar@gmail.at', 0, 0, '1', 'Ali', 'Cinar', 'Hauptplatz', '73', 'Oberkulm', '4210', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1999-01-02', '+43 650 7131945', 'michael.spiegel@gmx.at', 0, 0, '2', 'Michael', 'Spiegel', 'Polletstraße', '26', 'Wien', '1220', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1999-01-01', '+43 660 2648080', 'johannes.moosbrugger@gmx.at', 0, 0, '3', 'Johannes', 'Moosbrugger', 'Birkenweg', '9', 'Radstadt', '5550', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1979-10-01', '+43 660 2649080', 'herbert.steurer@yahoo.at', 0, 0, '4', 'Herbert', 'Steurer', 'Kielmansggasse', '29', 'Radstadt', '5550', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1999-01-01', '+43 676 4679464', 'dario.birbarmer@gmail.at', 0, 0, '5', 'Dario', 'Birbarmer', 'Forststraße', '8', 'Kirchbichl', '6322', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1999-01-01', '+43 680 4589452', 'umut.caglayan@gmx.at', 0, 0, '6', 'Umut', 'Caglayan', 'Maternaweg', '18', 'Wien', '1160', 'Austria');

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1969-06-09', '+43 2799702444450', 'emely.steinlechner@gmail.com', 0, 0, '8', 'Emely', 'Steinlechner', 'Piebergasse', '6/6', 'Dürnstein', '6332', 'Austria')

    insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1981-02-25', '+43 0607150720710', 'paula.schwarzl@gmail.com', 0, 0, '9', 'Paula', 'Schwarzl', 'Kai-Mayerhofer-Weg', '27', 'Gänserndorf', '9390', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1963-08-22', '+43 2496303123339', 'natan.buchinger@gmail.com', 0, 0, '10', 'Natan', 'Buchinger', 'Rabitschplatz', '4', 'Friesach', '1012', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1976-05-18', '+43 3405871542869', 'maria.reinthaler@gmail.com', 0, 0, '11', 'Maria', 'Reinthaler', 'Richterweg', '75', 'Knittelfeld', '5188', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1999-02-03', '+43 9021313156625', 'anabella.vogel@gmail.com', 0, 0, '12', 'Anabella', 'Vogel', 'Siebererstraße', '7/7', 'Wilhelmsburg', '5516', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1944-06-18', '+43 3231123694514', 'cristiano.bischof@gmail.com', 0, 0, '13', 'Cristiano', 'Bischof', 'Larcherring', '4', 'Radstadt', '6864', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1982-12-23', '+43 4475869561793', 'david.toth@gmail.com', 0, 0, '14', 'David', 'Toth', 'Natan-Mathis-Platz', '1', 'Lienz', '2151', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('2001-05-21', '+43 1312786324633', 'karina.schuster@gmail.com', 0, 0, '15', 'Karina', 'Schuster', 'Kratzerweg', '87', 'Hallein', '8276', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1987-10-11', '+43 1182768225707', 'viola.rausch@gmail.com', 0, 0, '16', 'Viola', 'Rausch', 'Reichlweg', '9', 'Gerasdorf bei Wien', '4625', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1996-03-23', '+43 1435451663454', 'hubert.ortner@gmail.com', 0, 0, '17', 'Hubert', 'Ortner', 'Finn-Weninger-Gasse', '4/1', 'Zell am See', '1352', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1992-09-21', '+43 0986726580032', 'helene.zettl@gmail.com', 0, 0, '18', 'Helene', 'Zettl', 'Steinböckgasse', '8', 'Steyregg', '4554', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1931-07-07', '+43 4703839823899', 'sarah.staudacher@gmail.com', 0, 0, '19', 'Sarah', 'Staudacher', 'Niederlplatz', '1', 'Eferding', '6009', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('2003-03-14', '+43 3756004640283', 'boris.haderer@gmail.com', 0, 0, '20', 'Boris', 'Haderer', 'Felberweg', '4', 'Feldbach', '5610', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1967-03-04', '+43 4888295828962', 'tristan.riedler@gmail.com', 0, 0, '21', 'Tristan', 'Riedler', 'Hebenstreitring', '918', 'Salzburg', '1749', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1990-04-26', '+43 8080950102717', 'kyra.mörth@gmail.com', 0, 0, '22', 'Kyra', 'Mörth', 'Vera-Mayerhofer-Platz', '91', 'Dornbirn', '4526', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1970-12-17', '+43 0774980536743', 'elsa.kastner@gmail.com', 0, 0, '23', 'Elsa', 'Kastner', 'Karrerstr.', '783', 'Bad Leonfelden', '6250', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1992-06-29', '+43 8116152248054', 'ismail.eberhard@gmail.com', 0, 0, '24', 'Ismail', 'Eberhard', 'Alexandra-Paulitsch-Gasse', '51', 'Schwaz', '4581', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1934-12-04', '+43 8842254617642', 'miriam.grill@gmail.com', 0, 0, '25', 'Miriam', 'Grill', 'Junggasse', '053', 'Feldbach', '6035', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1932-11-12', '+43 7028666804847', 'laurenz.jovanovic@gmail.com', 0, 0, '26', 'Laurenz', 'Jovanovic', 'Kalteneggerring', '0/7', 'Haag', '2818', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1999-08-28', '+43 3072090960758', 'michelle.jost@gmail.com', 0, 0, '27', 'Michelle', 'Jost', 'Tothring', '07', 'Eisenstadt', '4826', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1993-08-24', '+43 7513936523192', 'lara-sophie.kocher@gmail.com', 0, 0, '28', 'Lara-Sophie', 'Kocher', 'Huberring', '88', 'Sankt Valentin', '6625', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1968-09-13', '+43 3740258167135', 'alexa.fehringer@gmail.com', 0, 0, '29', 'Alexa', 'Fehringer', 'Wittmannstraße', '4/7', 'Marchtrenk', '1600', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1995-05-24', '+43 2941389215704', 'mira.steinwender@gmail.com', 0, 0, '30', 'Mira', 'Steinwender', 'Grafstraße', '113', 'Eferding', '4592', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1974-07-23', '+43 8976474920437', 'leander.riedmann@gmail.com', 0, 0, '31', 'Leander', 'Riedmann', 'Gasserring', '6', 'Völkermarkt', '2572', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1930-10-16', '+43 8244767862419', 'ronja.walder@gmail.com', 0, 0, '32', 'Ronja', 'Walder', 'Gratzlring', '50', 'Weiz', '6345', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1970-01-12', '+43 7807311926795', 'andrea.unterberger@gmail.com', 0, 0, '33', 'Andrea', 'Unterberger', 'Sofija-Fuchs-Platz', '95', 'Köflach', '6755', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1985-10-22', '+43 9896563372912', 'lotta.jauk@gmail.com', 0, 0, '34', 'Lotta', 'Jauk', 'Anastasia-Foidl-Straße', '5/7', 'Gföhl', '6325', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1943-01-04', '+43 5248212628646', 'lucia.leitgeb@gmail.com', 0, 0, '35', 'Lucia', 'Leitgeb', 'Florian-Erler-Ring', '75', 'Sankt Andrä im Lavanttal', '6699', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1966-09-20', '+43 7277239696890', 'liam.kratzer@gmail.com', 0, 0, '36', 'Liam', 'Kratzer', 'Hofstetterring', '2', 'Bad Vöslau', '8573', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1945-08-30', '+43 1074959378032', 'annalena.frank@gmail.com', 0, 0, '37', 'Annalena', 'Frank', 'Schröderplatz', '7', 'Gloggnitz', '8359', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1947-06-27', '+43 0537593710366', 'aurelio.rosenberger@gmail.com', 0, 0, '38', 'Aurelio', 'Rosenberger', 'Carina-Reinprecht-Weg', '0', 'Sankt Pölten', '9287', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1976-06-16', '+43 1436067571901', 'emmanuel.stocker@gmail.com', 0, 0, '39', 'Emmanuel', 'Stocker', 'Vogelplatz', '32', 'Imst', '1066', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1968-06-10', '+43 8624753950531', 'marie-louise.thalhammer@gmail.com', 0, 0, '40', 'Marie-Louise', 'Thalhammer', 'Krammerweg', '53', 'Marchtrenk', '7663', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1972-03-26', '+43 3172731450064', 'dario.ecker@gmail.com', 0, 0, '41', 'Dario', 'Ecker', 'Raphael-Linder-Ring', '487', 'Herzogenburg', '5440', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1981-06-02', '+43 3313601213448', 'peter.tauber@gmail.com', 0, 0, '42', 'Peter', 'Tauber', 'Bittnergasse', '9', 'Friesach', '7461', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1928-12-12', '+43 7698935357271', 'hubert.reindl@gmail.com', 0, 0, '43', 'Hubert', 'Reindl', 'Kilian-Berger-Platz', '2', 'Frohnleiten', '1859', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1929-05-13', '+43 9466446640895', 'charlotte.steidl@gmail.com', 0, 0, '44', 'Charlotte', 'Steidl', 'Gassnergasse', '4', 'Frohnleiten', '5466', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1994-01-24', '+43 4428107019285', 'adriana.burgstaller@gmail.com', 0, 0, '45', 'Adriana', 'Burgstaller', 'Kollerstraße', '4/0', 'Gföhl', '5111', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1927-10-12', '+43 4721552053484', 'ina.steurer@gmail.com', 0, 0, '46', 'Ina', 'Steurer', 'Larchergasse', '099', 'Wolfsberg', '2273', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1993-09-25', '+43 1739007162113', 'ela.reischl@gmail.com', 0, 0, '47', 'Ela', 'Reischl', 'Zoe-Sammer-Gasse', '7', 'Salzburg', '2878', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1983-08-17', '+43 9840871646916', 'marco.landl@gmail.com', 0, 0, '48', 'Marco', 'Landl', 'Rettenbacherstraße', '3', 'Gmünd in Kärnten', '1515', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1952-12-04', '+43 4485930774268', 'naomi.walch@gmail.com', 0, 0, '49', 'Naomi', 'Walch', 'Lucie-Froschauer-Platz', '8', 'Schrems', '8686', 'Austria')

insert into guest(birth_date, phone_number, mail_address, discount_in_percent, gender, domain_id, first_name, last_name, street_name, street_number, city, zip_code, country)
VALUES('1964-01-03', '+43 5207680540965', 'lorenzo.kalcher@gmail.com', 0, 0, '50', 'Lorenzo', 'Kalcher', 'Zachring', '8', 'Sankt Veit an der Glan', '6071', 'Austria')
