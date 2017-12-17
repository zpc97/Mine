package 数据库大作业;

import javafx.application.Application;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.shape.Rectangle;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

//自定义按钮类：继承Button类
class Bu extends Button {
	public DropShadow shadow = new DropShadow();

	public Bu(String a) {
		super(a); // 调用Button构造

		// 设置按钮阴影
		addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setEffect(shadow);
			}
		});
		addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setEffect(null);
			}
		});
	}
	
	public Bu(String a, Node b){
		super(a, b); // 调用Button构造

		// 设置按钮阴影
		addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setEffect(shadow);
			}
		});
		addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				setEffect(null);
			}
		});
	}
}

// 主类
public class QQ extends Application {
	public static Stage signstage = new Stage(); // 登录舞台
	public static Stage registerstage = new Stage(); // 注册舞台
	public static Stage addstage = new Stage();// 添加好友舞台
	public static ScrollPane mainPane = new ScrollPane(); // 主界面
	public static Pane signPane = new Pane(); // 登录界面
	public static Pane registerPane = new Pane(); // 注册界面
	public static Pane chatPane = new Pane();// 聊天界面
	public static Pane addpane = new Pane();// 添加好友界面
	public static String chat = new String();// 聊天对象名称
	public static Font signfont = new Font("Arial", 20); // 字体
	public static Label Prompt = new Label("\t\t"); // Prompt:注释
	public static Pane MainPane;// 声明主界面中变量
	public static int countFriend = 1;// 监控好友数量
	public static StackPane[] stkpane = new StackPane[101];// 好友STKPane
	public static int[] mesage = new int[101];

	public static int CountFriend() {
		int i;
		for (i = 1; i < 101; i++) {
			if (JDBC.friend[i] == null)
				break;
		}
		return i;
	}

	public static void showFriendInfo(String friendname) {

	}

	public static void deleteFriend(int i) {
		MainPane.getChildren().remove(i + 5);
	}

	public static void refeshFriend(JDBC sql, String username) {
		sql.pollingfriend(username);
		if (countFriend < CountFriend()) {
			int i = countFriend;
			for (; JDBC.friend[i] != null; i++) {
				String friendname = JDBC.friend[i];
				Label ID = new Label();
				Bu Info = new Bu("好友信息");
				Bu Chat = new Bu("发送消息");
				Bu Dele = new Bu("删除好友");
				Label friendMesage = new Label(String.valueOf(mesage[i]));
				sql.receiveMessage(username, friendname, friendMesage);
				Pane pane = new Pane(ID, Info, Chat, Dele, friendMesage);
				stkpane[i] = new StackPane(new Rectangle(375, 50, Color.DARKGRAY), pane);
				MainPane.getChildren().add(stkpane[i]);
				stkpane[i].setPrefHeight(50);
				stkpane[i].setPrefWidth(330);
				stkpane[i].setLayoutX(0);
				stkpane[i].setLayoutY(i * 52 + 50);
				ID.setLayoutX(10);
				ID.setLayoutY(15);
				ID.setText(JDBC.friend[i]);
				Info.setLayoutX(80);
				Info.setLayoutY(5);
				Info.setPrefSize(80, 40);
				Info.setOnAction(e2 -> {
					showFriendInfo(friendname);
				});
				Chat.setLayoutX(160);
				Chat.setLayoutY(5);
				Chat.setPrefSize(80, 40);
				int a = mesage[i];
				int b = i;
				Chat.setOnAction(e2 -> {
					mesage[b] = 0;
					friendMesage.setText("0");
					ChatPane chatPane = new ChatPane(username, friendname, sql);
					chatPane.chatStage.setResizable(false);
					chatPane.chatStage.show();
					if (a != 0) {
						chatPane.showMessage(sql.readMessage(username), friendname);
					}
				});
				Dele.setLayoutX(240);
				Dele.setLayoutY(5);
				Dele.setPrefSize(80, 40);
				final int j = i;
				Dele.setOnAction(e2 -> {
					deleteFriend(j);
					countFriend--;
					try {
						sql.deletefriend(username, friendname);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				});
				friendMesage.setLayoutX(320);
				friendMesage.setLayoutY(5);
				friendMesage.setPrefSize(50, 40);
				friendMesage.setAlignment(Pos.CENTER);
				countFriend++;
			}
		}
	}

	public static void messageNumber(String friendname, Label friendMessage) {
		int i = 1;
		while (true) {
			if (JDBC.friend[i] == null) {
				break;
			} else if (friendname.equals(JDBC.friend[i])) {
				mesage[i]++;
				friendMessage.setText(Integer.toString(mesage[i]));
				break;
			} else {
				i++;
			}
		}
	}

	// START方法↓↓↓↓↓↓↓

	@Override
	public void start(Stage primarystage) throws IOException, SQLException {

		JDBC sql = new JDBC();
		for (int i = 1; i < 101; i++)
			mesage[i] = 0;

		// 禁用窗口缩放

		primarystage.setResizable(false);
		signstage.setResizable(false);
		registerstage.setResizable(false);

		// 登录界面↓↓↓↓↓↓↓

		// 加载图片
		Image loadimg = new Image(getClass().getResourceAsStream("背景.jpg")); // 加载图片
		ImageView LoadImg = new ImageView(loadimg);
		TextField username = new TextField(); // 用户名文本框
		username.setPromptText("请输入登录名"); // 设置注释文字
		PasswordField password = new PasswordField(); // 密码框
		password.setPromptText("请输入密码"); // 设置注释文字

		// 设置密码框动作
		password.setOnAction(e -> {
			int result = sql.load(username.getText(), password.getText());
			if (username.getText().isEmpty()) {
				Prompt.setText("用户名不能为空！");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (password.getText().isEmpty()) {
				Prompt.setText("密码不能为空！");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (result == -3) {
				Prompt.setText("连接数据库失败！");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == -2) {
				Prompt.setText("该用户不存在！");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == -1) {
				Prompt.setText("密码错误!");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == 1) {
				Prompt.setText("密码正确");
				Prompt.setTextFill(Color.rgb(21, 117, 84));
				signstage.close();
				refeshFriend(sql, username.getText());
				primarystage.show();
			}
			password.setText(""); // 清空密码框
		});

		// 创建按钮

		Bu load = new Bu("  登录  ");
		Bu register = new Bu("  注册  ");

		// 设置按钮动作
		load.setOnAction(e -> {
			int result = sql.load(username.getText(), password.getText());
			if (username.getText().isEmpty()) {
				Prompt.setText("用户名不能为空！");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (password.getText().isEmpty()) {
				Prompt.setText("密码不能为空！");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (result == -3) {
				Prompt.setText("连接数据库失败！");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == -2) {
				Prompt.setText("该用户不存在！");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == -1) {
				Prompt.setText("密码错误!");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == 1) {
				Prompt.setText("密码正确");
				Prompt.setTextFill(Color.rgb(21, 117, 84));
				signstage.close();
				refeshFriend(sql, username.getText());
				primarystage.show();
			}
			password.setText(""); // 清空密码框
		});

		// 设置按钮动作
		register.setOnAction(e -> {
			registerstage.show(); // 打开注册界面
		});

		// 摆放控件
		FlowPane Bu = new FlowPane();
		Bu.setHgap(50);
		Bu.getChildren().add(load);
		Bu.getChildren().add(register);
		signPane.getChildren().add(new Rectangle(500, 400, Color.WHITE));
		signPane.getChildren().add(LoadImg);
		LoadImg.setLayoutX(0);
		LoadImg.setLayoutY(144);
		signPane.getChildren().add(username);
		username.setLayoutX(140);
		username.setLayoutY(160);
		signPane.getChildren().add(password);
		password.setLayoutX(140);
		password.setLayoutY(220);
		signPane.getChildren().add(Prompt);
		Prompt.setLayoutX(190);
		Prompt.setLayoutY(250);
		signPane.getChildren().add(Bu);
		Bu.setLayoutX(148);
		Bu.setLayoutY(280);

		// 登录界面结束

		// 注册界面↓↓↓↓↓↓↓
		Label registerPrompt = new Label("\t\t"); // 注释
		TextField registername = new TextField(); // 用户名文本框
		registername.setPromptText("请输入登录名"); // 设置注释文本
		PasswordField registerpassword = new PasswordField(); // 密码框
		registerpassword.setPromptText("请输入密码"); // 设置注释文本
		// 设置密码框动作
		registerpassword.setOnAction(e -> {
			int result = sql.register(registername.getText(), registerpassword.getText());
			if (registername.getText().isEmpty()) {
				registerPrompt.setText("用户名不能为空！");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (registerpassword.getText().isEmpty()) {
				registerPrompt.setText("密码不能为空！");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (result == -3) {
				registerPrompt.setText("连接数据库失败！");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == -2 || result == -1) {
				registerPrompt.setText("注册失败！");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == 1) {
				registerPrompt.setText("注册成功！");
				registerPrompt.setTextFill(Color.rgb(21, 117, 84));
			} else if (result == 2) {
				registerPrompt.setText("用户名被占用！");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
			}
			registerpassword.setText(""); // 清空密码框
		});

		// 创建按钮
		Bu confirm = new Bu("  确认  ");
		Bu cancel = new Bu("  取消  ");

		// 设置按钮动作
		confirm.setOnAction(e -> {
			int result = sql.register(registername.getText(), registerpassword.getText());
			if (registername.getText().isEmpty()) {
				registerPrompt.setText("用户名不能为空！");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (registerpassword.getText().isEmpty()) {
				registerPrompt.setText("密码不能为空！");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (result == -3) {
				registerPrompt.setText("连接数据库失败！");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == 1) {
				registerPrompt.setText("注册成功！");
				registerPrompt.setTextFill(Color.rgb(21, 117, 84));
			} else if (result == 2) {
				registerPrompt.setText("用户名被占用！");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
			}
			registerpassword.setText(""); // 清空密码框
		});

		// 设置按钮动作
		cancel.setOnAction(e -> {
			registerstage.close(); // 关闭注册界面
		});

		// 摆放控件位置
		FlowPane Bur = new FlowPane();
		Bur.setHgap(50);
		Bur.getChildren().add(confirm);
		Bur.getChildren().add(cancel);
		Image registerimg = new Image(getClass().getResourceAsStream("背景.jpg"));
		ImageView RegisterImg = new ImageView(registerimg);
		registerPane.getChildren().add(new Rectangle(500, 210, Color.WHITE));
		registerPane.getChildren().add(RegisterImg);
		RegisterImg.setLayoutX(0);
		RegisterImg.setLayoutY(-56);
		registerPane.getChildren().add(registername);
		registername.setLayoutX(150);
		registername.setLayoutY(30);
		registerPane.getChildren().add(registerpassword);
		registerpassword.setLayoutX(150);
		registerpassword.setLayoutY(90);
		registerPane.getChildren().add(registerPrompt);
		registerPrompt.setLayoutX(188);
		registerPrompt.setLayoutY(120);
		registerPane.getChildren().add(Bur);
		Bur.setLayoutX(148);
		Bur.setLayoutY(150);

		// 注册界面结束

		// 主界面↓↓↓↓↓↓↓
		mainPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		mainPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		// 加载图片
		Image searchimg = new Image(getClass().getResourceAsStream("用户.jpg"));
		ImageView SearchImg = new ImageView(searchimg);
		Image addfriendimg = new Image(getClass().getResourceAsStream("添加好友.jpg"));
		ImageView AddFriendImg = new ImageView(addfriendimg);

		// 创建按钮
		Bu searchbu = new Bu("个人资料", SearchImg);
		searchbu.setOnAction(e->{
			
		});
		Bu addbu = new Bu("添加好友", AddFriendImg);
		addbu.setOnAction(e -> {
			addstage.show();
		});
		Label refeshBuLabel = new Label("刷新");
		refeshBuLabel.setFont(new Font("华文行楷", 20));
		Bu refresh = new Bu("", refeshBuLabel);
		refresh.setPrefWidth(60);
		refresh.setOnAction(e -> {
			sql.pollingfriend(username.getText());
			if (countFriend < CountFriend()) {
				int i = countFriend;
				for (; JDBC.friend[i] != null; i++) {
					String friendname = JDBC.friend[i];
					Label ID = new Label();
					Bu Info = new Bu("好友信息");
					Bu Chat = new Bu("发送消息");
					Bu Dele = new Bu("删除好友");
					Label friendMesage = new Label(String.valueOf(mesage[i]));
					sql.receiveMessage(username.getText(), friendname, friendMesage);
					Pane pane = new Pane(ID, Info, Chat, Dele, friendMesage);
					stkpane[i] = new StackPane(new Rectangle(375, 50, Color.DARKGRAY), pane);
					MainPane.getChildren().add(stkpane[i]);
					stkpane[i].setPrefHeight(50);
					stkpane[i].setPrefWidth(330);
					stkpane[i].setLayoutX(0);
					stkpane[i].setLayoutY(i * 52 + 50);
					ID.setLayoutX(10);
					ID.setLayoutY(15);
					ID.setText(JDBC.friend[i]);
					Info.setLayoutX(80);
					Info.setLayoutY(5);
					Info.setPrefSize(80, 40);
					Info.setOnAction(e2 -> {
						showFriendInfo(friendname);
					});
					Chat.setLayoutX(160);
					Chat.setLayoutY(5);
					Chat.setPrefSize(80, 40);
					int a = mesage[i];
					int b = i;
					Chat.setOnAction(e2 -> {
						mesage[b] = 0;
						friendMesage.setText("0");
						ChatPane chatPane = new ChatPane(username.getText(), friendname, sql);
						chatPane.chatStage.setResizable(false);
						chatPane.chatStage.show();
						if (a != 0) {
							chatPane.showMessage(sql.readMessage(username.getText()), friendname);
						}
					});
					Dele.setLayoutX(240);
					Dele.setLayoutY(5);
					Dele.setPrefSize(80, 40);
					final int j = i;
					Dele.setOnAction(e2 -> {
						deleteFriend(j);
						countFriend--;
						try {
							sql.deletefriend(username.getText(), friendname);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					});
					friendMesage.setLayoutX(320);
					friendMesage.setLayoutY(5);
					friendMesage.setPrefSize(50, 40);
					friendMesage.setAlignment(Pos.CENTER);
					countFriend++;
				}
			} else if (countFriend > CountFriend()) {
				System.out.print("Dle" + countFriend + " ");
				deleteFriend(countFriend - 1);
				countFriend--;
			}
		});

		// 创建文本域

		// 创建标签
		Label friendnameLabel = new Label("好友昵称");
		friendnameLabel.setFont(new Font("华文行楷", 20));
		Label mesageNumber = new Label("消息");
		mesageNumber.setFont(new Font("华文行楷", 20));
		Label friendLabel = new Label("好友列表");
		friendLabel.setFont(new Font("华文琥珀", 20));
		friendLabel.setPrefSize(375, 20);
		friendLabel.setAlignment(Pos.CENTER);

		// 摆放控件
		MainPane = new Pane();
		MainPane.setMinHeight(900);
		Image mianimg = new Image(getClass().getResourceAsStream("主背景.jpg"));
		BackgroundSize backgroundSize = new BackgroundSize(300, 405, false, false, false, false);
		BackgroundImage backgroundImage = new BackgroundImage(mianimg, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT, backgroundSize);
		MainPane.setBackground(new Background(backgroundImage));
		MainPane.getChildren().add(searchbu);
		MainPane.getChildren().add(addbu);
		MainPane.getChildren().add(friendLabel);
		MainPane.getChildren().add(friendnameLabel);
		MainPane.getChildren().add(mesageNumber);
		MainPane.getChildren().add(refresh);
		mainPane.setContent(MainPane);
		searchbu.setLayoutX(0);
		searchbu.setLayoutY(0);
		addbu.setLayoutX(260);
		addbu.setLayoutY(0);
		friendLabel.setLayoutX(0);
		friendLabel.setLayoutY(35);
		friendnameLabel.setLayoutX(0);
		friendnameLabel.setLayoutY(65);
		mesageNumber.setLayoutX(325);
		mesageNumber.setLayoutY(65);
		refresh.setLayoutX((375 - refresh.getPrefWidth()) / 2);
		refresh.setLayoutY(60);

		// 主界面结束

		// 添加好友界面↓↓↓↓↓↓↓
		Label addPrompt = new Label("\t\t"); // 注释
		TextField addname = new TextField(); // 好友用户名文本框
		addname.setPromptText("请输入好友ID"); // 设置注释文本
		addname.setOnAction(e -> {
			if (!username.getText().equals(addname.getText())) {
				int result = sql.addfriend(username.getText(), addname.getText());
				if (addname.getText().isEmpty()) {
					addPrompt.setText("ID不能为空！");
					addPrompt.setTextFill(Color.rgb(210, 39, 30));
				} else if (result == 1) {
					addPrompt.setText("好友添加成功!");
					addPrompt.setTextFill(Color.rgb(21, 117, 84));
				} else if (result == 2) {
					addPrompt.setText("你们已经是好友！");
					addPrompt.setTextFill(Color.rgb(210, 39, 30));
				} else if (result == -2) {
					addPrompt.setText("该用户名不存在！");
					addPrompt.setTextFill(Color.rgb(210, 39, 30));
				}
			} else {
				addPrompt.setText("不能添加自己！");
				addPrompt.setTextFill(Color.rgb(210, 39, 30));
			}
			addname.clear();
		});
		Image addimg = new Image(getClass().getResourceAsStream("背景.jpg")); // 加载图片
		ImageView AddImg = new ImageView(addimg);

		// 设置按钮
		Bu addconfirm = new Bu("  提交  ");
		Bu addcancel = new Bu("  关闭  ");
		addconfirm.setOnAction(e -> {
			if (!username.getText().equals(addname.getText())) {
				int result = sql.addfriend(username.getText(), addname.getText());
				if (addname.getText().isEmpty()) {
					addPrompt.setText("ID不能为空！");
					addPrompt.setTextFill(Color.rgb(210, 39, 30));
				} else if (result == 1) {
					addPrompt.setText("好友添加成功!");
					addPrompt.setTextFill(Color.rgb(21, 117, 84));
				} else if (result == 2) {
					addPrompt.setText("你们已经是好友！");
					addPrompt.setTextFill(Color.rgb(210, 39, 30));
				} else if (result == -2) {
					addPrompt.setText("该用户名不存在！");
					addPrompt.setTextFill(Color.rgb(210, 39, 30));
				}
			} else {
				addPrompt.setText("不能添加自己！");
				addPrompt.setTextFill(Color.rgb(210, 39, 30));
			}
			addname.clear();
		});
		addcancel.setOnAction(e -> {
			addPrompt.setText("");
			addstage.close();
		});

		// 摆放控件
		addpane.getChildren().add(new Rectangle(500, 210, Color.WHITE));
		addpane.getChildren().add(AddImg);
		AddImg.setLayoutX(0);
		AddImg.setLayoutY(-56);
		addpane.getChildren().add(addname);
		addname.setLayoutX(150);
		addname.setLayoutY(60);
		addpane.getChildren().add(addPrompt);
		addPrompt.setLayoutX(200);
		addPrompt.setLayoutY(90);
		addpane.getChildren().add(addconfirm);
		addconfirm.setLayoutX(150);
		addconfirm.setLayoutY(150);
		addpane.getChildren().add(addcancel);
		addcancel.setLayoutX(280);
		addcancel.setLayoutY(150);

		// 添加好友界面结束

		// 舞台↓↓↓↓↓↓↓
		Scene mainscene = new Scene(mainPane, 375, 700);
		Scene signscene = new Scene(signPane, 488, 398);
		Scene registerscene = new Scene(registerPane, 488, 198);
		Scene addscene = new Scene(addpane, 488, 198);
		primarystage.setTitle("简易QQ");
		primarystage.setScene(mainscene);
		signstage.setTitle("欢迎使用  请登录");
		signstage.setScene(signscene);
		registerstage.setTitle("注册账号");
		registerstage.setScene(registerscene);
		addstage.setTitle("添加好友");
		addstage.setScene(addscene);
		signstage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
