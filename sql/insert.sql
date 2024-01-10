-- Авторизация/регистрация
INSERT INTO roles(role_id, role_name, role_description, role_created_datetime)
VALUES
    (default, 'user', 'Базовая роль, для обычного пользователя', default),
    (default, 'moder', 'Роль модератора, работника сервиса, почти не имеет ограничений', default),
    (default, 'admin', 'Роль администратора, владельца сервиса, не имеет ограничений', default)
    ;
INSERT INTO users(
    user_id,
    user_email,
    user_login,
    user_password_hash,
    user_created_datetime,
    user_status)
VALUES
    (default,
     'admin@mail.ru',
     'admin',
     '$2a$12$DMCQJpUCLrF.eByReybQXOeYjE1s3WdRWVAbAAe8Vapb1xr0DQ4zq',
     default,
     'verified')
    ;
-- Вендинговые точки
INSERT INTO vending_points(
    vending_point_id,
    vending_point_address,
    vending_point_description,
    vending_point_number_machines,
    vending_point_cords)
VALUES
    (0,
     'Невский пр-кт, 2',
     'Находится рядом с банкоматом',
     2,
     point(59.93716299256363, 30.313378213455664)),
    (1,
     'Комендантcкий пр-кт, 34',
     'Находится в подвале рядом с шаурмечной',
     1,
     point(60.02097852901829, 30.242859111340625))
    ;
INSERT INTO vending_point_schedules(
    vending_point_schedule_id,
    vending_point_id,
    vending_point_schedule_day_week,
    vending_point_schedule_time_start,
    vending_point_schedule_time_end)
VALUES
    (0, 0, 'monday', '10:00', '20:00'),
    (1, 0, 'tuesday', '09:00', '21:00'),
    (2, 0, 'wednesday', '11:00', '23:00'),
    (3, 0, 'thursday', '10:00', '20:00'),
    (4, 0, 'friday', '10:00', '20:00'),
    (5, 1, 'saturday', '06:35', '22:20'),
    (6, 1, 'sunday', '09:03', '21:55')
    ;
INSERT INTO vending_point_unusual_schedules(
    vending_point_unusual_schedule_id,
    vending_point_id,
    vending_point_unusual_schedule_date,
    vending_point_schedule_time_start,
    vending_point_schedule_time_end)
VALUES
    (0, 0, '15.11.2024', '02:00', '22:00')
    ;
-- Вендинговые аппараты (машины)
INSERT INTO machines(machine_id, vending_point_id)
VALUES
    (0, 0),
    (1, 0),
    (2, 1)
    ;
INSERT INTO function_variants(function_variant_id, vending_point_id, machine_id, function_variant)
VALUES
    (default, 0, 0, 'black_white_print'),
    (default, 0, 0, 'color_print'),
    (default, 0, 1, 'black_white_print'),
    (default, 0, 1, 'color_print'),
    (default, 0, 1, 'scan'),
    (default, 1, 2, 'black_white_print'),
    (default, 1, 2, 'scan')
    ;
INSERT INTO machine_supplies(
    machine_supplies_id,
    machine_id,
    machine_supplies_quantity_paper,
    machine_supplies_ink_level,
    machine_supplies_datetime)
VALUES
    (default, 0, 100, 900, default),
    (default, 0, 40, 800, default),
    (default, 1, 200, 500, default),
    (default, 2, 800, 1000, default)
    ;
INSERT INTO machine_conditions(machine_condition_id, machine_id, machine_status, machine_condition_datetime)
VALUES
    (default, 0, 'work', default),
    (default, 0, 'temporarily_not_work', default),
    (default, 0, 'work', default),
    (default, 1, 'work', default),
    (default, 2, 'temporarily_not_work', default)
    ;
