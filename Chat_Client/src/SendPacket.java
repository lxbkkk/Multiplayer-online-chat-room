import java.io.Serializable;

// ���ݰ���ʽ
public class SendPacket implements Serializable{
	// ���ڱ�ʾ���ݰ�����
	
	// ��ʾ���ݰ��ǿͻ��˷��͵ĵ�¼��Ϣ
	final static int LOGIN = 0;
	
	// ��ʾ���ݰ��ǿͻ��˷��͵�ע����Ϣ
	final static int REGISTER = 1;
	
	// ��ʾ���ݰ��ǿͻ��˷��͵�������Ϣ
	final static int SENDMESSAGE = 2;
	
	// ��ʾ���ݰ��Ƿ���˷��͵ĵ�¼�ɹ�����Ӧ���ݰ�
	final static int LOGIN_OK = 3;
	
	// ��ʾ���ݰ��Ƿ���˷��͵��û�δע�����Ӧ���ݰ�(#`O��)
	final static int LOGIN_GOREGISTER = 4;
	
	// ��ʾ���ݰ��Ƿ���˷��͵�����������Ӧ���ݰ�
	final static int LOGIN_PW = 5;
	
	// ����ˣ�ע��ɹ�
	final static int REGISTER_OK = 6;
	
	// ����ˣ��û�����ռ��
	final static int REGISTER_USED = 7;
	
	// ����ˣ����ݷ��ͳɹ�
	final static int MESSAGE_OK = 8;
	
	// ����ˣ��û��ѵ�¼
	final static int USER_LOGIN = 9;
	
	// �ͻ��ˣ�886
	final static int BAIBAI = 10;
	
	// ���ݰ�����
	public int ask_type;
	
	// �û���
	public String user_name;
	
	// ����
	public String user_pw;
	
	// ������Ϣ
	public String send_message;
	
	// ����Ŀ��
	public String send_to;
	
	// ����ʱ��
	public String send_time;
	
	// ֮ǰ�������¼
	public String chat_before;
}	
