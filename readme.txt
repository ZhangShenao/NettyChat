1.项目架构
	网络通信框架:Netty
	ORM框架:Hibernate
	业务层框架:Spring
2.通信模型
	服务器和客户端之间通过Request和Response进行通信。
	Request和Response为自定义协议,采用自定义方式进行序列化/反序列化。Request和Response均由三部分组成:模块号、命令号和字节数据,其中字节数据采用Protobuf进行marshal。