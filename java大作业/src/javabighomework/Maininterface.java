package javabighomework;


import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.Scanner;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
public class Maininterface{
	String name;
	int n1=1,n2=1;
	private Stage primarystage=new Stage();
	private VBox vBox_main =new VBox();//�����
	private StackPane stackPane_up=new StackPane();//�ϲ����
	private StackPane stackPane_down=new StackPane();//�²����
	public Maininterface(String n){
		/*���ϲ������²�������������У��ҽ��п�͸ߵİ�*/
		name=n;
		vBox_main.getChildren().addAll(stackPane_up,stackPane_down);
		stackPane_up.prefWidthProperty().bind(vBox_main.widthProperty());
		stackPane_up.prefHeightProperty().bind(vBox_main.heightProperty().subtract(40));
		stackPane_down.prefWidthProperty().bind(vBox_main.widthProperty());
		
		/*������壬�������²��İ�ť*/
		HBox hBox_button=new HBox();
		hBox_button.prefHeightProperty().bind(stackPane_down.heightProperty());
		stackPane_down.getChildren().add(hBox_button);
		
		/*��ҳ��ť*/
		VBox vBox_home=new VBox(new ImageView("��ҳ.png"),new Label("��ҳ"));
		vBox_home.setAlignment(Pos.BASELINE_CENTER);
		Button button_home=new Button(null,vBox_home);
		button_home.prefHeightProperty().bind(hBox_button.heightProperty());
		button_home.prefWidthProperty().bind(hBox_button.widthProperty().divide(3));
		
		/*ѧϰ��ť*/
		VBox vBox_study=new VBox(new ImageView("ѧϰ.png"),new Label("ѧϰ��¼"));
		vBox_study.setAlignment(Pos.BASELINE_CENTER);
		Button button_study=new Button(null,vBox_study);
		button_study.prefHeightProperty().bind(hBox_button.heightProperty());
		button_study.prefWidthProperty().bind(hBox_button.widthProperty().divide(3));
		
		/*�Ұ�ť*/
		VBox vBox_my=new VBox(new ImageView("��.png"),new Label("��"));
		vBox_my.setAlignment(Pos.BASELINE_CENTER);
		Button button_my=new Button(null,vBox_my);
		button_my.prefHeightProperty().bind(hBox_button.heightProperty());
		button_my.prefWidthProperty().bind(hBox_button.widthProperty().divide(3));
		hBox_button.getChildren().addAll(button_home,button_study,button_my);
		
		stackPane_up.getChildren().clear();//����ϲ�����е����
		stackPane_up.getChildren().add(homepage());//�ϲ�����м���Ĭ�ϳ�ʼΪ��ҳ���
		
		/*��ҳ��ť�¼�*/
		button_home.setOnAction(e->{
			stackPane_up.getChildren().clear();//����ϲ����
			stackPane_up.getChildren().add(homepage());//���ϲ�����������ҳ���
		});
		
		/*ѧϰ��ť�¼�*/
		button_study.setOnAction(e->{
			stackPane_up.getChildren().clear();//����ϲ����
			stackPane_up.getChildren().add(studypage());//���ϲ���������ѧϰ���
		});
		
		/*�Ұ�ť�¼�*/
		button_my.setOnAction(e->{
			stackPane_up.getChildren().clear();//����ϲ����
			try {
				stackPane_up.getChildren().add(mypage());
			} catch (FileNotFoundException e1) {
				// TODO �Զ����ɵ� catch ��
				e1.printStackTrace();
			}//���ϲ��������������
		});
		
		Scene scene=new Scene(vBox_main,400,400);//���ɳ����������������
		primarystage.setScene(scene);//������̨�еĳ���
		primarystage.setResizable(false);
		primarystage.setTitle("��ӭʹ��ѧϰϵͳ");
		primarystage.show();//չʾ
	}
	/*����һ�������*/
	public Pane homepage() {
		Pane pane=new Pane();
		BackgroundImage bg=new BackgroundImage(new Image("9.jpg", 400, 300, false, true),
				null, null, null,null);
		pane.setBackground(new Background(bg));
		Label titlelabel=new Label("ѧϰ�ռ�");
		Label promptlabel1=new Label("��ѡ������꼶");
		Label promptlabel2=new Label("��ѡ����Ҫѧϰ�Ŀ�Ŀ");
		Label chapterlabel[]={new Label("��һ��Ԫ"),
				new Label("�ڶ���Ԫ"),
				new Label("������Ԫ"),
				new Label("���ĵ�Ԫ"),
				new Label("���嵥Ԫ"),
				new Label("������Ԫ")};
		ComboBox<String> subjectcbo=new ComboBox<>();
		subjectcbo.getItems().addAll("   ����","   ��ѧ","   Ӣ��");
		ComboBox<String> gradecbo=new ComboBox<>();
		gradecbo.getItems().addAll("һ�꼶","���꼶","���꼶",
				"���꼶","���꼶","���꼶");
		subjectcbo.setValue("null");
		gradecbo.setValue("null");
		Label warnlabel1=new Label("�꼶����Ϊ��");
		Label warnlabel2=new Label("��Ŀ����Ϊ��");
		
		
		
		FadeTransition ft1=new FadeTransition(Duration.millis(3000),warnlabel1);
		FadeTransition ft2=new FadeTransition(Duration.millis(3000),warnlabel2);
		ft1.setFromValue(1);
		ft1.setToValue(0);
		ft1.setCycleCount(1);
		warnlabel1.setTextFill(Color.RED);
		warnlabel2.setTextFill(Color.RED);
		ft2.setFromValue(1);
		ft2.setToValue(0);
		ft2.setCycleCount(1);
		warnlabel1.setTextFill(Color.RED);
		warnlabel2.setTextFill(Color.RED);
		
		
		subjectcbo.setEditable(false);
		gradecbo.setEditable(false);
		titlelabel.setFont(new Font("������κ",30));
		promptlabel1.setFont(new Font(20));
		promptlabel2.setFont(new Font(20));
		titlelabel.setLayoutX(140);
		titlelabel.setLayoutY(10);
		promptlabel1.setLayoutX(30);
		promptlabel1.setLayoutY(50);
		gradecbo.setLayoutX(30);
		gradecbo.setLayoutY(83);
		warnlabel1.setLayoutX(120);
		warnlabel1.setLayoutY(88);
		warnlabel2.setLayoutX(120);
		warnlabel2.setLayoutY(140);
		promptlabel2.setLayoutX(30);
		promptlabel2.setLayoutY(110);
		subjectcbo.setLayoutX(30);
		subjectcbo.setLayoutY(140);
		
		for(int i=0;i<6;i++){
			
			chapterlabel[i].setFont(new Font(15));
			chapterlabel[i].setTextFill(Color.BLUE);
			chapterlabel[i].setLayoutX(30);
			chapterlabel[i].setLayoutY(170+i*30);
			int j=i;
			chapterlabel[i].setOnMouseEntered(e->{
				chapterlabel[j].setTextFill(Color.RED);
			});
			chapterlabel[i].setOnMouseExited(e->{
				chapterlabel[j].setTextFill(Color.BLUE);
			});
			chapterlabel[i].setOnMouseClicked(e->{
				String subject=subjectcbo.getValue();
				String grade=gradecbo.getValue();
						try {
							if(subject!="null"&&grade!="null"){
								new Testpane(subject,grade,chapterlabel[j].getText());
							}else if((subject!="null"&&grade=="null")||(subject=="null"&&grade=="null")){
								if(n1==1){
									pane.getChildren().add(warnlabel1);
								}
								if(n1>1){
									pane.getChildren().remove(warnlabel1);
									pane.getChildren().add(warnlabel1);
								}
								ft1.play();
								n1++;
							}else if(grade!="null"&&subject=="null"){
								if(n2==1){
									pane.getChildren().add(warnlabel2);
								}
								if(n2>1){
									pane.getChildren().remove(warnlabel2);
									pane.getChildren().add(warnlabel2);
								}
								ft2.play();
								n2++;
							}
						} catch (ClassNotFoundException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
						} catch (SQLException e1) {
							// TODO �Զ����ɵ� catch ��
							e1.printStackTrace();
						}
				//	}
			
			});
		}
		pane.getChildren().addAll(titlelabel,promptlabel1,subjectcbo,promptlabel2,
				gradecbo,chapterlabel[0],chapterlabel[1],chapterlabel[2],chapterlabel[3],
				chapterlabel[4],chapterlabel[5]);
		return pane;
	}
	/*����һ��ѧϰ���*/
	public Pane studypage() {
		Pane pane=new Pane(new Label("ѧϰ��¼"));
		return pane;
	}
	/*����һ�������*/
	public Pane mypage() throws FileNotFoundException {
		Pane pane=new Pane();
		BackgroundImage bg=new BackgroundImage(new Image("7.jpg", 400, 300, false, true),
				null, null, null,null);
		pane.setBackground(new Background(bg));
		File file=new File(name+".txt");
		Scanner output=new Scanner(file);
		String nam=output.nextLine();
		String num=output.nextLine();
		String temp=output.nextLine();
		String tem=output.nextLine();
		Label label=new Label("��������");
		Label namelabel=new Label("����:"+nam);
		Label numlabel=new Label("�˺�:"+num);
		Label temlabel=new Label("�绰:"+tem);
		Button updatebt=new Button("�޸�����");
		updatebt.setPrefSize(80, 40);
		updatebt.setTextFill(Color.RED);
		updatebt.setFont(new Font(15));
		label.setTextFill(Color.BLACK);
		label.setFont(new Font("������κ",30));
		namelabel.setTextFill(Color.CADETBLUE);
		namelabel.setFont(new Font("���ķ���",20));
		numlabel.setTextFill(Color.CADETBLUE);
		numlabel.setFont(new Font("���ķ���",20));
		temlabel.setTextFill(Color.CADETBLUE);
		temlabel.setFont(new Font("���ķ���",20));
		label.setLayoutX(145);
		label.setLayoutY(20);
		namelabel.setLayoutX(10);
		namelabel.setLayoutY(70);
		numlabel.setLayoutX(10);
		numlabel.setLayoutY(120);
		temlabel.setLayoutX(10);
		temlabel.setLayoutY(170);
		updatebt.setLayoutX(10);
		updatebt.setLayoutY(220);
		pane.getChildren().addAll(label,namelabel,numlabel,temlabel,updatebt);
		return pane;
	}
}
