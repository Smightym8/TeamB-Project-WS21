
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