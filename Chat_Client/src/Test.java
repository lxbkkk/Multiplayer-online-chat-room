import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {
    public static void main(String[] args) {
    	Socket socket = null;
		try {
			// ���� socket, ����Ϊ����˵ı���IP��ַ���˿�(����˿ں�ʹ����ѧ�ź���λ
			socket = new Socket("192.168.135.1",6213);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// ���� JFrame, ���б�Ҫ�ĳ�ʼ��, �����ú�����ɶ����е� JPanel �ĳ�ʼ��
    	JFrame clientInitFrame = new JFrame();
    	clientInitFrame.setTitle("Chat Client");
    	clientInitFrame.setSize(1000, 650);
    	clientInitFrame.setLocation(300,150);
    	clientInitFrame.setResizable(false);
    	clientInitFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	clientInitFrame.setVisible(true);
    	Init.InitAll(socket, clientInitFrame);
    }
}