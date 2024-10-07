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



