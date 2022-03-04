import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

// 服务端用于接收客户端发来的数据包的线程(每个客户端对应一个线程
public class AcceptThread implements Runnable {
	Socket mySocket = null;
	public void run() {
		// 接受连接信息
		System.out.println("accept client:");
		System.out.println(mySocket);
		
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		SendPacket revpacket = null;
		SendPacket sendpacket = null;
		sendpacket = new SendPacket();
		
		
		// 连接数据库
		Connection con = null;
		
		// 数据库信息
		String driver = "com.mysql.cj.jdbc.Driver";
    	String url = "jdbc:mysql://localhost:3306/java_hw5";
    	String user = "root";
    	String password = "absent1122";
    	
    	try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    
	     
		while(true) {
			try {
				// 接收数据包
				in = new ObjectInputStream(mySocket.getInputStream());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				revpacket = (SendPacket)in.readObject();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(revpacket != null) {
				// 发送消息
				if(revpacket.ask_type == revpacket.SENDMESSAGE) {
					Server.SendToAll(revpacket);
					try {
						if(!con.isClosed()) {
						    Statement statement = null;
							try {
								statement = con.createStatement();
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							// sql 语句
							// 将聊天信息保存到数据库中
							String sql = "insert into chat_info values(" + "\"" + revpacket.user_name + "\",\"" + revpacket.send_message + "\",\"" + revpacket.send_time + "\");";
						    try {
						    	// 执行 sql 语句
								statement.executeUpdate(sql);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(revpacket.ask_type == revpacket.LOGIN) {
					// 数据包类型为登录请求
			    	try {
						if(!con.isClosed()) {
						    Statement statement = null;
							try {
								statement = con.createStatement();
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							 // 检查用户名和密码是否正确
						     String sql = "select * from user_info where user_name=\"" + revpacket.user_name + "\"";
				             // 用来存放获取的结果
				             ResultSet rs = statement.executeQuery(sql);
				             System.out.println("--------------------------------------");
				             String id= null;
				             String pw = null;
				             int count = 0;
				             while(rs.next()){
				            	 count++;
				                 id = rs.getString("user_name");
				                 pw = rs.getString("user_password");
				                 if(pw.equals(revpacket.user_pw)) {
				                	 // 登录成功
				                	 sendpacket.ask_type = sendpacket.LOGIN_OK;
				                	 sendpacket.user_name = revpacket.user_name;
				                 }else {
				                	 // 密码错误
				                	 sendpacket.ask_type = sendpacket.LOGIN_PW;
				                	 sendpacket.user_name = revpacket.user_name;
				                 }
				             }
				             if(count == 0) {
				            	 // 先去注册
				            	 sendpacket.ask_type = sendpacket.LOGIN_GOREGISTER;
			                	 sendpacket.user_name = revpacket.user_name;
				             }
				             rs.close();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	try {
						out = new ObjectOutputStream(mySocket.getOutputStream());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try {
						// 将响应数据包发给对应客户端
						out.writeObject(sendpacket);
						// if 登录成功
						if(sendpacket.ask_type == sendpacket.LOGIN_OK) {
							 // 将我上线啦！消息发给所有客户端
							 revpacket.send_message = "我上线啦！";
		                	 revpacket.ask_type = revpacket.SENDMESSAGE;
		                	 SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		             		 String time = date.format(new Date());
		             		 revpacket.send_time = time;
		             		 // 调用函数发送给所有
		                	 Server.SendToAll(revpacket);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(revpacket.ask_type == revpacket.REGISTER) {
					// 数据包类型为注册请求
					try {
						if(!con.isClosed()) {
						    Statement statement = null;
							try {
								statement = con.createStatement();
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							 // 检查是否有重复用户名
						     String sql = "select * from user_info where user_name=\"" + revpacket.user_name + "\"";
						     ResultSet rs = statement.executeQuery(sql);
						     System.out.println("--------------------------------------");
						     int count = 0;
						     
						     while(rs.next()){
				            	 count++;
				             }
						     
						     if(count == 0) {
						    	 try {
										statement = con.createStatement();
									} catch (SQLException e2) {
										// TODO Auto-generated catch block
										e2.printStackTrace();
									}
						    	 // 将用户名和密码插入到数据库中
						    	 sql = "insert into user_info values(" + "\"" + revpacket.user_name + "\",\"" + revpacket.user_pw + "\");";
							     // 执行 sql 语句
						    	 statement.executeUpdate(sql);
							     sendpacket.ask_type = sendpacket.REGISTER_OK;
							     sendpacket.user_name = revpacket.user_name;
						     }else{
						    	 //用户名已占用
						    	 sendpacket.ask_type = sendpacket.REGISTER_USED;
						    	 sendpacket.user_name = revpacket.user_name;
						     }
						     rs.close();
						}
						try {
							out = new ObjectOutputStream(mySocket.getOutputStream());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							out.writeObject(sendpacket);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(revpacket.ask_type == revpacket.BAIBAI) {
					// 数据包类型为 886
					try {
						// 关闭 socket 和线程
						Server.RemoveSocket(mySocket);
						mySocket.close();
						break;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	// 构造
	AcceptThread(Socket socket){
		mySocket = socket;
	}
	
}
