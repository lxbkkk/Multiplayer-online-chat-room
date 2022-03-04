import java.net.Socket;

// 用于记录当前连接的客户端的连接信息
public class ClientPool {
	// 连接的客户端数量
	public static int NumberOfClient = 0;
	// 存放 socket 信息
	public static Socket[] clientSocket= new Socket[100];
}
