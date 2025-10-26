create type qr_type as enum ('simpleQr', 'qrWithStatistics', 'qrList');

create table qr_routes (
    qr_code_id UUID primary key,
    type qr_type,
    redirect_url text
);

insert into qr_routes(qr_code_id, type, redirect_url)
values ('0c666d4c-6581-4d6b-b35d-1606ea73cf18', 'qrWithStatistics', 'https://music.yandex.ru/');