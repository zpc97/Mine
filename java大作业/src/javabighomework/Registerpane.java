package javabighomework;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Registerpane{
	public Stage registerstage=new Stage();//注册舞台
	private Pane registerpane=new Pane();//注册界面
	private Label nicknamelabel=new Label("昵称");//昵称标签
	private Label temlabel=new Label("手机号");//手机号标签
	private Label registerlabel1=new Label("账号");//账号标签
	private Label registerlabel2=new Label("密码");//密码标签
	private TextField nicknametext=new TextField();//昵称文本框
	private TextField registertext1=new TextField();//账号文本框
	private PasswordField registertext2=new PasswordField();//密码文本框
	private TextField temtext=new TextField();//手机号文本框
	private Button registerbt=new Button("确定");//确定按钮
	public Registerpane(){
		//设置字体颜色
		nicknamelabel.setTextFill(Color.BLACK);
		registerlabel1.setTextFill(Color.BLACK);
		registerlabel2.setTextFill(Color.BLACK);
		temlabel.setTextFill(Color.BLACK);
		//设置背景图片
		BackgroundImage bg=new BackgroundImage(new Image("bg2.jpg"),null, null, null,null);
		registerpane.setBackground(new Background(bg));
		//控件位置摆放
		registerpane.getChildren().addAll(nicknamelabel,registerlabel1,registerlabel2,temlabel,nicknametext
				,registertext1,registertext2,temtext,registerbt);
		nicknamelabel.setLayoutX(40);
		nicknamelabel.setLayoutY(43);
		nicknametext.setLayoutX(80);
		nicknametext.setLayoutY(40);
		registerlabel1.setLayoutX(40);
		registerlabel1.setLayoutY(83);
		registertext1.setLayoutX(80);
		registertext1.setLayoutY(80);
		registerlabel2.setLayoutX(40);
		registerlabel2.setLayoutY(123);
		registertext2.setLayoutX(80);
		registertext2.setLayoutY(120);
		temlabel.setLayoutX(40);
		temlabel.setLayoutY(163);
		temtext.setLayoutX(80);
		temtext.setLayoutY(160);
		registerbt.setLayoutX(130);
		registerbt.setLayoutY(190);
		
		//确定点击时间 （注册完成事件）
		registerbt.setOnAction(e->{
			String name=nicknametext.getText();
			String zhanghao=registertext1.getText();
			String passward=registertext2.getText();
			String tem=temtext.getText();
			File file=new File(zhanghao+".txt");
			if(file.exists()){
				;
			}else{
				try {
					PrintWriter input=new PrintWriter(file);
					input.println(name);
					input.println(zhanghao);
					input.println(passward);
					input.println(tem);
					input.close();
					registerstage.close();
				} catch (FileNotFoundException e1) {
					// TODO 自动生成的 catch 块
					e1.printStackTrace();
				}
			}
		});
		
		
		Scene registerscene=new Scene(registerpane,300,250);
		registerstage.setTitle("注册");
		registerstage.setScene(registerscene);
		registerstage.setResizable(false);
		registerstage.show();
	}
}
