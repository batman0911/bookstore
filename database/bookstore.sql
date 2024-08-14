create table DATABASECHANGELOG
(
    ID            varchar(255) null,
    AUTHOR        varchar(255) null,
    FILENAME      varchar(255) null,
    DATEEXECUTED  datetime     null,
    ORDEREXECUTED int          null,
    EXECTYPE      varchar(10)  null,
    MD5SUM        varchar(35)  null,
    DESCRIPTION   varchar(255) null,
    COMMENTS      varchar(255) null,
    TAG           varchar(255) null,
    LIQUIBASE     varchar(20)  null,
    CONTEXTS      varchar(255) null,
    LABELS        varchar(255) null,
    DEPLOYMENT_ID varchar(10)  null
);

create table DATABASECHANGELOGLOCK
(
    ID          int          null,
    LOCKED      tinyint      null,
    LOCKGRANTED datetime     null,
    LOCKEDBY    varchar(255) null
);

create table author
(
    id           bigint unsigned null,
    name         varchar(100)    null,
    organization varchar(100)    null,
    created_at   timestamp(3)    null,
    updated_at   timestamp(3)    null,
    enabled      tinyint(1)      null
);

create table author_book
(
    id         bigint unsigned null,
    author_id  bigint unsigned null,
    book_id    bigint unsigned null,
    is_primary tinyint(1)      null,
    created_at timestamp(3)    null,
    updated_at timestamp(3)    null,
    enabled    tinyint(1)      null
);

create table book
(
    id          bigint unsigned null,
    title       varchar(200)    null,
    description varchar(2000)   null,
    publisher   varchar(200)    null,
    year        int unsigned    null,
    price       int unsigned    null,
    created_at  timestamp(3)    null,
    updated_at  timestamp(3)    null,
    enabled     tinyint(1)      null
);

create table book_transaction
(
    id             bigint unsigned null,
    request_id     varchar(200)    null,
    book_id        bigint unsigned null,
    created_by     varchar(100)    null,
    price          bigint          null,
    amount         bigint          null,
    created_at     timestamp       null,
    updated_at     timestamp       null,
    status         varchar(20)     null,
    payment_method varchar(20)     null
);

create table payment_code
(
    id         bigint unsigned null,
    code       varchar(20)     null,
    book_id    bigint unsigned null,
    amount     int unsigned    null,
    expired_at timestamp(3)    null,
    created_at timestamp(3)    null,
    updated_at timestamp(3)    null,
    enabled    tinyint(1)      null
);

create table role
(
    id         bigint unsigned null,
    role       varchar(20)     null,
    created_at timestamp(3)    null,
    updated_at timestamp(3)    null,
    enabled    tinyint(1)      null
);

create table transaction_log
(
    id             int             null,
    transaction_id bigint unsigned null,
    client         varchar(100)    null,
    trans_type     varchar(32)     null,
    url            varchar(1000)   null,
    method         varchar(20)     null,
    request_body   mediumtext      null,
    response_body  mediumtext      null,
    http_status    int             null,
    created        timestamp       null
);

create table user
(
    id         bigint unsigned null,
    username   varchar(50)     null,
    password   varchar(1000)   null,
    created_at timestamp(3)    null,
    updated_at timestamp(3)    null,
    enabled    tinyint(1)      null
);

create table user_role
(
    user_id bigint unsigned null,
    role_id bigint unsigned null
);




