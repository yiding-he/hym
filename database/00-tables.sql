create table if not exists `hym_user` (
    `id` int(11) unsigned not null primary key auto_increment,
    `username` varchar(255) unique not null,
    `password` varchar(255) not null,
    `created_at` datetime not null default current_timestamp,
    `updated_at` datetime not null default current_timestamp on update current_timestamp
)
;