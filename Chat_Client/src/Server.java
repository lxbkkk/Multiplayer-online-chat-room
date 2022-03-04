import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	// ��������
	public static Scanner sn = new Scanner(System.in);
	
	// ���ڼ�¼��ǰ���ӵĿͻ���
	public static ClientPool pool = new ClientPool();
	
	public static void main(String[] args) {
		// ��ӡ��ʾ��Ϣ
		Integer FirstStep = ServerWelcome();
		if(FirstStep.equals(1)) {
			try {
				// ���� socket
				ServerSocket server = new ServerSocket(6213);
				Socket socket = new Socket();
				Thread new_thread;
				while(true) {
					// ���տͻ�������
					socket = server.accept();
					// ��������Ϣ������ ClientPool �в������߳̽�����Ϣ
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
		// ��ӡ��ӭ����ʾ��Ϣ
		System.out.println("Welcome to kk Chat Server!");
		System.out.println("���������������ҵķ�������, �밴������ʾ������ȷ�����ֽ�����һ��: ");
		System.out.println("1 - ����������, ��ʼ���������û�����������");
		System.out.println("2 - �رղ��˳�");
		Integer FirstNumber = Integer.parseInt(sn.nextLine());
		while(FirstNumber < 1 || FirstNumber > 2) {
			System.out.println("!!������Ϸ�������( 1 �� 2 )");
			FirstNumber = Integer.parseInt(sn.nextLine());
		}
		return FirstNumber;
	}
	
	// ������һ���ͻ��˷�������Ϣת���������ͻ���
	static synchronized void SendToAll(SendPacket Message) {
		Socket tmpsocket = null;
		// �������пͻ���
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
	
	// ɾ���ͻ���( �ڿͻ��˹رպ�
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
