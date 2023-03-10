CREATE DATABASE IF NOT EXISTS `mybatis`;
use `mybatis`;

CREATE TABLE IF NOT EXISTS `user`
(
    `id`   int(20) primary key,
    `name` varchar(30) not null default '',
    `pwd`  varchar(30) not null default ''
) engine = innodb
  default charset = utf8;

insert into `user`
values (1, 'xiaoming', '123456'),
       (2, 'xiaorang', '123456'),
       (3, 'xiaowang', '123456'),
       (4, 'xiaohong', '123456');

CREATE TABLE IF NOT EXISTS `teacher`
(
    `id`   INT(10) NOT NULL,
    `name` VARCHAR(30) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

INSERT INTO teacher(`id`, `name`)
VALUES (1, '秦老师');

CREATE TABLE IF NOT EXISTS `student`
(
    `id`   INT(10) NOT NULL,
    `name` VARCHAR(30) DEFAULT NULL,
    `tid`  INT(10)     DEFAULT NULL,
    PRIMARY KEY (`id`),
    KEY `fktid` (`tid`),
    CONSTRAINT `fktid` FOREIGN KEY (`tid`) REFERENCES `teacher` (`id`)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;
INSERT INTO `student` (`id`, `name`, `tid`)
VALUES (1, '小明', 1);
INSERT INTO `student` (`id`, `name`, `tid`)
VALUES (2, '小红', 1);
INSERT INTO `student` (`id`, `name`, `tid`)
VALUES (3, '小张', 1);
INSERT INTO `student` (`id`, `name`, `tid`)
VALUES (4, '小李', 1);
INSERT INTO `student` (`id`, `name`, `tid`)
VALUES (5, '小王', 1);

CREATE TABLE IF NOT EXISTS `blog`
(
    `id`          VARCHAR(50)  NOT NULL COMMENT '博客id',
    `title`       VARCHAR(100) NOT NULL COMMENT '博客标题',
    `author`      VARCHAR(30)  NOT NULL COMMENT '博客作者',
    `create_time` DATETIME     NOT NULL COMMENT '创建时间',
    `views`       INT(30)      NOT NULL COMMENT '浏览量'
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

insert into blog
values ('95231459-b135-9ca3-386a-24992d5e2c72', 'java如此简单', 'xiaorang', now(), 25),
       ('69824e2e-8048-0bf4-3477-0e04299be4a9', 'js如此简单', 'xiaoming', now(), 1000),
       ('0fd1ef61-03f0-6459-561d-a3bb70edca48', 'vue如此简单', 'xiaohong', now(), 9999),
       ('c8eac9e8-dd38-8d02-367c-701077bef4a5', 'spring源码解读', 'xiaoxing', now(), 9999);