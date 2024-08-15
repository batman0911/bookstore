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

INSERT INTO bookstore.book (title, description, publisher, year, price, created_at, updated_at, enabled)
VALUES ('book 1',
        'Nam ultricies at massa id tristique. Quisque vel leo erat. Duis odio diam, egestas a ultrices vel, commodo in urna. Sed eu erat vitae neque ultrices porta. Nam ultrices tincidunt varius. Nulla placerat nisl vel sapien luctus luctus. Etiam maximus lorem convallis dui cursus imperdiet. Vestibulum dignissim odio metus. Praesent ornare mattis elit vitae feugiat. Phasellus congue dolor et mi tristique sagittis. Sed pharetra metus quis lorem auctor pellentesque. Vestibulum ullamcorper ullamcorper accumsan. Ut placerat sem in vestibulum ultrices. Proin lorem leo, pretium et mi et, lacinia placerat elit.',
        'Hanoi', 2024, 100000, '2024-08-15 14:05:41.000', '2024-08-15 14:05:43.000', 1),
       ('book 2',
        'Nam ultricies at massa id tristique. Quisque vel leo erat. Duis odio diam, egestas a ultrices vel, commodo in urna. Sed eu erat vitae neque ultrices porta. Nam ultrices tincidunt varius. Nulla placerat nisl vel sapien luctus luctus. Etiam maximus lorem convallis dui cursus imperdiet. Vestibulum dignissim odio metus. Praesent ornare mattis elit vitae feugiat. Phasellus congue dolor et mi tristique sagittis. Sed pharetra metus quis lorem auctor pellentesque. Vestibulum ullamcorper ullamcorper accumsan. Ut placerat sem in vestibulum ultrices. Proin lorem leo, pretium et mi et, lacinia placerat elit.',
        'Hanoi', 2024, 200000, '2024-08-15 14:05:41.000', '2024-08-15 14:05:43.000', 1),
       ('book 3',
        'Nam ultricies at massa id tristique. Quisque vel leo erat. Duis odio diam, egestas a ultrices vel, commodo in urna. Sed eu erat vitae neque ultrices porta. Nam ultrices tincidunt varius. Nulla placerat nisl vel sapien luctus luctus. Etiam maximus lorem convallis dui cursus imperdiet. Vestibulum dignissim odio metus. Praesent ornare mattis elit vitae feugiat. Phasellus congue dolor et mi tristique sagittis. Sed pharetra metus quis lorem auctor pellentesque. Vestibulum ullamcorper ullamcorper accumsan. Ut placerat sem in vestibulum ultrices. Proin lorem leo, pretium et mi et, lacinia placerat elit.',
        'Hanoi', 2024, 150000, '2024-08-15 14:05:41.000', '2024-08-15 14:05:43.000', 1),
       ('book 4',
        'Nam ultricies at massa id tristique. Quisque vel leo erat. Duis odio diam, egestas a ultrices vel, commodo in urna. Sed eu erat vitae neque ultrices porta. Nam ultrices tincidunt varius. Nulla placerat nisl vel sapien luctus luctus. Etiam maximus lorem convallis dui cursus imperdiet. Vestibulum dignissim odio metus. Praesent ornare mattis elit vitae feugiat. Phasellus congue dolor et mi tristique sagittis. Sed pharetra metus quis lorem auctor pellentesque. Vestibulum ullamcorper ullamcorper accumsan. Ut placerat sem in vestibulum ultrices. Proin lorem leo, pretium et mi et, lacinia placerat elit.',
        'Hanoi', 2024, 100000, '2024-08-15 14:05:41.000', '2024-08-15 14:05:43.000', 1);

INSERT INTO bookstore.payment_code (code, book_id, amount, expired_at, created_at, updated_at, enabled) VALUES ('pmc-01', 1, 100000, '2024-09-15 14:08:20.000', '2024-08-15 14:08:25.000', '2024-08-15 14:08:26.000', 1),
                                                                                                               ('pmc-02', 2, 200000, '2024-09-15 14:08:20.000', '2024-08-15 14:08:25.000', '2024-08-15 14:08:26.000', 1);