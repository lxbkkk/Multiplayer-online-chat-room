import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JTextArea;

// �ͻ������߳�, ���ڽ����û��˷�������Ϣ
public class ClientThread implements Runnable {
	static Socket mySocket = null;
	// ������Ϣ�����������ݵ�չʾ��
	JTextArea textArea;
	static StringBuffer ChatMessage;
	// �߳���ֹ�ź�
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
							// �յ�����Ϊ SENDMESSAGE �����ݰ���ͽ��յ����������ӵ���ǰ��������֮��
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
	
	// ����
	ClientThread(Socket socket,JTextArea text,StringBuffer message){
		mySocket = socket;
		textArea = text;
		ChatMessage = message;
	}

}
