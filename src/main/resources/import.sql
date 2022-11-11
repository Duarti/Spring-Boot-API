INSERT INTO user VALUES (1, 'duart', 'duartn', 'helloworld');
INSERT INTO user VALUES (2, 'duart2', 'duartn', 'helloworld');
INSERT INTO user VALUES (3, 'duart3', 'duartn', 'helloworld');
INSERT INTO user VALUES (4, 'duart4', 'duartn', 'helloworld');
INSERT INTO user VALUES (5, 'duart5', 'duartn', 'helloworld');

INSERT INTO post VALUES (2, 'postBody', 'post1', 2);
INSERT INTO post VALUES (3, 'postBody', 'post2', 2);

DELETE FROM post WHERE id>0;
DELETE FROM user where id>0;
