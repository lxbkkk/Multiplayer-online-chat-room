import java.io.Serializable;

// 数据包格式
public class SendPacket implements Serializable{
	// 用于表示数据包类型
	
	// 表示数据包是客户端发送的登录信息
	final static int LOGIN = 0;
	
	// 表示数据包是客户端发送的注册信息
	final static int REGISTER = 1;
	
	// 表示数据包是客户端发送的聊天信息
	final static int SENDMESSAGE = 2;
	
	// 表示数据包是服务端发送的登录成功的响应数据包
	final static int LOGIN_OK = 3;
	
	// 表示数据包是服务端发送的用户未注册的响应数据包(#`O′)
	final static int LOGIN_GOREGISTER = 4;
	
	// 表示数据包是服务端发送的密码错误的响应数据包
	final static int LOGIN_PW = 5;
	
	// 服务端：注册成功
	final static int REGISTER_OK = 6;
	
	// 服务端：用户名被占用
	final static int REGISTER_USED = 7;
	
	// 服务端：数据发送成功
	final static int MESSAGE_OK = 8;
	
	// 服务端：用户已登录
	final static int USER_LOGIN = 9;
	
	// 客户端：886
	final static int BAIBAI = 10;
	
	// 数据包类型
	public int ask_type;
	
	// 用户名
	public String user_name;
	
	// 密码
	public String user_pw;
	
	// 发送消息
	public String send_message;
	
	// 发送目标
	public String send_to;
	
	// 发送时间
	public String send_time;
	
	// 之前的聊天记录
	public String chat_before;
}	
