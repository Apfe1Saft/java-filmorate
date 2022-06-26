### Filmorate

![alt text]([README_static.DBv3](https://github.com/Apfe1Saft/java-filmorate/blob/add-database/README_static/DBv3.png))

### ПРИМЕРЫ КОМАНД:

### Команды для данных о фильмах

Получение списка фильмов

    SELECT * FROM films;

Получение фильма по id

    SELECT * FROM films WHERE id = ?;

Получение отсортированного списка фильмов по лайкам

    SELECT DISTINCT film_id,
            count(user_id) AS cui
    FROM likes 
    GROUP BY film_id 
    ORDER BY count(user_id) DESC;

### Команды для данных о пользователях

Получение списка пользователей

    SELECT * FROM users;

Получение пользователя по d

    SELECT * FROM films WHERE id = ?;

Получение всех друзей пользователя

    SELECT * FROM likes WHERE user_id = ?;

Получение общих друзей

    SELECT friend_id FROM (
        SELECT * FROM friends AS fr1 JOIN (SELECT * FROM friends) AS fr2
        WHERE (fr1.friend_id = fr2.friend_id and fr1.user_id = 1 AND fr2.user_id = 2)
        OR (fr1.friend_id = fr2.friend_id AND fr1.user_id = 2 AND fr2.user_id = 1)
    );
