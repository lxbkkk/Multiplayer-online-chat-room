import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JTextArea;

// 客户端子线程, 用于接收用户端发来的信息
public class ClientThread implements Runnable {
	static Socket mySocket = null;
	// 根据信息设置聊天内容的展示框
	JTextArea textArea;
	static StringBuffer ChatMessage;
	// 线程终止信号
	static boolean exit = false;

	public void run() {
		// TODO Auto-generated method stub
		while(!exit) {
			ObjectInputStream in = null;
			try {
				if(mySocket.isConnected()) {
					in = new ObjectInputStream(mySocket.getInputStream());
					try {
						SendPacket tmp = (SendPacket) in.readObject();
						if(tmp.ask_type == tmp.SENDMESSAGE) {
							// 收到类型为 SENDMESSAGE 的数据包后就将收到的数据连接到当前聊天内容之后
							System.out.println(tmp.send_message);
							String tmpstring = ChatMessage + "\n\n " + tmp.user_name + "  " + tmp.send_time + "\n " + tmp.send_message;
							System.out.println(tmpstring);
							ChatMessage = new StringBuffer();
							ChatMessage.append(tmpstring);
							textArea.setText(tmpstring);
						}
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			mySocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// 构造
	ClientThread(Socket socket,JTextArea text,StringBuffer message){
		mySocket = socket;
		textArea = text;
		ChatMessage = message;
	}

}
