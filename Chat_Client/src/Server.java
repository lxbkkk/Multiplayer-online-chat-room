import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	// 用于输入
	public static Scanner sn = new Scanner(System.in);
	
	// 用于记录当前连接的客户端
	public static ClientPool pool = new ClientPool();
	
	public static void main(String[] args) {
		// 打印提示信息
		Integer FirstStep = ServerWelcome();
		if(FirstStep.equals(1)) {
			try {
				// 创建 socket
				ServerSocket server = new ServerSocket(6213);
				Socket socket = new Socket();
				Thread new_thread;
				while(true) {
					// 接收客户端连接
					socket = server.accept();
					// 将连接信息保存在 ClientPool 中并创建线程接收信息
					pool.clientSocket[pool.NumberOfClient] = socket;
					pool.NumberOfClient++;
					new_thread = new Thread(new AcceptThread(socket));
					new_thread.start();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else {
			System.exit(0);
		}
    }
	
	static Integer ServerWelcome() {
		// 打印欢迎的提示信息
		System.out.println("Welcome to kk Chat Server!");
		System.out.println("这里是在线聊天室的服务器端, 请按以下提示输入正确的数字进行下一步: ");
		System.out.println("1 - 开启服务器, 开始接收线上用户的连接请求");
		System.out.println("2 - 关闭并退出");
		Integer FirstNumber = Integer.parseInt(sn.nextLine());
		while(FirstNumber < 1 || FirstNumber > 2) {
			System.out.println("!!请输入合法的数字( 1 或 2 )");
			FirstNumber = Integer.parseInt(sn.nextLine());
		}
		return FirstNumber;
	}
	
	// 将其中一个客户端发来的消息转发给其他客户端
	static synchronized void SendToAll(SendPacket Message) {
		Socket tmpsocket = null;
		// 遍历所有客户端
		for(int i=0;i<pool.NumberOfClient;i++) {
			tmpsocket = pool.clientSocket[i];
			ObjectOutputStream out = null;
			try {
				out = new ObjectOutputStream(tmpsocket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				out.writeObject(Message);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// 删除客户端( 在客户端关闭后
	static synchronized void RemoveSocket(Socket socket) {
		for(int i=0;i<pool.NumberOfClient;i++) {
			if(pool.clientSocket[i].equals(socket)) {
				pool.clientSocket[i] = pool.clientSocket[pool.NumberOfClient-1];
				pool.NumberOfClient--;
				break;
			}
		}
	}
}
