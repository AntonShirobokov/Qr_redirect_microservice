create type qr_type as enum ('simpleQr', 'qrWithStatistics', 'qrList');

create table qr_routes (
    qr_code_id UUID primary key,
    type qr_type,
    redirect_url text
);