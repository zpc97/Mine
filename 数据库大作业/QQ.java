package ���ݿ����ҵ;

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

//�Զ��尴ť�ࣺ�̳�Button��
class Bu extends Button {
	public DropShadow shadow = new DropShadow();

	public Bu(String a) {
		super(a); // ����Button����

		// ���ð�ť��Ӱ
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
		super(a, b); // ����Button����

		// ���ð�ť��Ӱ
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

// ����
public class QQ extends Application {
	public static Stage signstage = new Stage(); // ��¼��̨
	public static Stage registerstage = new Stage(); // ע����̨
	public static Stage addstage = new Stage();// ��Ӻ�����̨
	public static ScrollPane mainPane = new ScrollPane(); // ������
	public static Pane signPane = new Pane(); // ��¼����
	public static Pane registerPane = new Pane(); // ע�����
	public static Pane chatPane = new Pane();// �������
	public static Pane addpane = new Pane();// ��Ӻ��ѽ���
	public static String chat = new String();// �����������
	public static Font signfont = new Font("Arial", 20); // ����
	public static Label Prompt = new Label("\t\t"); // Prompt:ע��
	public static Pane MainPane;// �����������б���
	public static int countFriend = 1;// ��غ�������
	public static StackPane[] stkpane = new StackPane[101];// ����STKPane
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
				Bu Info = new Bu("������Ϣ");
				Bu Chat = new Bu("������Ϣ");
				Bu Dele = new Bu("ɾ������");
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

	// START������������������

	@Override
	public void start(Stage primarystage) throws IOException, SQLException {

		JDBC sql = new JDBC();
		for (int i = 1; i < 101; i++)
			mesage[i] = 0;

		// ���ô�������

		primarystage.setResizable(false);
		signstage.setResizable(false);
		registerstage.setResizable(false);

		// ��¼�����������������

		// ����ͼƬ
		Image loadimg = new Image(getClass().getResourceAsStream("����.jpg")); // ����ͼƬ
		ImageView LoadImg = new ImageView(loadimg);
		TextField username = new TextField(); // �û����ı���
		username.setPromptText("�������¼��"); // ����ע������
		PasswordField password = new PasswordField(); // �����
		password.setPromptText("����������"); // ����ע������

		// �����������
		password.setOnAction(e -> {
			int result = sql.load(username.getText(), password.getText());
			if (username.getText().isEmpty()) {
				Prompt.setText("�û�������Ϊ�գ�");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (password.getText().isEmpty()) {
				Prompt.setText("���벻��Ϊ�գ�");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (result == -3) {
				Prompt.setText("�������ݿ�ʧ�ܣ�");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == -2) {
				Prompt.setText("���û������ڣ�");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == -1) {
				Prompt.setText("�������!");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == 1) {
				Prompt.setText("������ȷ");
				Prompt.setTextFill(Color.rgb(21, 117, 84));
				signstage.close();
				refeshFriend(sql, username.getText());
				primarystage.show();
			}
			password.setText(""); // ��������
		});

		// ������ť

		Bu load = new Bu("  ��¼  ");
		Bu register = new Bu("  ע��  ");

		// ���ð�ť����
		load.setOnAction(e -> {
			int result = sql.load(username.getText(), password.getText());
			if (username.getText().isEmpty()) {
				Prompt.setText("�û�������Ϊ�գ�");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (password.getText().isEmpty()) {
				Prompt.setText("���벻��Ϊ�գ�");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (result == -3) {
				Prompt.setText("�������ݿ�ʧ�ܣ�");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == -2) {
				Prompt.setText("���û������ڣ�");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == -1) {
				Prompt.setText("�������!");
				Prompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == 1) {
				Prompt.setText("������ȷ");
				Prompt.setTextFill(Color.rgb(21, 117, 84));
				signstage.close();
				refeshFriend(sql, username.getText());
				primarystage.show();
			}
			password.setText(""); // ��������
		});

		// ���ð�ť����
		register.setOnAction(e -> {
			registerstage.show(); // ��ע�����
		});

		// �ڷſؼ�
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

		// ��¼�������

		// ע������������������
		Label registerPrompt = new Label("\t\t"); // ע��
		TextField registername = new TextField(); // �û����ı���
		registername.setPromptText("�������¼��"); // ����ע���ı�
		PasswordField registerpassword = new PasswordField(); // �����
		registerpassword.setPromptText("����������"); // ����ע���ı�
		// �����������
		registerpassword.setOnAction(e -> {
			int result = sql.register(registername.getText(), registerpassword.getText());
			if (registername.getText().isEmpty()) {
				registerPrompt.setText("�û�������Ϊ�գ�");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (registerpassword.getText().isEmpty()) {
				registerPrompt.setText("���벻��Ϊ�գ�");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (result == -3) {
				registerPrompt.setText("�������ݿ�ʧ�ܣ�");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == -2 || result == -1) {
				registerPrompt.setText("ע��ʧ�ܣ�");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == 1) {
				registerPrompt.setText("ע��ɹ���");
				registerPrompt.setTextFill(Color.rgb(21, 117, 84));
			} else if (result == 2) {
				registerPrompt.setText("�û�����ռ�ã�");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
			}
			registerpassword.setText(""); // ��������
		});

		// ������ť
		Bu confirm = new Bu("  ȷ��  ");
		Bu cancel = new Bu("  ȡ��  ");

		// ���ð�ť����
		confirm.setOnAction(e -> {
			int result = sql.register(registername.getText(), registerpassword.getText());
			if (registername.getText().isEmpty()) {
				registerPrompt.setText("�û�������Ϊ�գ�");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (registerpassword.getText().isEmpty()) {
				registerPrompt.setText("���벻��Ϊ�գ�");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
				return;
			} else if (result == -3) {
				registerPrompt.setText("�������ݿ�ʧ�ܣ�");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
			} else if (result == 1) {
				registerPrompt.setText("ע��ɹ���");
				registerPrompt.setTextFill(Color.rgb(21, 117, 84));
			} else if (result == 2) {
				registerPrompt.setText("�û�����ռ�ã�");
				registerPrompt.setTextFill(Color.rgb(210, 39, 30));
			}
			registerpassword.setText(""); // ��������
		});

		// ���ð�ť����
		cancel.setOnAction(e -> {
			registerstage.close(); // �ر�ע�����
		});

		// �ڷſؼ�λ��
		FlowPane Bur = new FlowPane();
		Bur.setHgap(50);
		Bur.getChildren().add(confirm);
		Bur.getChildren().add(cancel);
		Image registerimg = new Image(getClass().getResourceAsStream("����.jpg"));
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

		// ע��������

		// �������������������
		mainPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		mainPane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		// ����ͼƬ
		Image searchimg = new Image(getClass().getResourceAsStream("�û�.jpg"));
		ImageView SearchImg = new ImageView(searchimg);
		Image addfriendimg = new Image(getClass().getResourceAsStream("��Ӻ���.jpg"));
		ImageView AddFriendImg = new ImageView(addfriendimg);

		// ������ť
		Bu searchbu = new Bu("��������", SearchImg);
		searchbu.setOnAction(e->{
			
		});
		Bu addbu = new Bu("��Ӻ���", AddFriendImg);
		addbu.setOnAction(e -> {
			addstage.show();
		});
		Label refeshBuLabel = new Label("ˢ��");
		refeshBuLabel.setFont(new Font("�����п�", 20));
		Bu refresh = new Bu("", refeshBuLabel);
		refresh.setPrefWidth(60);
		refresh.setOnAction(e -> {
			sql.pollingfriend(username.getText());
			if (countFriend < CountFriend()) {
				int i = countFriend;
				for (; JDBC.friend[i] != null; i++) {
					String friendname = JDBC.friend[i];
					Label ID = new Label();
					Bu Info = new Bu("������Ϣ");
					Bu Chat = new Bu("������Ϣ");
					Bu Dele = new Bu("ɾ������");
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

		// �����ı���

		// ������ǩ
		Label friendnameLabel = new Label("�����ǳ�");
		friendnameLabel.setFont(new Font("�����п�", 20));
		Label mesageNumber = new Label("��Ϣ");
		mesageNumber.setFont(new Font("�����п�", 20));
		Label friendLabel = new Label("�����б�");
		friendLabel.setFont(new Font("��������", 20));
		friendLabel.setPrefSize(375, 20);
		friendLabel.setAlignment(Pos.CENTER);

		// �ڷſؼ�
		MainPane = new Pane();
		MainPane.setMinHeight(900);
		Image mianimg = new Image(getClass().getResourceAsStream("������.jpg"));
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

		// ���������

		// ��Ӻ��ѽ����������������
		Label addPrompt = new Label("\t\t"); // ע��
		TextField addname = new TextField(); // �����û����ı���
		addname.setPromptText("���������ID"); // ����ע���ı�
		addname.setOnAction(e -> {
			if (!username.getText().equals(addname.getText())) {
				int result = sql.addfriend(username.getText(), addname.getText());
				if (addname.getText().isEmpty()) {
					addPrompt.setText("ID����Ϊ�գ�");
					addPrompt.setTextFill(Color.rgb(210, 39, 30));
				} else if (result == 1) {
					addPrompt.setText("������ӳɹ�!");
					addPrompt.setTextFill(Color.rgb(21, 117, 84));
				} else if (result == 2) {
					addPrompt.setText("�����Ѿ��Ǻ��ѣ�");
					addPrompt.setTextFill(Color.rgb(210, 39, 30));
				} else if (result == -2) {
					addPrompt.setText("���û��������ڣ�");
					addPrompt.setTextFill(Color.rgb(210, 39, 30));
				}
			} else {
				addPrompt.setText("��������Լ���");
				addPrompt.setTextFill(Color.rgb(210, 39, 30));
			}
			addname.clear();
		});
		Image addimg = new Image(getClass().getResourceAsStream("����.jpg")); // ����ͼƬ
		ImageView AddImg = new ImageView(addimg);

		// ���ð�ť
		Bu addconfirm = new Bu("  �ύ  ");
		Bu addcancel = new Bu("  �ر�  ");
		addconfirm.setOnAction(e -> {
			if (!username.getText().equals(addname.getText())) {
				int result = sql.addfriend(username.getText(), addname.getText());
				if (addname.getText().isEmpty()) {
					addPrompt.setText("ID����Ϊ�գ�");
					addPrompt.setTextFill(Color.rgb(210, 39, 30));
				} else if (result == 1) {
					addPrompt.setText("������ӳɹ�!");
					addPrompt.setTextFill(Color.rgb(21, 117, 84));
				} else if (result == 2) {
					addPrompt.setText("�����Ѿ��Ǻ��ѣ�");
					addPrompt.setTextFill(Color.rgb(210, 39, 30));
				} else if (result == -2) {
					addPrompt.setText("���û��������ڣ�");
					addPrompt.setTextFill(Color.rgb(210, 39, 30));
				}
			} else {
				addPrompt.setText("��������Լ���");
				addPrompt.setTextFill(Color.rgb(210, 39, 30));
			}
			addname.clear();
		});
		addcancel.setOnAction(e -> {
			addPrompt.setText("");
			addstage.close();
		});

		// �ڷſؼ�
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

		// ��Ӻ��ѽ������

		// ��̨��������������
		Scene mainscene = new Scene(mainPane, 375, 700);
		Scene signscene = new Scene(signPane, 488, 398);
		Scene registerscene = new Scene(registerPane, 488, 198);
		Scene addscene = new Scene(addpane, 488, 198);
		primarystage.setTitle("����QQ");
		primarystage.setScene(mainscene);
		signstage.setTitle("��ӭʹ��  ���¼");
		signstage.setScene(signscene);
		registerstage.setTitle("ע���˺�");
		registerstage.setScene(registerscene);
		addstage.setTitle("��Ӻ���");
		addstage.setScene(addscene);
		signstage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
