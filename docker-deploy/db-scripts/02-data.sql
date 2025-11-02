USE blog;

INSERT IGNORE INTO user (id, username, password, role) VALUES 
(1, 'enbbk', '123456', 'admin'),
(2, 'testuser', 'test123', 'user');

INSERT IGNORE INTO article (id, title, content, author_id) VALUES 
(1, 'Docker部署指南', '本文介绍如何使用Docker部署Spring Boot应用...', 1),
(2, 'Spring Boot入门', 'Spring Boot是一个快速开发框架...', 1),
(3, 'MyBatis-Plus使用技巧', 'MyBatis-Plus是MyBatis的增强工具...', 2),
(4, '前后端分离实践', '前后端分离是现代Web开发的主流模式...', 1),
(5, 'Java学习路线', '从C语言转到Java需要掌握的知识点...', 2);

INSERT IGNORE INTO comment (id, content, user_id, article_id) VALUES 
(1, '写得很好，学到了很多！', 2, 1),
(2, '请问Docker Compose和Kubernetes有什么区别？', 2, 1),
(3, '期待更多关于微服务的内容', 2, 2),
(4, 'MyBatis-Plus确实很方便', 1, 3),
(5, '作为从C转过来的，感觉Java生态很丰富', 1, 5);

INSERT IGNORE INTO article_like (id, user_id, article_id) VALUES 
(1, 2, 1),
(2, 1, 3),
(3, 2, 4);

