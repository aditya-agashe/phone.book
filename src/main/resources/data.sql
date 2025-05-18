-- Mobile Numbers

INSERT INTO customer(customer_name) VALUES ('Customer 1');
INSERT INTO customer(customer_name) VALUES ('Customer 2');
INSERT INTO customer(customer_name) VALUES ('Customer 3');
INSERT INTO customer(customer_name) VALUES ('Customer 4');


INSERT INTO phone_number (contact_number, is_activated, customer_id) VALUES ('+61412345678', true, 1);
INSERT INTO phone_number (contact_number, is_activated, customer_id) VALUES ('+61411112222', true, 2);
INSERT INTO phone_number (contact_number, is_activated, customer_id) VALUES ('+61422223333', true, 3);
INSERT INTO phone_number (contact_number, is_activated, customer_id) VALUES ('+61433334444', true, 4);
INSERT INTO phone_number (contact_number, is_activated, customer_id) VALUES ('+61444445555', false, 1);
INSERT INTO phone_number (contact_number, is_activated, customer_id) VALUES ('+61455556666', false, 2);
INSERT INTO phone_number (contact_number, is_activated, customer_id) VALUES ('+61477778888', false, 3);
INSERT INTO phone_number (contact_number, is_activated, customer_id) VALUES ('+61488889999', false, 4);

-- Landline Numbers
INSERT INTO phone_number (contact_number, is_activated, customer_id) VALUES ('+61200001111', true, 1);
INSERT INTO phone_number (contact_number, is_activated, customer_id) VALUES ('+61300001111', true, 2);
INSERT INTO phone_number (contact_number, is_activated, customer_id) VALUES ('+61700001111', false, 3);
INSERT INTO phone_number (contact_number, is_activated, customer_id) VALUES ('+61800001111', false, 4);