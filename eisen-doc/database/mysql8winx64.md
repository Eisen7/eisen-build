#初始化
D:\Code\mysql\base\bin\mysqld --defaults-file=D:\Code\mysql\my.cnf --initialize 
#控制台启动 start /b 命令后台 关闭窗口服务也会关闭
start /b D:\Code\mysql\base\bin\mysqld  --defaults-file=D:\Code\mysql\my.cnf >>D:\Code\mysql\log\mysql.log

#登陆
D:\Code\mysql\base\bin\mysql -uroot -p
#修改密码
ALTER USER 'root'@'localhost' IDENTIFIED BY 'root';
#停止服务
shutdown; 或 mysqladmin -uroot -p shutdown

#注册服务
D:\Code\mysql\base\bin\mysqld --install MySQL --defaults-file=D:\Code\mysql\my.cnf
#删除服务
mysqld -remove MySQL

#创建数据库
create database eisendb;
#创建用户
create user 'eisen'@'%' identified by 'eisen';
#分配权限 with grant option表示给i啊用户可以将拥有的权限授予别人
grant all privileges on eisendb.* to 'eisen'@'%' with grant option;
#查看账户权限
SHOW GRANTS FOR 'eisen'@'%';
#查看没有权限的值
SHOW CREATE USER 'eisen'@'%'\G

#创建类似于访客的用户
CREATE USER 'eisen2'@'192.168.%' IDENTIFIED BY 'eisen2';
GRANT SELECT,INSERT,UPDATE,DELETE,CREATE,DROP ON eisen.* TO 'eisen2'@'192.168.%';
#刷新权限
flush privileges;
#撤销权限
revoke all privileges on eisen.* from  `eisen`@`%`;

#修改密码认证方式 caching_sha2_password就挺好  不用改
ALTER USER 'eisen'@'%' IDENTIFIED WITH mysql_native_password(caching_sha2_password) BY 'eisen';

#展示当前所有连接
show full processlist;
#展示当前 前100条连接
show processlist;