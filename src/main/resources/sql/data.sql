INSERT INTO material (id, type, description, storage_price, selling_price)
VALUES (gen_random_uuid(),
        'GIPS',
        'Gips is een zacht sulfaatmineraal bestaande uit calciumsulfaatdihydraat.',
        1.00,
        13.00);

INSERT INTO material (id, type, description, storage_price, selling_price)
VALUES (gen_random_uuid(),
        'IJZERERTS',
        'IJzererts is een natuurlijk voorkomend mineraal waaruit ijzer wordt gewonnen.',
        5.00,
        110.00);

INSERT INTO material (id, type, description, storage_price, selling_price)
VALUES (gen_random_uuid(),
        'CEMENT',
        'Cement is een bindmiddel dat in de bouw wordt gebruikt en dat uithardt en aan andere materialen hecht om ze samen te binden.',
        3.00,
        95.00);

INSERT INTO material (id, type, description, storage_price, selling_price)
VALUES (gen_random_uuid(),
        'PETCOKE',
        'Petcoke is een koolstofrijk vast materiaal afkomstig van olieraffinage.',
        10.00,
        210.00);

INSERT INTO material (id, type, description, storage_price, selling_price)
VALUES (gen_random_uuid(),
        'SLAK',
        'Slak is een bijproduct van het smeltproces dat wordt gebruikt om metalen uit hun ertsen te produceren.',
        7.00,
        160.00);

insert into buyer(uuid, address, name)
values (gen_random_uuid(), 'test address buyer', 'buyer');

insert into seller(uuid, address, name)
values (gen_random_uuid(), 'test address seller', 'seller');

insert into invoice(uuid, total_commision_cost, seller_uuid)
values (gen_random_uuid(), 0, (SELECT uuid FROM seller WHERE name = 'seller'));

update seller
set invoice_uuid = ((SELECT uuid FROM invoice WHERE name = 'seller'));

update seller
set invoice_uuid = ((SELECT uuid FROM invoice WHERE name = 'seller'))
where name = 'seller';

-- Gips warehouse
INSERT INTO warehouse (id, material_id, capacity, occupancy, seller_uuid)
VALUES (gen_random_uuid(),
        (SELECT id FROM material WHERE type = 'GIPS'),
        500000,
        100000,
        (SELECT uuid FROM seller WHERE name = 'seller'));

-- IJzererts warehouse
INSERT INTO warehouse (id, material_id, capacity, occupancy, seller_uuid)
VALUES (gen_random_uuid(),
        (SELECT id FROM material WHERE type = 'IJZERERTS'),
        500000,
        999,
        (SELECT uuid FROM seller WHERE name = 'seller'));

-- Cement warehouse
INSERT INTO warehouse (id, material_id, capacity, occupancy, seller_uuid)
VALUES (gen_random_uuid(),
        (SELECT id FROM material WHERE type = 'CEMENT'),
        500000,
        100000,
        (SELECT uuid FROM seller WHERE name = 'seller'));

-- Petcoke warehouse
INSERT INTO warehouse (id, material_id, capacity, occupancy, seller_uuid)
VALUES (gen_random_uuid(),
        (SELECT id FROM material WHERE type = 'PETCOKE'),
        500000,
        400000,
        (SELECT uuid FROM seller WHERE name = 'seller'));

-- Slak warehouse
INSERT INTO warehouse (id, material_id, capacity, occupancy, seller_uuid)
VALUES (gen_random_uuid(),
        (SELECT id FROM material WHERE type = 'SLAK'),
        500000,
        100000,
        (SELECT uuid FROM seller WHERE name = 'seller'));

INSERT INTO delivery(amount, cost_price, days, storage_price, delivery_date, uuid, warehouse_id, material_type)
VALUES (3000,
        50,
        4,
        1.00,
        '2024-07-27 10:00:00',
        gen_random_uuid(),
        (SELECT id
         FROM warehouse
         WHERE seller_uuid = (SELECT uuid FROM seller WHERE name = 'seller')
           AND material_id = (SELECT id FROM material WHERE type = 'GIPS')), 'GIPS');

INSERT INTO delivery(amount, cost_price, days, storage_price, delivery_date, uuid, warehouse_id, material_type)
VALUES (100000,
        50,
        4,
        1.00,
        '2024-07-27 9:00:00',
        gen_random_uuid(),
        (SELECT id
         FROM warehouse
         WHERE seller_uuid = (SELECT uuid FROM seller WHERE name = 'seller')
           AND material_id = (SELECT id FROM material WHERE type = 'GIPS')), 'GIPS');

INSERT INTO delivery(amount, cost_price, days, storage_price, delivery_date, uuid, warehouse_id, material_type)
VALUES (990,
        50,
        4,
        5.00,
        '2024-07-27 09:10:00',
        gen_random_uuid(),
        (SELECT id
         FROM warehouse
         WHERE seller_uuid = (SELECT uuid FROM seller WHERE name = 'seller')
           AND material_id = (SELECT id FROM material WHERE type = 'IJZERERTS')), 'IJZERERTS');

INSERT INTO warehouse_deliveries (deliveries_uuid, warehouse_id)
VALUES ((SELECT uuid FROM delivery WHERE delivery_date = '2024-07-27 10:00:00' AND material_type = 'GIPS'),
        (SELECT id
         FROM warehouse
         WHERE seller_uuid = (SELECT uuid FROM seller WHERE name = 'seller')
           AND material_id = (SELECT id FROM material WHERE type = 'GIPS')));
INSERT INTO warehouse_deliveries (deliveries_uuid, warehouse_id)
VALUES ((SELECT uuid FROM delivery WHERE delivery_date = '2024-07-27 09:00:00' AND material_type = 'GIPS'),
        (SELECT id
         FROM warehouse
         WHERE seller_uuid = (SELECT uuid FROM seller WHERE name = 'seller')
           AND material_id = (SELECT id FROM material WHERE type = 'GIPS')));


INSERT INTO warehouse_deliveries (deliveries_uuid, warehouse_id)
VALUES ((SELECT uuid FROM delivery WHERE delivery_date = '2024-07-27 09:10:00' AND material_type = 'IJZERERTS'),
        (SELECT id
         FROM warehouse
         WHERE seller_uuid = (SELECT uuid FROM seller WHERE name = 'seller')
           AND material_id = (SELECT id FROM material WHERE type = 'IJZERERTS')));

INSERT INTO seller_warehouses(seller_uuid, warehouses_id)
VALUES ((SELECT uuid FROM seller WHERE name = 'seller'),
        (SELECT id FROM warehouse WHERE seller_uuid = (SELECT uuid FROM seller WHERE name = 'seller') AND
        material_id = (SELECT id FROM material WHERE type = 'GIPS')));

INSERT INTO seller_warehouses(seller_uuid, warehouses_id)
VALUES ((SELECT uuid FROM seller WHERE name = 'seller'),
        (SELECT id FROM warehouse WHERE seller_uuid = (SELECT uuid FROM seller WHERE name = 'seller') AND
                material_id = (SELECT id FROM material WHERE type = 'IJZERERTS')));

INSERT INTO seller_warehouses(seller_uuid, warehouses_id)
VALUES ((SELECT uuid FROM seller WHERE name = 'seller'),
        (SELECT id FROM warehouse WHERE seller_uuid = (SELECT uuid FROM seller WHERE name = 'seller') AND
                material_id = (SELECT id FROM material WHERE type = 'CEMENT')));

INSERT INTO seller_warehouses(seller_uuid, warehouses_id)
VALUES ((SELECT uuid FROM seller WHERE name = 'seller'),
        (SELECT id FROM warehouse WHERE seller_uuid = (SELECT uuid FROM seller WHERE name = 'seller') AND
                material_id = (SELECT id FROM material WHERE type = 'PETCOKE')));

INSERT INTO seller_warehouses(seller_uuid, warehouses_id)
VALUES ((SELECT uuid FROM seller WHERE name = 'seller'),
        (SELECT id FROM warehouse WHERE seller_uuid = (SELECT uuid FROM seller WHERE name = 'seller') AND
                material_id = (SELECT id FROM material WHERE type = 'SLAK')));

INSERT INTO purchase_order(buyer_uuid, referenceuuid, seller_uuid, purchase_date, po_number)
VALUES ((SELECT uuid FROM buyer WHERE name = 'buyer'),gen_random_uuid(),(SELECT uuid FROM seller WHERE name = 'seller'),'2024-07-27 09:00:00','PO12345')