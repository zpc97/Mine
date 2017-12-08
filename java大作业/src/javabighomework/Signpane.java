package javabighomework;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;

public class Signpane extends Application{
	private static Stage signStage=new Stage();
	private static String pass,sign;
	private static int n1=1,n2=1,n3=1,n4=1;
	private static Label welcomlabel=new Label("登录");
	private static Pane mainpane=new Pane();//主界面
	private static TextField signtext=new TextField();//账号输入框
	private static PasswordField passtext=new PasswordField();//密码输入框
	private static Button signbutton=new Button("登录");//登录按钮
	private static Button registerbutton=new Button("注册");//注册按钮
	private static Label signlabel=new Label("账号");//账号标签
	private static Label passlabel=new Label("密码");//密码标签
	//提示标签
	private static Label warnlabel1=new Label("账号不能为空");
	private static Label warnlabel2=new Label("账号不存在");
	private static Label warnlabel3=new Label("密码不能为空");
	private static Label warnlabel4=new Label("密码错误");
	private static FadeTransition ft1=new FadeTransition(Duration.millis(3000),warnlabel1);
	private static FadeTransition ft2=new FadeTransition(Duration.millis(3000),warnlabel2);
	private static FadeTransition ft3=new FadeTransition(Duration.millis(3000),warnlabel3);
	private static FadeTransition ft4=new FadeTransition(Duration.millis(3000),warnlabel4);
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		launch();
	}
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO 自动生成的方法存根
		//字体颜色
		signlabel.setTextFill(Color.BLUE);
		passlabel.setTextFill(Color.BLUE);
		warnlabel1.setTextFill(Color.RED);
		warnlabel2.setTextFill(Color.RED);
		warnlabel3.setTextFill(Color.RED);
		warnlabel4.setTextFill(Color.RED);
		welcomlabel.setTextFill(Color.BLANCHEDALMOND);
		welcomlabel.setFont(new Font("华文宋体",30));
		//背景
		BackgroundImage bg=new BackgroundImage(new Image("bg1.jpg", 400, 300, false, true),
				BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER,BackgroundSize.DEFAULT);
		mainpane.setBackground(new Background(bg));
		//控件位置
		mainpane.getChildren().addAll(welcomlabel,signtext,passtext,signbutton,registerbutton,signlabel,passlabel);
		welcomlabel.setLayoutX(170);
		welcomlabel.setLayoutY(50);
		signlabel.setLayoutX(90);
		signlabel.setLayoutY(143);
		passlabel.setLayoutX(90);
		passlabel.setLayoutY(193);
		signtext.setLayoutX(120);
		signtext.setLayoutY(140);
		passtext.setLayoutX(120);
		passtext.setLayoutY(190);
		signbutton.setLayoutX(140);
		signbutton.setLayoutY(250);
		registerbutton.setLayoutX(220);
		registerbutton.setLayoutY(250);
		
		warnlabel1.setLayoutX(120);
		warnlabel1.setLayoutY(170);
		warnlabel3.setLayoutX(120);
		warnlabel3.setLayoutY(220);
		warnlabel2.setLayoutX(120);
		warnlabel2.setLayoutY(170);
		warnlabel4.setLayoutX(120);
		warnlabel4.setLayoutY(220);
		//提示动画
		ft1.setFromValue(1);
		ft1.setToValue(0);
		ft1.setCycleCount(1);
		ft2.setFromValue(1);
		ft2.setToValue(0);
		ft2.setCycleCount(1);
		ft3.setFromValue(1);
		ft3.setToValue(0);
		ft3.setCycleCount(1);
		ft4.setFromValue(1);
		ft4.setToValue(0);
		ft4.setCycleCount(1);
		//注册点击事件
		registerbutton.setOnAction(e->{
			new Registerpane();
		});
		signbutton.setOnAction(e->{
			action();
		});
		passtext.setOnAction(e->{
			action();
		});
		Scene mainscene=new Scene(mainpane,400,300);
		signStage.setScene(mainscene);
		signStage.setTitle("欢迎使用个性化学学习系统");
		signStage.setResizable(false);
		signStage.show();
	}
	public void action(){
		sign=signtext.getText();
		pass=passtext.getText();
		File file=new File(sign+".txt");
		if(sign.equals("")){
			if(n1!=1){
				mainpane.getChildren().remove(warnlabel1);
			}
			mainpane.getChildren().add(warnlabel1);
			ft1.play();
			n1++;
		}else if(!sign.equals("")&&pass.equals("")){
			if(n3!=1){
				mainpane.getChildren().remove(warnlabel3);
			}
			mainpane.getChildren().add(warnlabel3);
			ft3.play();
			n3++;
		}
		if(file.exists()){
			try {
				Scanner output = new Scanner(file);
				String temp = output.nextLine();
				String zhanghao=output.next();
				String passward=output.next();
				if(zhanghao.equals(signtext.getText())&&passward.equals(passtext.getText())){
					signStage.close();
					new Maininterface(sign);
				}
				if(zhanghao.equals(signtext.getText())&&!pass.equals("")){
					if(n4!=1){
						mainpane.getChildren().remove(warnlabel4);
					}
					mainpane.getChildren().add(warnlabel4);
					ft4.play();
					n4++;
				}
				output.close();
			} catch (FileNotFoundException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}	
		}else if(!file.exists()&&!pass.equals("")&&!sign.equals("")){
			if(n2!=1){
				mainpane.getChildren().remove(warnlabel2);
			}
			mainpane.getChildren().add(warnlabel2);
			ft2.play();
			n2++;
		}
		
	}
}
