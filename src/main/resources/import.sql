# Add 2 users and 1 admin. User 1 has password Password1, User 2 has password Password2, admin has password admin. If you want to add only the admin, run only queries on lines 3, 4, 13, 14.

INSERT INTO users (id, name, username, password) VALUES((select next_val from user_id_generator), 'Admin', 'admin', '$2a$12$RBBbyfWPFNR3XQHHrunskuX8NovAbOqFys9dneXnUfT1JZjRQQaWe');
update user_id_generator set next_val= next_val+1 where next_val=next_val;
INSERT INTO users (id, name, username, password) VALUES((select next_val from user_id_generator), 'User 1', 'Username1', '$2a$12$N.vFe0u5TW98bgaG9MAZsuzB/Myhxd1NuLGzvmERZfOBFg6jmw5cy');
update user_id_generator set next_val= next_val+1 where next_val=next_val;
INSERT INTO users (id, name, username, password) VALUES((select next_val from user_id_generator), 'User 2', 'Username2', '$2a$12$MV8Wx0Fb6B4BztfA4WxTK.NeuV.cLjPG1C3CyrcY3Eb27uCMqgpNW');
update user_id_generator set next_val= next_val+1 where next_val=next_val;


# Add roles to each user. Add USER_ROLE to every user, and USER_ADMIN to user with ID 1.

INSERT INTO users_roles (user_id, role_id) VALUES(1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES(1, 2);
INSERT INTO users_roles (user_id, role_id) VALUES(2, 1);
INSERT INTO users_roles (user_id, role_id) VALUES(3, 1);

# Add posts

INSERT INTO posts (id, title, body, user_id) VALUES((select next_val from post_id_generator), 'Post Title 1', 'Post Body 1', 1);
update post_id_generator set next_val= next_val+1 where next_val=next_val;
INSERT INTO posts (id, title, body, user_id) VALUES((select next_val from post_id_generator), 'Post Title 2', 'Post Body 2', 2);
update post_id_generator set next_val= next_val+1 where next_val=next_val;

