create table author
(
    id           bigint unsigned auto_increment
        primary key,
    name         varchar(100)                              null,
    organization varchar(100)                              null,
    created_at   timestamp(3) default CURRENT_TIMESTAMP(3) not null,
    updated_at   timestamp(3)                              null on update CURRENT_TIMESTAMP(3),
    enabled      tinyint(1)   default 0                    not null
);

create table author_book
(
    id         bigint unsigned auto_increment
        primary key,
    author_id  bigint unsigned                           not null,
    book_id    bigint unsigned                           not null,
    is_primary tinyint(1)   default 0                    not null,
    created_at timestamp(3) default CURRENT_TIMESTAMP(3) not null,
    updated_at timestamp(3)                              null on update CURRENT_TIMESTAMP(3),
    enabled    tinyint(1)   default 0                    not null
);

create index author_book_author_id_index
    on author_book (author_id);

create index author_book_book_id_index
    on author_book (book_id);

create table book
(
    id          bigint unsigned auto_increment
        primary key,
    title       varchar(200)                              null,
    description varchar(2000)                             null,
    publisher   varchar(200)                              null,
    year        int unsigned                              null,
    price       int unsigned default '0'                  not null,
    created_at  timestamp(3) default CURRENT_TIMESTAMP(3) not null,
    updated_at  timestamp(3)                              null on update CURRENT_TIMESTAMP(3),
    enabled     tinyint(1)   default 0                    not null
);

create table book_transaction
(
    id             bigint unsigned auto_increment
        primary key,
    request_id     varchar(200)                          null comment 'request id khi gọi sang trung gian thanh toán',
    book_id        bigint unsigned                       not null,
    created_by     varchar(100)                          not null comment 'username',
    price          bigint      default 0                 null comment 'giá sản phẩm tại thời điểm giao dịch',
    amount         bigint      default 0                 null,
    created_at     timestamp   default CURRENT_TIMESTAMP null,
    updated_at     timestamp                             null on update CURRENT_TIMESTAMP,
    status         varchar(20) default 'CREATED'         null comment 'trạng thái giao dịch CREATED, PENDING, SUCCESS, FAILED',
    payment_method varchar(20) default 'MOMO'            null comment 'MOMO, VISA, MASTER, MANUAL'
);

create index book_transaction_book_id_index
    on book_transaction (book_id);

create index book_transaction_created_by_index
    on book_transaction (created_by);

create index book_transaction_request_id_index
    on book_transaction (request_id);

create table payment_code
(
    id         bigint unsigned auto_increment
        primary key,
    code       varchar(20)                               null,
    book_id    bigint unsigned                           null,
    amount     int unsigned default '0'                  null,
    expired_at timestamp(3)                              null,
    created_at timestamp(3) default CURRENT_TIMESTAMP(3) not null,
    updated_at timestamp(3)                              null on update CURRENT_TIMESTAMP(3),
    enabled    tinyint(1)   default 0                    not null
);

create index payment_code_book_id_index
    on payment_code (book_id);

create index payment_code_code_index
    on payment_code (code);

create table role
(
    id         bigint unsigned auto_increment
        primary key,
    role       varchar(20)  default 'USER'               not null,
    created_at timestamp(3) default CURRENT_TIMESTAMP(3) not null,
    updated_at timestamp(3)                              null on update CURRENT_TIMESTAMP(3),
    enabled    tinyint(1)   default 0                    null
);

create table transaction_log
(
    id             bigint unsigned auto_increment
        primary key,
    transaction_id bigint unsigned                           not null comment 'id của giao dịch',
    client         varchar(100) default 'MOMO'               null comment 'đối tác thanh toán',
    trans_type     varchar(32)  default 'PAY'                null comment 'loại giao dịch PAY, IPN',
    url            varchar(1000)                             null comment 'uri gọi sang đối tác thanh toán',
    method         varchar(20)  default 'POST'               null,
    request_body   mediumtext                                null,
    response_body  mediumtext                                null,
    http_status    int          default 200                  null,
    created_at     timestamp(3) default CURRENT_TIMESTAMP(3) null
);

create index transaction_log_transaction_id_index
    on transaction_log (transaction_id);

create table user
(
    id         bigint unsigned auto_increment
        primary key,
    username   varchar(50)                               null,
    password   varchar(1000)                             null,
    created_at timestamp(3) default CURRENT_TIMESTAMP(3) not null,
    updated_at timestamp(3)                              null on update CURRENT_TIMESTAMP(3),
    enabled    tinyint(1)   default 0                    not null
);

create table user_role
(
    user_id bigint unsigned not null,
    role_id bigint unsigned not null,
    primary key (user_id, role_id)
);

