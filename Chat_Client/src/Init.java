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

	// 客户端GUI共有四个 JPanel, 页面的切换通过切换 JPanel实现
	// 初始页面
	static JPanel clientInit = new JPanel();
	
	// 登录页面
	static JPanel clientLogin = new JPanel();
	
	// 注册页面
	static JPanel clientRegister = new JPanel();
	
	// 聊天页面
	static JPanel clientChat = new JPanel(new BorderLayout());
	
	// 用于放置以上四个页面
	static JPanel all = new JPanel();
	
	// 聊天页面顶部的当前登录用户信息
	static JLabel UserNameLabel = new JLabel();
	
	// 聊天页面的对话框
	static JTextArea textArea = new JTextArea();
	
	// 聊天页面的输入框
	static JTextArea userArea = new JTextArea();
	
	// 当前登录的用户名
	static String UserName = "";
	
	// 对话框中的聊天信息
	static StringBuffer ChatMessage = new StringBuffer();
	
	// 所有当前在线的用户信息
	static StringBuffer UserInfo = new StringBuffer();
	
	// 完成客户端用户界面初始化
	public static void InitAll(Socket socket, JFrame clientFrame) {
		
		// 分别初始化四个 JPanel
		ClientInit(socket,clientFrame);
		ClientLogin(socket,clientFrame);
		ClientRegister(socket,clientFrame);
		ClientChat(socket,clientFrame);
		
		// 设置 JPanel 是否可见
        clientInit.setVisible(true);
        clientLogin.setVisible(false);
        clientRegister.setVisible(false);
        clientChat.setVisible(false);
        
        // 设置布局为 GridLayout(1,1)
        all.setLayout(new GridLayout(1,1));
        
        // 将初始页面添加到 all 中
        all.add(clientInit);
        
        // 设置 JFrame 布局为 GirdLayout(1,1)
        clientFrame.setLayout(new GridLayout(1,1));
        
        // 将最外层的 JPanel 放置到 JFrame 中
        clientFrame.add(all);
	}
	
	// 初始化用户进入程序后的初始界面
	public static void ClientInit(Socket socket, JFrame clientFrame) {
        
		// 设置背景色
        clientInit.setBackground(new Color(250,235,215));
        
    	// icon label
    	ImageIcon imageIcon = new ImageIcon(".\\img\\1.jpg");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT));
        JLabel imageLabel = new JLabel(imageIcon);
        imageLabel.setVisible(true);
        
        // hello label
        String helloString = "<html><br>&nbsp;&nbsp;^ ^ Welcome to kkChat<br>&nbsp;&nbsp;这里是一个在线的社交聊天室<br>&nbsp;&nbsp;你可以在下面的按钮中选择下一步操作<br>&nbsp;&nbsp;选择 \"登录\" 使用注册成功的账号登录<br>&nbsp;&nbsp;选择 \"注册\" 前往注册<br>&nbsp;&nbsp;选择 \"退出\" 退出程序<br><br>";
        JLabel helloLabel = new JLabel(helloString);
        // 设置字体及颜色
        helloLabel.setForeground(new Color(210,180,140));
        helloLabel.setFont(new java.awt.Font("楷体", 1, 15));
        
        // 去登录 按钮, 用于切换到登录页面
        JButton buttonGoLogin = new JButton("去登录");
        // JButton 样式设置
        buttonGoLogin.setPreferredSize(new Dimension(100,50));
        buttonGoLogin.setBackground(new Color(255,250,240));
        buttonGoLogin.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonGoLogin.setForeground(new Color(210,180,140));
        buttonGoLogin.setFont(new java.awt.Font("楷体",1,15)); 
        
        // 点击监听
        buttonGoLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// 通过 remove 当前 JPanel 和 add 要切换的 JPanel 完成切换
        		clientLogin.setVisible(true);
        		clientInit.setVisible(false);
        		all.remove(clientInit);
        		all.add(clientLogin);
        	}
        });
        
        // 去注册 按钮, 用于切换到注册页面
        JButton buttonGoRegister = new JButton("去注册");
        // JButton 样式设置
        buttonGoRegister.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonGoRegister.setPreferredSize(new Dimension(100,50));
        buttonGoRegister.setBackground(new Color(255,250,240));
        buttonGoRegister.setForeground(new Color(210,180,140));
        buttonGoRegister.setFont(new java.awt.Font("楷体",1,15)); 
        // 点击监听
        buttonGoRegister.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clientInit.setVisible(false);
        		clientRegister.setVisible(true);
        		all.remove(clientInit);
        		all.add(clientRegister);
        	}
        });
        
        // 退出 按钮, 用于退出程序
        JButton buttonExit = new JButton("退出");
        // 按钮样式设置
        buttonExit.setPreferredSize(new Dimension(100,50));
        buttonExit.setBackground(new Color(255,250,240));
        buttonExit.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonExit.setForeground(new Color(210,180,140));
        buttonExit.setFont(new java.awt.Font("楷体",1,15)); 
        
        // 点击事件
        buttonExit.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// 想服务端发送 socket 断开连接的信息, 结束客户端子线程并退出程序
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
        
        // 组件布局
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
        
        // 将以上组件添加到 JPanel 中
        clientInit.add(imageLabel);
        clientInit.add(helloLabel);
        clientInit.add(buttonGoLogin);
        clientInit.add(buttonGoRegister);
        clientInit.add(buttonExit);
    }
	
	public static void ClientLogin(Socket socket, JFrame clientFrame) {
		// 设置背景色
        clientLogin.setBackground(new Color(250,235,215));
        
    	// icon label
    	ImageIcon imageIconLogin = new ImageIcon(".\\img\\2.jpg");
    	imageIconLogin.setImage(imageIconLogin.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT ));
        JLabel imageLabelLogin = new JLabel(imageIconLogin);
        imageLabelLogin.setVisible(true);
        
        // hello label
        String LoginString = "这里是登录！";
        JLabel LoginLabel = new JLabel(LoginString);
        LoginLabel.setForeground(new Color(210,180,140));
        LoginLabel.setFont(new java.awt.Font("楷体", 1, 15));
        
        // 输入用户名
        JLabel user_name = new JLabel("用户名：");
        user_name.setFont(new java.awt.Font("楷体", 1, 15));
        user_name.setForeground(new Color(210,180,140));
        user_name.setVisible(true);
        JTextField input_name = new JTextField();
        input_name.setColumns(20);
        input_name.setVisible(true);
        
        // 输入密码
        JLabel user_pw = new JLabel("密码：");
        user_pw.setFont(new java.awt.Font("楷体", 1, 15));
        user_pw.setForeground(new Color(210,180,140));
        user_pw.setVisible(true);
        JTextField input_pw = new JTextField();
        input_pw.setColumns(20);
        input_pw.setVisible(true);
        
        
        // 登录 按钮, 用于提交登录信息到服务端, 检查用户名和密码是否正确
        JButton buttonLogin = new JButton("登录");
        buttonLogin.setPreferredSize(new Dimension(100,50));
        buttonLogin.setBackground(new Color(255,250,240));
        buttonLogin.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonLogin.setForeground(new Color(210,180,140));
        buttonLogin.setFont(new java.awt.Font("楷体",1,15)); 
        buttonLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// 发送数据包给服务端, 并清空两个输入文本框
        		String name = input_name.getText().trim();;
        		String pw = input_pw.getText().trim();
        		SendPacket send = new SendPacket();
        		send.ask_type = send.LOGIN;
        		send.user_name = input_name.getText().trim();
        		send.user_pw = input_pw.getText().trim();
        		input_name.setText("");
        		input_pw.setText("");
        		if(name.equals("") | pw.equals("")) {
        			JOptionPane.showMessageDialog(null, "输入不能为空:(");
        		}else{
        			buttonLoginAction(socket,send);
        		}
        	}
        });
        
        // 点击跳转到注册页面
        JButton buttonLoginRegister = new JButton("去注册");
        buttonLoginRegister.setPreferredSize(new Dimension(100,50));
        buttonLoginRegister.setBackground(new Color(255,250,240));
        buttonLoginRegister.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonLoginRegister.setForeground(new Color(210,180,140));
        buttonLoginRegister.setFont(new java.awt.Font("楷体",1,15)); 
        buttonLoginRegister.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clientLogin.setVisible(false);
        		clientRegister.setVisible(true);
        		all.remove(clientLogin);
        		all.add(clientRegister);
        	}
        });
        
        // 点击退出程序
        JButton buttonLoginExit = new JButton("退出");
        buttonLoginExit.setPreferredSize(new Dimension(100,50));
        buttonLoginExit.setBackground(new Color(255,250,240));
        buttonLoginExit.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonLoginExit.setForeground(new Color(210,180,140));
        buttonLoginExit.setFont(new java.awt.Font("楷体",1,15)); 
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
        
        // 设置组件布局关系
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
	
	// 初始化注册页面
	public static void ClientRegister(Socket socket, JFrame clientFrame) {
		clientRegister.setBackground(new Color(250,235,215));
        
    	// icon label
    	ImageIcon imageIconRegister = new ImageIcon(".\\img\\3.jpg");
    	imageIconRegister.setImage(imageIconRegister.getImage().getScaledInstance(150, 150,Image.SCALE_DEFAULT ));
        JLabel imageLabelRegister = new JLabel(imageIconRegister);
        imageLabelRegister.setVisible(true);
        
        // hello label
        String RegisterString = "这里是注册！";
        JLabel RegisterLabel = new JLabel(RegisterString);
        RegisterLabel.setForeground(new Color(210,180,140));
        RegisterLabel.setFont(new java.awt.Font("楷体", 1, 15));
        
        // 输入用户名
        JLabel register_name = new JLabel("用户名：");
        register_name.setFont(new java.awt.Font("楷体", 1, 15));
        register_name.setForeground(new Color(210,180,140));
        register_name.setVisible(true);
        JTextField register_input_name = new JTextField();
        register_input_name.setColumns(20);
        register_input_name.setVisible(true);
        
        // 输入密码和确认密码
        JLabel register_pw = new JLabel("密码：");
        register_pw.setFont(new java.awt.Font("楷体", 1, 15));
        register_pw.setForeground(new Color(210,180,140));
        register_pw.setVisible(true);
        JTextField register_pw_input = new JTextField();
        register_pw_input.setColumns(20);
        register_pw_input.setVisible(true);
        
        JLabel register_pw2 = new JLabel("确认密码：");
        register_pw2.setFont(new java.awt.Font("楷体", 1, 15));
        register_pw2.setForeground(new Color(210,180,140));
        register_pw2.setVisible(true);
        JTextField register_pw_input2 = new JTextField();
        register_pw_input2.setColumns(20);
        register_pw_input2.setVisible(true);
        
        // 点击提交注册信息到服务端
        JButton buttonRegister = new JButton("注册");
        buttonRegister.setPreferredSize(new Dimension(100,50));
        buttonRegister.setBackground(new Color(255,250,240));
        buttonRegister.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonRegister.setForeground(new Color(210,180,140));
        buttonRegister.setFont(new java.awt.Font("楷体",1,15)); 
        buttonRegister.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		// 将注册信息整合为数据包
        		String name = register_input_name.getText().trim();
        		String pw1 = register_pw_input.getText().trim();
        		String pw2 = register_pw_input2.getText().trim();
        		System.out.println(name);
        		System.out.println(pw1);
        		System.out.println(pw2);
        		if(pw1.equals("") | pw2.equals("") | name.equals("")) {
        			JOptionPane.showMessageDialog(null, "输入不能为空:(");
        		}
        		else {
        			// 判断密码和确认密码是否相同
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
            			JOptionPane.showMessageDialog(null, "确认密码不一致:(");
            		}
        		}
        	}
        });
        
        // 点击跳转到登录页面
        JButton buttonRegisterLogin = new JButton("去登录");
        buttonRegisterLogin.setPreferredSize(new Dimension(100,50));
        buttonRegisterLogin.setBackground(new Color(255,250,240));
        buttonRegisterLogin.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonRegisterLogin.setForeground(new Color(210,180,140));
        buttonRegisterLogin.setFont(new java.awt.Font("楷体",1,15)); 
        buttonRegisterLogin.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		clientRegister.setVisible(false);
        		clientLogin.setVisible(true);
        		all.remove(clientRegister);
        		all.add(clientLogin);
        	}
        });
        
        // 点击退出程序
        JButton buttonRegisterExit = new JButton("退出");
        buttonRegisterExit.setPreferredSize(new Dimension(100,50));
        buttonRegisterExit.setBackground(new Color(255,250,240));
        buttonRegisterExit.setBorder(BorderFactory.createRaisedBevelBorder());
        buttonRegisterExit.setForeground(new Color(210,180,140));
        buttonRegisterExit.setFont(new java.awt.Font("楷体",1,15)); 
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
        
        // 设置组件的布局关系
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
	
	// 初始化聊天页面
	public static void ClientChat(Socket socket ,JFrame clientFrame) {
		
		clientChat.setBackground(new Color(250,235,215));
		
		// 用于展示聊天记录的部分
		textArea.setEditable(false);
		textArea.setCaretColor(new Color(0,128,0));
		textArea.setSelectionColor(new Color(240,255,240));
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setFont(new java.awt.Font("楷体", 0, 16));
		textArea.setBackground(new Color(250,235,215));
		
		// 聊天部分的滑动
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(textArea);
		scroll.setBorder(null);
		
		// 用于输入对话的对话框
		JTextArea input_text = new JTextArea();
		input_text.setBackground(new Color(255,250,240));
		input_text.setVisible(true);
		input_text.setPreferredSize(new Dimension (820,150));
		input_text.setLineWrap(true);
		input_text.setWrapStyleWord(true);
		input_text.setFont(new java.awt.Font("楷体", 0, 16));
		
		// 点击发送信息
		JButton sendButton = new JButton("发送");
		sendButton.setPreferredSize(new Dimension(100,50));
        sendButton.setBackground(new Color(255,240,245));
        sendButton.setBorder(BorderFactory.createRaisedBevelBorder());
        sendButton.setForeground(new Color(106,90,205));
        sendButton.setFont(new java.awt.Font("楷体",1,15)); 
        sendButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String input = input_text.getText().trim();
        		// 判断使发送内容不能为空
        		if(input.equals("")) {
        			JOptionPane.showMessageDialog(null, "发送内容不能为空:(");
        		}else {
        			// 将当前时间信息一同发送到服务端
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
        
        // 用于清空聊天记录的展示部分
        JButton clearButton = new JButton("清空");
        clearButton.setPreferredSize(new Dimension(100,50));
        clearButton.setBackground(new Color(255,240,245));
        clearButton.setBorder(BorderFactory.createRaisedBevelBorder());
        clearButton.setForeground(new Color(106,90,205));
        clearButton.setFont(new java.awt.Font("楷体",1,15)); 
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
        
        // 退出程序
        JButton exitButton = new JButton("退出");
        exitButton.setPreferredSize(new Dimension(100,50));
        exitButton.setBackground(new Color(255,240,245));
        exitButton.setBorder(BorderFactory.createRaisedBevelBorder());
        exitButton.setForeground(new Color(106,90,205));
        exitButton.setFont(new java.awt.Font("楷体",1,15)); 
        exitButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
    			SendPacket send = new SendPacket();
        		send.ask_type = send.BAIBAI;
        		buttonSendAction(socket,send);
        		ClientThread.exit = true;
        		System.exit(0);
        	}
        });
        
        // 设置组件的布局关系
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
	
	// 提交登录信息的处理函数
    public static void buttonLoginAction(Socket socket,SendPacket send) {
    	try {
    		// 将数据包发送给服务端
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(send);
			System.out.println("send packet finish");
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			// 从服务端获取返回的数据包, 以得到登录是否成功等信息
			SendPacket tmp = (SendPacket) in.readObject();
			// 登录成功, 跳转到聊天页面, 并创建子线程不断获取从服务端发来的信息
			if(tmp.ask_type == tmp.LOGIN_OK) {
				JOptionPane.showMessageDialog(null, "登录成功:D");
				UserName = tmp.user_name;
				clientLogin.setVisible(false);
	    		all.remove(clientLogin);
	    		UserNameLabel.setText(" 当前用户：" + UserName);
	    		UserNameLabel.setBackground(new Color(250,235,215));
	    		UserNameLabel.setFont(new java.awt.Font("楷体", 0, 16));
	    		UserNameLabel.setVisible(true);
	    		clientChat.setVisible(true);
	    		all.add(clientChat);
	    		Thread threadClient = new Thread(new ClientThread(socket,textArea,ChatMessage));
	    		threadClient.start();
			}else if(tmp.ask_type == tmp.LOGIN_PW) {
				// 密码错误, 弹窗提示
				JOptionPane.showMessageDialog(null, "密码错误!");  
			}else if(tmp.ask_type == tmp.LOGIN_GOREGISTER) {
				// 用户未注册, 弹窗提示
				JOptionPane.showMessageDialog(null, "该账号未注册,请先前往注册!");
			}else if(tmp.ask_type == tmp.USER_LOGIN) {
				// 用户已登录, 弹窗提示
				JOptionPane.showMessageDialog(null, "用户已登录");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    // 发送聊天信息到服务端
    public static void buttonSendAction(Socket socket,SendPacket send) {
    	try {
    		// 发送数据包到服务端
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(send);
			System.out.println("send message finish");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    // 提交注册信息到服务端
    public static void buttonRegisterAction(Socket socket,SendPacket send) {
    	try {
    		// 发送数据包到服务端
    		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
			out.writeObject(send);
			System.out.println("send message finish");
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			SendPacket tmp = null;
			try {
				// 获取服务端返回的数据包
				tmp = (SendPacket) in.readObject();
				System.out.println(tmp.user_name);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// 注册成功, 跳转到登录页面
			if(tmp.ask_type == tmp.REGISTER_OK) {
				JOptionPane.showMessageDialog(null, "注册成功:D请登录！");
				clientRegister.setVisible(false);
	    		all.remove(clientRegister);
	    		clientLogin.setVisible(true);
	    		all.add(clientLogin);
			}else if(tmp.ask_type == tmp.REGISTER_USED) {
				// 用户名已被占用, 弹窗提示
				JOptionPane.showMessageDialog(null, "用户名已被使用:(");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
