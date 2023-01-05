--liquibase formatted sql
--changeset Mirko:create-tables spitStatement:true andDelimiter:;

CREATE TABLE users (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    active BIT,
    password VARCHAR(255),
    roles VARCHAR(255),
    user_name VARCHAR(255)
);

CREATE TABLE dictionary_words (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    word_from_dictionary VARCHAR(255)
);

CREATE TABLE asocijacija_game (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    rijeci_id INTEGER
);

CREATE TABLE games (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    asocijacija_game_id INTEGER,
    ko_zna_zna_game_id INTEGER,
    moj_broj_game_id INTEGER,
    skocko_game_id INTEGER,
    slagalica_game_id INTEGER,
    spojnice_game_id INTEGER
);

CREATE TABLE is_active (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    is_active_asocijacije BIT,
    is_active_ko_zna_zna BIT,
    is_active_moj_broj BIT,
    is_active_skocko BIT,
    is_active_slagalica BIT,
    is_active_spojnice BIT
);

CREATE TABLE ko_zna_zna_game (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    index_of_the_current_question INTEGER
);

CREATE TABLE ko_zna_zna_game_questions (
    ko_zna_zna_game_id INTEGER,
    questions_id INTEGER
);

CREATE TABLE moj_broj_game (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    solution VARCHAR(255)
);

CREATE TABLE moj_broj_game_numbers (
    moj_broj_game_id INTEGER,
    numbers INTEGER
);

CREATE TABLE one_player_game (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    finished BIT,
    games_id INTEGER,
    is_active_id INTEGER,
    points_id INTEGER,
    timers_id INTEGER,
    user_id INTEGER
);

CREATE TABLE pairs_model (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    column1 VARCHAR(255),
    column2 VARCHAR(255),
    headline VARCHAR(255)
);

CREATE TABLE points (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    num_of_points_asocijacije INTEGER,
    num_of_points_ko_zna_zna INTEGER,
    num_of_points_moj_broj INTEGER,
    num_of_points_skocko INTEGER,
    num_of_points_slagalica INTEGER,
    num_of_points_spojnice INTEGER
);

CREATE TABLE question (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    correct_answer INTEGER,
    question_content VARCHAR(255)
);

CREATE TABLE question_games (
    question_id INTEGER,
    games_id INTEGER
);

CREATE TABLE question_options (
    question_id INTEGER,
    options VARCHAR(255)
);

CREATE TABLE skocko_game (
    id INTEGER PRIMARY KEY AUTO_INCREMENT
);

CREATE TABLE skocko_game_combination (
    skocko_game_id INTEGER,
    combination INTEGER
);

CREATE TABLE slagalica (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    computer_longest_word VARCHAR(255),
    letters_for_finding_the_word VARCHAR(255)
);

CREATE TABLE spojnice_game (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    pairs_model_id INTEGER
);

CREATE TABLE timers (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    start_time_asocijacije VARCHAR(255),
    start_time_ko_zna_zna VARCHAR(255),
    start_time_moj_broj VARCHAR(255),
    start_time_skocko VARCHAR(255),
    start_time_slagalica VARCHAR(255),
    start_time_spojnice VARCHAR(255)
);

CREATE TABLE two_player_game (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    games_id INTEGER,
    is_active1_id INTEGER,
    is_active2_id INTEGER,
    points1_id INTEGER,
    points2_id INTEGER,
    timers1_id INTEGER,
    timers2_id INTEGER,
    user1_id INTEGER,
    user2_id INTEGER
);

CREATE TABLE word_model (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    columnA VARCHAR(255),
    columnB VARCHAR(255),
    columnC VARCHAR(255),
    columnD VARCHAR(255),
    final_word VARCHAR(255)
);