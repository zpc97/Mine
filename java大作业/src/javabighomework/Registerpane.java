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
	public Stage registerstage=new Stage();//ע����̨
	private Pane registerpane=new Pane();//ע�����
	private Label nicknamelabel=new Label("�ǳ�");//�ǳƱ�ǩ
	private Label temlabel=new Label("�ֻ���");//�ֻ��ű�ǩ
	private Label registerlabel1=new Label("�˺�");//�˺ű�ǩ
	private Label registerlabel2=new Label("����");//�����ǩ
	private TextField nicknametext=new TextField();//�ǳ��ı���
	private TextField registertext1=new TextField();//�˺��ı���
	private PasswordField registertext2=new PasswordField();//�����ı���
	private TextField temtext=new TextField();//�ֻ����ı���
	private Button registerbt=new Button("ȷ��");//ȷ����ť
	public Registerpane(){
		//����������ɫ
		nicknamelabel.setTextFill(Color.BLACK);
		registerlabel1.setTextFill(Color.BLACK);
		registerlabel2.setTextFill(Color.BLACK);
		temlabel.setTextFill(Color.BLACK);
		//���ñ���ͼƬ
		BackgroundImage bg=new BackgroundImage(new Image("bg2.jpg"),null, null, null,null);
		registerpane.setBackground(new Background(bg));
		//�ؼ�λ�ðڷ�
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
		
		//ȷ�����ʱ�� ��ע������¼���
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
					// TODO �Զ����ɵ� catch ��
					e1.printStackTrace();
				}
			}
		});
		
		
		Scene registerscene=new Scene(registerpane,300,250);
		registerstage.setTitle("ע��");
		registerstage.setScene(registerscene);
		registerstage.setResizable(false);
		registerstage.show();
	}
}
