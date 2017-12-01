package william.swing;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import william.core.entity.Request;
import william.main.Client;
import william.module.ModuleId;
import william.module.chat.ChatCmd;
import william.module.chat.proto.ChatModule;
import william.module.chat.proto.ChatModule.PrivateChatRequest;
import william.module.chat.proto.ChatModule.PublicChatRequest;
import william.module.player.PlayerCmd;
import william.module.player.proto.PlayerModule;
import william.module.player.proto.PlayerModule.LoginRequest;
import william.module.player.proto.PlayerModule.PlayerResponse;
import william.module.player.proto.PlayerModule.RegisterRequest;
import william.swing.constant.ButtonCommand;

/**
 * 
 * <p>Description:Swing客户端</p>
 * @author ZhangShenao
 * @date 2017年12月1日
 */
@Component
public class SwingClient extends JFrame implements ActionListener {
	
	@Autowired
	private Client client;	//Netty客户端实例
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2572235358190956651L;
	
	/**
	 * 玩家信息
	 */
	private PlayerResponse playerResponse;
	
	/**
	 * 用户名
	 */
	private JTextField playerName;
	
	/**
	 * 密码
	 */
	private JTextField passward;
	
	/**
	 * 登录按钮
	 */
	private JButton loginButton;
	
	
	/**
	 * 注册按钮
	 */
	private JButton register;

	/**
	 * 聊天内容
	 */
	private JTextArea chatContext;
	
	/**
	 * 发送内容
	 */
	private JTextField message;
	
	/**
	 * 目标用户
	 */
	private JTextField targetPlayer;
	
	/**
	 * 发送按钮
	 */
	private JButton sendButton;

	/**
	 * 操作提示
	 */
	private JLabel tips;


	public SwingClient() {
		
		getContentPane().setLayout(null);
		
		//登录部分
		JLabel lblIp = new JLabel("角色名");
		lblIp.setFont(new Font("宋体", Font.PLAIN, 12));
		lblIp.setBounds(76, 40, 54, 15);
		getContentPane().add(lblIp);
		
		playerName = new JTextField();
		playerName.setBounds(139, 37, 154, 21);
		getContentPane().add(playerName);
		playerName.setColumns(10);
		
		JLabel label = new JLabel("密  码");
		label.setFont(new Font("宋体", Font.PLAIN, 12));
		label.setBounds(76, 71, 54, 15);
		getContentPane().add(label);
		
		passward = new JTextField();
		passward.setColumns(10);
		passward.setBounds(139, 68, 154, 21);
		getContentPane().add(passward);
		
		//登录
		loginButton = new JButton("登录");
		loginButton.setFont(new Font("宋体", Font.PLAIN, 12));
		loginButton.setActionCommand(ButtonCommand.LOGIN);
		loginButton.addActionListener(this);
		loginButton.setBounds(315, 37, 93, 23);
		getContentPane().add(loginButton);
		
		//注册
		register = new JButton("注册");
		register.setFont(new Font("宋体", Font.PLAIN, 12));
		register.setActionCommand(ButtonCommand.REGISTER);
		register.addActionListener(this);
		register.setBounds(315, 67, 93, 23);
		getContentPane().add(register);
		
		//聊天内容框
		chatContext = new JTextArea();
		chatContext.setLineWrap(true);
		
		JScrollPane scrollBar = new JScrollPane(chatContext);
		scrollBar.setBounds(76, 96, 93, 403);
		scrollBar.setSize(336, 300);
		getContentPane().add(scrollBar);

		
		//发送部分
		JLabel label_7 = new JLabel("消息");
		label_7.setFont(new Font("宋体", Font.PLAIN, 12));
		label_7.setBounds(76, 411, 54, 15);
		getContentPane().add(label_7);
		
		message = new JTextField();
		message.setBounds(139, 408, 222, 21);
		getContentPane().add(message);
		message.setColumns(10);
		
		JLabel lblid = new JLabel("角色");
		lblid.setFont(new Font("宋体", Font.PLAIN, 12));
		lblid.setBounds(76, 436, 43, 24);
		getContentPane().add(lblid);

		targetPlayer = new JTextField();
		targetPlayer.setBounds(139, 438, 133, 21);
		getContentPane().add(targetPlayer);
		targetPlayer.setColumns(10);
		
		sendButton = new JButton("发送");
		sendButton.setFont(new Font("宋体", Font.PLAIN, 12));
		sendButton.setBounds(382, 407, 67, 23);
		sendButton.setActionCommand(ButtonCommand.SEND);
		sendButton.addActionListener(this);
		getContentPane().add(sendButton);
		
		//错误提示区域
		tips = new JLabel();
		tips.setForeground(Color.red);
		tips.setFont(new Font("宋体", Font.PLAIN, 14));
		tips.setBounds(76, 488, 200, 15);
		getContentPane().add(tips);
		

		int weigh = 500;
		int heigh = 600;
		int w = (Toolkit.getDefaultToolkit().getScreenSize().width - weigh) / 2;
		int h = (Toolkit.getDefaultToolkit().getScreenSize().height - heigh) / 2;
		this.setLocation(w, h);
		this.setTitle("聊天工具");
		this.setSize(weigh, heigh);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(true);
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		switch (event.getActionCommand()) {
		//登录
		case ButtonCommand.LOGIN:
			try {
				LoginRequest loginRequest = PlayerModule.LoginRequest.newBuilder()
						.setPlayerName(playerName.getText())
						.setPassward(passward.getText())
						.build();
				//构建请求
				Request request = Request.valueOf(ModuleId.PLAYER, PlayerCmd.LOGIN, loginRequest.toByteArray());
				client.sendRequest(request);
			} catch (Exception e) {
				tips.setText("无法连接服务器");
			}
			break;
		//注册
		case ButtonCommand.REGISTER:
			try {
				RegisterRequest registerRequest = PlayerModule.RegisterRequest.newBuilder()
						.setPlayerName(playerName.getText())
						.setPassward(passward.getText())
						.build();
				
				//构建请求
				Request request = Request.valueOf(ModuleId.PLAYER, PlayerCmd.REGISTER_AND_LOGIN, registerRequest.toByteArray());
				client.sendRequest(request);
			} catch (Exception e) {
				tips.setText("无法连接服务器");
			}
			break;
		//发送消息
		case ButtonCommand.SEND:
			try {
				//判断是广播还是私聊
				if(StringUtils.isEmpty(targetPlayer.getText()) && !StringUtils.isEmpty(message.getText())){
					PublicChatRequest publicChatRequest = ChatModule.PublicChatRequest.newBuilder()
							.setContext(message.getText())
							.build();
					
					//构建请求
					Request request = Request.valueOf(ModuleId.CHAT, ChatCmd.PUBLIC_CHAT, publicChatRequest.toByteArray());
					client.sendRequest(request);
				}else{
					if(StringUtils.isEmpty(message.getText())){
						tips.setText("发送内容不能为空");
						return;
					}
					
					long palyerId = 0; 
					try {
						palyerId = Long.parseLong(targetPlayer.getText());
					} catch (NumberFormatException e) {
						tips.setText("玩家id必须为数字");
						return;
					}
					PrivateChatRequest privateChatRequest = ChatModule.PrivateChatRequest.newBuilder()
							.setContext(message.getText())
							.setTargetPlayerId(palyerId)
							.build();
					
					//构建请求
					Request request = Request.valueOf(ModuleId.CHAT, ChatCmd.PRIVATE_CHAT, privateChatRequest.toByteArray());
					client.sendRequest(request);
				}
			} catch (Exception e) {
				tips.setText("无法连接服务器");
			}
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING){
			client.shutdown();
		}
		super.processWindowEvent(e);
	}

	public JTextArea getChatContext() {
		return chatContext;
	}

	public JLabel getTips() {
		return tips;
	}

	public void setPlayerResponse(PlayerResponse playerResponse) {
		this.playerResponse = playerResponse;
	}

	public PlayerResponse getPlayerResponse() {
		return playerResponse;
	}
}
