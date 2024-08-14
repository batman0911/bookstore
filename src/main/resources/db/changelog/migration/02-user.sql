insert into bookstore.user (id, username, password, created_at, updated_at, enabled)
values  (1, 'linhnm', '$2a$12$O0l7f1gTcYobM/DsnvxaKuebNwcMum9LXiPf0ug7NQDCmn8WXX056', '2024-08-14 14:48:01.000', '2024-08-14 14:48:02.000', 1),
        (null, 'admin', '$2a$12$O0l7f1gTcYobM/DsnvxaKuebNwcMum9LXiPf0ug7NQDCmn8WXX056', '2024-08-14 18:23:28.000', '2024-08-14 18:23:29.000', 1);

insert into bookstore.role (id, role, created_at, updated_at, enabled)
values  (1, 'USER', '2024-08-14 14:48:24.000', '2024-08-14 14:48:25.000', 1),
        (2, 'ADMIN', '2024-08-14 14:48:35.000', '2024-08-14 14:48:36.000', 1);

insert into bookstore.user_role (user_id, role_id)
values  (1, 1),
        (2, 1),
        (2, 2);