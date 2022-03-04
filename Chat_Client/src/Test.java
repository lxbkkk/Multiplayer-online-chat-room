import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Test {
    public static void main(String[] args) {
    	Socket socket = null;
		try {
			// 创建 socket, 参数为服务端的本机IP地址及端口(这里端口号使用了学号后四位
			socket = new Socket("192.168.135.1",6213);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// 创建 JFrame, 进行必要的初始化, 并调用函数完成对其中的 JPanel 的初始化
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