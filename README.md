# fashionAI

时尚平台后端

首先介绍src目录结构

```
├─main
│  ├─java
│  │  └─ices
│  │      └─fashion
│  │          ├─config		//配置文件夹
│  │          ├─controller	//控制层，与前端请求对应
│  │          ├─entity		//实体类层，存放与数据库表格一一对应的实体类
│  │          ├─mapper		//dao层，用于和数据库数据进行操作
│  │          ├─service		//服务层，主要业务逻辑集中在这一层
│  │          │  ├─dto		//dto即封装数据传输类，通常为返回给前端的数据
│  │          │  └─impl		//服务层接口的实现
│  │          └─util		//存放各种编写的工具类
│  └─resources
└─test
    └─java
        └─ices
            └─fashion		//可以在这里写接口测试
```

## 代码执行流程

1. conroller层接收到对应的前端请求 

2. 编写代码将controller层调用service的方法进行处理

3. service处理过程中如果涉及操作数据库，则调用mapper里面提供的方法

4. mapper操作数据库得到原始数据库数据
5. service层得到数据封装前端需要的数据对应的dto
6. service将结果返回给调用它的方法的controller层，controller层对请求进行应答



`详细例子可以看TestController`

关于mapper层使用了mybatis-plus，使用方式参考官方文档<https://baomidou.com/>

整个项目基于springboot搭建，使用方式参考官方文档<https://spring.io/projects/spring-boot>

