create type qr_type as enum ('simpleQr', 'qrWithStatistics', 'qrList');

create table qr_routes (
    qr_route_id UUID primary key,
    type qr_type,
    target_url text,
    list_url text
);