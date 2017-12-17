package 数据库大作业;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

class ChatLabel extends Label {
	public boolean Warp;
	private BackgroundFill fill = new BackgroundFill(null, CornerRadii.EMPTY, Insets.EMPTY);
	private Background buBackground = new Background(fill);
	public double Width;
	private Font font = new Font("华文新魏", 20);

	public ChatLabel(String text, double width) {
		super(text);
		setFont(font);
		Width = width;
		setBackground(buBackground);
		if (width <= 400) {
			setPrefWidth(400);
			setPrefHeight(25);
			Warp = false;
		} else {
			setPrefWidth(400);
			setPrefHeight((width / 400 + 1) * 25);
			Warp = true;
		}
		setWrapText(true);
	}
}

class ChatPane {
	private Pane mianPane = new Pane();
	private ScrollPane ChatPane = new ScrollPane();
	private Pane chatpane = new Pane();
	private TextArea chatText = new TextArea();
	private Font buFont = new Font("华文新魏", 20);
	private Label sent = new Label("发送"), exit = new Label("关闭"), refresh = new Label("刷新");
	private Button Sent = new Button("", sent), Exit = new Button("", exit), Refresh = new Button("", refresh);
	private Font noteFont = new Font("华文中宋", 20);
	private Label chatNote = new Label("");
	private int chatCount = 0;
	public Stage chatStage = new Stage();

	private boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	private double countWidth(String strName) {
		int countChinese = 0, countEnglish = 0, countNumber1 = 0, countNumber = 0;
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				countChinese++;
			} else if (c == '1') {
				countNumber1++;
			} else if (c >= '0' && c <= '9') {
				countNumber++;
			} else {
				countEnglish++;
			}
		}
		return countChinese * 20 + countEnglish * 10 + countNumber1 * 8 + countNumber * 11.5;
	}

	public void showMessage(String message[], String friendname) {
		for (int j = 0; message[j] != null; j++) {
			Label FriendName = new Label(friendname + "：");
			FriendName.setTextFill(Color.RED);
			FriendName.setFont(new Font("华文新魏", 20));
			FriendName.setPrefSize(100, 20);
			FriendName.setAlignment(Pos.CENTER);
			chatpane.getChildren().add(FriendName);
			FriendName.setLayoutY(15 + chatCount);
			ChatLabel chatLabel = new ChatLabel(message[j], countWidth(message[j]));
			chatpane.getChildren().add(chatLabel);
			chatLabel.setLayoutX(100);
			chatLabel.setLayoutY(10 + chatCount);
			chatCount += chatLabel.getPrefHeight() + 20;
		}
	}

	public ChatPane(String username, String friendname, JDBC sql) {
		Image mianimg = new Image(getClass().getResourceAsStream("主背景1.jpg"));
		BackgroundSize backgroundSize = new BackgroundSize(300, 300, false, false, false, false);
		BackgroundImage backgroundImage = new BackgroundImage(mianimg, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
				BackgroundPosition.DEFAULT, backgroundSize);
		chatpane.setBackground(new Background(backgroundImage));
		chatpane.setMinHeight(350);
		chatpane.setMinWidth(500);
		ChatPane.setPrefSize(500, 350);
		ChatPane.setHbarPolicy(ScrollBarPolicy.NEVER);
		ChatPane.setContent(chatpane);
		chatText.setPrefSize(500, 150);
		chatText.setWrapText(true);
		chatNote.setFont(noteFont);
		mianPane.getChildren().addAll(ChatPane, chatText, Sent, Exit, Refresh, chatNote);
		chatText.setLayoutY(350);
		Sent.setLayoutX(350);
		Sent.setLayoutY(450);
		Exit.setLayoutX(425);
		Exit.setLayoutY(450);
		Refresh.setLayoutX(430);
		Refresh.setLayoutY(310);
		sent.setFont(buFont);
		exit.setFont(buFont);
		refresh.setFont(new Font("华文新魏", 20));
		chatNote.setLayoutX(250 - countWidth(chatNote.getText()) / 2);
		chatNote.setLayoutY(475);
		BackgroundFill fill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
		Background buBackground = new Background(fill);
		chatNote.setBackground(buBackground);
		chatNote.setTextFill(Color.WHITE);
		Sent.setOnAction(e -> {
			chatNote.setText("");
			chatNote.setLayoutX(250 - countWidth(chatNote.getText()) / 2);
			if (chatText.getText().isEmpty()) {
				chatNote.setText("内容不能为空！");
				chatNote.setLayoutX(250 - countWidth(chatNote.getText()) / 2);
			} else if (chatText.getText().length() > 120) {
				chatNote.setText("长度超出限制！（120字）");
				chatNote.setLayoutX(250 - countWidth(chatNote.getText()) / 2);
			} else {
				Label Myname = new Label(username + "：");
				Font MyFont = new Font("华文新魏", 20);
				Myname.setFont(MyFont);
				Myname.setTextFill(Color.BLUE);
				Myname.setPrefSize(100, 20);
				Myname.setAlignment(Pos.CENTER);
				chatpane.getChildren().add(Myname);
				Myname.setLayoutY(15 + chatCount);
				ChatLabel chatLabel = new ChatLabel(chatText.getText(), countWidth(chatText.getText()));
				chatpane.getChildren().add(chatLabel);
				chatLabel.setLayoutX(100);
				chatLabel.setLayoutY(10 + chatCount);
				chatCount += chatLabel.getPrefHeight() + 20;
				sql.sendMessage(username, friendname, chatText.getText());
				chatText.clear();
			}
		});
		Exit.setOnAction(e -> {
			chatText.clear();
			chatStage.close();
		});
		Refresh.setOnAction(e -> {
			showMessage(sql.readMessage(username), friendname);
		});
		Scene chatScene = new Scene(mianPane, 488, 488);
		chatStage.setTitle(friendname);
		chatStage.setScene(chatScene);
	}
}