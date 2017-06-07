CREATE TABLE account(id integer primary key autoincrement, accountNumber varchar(12) not null unique,  balance double default 0.0, dateCreation date,type integer default 0, customer_id integer not null, status int default 0, foreign key(customer_id) references customer(id));
CREATE TABLE customer(id integer primary key autoincrement, name varchar(30) not null unique, emailAddress varchar(30) not null, phoneNumber varchar(30), status int default 0, user_id integer not null, foreign key(user_id) references users(id));
CREATE TABLE operations(id integer primary key autoincrement, operationType integer not null, dateOperation date not null, description varchar(40) not null, account_id integer not null, user_id integer not null, foreign key(user_id) references users(id), foreign key(account_id) references account(id));
CREATE TABLE users (id integer primary key autoincrement, username varchar(20) not null unique, passwd varchar(30) not null, role integer default 0, status int default 0);

