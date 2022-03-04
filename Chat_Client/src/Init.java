import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
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

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Graphics;

import java.awt.*;

public class Init{

	// �ͻ���GUI�����ĸ� JPanel, ҳ����л�ͨ���л� JPanelʵ��
	// ��ʼҳ��
	static JPanel clientInit = new JPanel();
	
	// ��¼ҳ��
	static JPanel clientLogin = new JPanel();
	
	// ע��ҳ��
	static JPanel clientRegister = new JPanel();
	
	// ����ҳ��
	static JPanel clientChat = new JPanel(new BorderLayout());
	
	// ���ڷ��������ĸ�ҳ��
	static JPanel all = new JPanel();
	
	// ����ҳ�涥���ĵ�ǰ��¼�û���Ϣ
	static JLabel UserNameLabel = new JLabel();
	
	// ����ҳ��ĶԻ���
	static JTextArea textArea = new JTextArea();
	
	// ����ҳ��������
	static JTextArea userArea = new JTextArea();
	
	// ��ǰ��¼���û���
	static String UserName = "";
	
	// �Ի����е�������Ϣ
	static StringBuffer ChatMessage = new StringBuffer();
	
	// ���е�ǰ���ߵ��û���Ϣ
	static StringBuffer UserInfo = new StringBuffer();
	
	// ��ɿͻ����û������ʼ��
	public static void InitAll(Socket socket, JFrame clientFrame) {
		
		// �ֱ��ʼ���ĸ� JPanel
		ClientInit(socket,clientFrame);
		ClientLogin(socket,clientFrame);
		ClientRegister(socket,clientFrame);
		ClientChat(socket,clientFrame);
		
		// ���� JPanel �Ƿ�ɼ�
        clientInit.setVisible(true);
        clientLogin.setVisible(false);
        clientRegister.setVisible(false);
        clientChat.setVisible(false);
        
        // ���ò���Ϊ GridLayout(1,1)
        all.setLayout(new GridLayout(1,1));
        
        // ����ʼҳ����ӵ� all ��
        all.add(clientInit);
        
        // ���� JFrame ����Ϊ GirdLayout(1,1)
        clientFrame.setLayout(new GridLayout(1,1));
        
        // �������� JPanel ���õ� JFrame ��
        clientFrame.add(all);
	}
	
	// ��ʼ���û���������ĳ�ʼ����
	public static void ClientInit(Socket socket, JFrame clientFrame) {
        
		// ���ñ���ɫ
        clientInit.setBackground(new Color(250,235,215));
        
    	// icon label
    	ImageIcon imageIcon = new ImageIcon(".\\img\\1.jpg");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setVisible(true);
        
        // hello label
        String helloString = "<html><br>&nbsp;&nbsp;^ ^ Welcome to kkChat<br>&nbsp;&nbsp;������һ�����ߵ��罻������<br>&nbsp;&nbsp;�����������İ�ť��ѡ����һ������<br>&nbsp;&nbsp;ѡ�� \"��¼\" ʹ��ע��ɹ����˺ŵ�¼<br>&nbsp;&nbsp;ѡ�� \"ע��\" ǰ��ע��<br>&nbsp;&nbsp;ѡ�� \"�˳�\" �˳�����<br><br>";
        JLabel helloLabel = new JLabel(helloString);
        // �������弰��ɫ
        helloLabel.setForeground(new Color(210,180,140));
        helloLabel.setFont(new java.awt.Font("����", 1, 15));
        
        // ȥ��¼ ��ť, �����л�����¼ҳ��
        JButton buttonGoLogin = new JButton("ȥ��¼");
        // JButton ��ʽ����
        buttonGoLogin.setPreferredSize(new Dimension(100,50));
        buttonGoLogin.setBackground(new Color(255,250,240));
        buttonGoLogin.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonGoLogin.setForeground(new Color(210,180,140));
        buttonGoLogin.setFont(new java.awt.Font("����",1,15)); 
        
        // �������
        buttonGoLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// ͨ�� remove ��ǰ JPanel �� add Ҫ�л��� JPanel ����л�
        		clientLogin.setVisible(true);
        		clientInit.setVisible(false);
        		all.remove(clientInit);
        		all.add(clientLogin);
        	}
        });
        
        // ȥע�� ��ť, �����л���ע��ҳ��
        JButton buttonGoRegister = new JButton("ȥע��");
        // JButton ��ʽ����
        buttonGoRegister.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonGoRegister.setPreferredSize(new Dimension(100,50));
        buttonGoRegister.setBackground(new Color(255,250,240));
        buttonGoRegister.setForeground(new Color(210,180,140));
        buttonGoRegister.setFont(new java.awt.Font("����",1,15)); 
        // �������
        buttonGoRegister.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clientInit.setVisible(false);
        		clientRegister.setVisible(true);
        		all.remove(clientInit);
        		all.add(clientRegister);
        	}
        });
        
        // �˳� ��ť, �����˳�����
        JButton buttonExit = new JButton("�˳�");
        // ��ť��ʽ����
        buttonExit.setPreferredSize(new Dimension(100,50));
        buttonExit.setBackground(new Color(255,250,240));
        buttonExit.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonExit.setForeground(new Color(210,180,140));
        buttonExit.setFont(new java.awt.Font("����",1,15)); 
        
        // ����¼�
        buttonExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// �����˷��� socket �Ͽ����ӵ���Ϣ, �����ͻ������̲߳��˳�����
        		SendPacket send = new SendPacket();
        		send.ask_type = send.BAIBAI;
        		buttonSendAction(socket,send);
        		ClientThread.exit = true;
        		try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		System.exit(0);
        	}
        });
        
        // �������
        GridBagLayout gridBagLayout = new GridBagLayout();
        
        clientInit.setLayout(gridBagLayout);
        
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagLayout.setConstraints(imageLabel,gridBagConstraints);
        
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraints.gridwidth = 0;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(helloLabel,gridBagConstraints);
        
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.insets = new Insets(0,0,0,10);
        gridBagLayout.setConstraints(buttonGoLogin,gridBagConstraints);
        
        gridBagConstraints.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(buttonGoRegister,gridBagConstraints);
        
        gridBagConstraints.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 1;
        gridBagLayout.setConstraints(buttonExit,gridBagConstraints);
        
        // �����������ӵ� JPanel ��
        clientInit.add(imageLabel);
        clientInit.add(helloLabel);
        clientInit.add(buttonGoLogin);
        clientInit.add(buttonGoRegister);
        clientInit.add(buttonExit);
    }
	
	public static void ClientLogin(Socket socket, JFrame clientFrame) {
		// ���ñ���ɫ
        clientLogin.setBackground(new Color(250,235,215));
        
    	// icon label
    	ImageIcon imageIconLogin = new ImageIcon(".\\img\\2.jpg");
    	imageIconLogin.setImage(imageIconLogin.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT ));
        JLabel imageLabelLogin = new JLabel(imageIconLogin);
        imageLabelLogin.setVisible(true);
        
        // hello label
        String LoginString = "�����ǵ�¼��";
        JLabel LoginLabel = new JLabel(LoginString);
        LoginLabel.setForeground(new Color(210,180,140));
        LoginLabel.setFont(new java.awt.Font("����", 1, 15));
        
        // �����û���
        JLabel user_name = new JLabel("�û�����");
        user_name.setFont(new java.awt.Font("����", 1, 15));
        user_name.setForeground(new Color(210,180,140));
        user_name.setVisible(true);
        JTextField input_name = new JTextField();
        input_name.setColumns(20);
        input_name.setVisible(true);
        
        // ��������
        JLabel user_pw = new JLabel("���룺");
        user_pw.setFont(new java.awt.Font("����", 1, 15));
        user_pw.setForeground(new Color(210,180,140));
        user_pw.setVisible(true);
        JTextField input_pw = new JTextField();
        input_pw.setColumns(20);
        input_pw.setVisible(true);
        
        
        // ��¼ ��ť, �����ύ��¼��Ϣ�������, ����û����������Ƿ���ȷ
        JButton buttonLogin = new JButton("��¼");
        buttonLogin.setPreferredSize(new Dimension(100,50));
        buttonLogin.setBackground(new Color(255,250,240));
        buttonLogin.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonLogin.setForeground(new Color(210,180,140));
        buttonLogin.setFont(new java.awt.Font("����",1,15)); 
        buttonLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// �������ݰ��������, ��������������ı���
        		String name = input_name.getText().trim();;
        		String pw = input_pw.getText().trim();
        		SendPacket send = new SendPacket();
        		send.ask_type = send.LOGIN;
        		send.user_name = input_name.getText().trim();
        		send.user_pw = input_pw.getText().trim();
        		input_name.setText("");
        		input_pw.setText("");
        		if(name.equals("") | pw.equals("")) {
        			JOptionPane.showMessageDialog(null, "���벻��Ϊ��:(");
        		}else{
        			buttonLoginAction(socket,send);
        		}
        	}
        });
        
        // �����ת��ע��ҳ��
        JButton buttonLoginRegister = new JButton("ȥע��");
        buttonLoginRegister.setPreferredSize(new Dimension(100,50));
        buttonLoginRegister.setBackground(new Color(255,250,240));
        buttonLoginRegister.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonLoginRegister.setForeground(new Color(210,180,140));
        buttonLoginRegister.setFont(new java.awt.Font("����",1,15)); 
        buttonLoginRegister.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clientLogin.setVisible(false);
        		clientRegister.setVisible(true);
        		all.remove(clientLogin);
        		all.add(clientRegister);
        	}
        });
        
        // ����˳�����
        JButton buttonLoginExit = new JButton("�˳�");
        buttonLoginExit.setPreferredSize(new Dimension(100,50));
        buttonLoginExit.setBackground(new Color(255,250,240));
        buttonLoginExit.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonLoginExit.setForeground(new Color(210,180,140));
        buttonLoginExit.setFont(new java.awt.Font("����",1,15)); 
        buttonLoginExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		SendPacket send = new SendPacket();
        		send.ask_type = send.BAIBAI;
        		buttonSendAction(socket,send);
        		ClientThread.exit = true;
        		try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		System.exit(0);
        	}
        });
        
        // ����������ֹ�ϵ
        GridBagLayout gridBagLayoutLogin = new GridBagLayout();
        clientLogin.setLayout(gridBagLayoutLogin);
        
        GridBagConstraints gridBagConstraintsLogin = new GridBagConstraints();
        gridBagConstraintsLogin.fill = GridBagConstraints.NONE;
        gridBagConstraintsLogin.gridx = 0;
        gridBagConstraintsLogin.gridy = 0;
        gridBagConstraintsLogin.gridwidth = 0;
        gridBagConstraintsLogin.gridheight = 1;
        gridBagConstraintsLogin.anchor = GridBagConstraints.WEST;
        gridBagConstraintsLogin.insets = new Insets(10,10,0,0);
        gridBagLayoutLogin.setConstraints(imageLabelLogin,gridBagConstraintsLogin);
        
        gridBagConstraintsLogin.gridx = 5;
        gridBagConstraintsLogin.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraintsLogin.gridwidth = 0;
        gridBagConstraintsLogin.gridheight = 1;
        gridBagLayoutLogin.setConstraints(LoginLabel,gridBagConstraintsLogin);
        
        gridBagConstraintsLogin.gridx = 5;
        gridBagConstraintsLogin.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraintsLogin.gridwidth = 0;
        gridBagConstraintsLogin.gridheight = 1;
        gridBagLayoutLogin.setConstraints(user_name,gridBagConstraintsLogin);
        
        gridBagConstraintsLogin.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraintsLogin.gridy = 10;
        gridBagConstraintsLogin.gridwidth = 0;
        gridBagConstraintsLogin.gridheight = 1;
        gridBagLayoutLogin.setConstraints(input_name,gridBagConstraintsLogin);
        
        gridBagConstraintsLogin.gridx = 5;
        gridBagConstraintsLogin.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraintsLogin.gridwidth = 0;
        gridBagConstraintsLogin.gridheight = 1;
        gridBagLayoutLogin.setConstraints(user_pw,gridBagConstraintsLogin);
        
        gridBagConstraintsLogin.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraintsLogin.gridy = 20;
        gridBagConstraintsLogin.gridwidth = 0;
        gridBagConstraintsLogin.gridheight = 1;
        gridBagLayoutLogin.setConstraints(input_pw,gridBagConstraintsLogin);
        
        gridBagConstraintsLogin.insets = new Insets(20,10,10,10);
        gridBagConstraintsLogin.gridx = 5;
        gridBagConstraintsLogin.gridy = 30;
        gridBagConstraintsLogin.gridwidth = 1;
        gridBagConstraintsLogin.gridheight = 1;
        gridBagLayoutLogin.setConstraints(buttonLogin,gridBagConstraintsLogin);
        
        
        gridBagConstraintsLogin.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraintsLogin.gridy = 30;
        gridBagConstraintsLogin.gridwidth = 1;
        gridBagConstraintsLogin.gridheight = 1;
        gridBagLayoutLogin.setConstraints(buttonLoginRegister,gridBagConstraintsLogin);
        
        gridBagConstraintsLogin.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraintsLogin.gridy = 30;
        gridBagConstraintsLogin.gridwidth = 1;
        gridBagConstraintsLogin.gridheight = 1;
        gridBagLayoutLogin.setConstraints(buttonLoginExit,gridBagConstraintsLogin);
        
        clientLogin.add(imageLabelLogin);
        clientLogin.add(LoginLabel);
        clientLogin.add(user_name);
        clientLogin.add(input_name);
        clientLogin.add(user_pw);
        clientLogin.add(input_pw);
        clientLogin.add(buttonLogin);
        clientLogin.add(buttonLoginRegister);
        clientLogin.add(buttonLoginExit);
	}
	
	// ��ʼ��ע��ҳ��
	public static void ClientRegister(Socket socket, JFrame clientFrame) {
		clientRegister.setBackground(new Color(250,235,215));
        
    	// icon label
    	ImageIcon imageIconRegister = new ImageIcon(".\\img\\3.jpg");
    	imageIconRegister.setImage(imageIconRegister.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT ));
        JLabel imageLabelRegister = new JLabel(imageIconRegister);
        imageLabelRegister.setVisible(true);
        
        // hello label
        String RegisterString = "������ע�ᣡ";
        JLabel RegisterLabel = new JLabel(RegisterString);
        RegisterLabel.setForeground(new Color(210,180,140));
        RegisterLabel.setFont(new java.awt.Font("����", 1, 15));
        
        // �����û���
        JLabel register_name = new JLabel("�û�����");
        register_name.setFont(new java.awt.Font("����", 1, 15));
        register_name.setForeground(new Color(210,180,140));
        register_name.setVisible(true);
        JTextField register_input_name = new JTextField();
        register_input_name.setColumns(20);
        register_input_name.setVisible(true);
        
        // ���������ȷ������
        JLabel register_pw = new JLabel("���룺");
        register_pw.setFont(new java.awt.Font("����", 1, 15));
        register_pw.setForeground(new Color(210,180,140));
        register_pw.setVisible(true);
        JTextField register_pw_input = new JTextField();
        register_pw_input.setColumns(20);
        register_pw_input.setVisible(true);
        
        JLabel register_pw2 = new JLabel("ȷ�����룺");
        register_pw2.setFont(new java.awt.Font("����", 1, 15));
        register_pw2.setForeground(new Color(210,180,140));
        register_pw2.setVisible(true);
        JTextField register_pw_input2 = new JTextField();
        register_pw_input2.setColumns(20);
        register_pw_input2.setVisible(true);
        
        // ����ύע����Ϣ�������
        JButton buttonRegister = new JButton("ע��");
        buttonRegister.setPreferredSize(new Dimension(100,50));
        buttonRegister.setBackground(new Color(255,250,240));
        buttonRegister.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonRegister.setForeground(new Color(210,180,140));
        buttonRegister.setFont(new java.awt.Font("����",1,15)); 
        buttonRegister.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// ��ע����Ϣ����Ϊ���ݰ�
        		String name = register_input_name.getText().trim();
        		String pw1 = register_pw_input.getText().trim();
        		String pw2 = register_pw_input2.getText().trim();
        		System.out.println(name);
        		System.out.println(pw1);
        		System.out.println(pw2);
        		if(pw1.equals("") | pw2.equals("") | name.equals("")) {
        			JOptionPane.showMessageDialog(null, "���벻��Ϊ��:(");
        		}
        		else {
        			// �ж������ȷ�������Ƿ���ͬ
            		if(pw1.equals(pw2)) {
            			SendPacket send = new SendPacket();
            			send.ask_type = send.REGISTER;
            			send.user_name = name;
            			send.user_pw = pw1;
            			register_input_name.setText("");
            			register_pw_input.setText("");
            			register_pw_input2.setText("");
                		buttonRegisterAction(socket,send);
            		}else {
            			JOptionPane.showMessageDialog(null, "ȷ�����벻һ��:(");
            		}
        		}
        	}
        });
        
        // �����ת����¼ҳ��
        JButton buttonRegisterLogin = new JButton("ȥ��¼");
        buttonRegisterLogin.setPreferredSize(new Dimension(100,50));
        buttonRegisterLogin.setBackground(new Color(255,250,240));
        buttonRegisterLogin.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonRegisterLogin.setForeground(new Color(210,180,140));
        buttonRegisterLogin.setFont(new java.awt.Font("����",1,15)); 
        buttonRegisterLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clientRegister.setVisible(false);
        		clientLogin.setVisible(true);
        		all.remove(clientRegister);
        		all.add(clientLogin);
        	}
        });
        
        // ����˳�����
        JButton buttonRegisterExit = new JButton("�˳�");
        buttonRegisterExit.setPreferredSize(new Dimension(100,50));
        buttonRegisterExit.setBackground(new Color(255,250,240));
        buttonRegisterExit.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonRegisterExit.setForeground(new Color(210,180,140));
        buttonRegisterExit.setFont(new java.awt.Font("����",1,15)); 
        buttonRegisterExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		SendPacket send = new SendPacket();
        		send.ask_type = send.BAIBAI;
        		buttonSendAction(socket,send);
        		ClientThread.exit = true;
        		try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		System.exit(0);
        	}
        });
        
        // ��������Ĳ��ֹ�ϵ
        GridBagLayout gridBagLayoutRegister = new GridBagLayout();
        clientRegister.setLayout(gridBagLayoutRegister);

        GridBagConstraints gridBagConstraintsRegister = new GridBagConstraints();
        gridBagConstraintsRegister.fill = GridBagConstraints.NONE;
        gridBagConstraintsRegister.gridx = 0;
        gridBagConstraintsRegister.gridy = 0;
        gridBagConstraintsRegister.gridwidth = 0;
        gridBagConstraintsRegister.gridheight = 1;
        gridBagConstraintsRegister.anchor = GridBagConstraints.WEST;
        gridBagConstraintsRegister.insets = new Insets(10,10,0,0);
        gridBagLayoutRegister.setConstraints(imageLabelRegister,gridBagConstraintsRegister);
        
        gridBagConstraintsRegister.gridx = 5;
        gridBagConstraintsRegister.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraintsRegister.gridwidth = 0;
        gridBagConstraintsRegister.gridheight = 1;
        gridBagLayoutRegister.setConstraints(RegisterLabel,gridBagConstraintsRegister);
        
        gridBagConstraintsRegister.gridx = 5;
        gridBagConstraintsRegister.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraintsRegister.gridwidth = 0;
        gridBagConstraintsRegister.gridheight = 1;
        gridBagLayoutRegister.setConstraints(register_name,gridBagConstraintsRegister);
        
        gridBagConstraintsRegister.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraintsRegister.gridy = 10;
        gridBagConstraintsRegister.gridwidth = 0;
        gridBagConstraintsRegister.gridheight = 1;
        gridBagLayoutRegister.setConstraints(register_input_name,gridBagConstraintsRegister);
        
        gridBagConstraintsRegister.gridx = 5;
        gridBagConstraintsRegister.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraintsRegister.gridwidth = 0;
        gridBagConstraintsRegister.gridheight = 1;
        gridBagLayoutRegister.setConstraints(register_pw,gridBagConstraintsRegister);
        
        gridBagConstraintsRegister.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraintsRegister.gridy = 20;
        gridBagConstraintsRegister.gridwidth = 0;
        gridBagConstraintsRegister.gridheight = 1;
        gridBagLayoutRegister.setConstraints(register_pw_input,gridBagConstraintsRegister);
        
        gridBagConstraintsRegister.gridx = 5;
        gridBagConstraintsRegister.gridy = GridBagConstraints.RELATIVE;
        gridBagConstraintsRegister.gridwidth = 0;
        gridBagConstraintsRegister.gridheight = 1;
        gridBagLayoutRegister.setConstraints(register_pw2,gridBagConstraintsRegister);
        
        gridBagConstraintsRegister.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraintsRegister.gridy = 30;
        gridBagConstraintsRegister.gridwidth = 0;
        gridBagConstraintsRegister.gridheight = 1;
        gridBagLayoutRegister.setConstraints(register_pw_input2,gridBagConstraintsRegister);
        
        gridBagConstraintsRegister.insets = new Insets(20,10,10,10);
        gridBagConstraintsRegister.gridx = 5;
        gridBagConstraintsRegister.gridy = 40;
        gridBagConstraintsRegister.gridwidth = 1;
        gridBagConstraintsRegister.gridheight = 1;
        gridBagLayoutRegister.setConstraints(buttonRegister,gridBagConstraintsRegister);
        
        gridBagConstraintsRegister.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraintsRegister.gridy = 40;
        gridBagConstraintsRegister.gridwidth = 1;
        gridBagConstraintsRegister.gridheight = 1;
        gridBagLayoutRegister.setConstraints(buttonRegisterLogin,gridBagConstraintsRegister);
        
        gridBagConstraintsRegister.gridx = GridBagConstraints.RELATIVE;
        gridBagConstraintsRegister.gridy = 40;
        gridBagConstraintsRegister.gridwidth = 1;
        gridBagConstraintsRegister.gridheight = 1;
        gridBagLayoutRegister.setConstraints(buttonRegisterExit,gridBagConstraintsRegister);
        
        clientRegister.add(imageLabelRegister);
        clientRegister.add(RegisterLabel);
        clientRegister.add(register_name);
        clientRegister.add(register_input_name);
        clientRegister.add(register_pw);
        clientRegister.add(register_pw_input);
        clientRegister.add(register_pw2);
        clientRegister.add(register_pw_input2);
        clientRegister.add(buttonRegister);
        clientRegister.add(buttonRegisterLogin);
        clientRegister.add(buttonRegisterExit);
	}
	
	// ��ʼ������ҳ��
	public static void ClientChat(Socket socket ,JFrame clientFrame) {
		
		clientChat.setBackground(new Color(250,235,215));
		
		// ����չʾ�����¼�Ĳ���
		textArea.setEditable(false);
		textArea.setCaretColor(new Color(0,128,0));
		textArea.setSelectionColor(new Color(240,255,240));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new java.awt.Font("����", 0, 16));
		textArea.setBackground(new Color(250,235,215));
		
		// ���첿�ֵĻ���
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(textArea);
		scroll.setBorder(null);
		
		// ��������Ի��ĶԻ���
		JTextArea input_text = new JTextArea();
		input_text.setBackground(new Color(255,250,240));
		input_text.setVisible(true);
		input_text.setPreferredSize(new Dimension (820,150));
		input_text.setLineWrap(true);
		input_text.setWrapStyleWord(true);
		input_text.setFont(new java.awt.Font("����", 0, 16));
		
		// ���������Ϣ
		JButton sendButton = new JButton("����");
		sendButton.setPreferredSize(new Dimension(100,50));
        sendButton.setBackground(new Color(255,240,245));
        sendButton.setBorder(BorderFactory.createRaisedBevelBorder());
        sendButton.setForeground(new Color(106,90,205));
        sendButton.setFont(new java.awt.Font("����",1,15)); 
        sendButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String input = input_text.getText().trim();
        		// �ж�ʹ�������ݲ���Ϊ��
        		if(input.equals("")) {
        			JOptionPane.showMessageDialog(null, "�������ݲ���Ϊ��:(");
        		}else {
        			// ����ǰʱ����Ϣһͬ���͵������
        			SendPacket send = new SendPacket();
            		send.ask_type = send.SENDMESSAGE;
            		send.user_name = UserName;
            		send.send_message = input_text.getText().trim();
            		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            		String time = date.format(new Date());
            		send.send_time = time;
            		buttonSendAction(socket,send);
            		input_text.setText("");
        		}
        	}
        });
        
        // ������������¼��չʾ����
        JButton clearButton = new JButton("���");
        clearButton.setPreferredSize(new Dimension(100,50));
        clearButton.setBackground(new Color(255,240,245));
        clearButton.setBorder(BorderFactory.createRaisedBevelBorder());
        clearButton.setForeground(new Color(106,90,205));
        clearButton.setFont(new java.awt.Font("����",1,15)); 
        clearButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		try {
        			int length = ClientThread.ChatMessage.length();
					ClientThread.ChatMessage.delete(0,length);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		textArea.setText("");
        	}
        });
        
        // �˳�����
        JButton exitButton = new JButton("�˳�");
        exitButton.setPreferredSize(new Dimension(100,50));
        exitButton.setBackground(new Color(255,240,245));
        exitButton.setBorder(BorderFactory.createRaisedBevelBorder());
        exitButton.setForeground(new Color(106,90,205));
        exitButton.setFont(new java.awt.Font("����",1,15)); 
        exitButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
    			SendPacket send = new SendPacket();
        		send.ask_type = send.BAIBAI;
        		buttonSendAction(socket,send);
        		ClientThread.exit = true;
        		System.exit(0);
        	}
        });
        
        // ��������Ĳ��ֹ�ϵ
        BorderLayout downLayout = new BorderLayout();
        
        JPanel ButtonPanel = new JPanel();
        ButtonPanel.setLayout(downLayout);
        ButtonPanel.setBackground(Color.red);
        ButtonPanel.add(sendButton,BorderLayout.NORTH);
        ButtonPanel.add(clearButton,BorderLayout.CENTER);
        ButtonPanel.add(exitButton,BorderLayout.SOUTH);
        JPanel DownPanel = new JPanel();
        
        downLayout = new BorderLayout();
        
        DownPanel.setLayout(downLayout);
        DownPanel.add(input_text,BorderLayout.WEST);
        DownPanel.add(ButtonPanel,BorderLayout.CENTER);
        clientChat.add(UserNameLabel,BorderLayout.NORTH);
        clientChat.add(scroll,BorderLayout.CENTER);
        clientChat.add(DownPanel,BorderLayout.SOUTH);
	}
	
	// �ύ��¼��Ϣ�Ĵ�����
    public static void buttonLoginAction(Socket socket,SendPacket send) {
    	try {
    		// �����ݰ����͸������
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(send);
			System.out.println("send packet finish");
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			// �ӷ���˻�ȡ���ص����ݰ�, �Եõ���¼�Ƿ�ɹ�����Ϣ
			SendPacket tmp = (SendPacket) in.readObject();
			// ��¼�ɹ�, ��ת������ҳ��, ���������̲߳��ϻ�ȡ�ӷ���˷�������Ϣ
			if(tmp.ask_type == tmp.LOGIN_OK) {
				JOptionPane.showMessageDialog(null, "��¼�ɹ�:D");
				UserName = tmp.user_name;
				clientLogin.setVisible(false);
	    		all.remove(clientLogin);
	    		UserNameLabel.setText(" ��ǰ�û���" + UserName);
	    		UserNameLabel.setBackground(new Color(250,235,215));
	    		UserNameLabel.setFont(new java.awt.Font("����", 0, 16));
	    		UserNameLabel.setVisible(true);
	    		clientChat.setVisible(true);
	    		all.add(clientChat);
	    		Thread threadClient = new Thread(new ClientThread(socket,textArea,ChatMessage));
	    		threadClient.start();
			}else if(tmp.ask_type == tmp.LOGIN_PW) {
				// �������, ������ʾ
				JOptionPane.showMessageDialog(null, "�������!");  
			}else if(tmp.ask_type == tmp.LOGIN_GOREGISTER) {
				// �û�δע��, ������ʾ
				JOptionPane.showMessageDialog(null, "���˺�δע��,����ǰ��ע��!");
			}else if(tmp.ask_type == tmp.USER_LOGIN) {
				// �û��ѵ�¼, ������ʾ
				JOptionPane.showMessageDialog(null, "�û��ѵ�¼");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    // ����������Ϣ�������
    public static void buttonSendAction(Socket socket,SendPacket send) {
    	try {
    		// �������ݰ��������
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(send);
			System.out.println("send message finish");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    // �ύע����Ϣ�������
    public static void buttonRegisterAction(Socket socket,SendPacket send) {
    	try {
    		// �������ݰ��������
    		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(send);
			System.out.println("send message finish");
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			SendPacket tmp = null;
			try {
				// ��ȡ����˷��ص����ݰ�
				tmp = (SendPacket) in.readObject();
				System.out.println(tmp.user_name);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// ע��ɹ�, ��ת����¼ҳ��
			if(tmp.ask_type == tmp.REGISTER_OK) {
				JOptionPane.showMessageDialog(null, "ע��ɹ�:D���¼��");
				clientRegister.setVisible(false);
	    		all.remove(clientRegister);
	    		clientLogin.setVisible(true);
	    		all.add(clientLogin);
			}else if(tmp.ask_type == tmp.REGISTER_USED) {
				// �û����ѱ�ռ��, ������ʾ
				JOptionPane.showMessageDialog(null, "�û����ѱ�ʹ��:(");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
