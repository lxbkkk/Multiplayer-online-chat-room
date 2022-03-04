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

// ��������ڽ��տͻ��˷��������ݰ����߳�(ÿ���ͻ��˶�Ӧһ���߳�
public class AcceptThread implements Runnable {
	Socket mySocket = null;
	public void run() {
		// ����������Ϣ
		System.out.println("accept client:");
		System.out.println(mySocket);
		
		ObjectInputStream in = null;
		ObjectOutputStream out = null;
		SendPacket revpacket = null;
		SendPacket sendpacket = null;
		sendpacket = new SendPacket();
		
		
		// �������ݿ�
		Connection con = null;
		
		// ���ݿ���Ϣ
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
				// �������ݰ�
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
				// ������Ϣ
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
							// sql ���
							// ��������Ϣ���浽���ݿ���
							String sql = "insert into chat_info values(" + "\"" + revpacket.user_name + "\",\"" + revpacket.send_message + "\",\"" + revpacket.send_time + "\");";
						    try {
						    	// ִ�� sql ���
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
					// ���ݰ�����Ϊ��¼����
			    	try {
						if(!con.isClosed()) {
						    Statement statement = null;
							try {
								statement = con.createStatement();
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							 // ����û����������Ƿ���ȷ
						     String sql = "select * from user_info where user_name=\"" + revpacket.user_name + "\"";
				             // ������Ż�ȡ�Ľ��
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
				                	 // ��¼�ɹ�
				                	 sendpacket.ask_type = sendpacket.LOGIN_OK;
				                	 sendpacket.user_name = revpacket.user_name;
				                 }else {
				                	 // �������
				                	 sendpacket.ask_type = sendpacket.LOGIN_PW;
				                	 sendpacket.user_name = revpacket.user_name;
				                 }
				             }
				             if(count == 0) {
				            	 // ��ȥע��
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
						// ����Ӧ���ݰ�������Ӧ�ͻ���
						out.writeObject(sendpacket);
						// if ��¼�ɹ�
						if(sendpacket.ask_type == sendpacket.LOGIN_OK) {
							 // ��������������Ϣ�������пͻ���
							 revpacket.send_message = "����������";
		                	 revpacket.ask_type = revpacket.SENDMESSAGE;
		                	 SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		             		 String time = date.format(new Date());
		             		 revpacket.send_time = time;
		             		 // ���ú������͸�����
		                	 Server.SendToAll(revpacket);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}else if(revpacket.ask_type == revpacket.REGISTER) {
					// ���ݰ�����Ϊע������
					try {
						if(!con.isClosed()) {
						    Statement statement = null;
							try {
								statement = con.createStatement();
							} catch (SQLException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
							 // ����Ƿ����ظ��û���
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
						    	 // ���û�����������뵽���ݿ���
						    	 sql = "insert into user_info values(" + "\"" + revpacket.user_name + "\",\"" + revpacket.user_pw + "\");";
							     // ִ�� sql ���
						    	 statement.executeUpdate(sql);
							     sendpacket.ask_type = sendpacket.REGISTER_OK;
							     sendpacket.user_name = revpacket.user_name;
						     }else{
						    	 //�û�����ռ��
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
					// ���ݰ�����Ϊ 886
					try {
						// �ر� socket ���߳�
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

	// ����
	AcceptThread(Socket socket){
		mySocket = socket;
	}
	
}
