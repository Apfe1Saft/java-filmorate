drop table films,users,genre_list,likes,friends,mpa,genres;--///
create table if not exists films
(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    film_name varchar,
    description varchar,
    release_date date,
    duration integer,
    mpa_id integer
);
create table if not exists users
(
    id integer PRIMARY KEY AUTO_INCREMENT,
    name varchar,
    login varchar,
    email varchar,
    birthday varchar
);
create table if not exists genre_list
(
    film_id integer,
    genre_id integer
);
create table if not exists likes
(
    film_id integer,
    user_id integer
);
create table if not exists friends
(
    user_id integer,
    friend_id integer,
    status boolean
);
create table if not exists mpa
(
    mpa_id integer,
    mpa_name varchar
);
create table if not exists genres
(
    genre_id integer,
    genre_name varchar
);