import java.net.Socket;

// ���ڼ�¼��ǰ���ӵĿͻ��˵�������Ϣ
public class ClientPool {
	// ���ӵĿͻ�������
	public static int NumberOfClient = 0;
	// ��� socket ��Ϣ
	public static Socket[] clientSocket= new Socket[100];
}
