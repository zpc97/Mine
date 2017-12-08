package javabighomework;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Scorepane {
	Stage scorestage=new Stage();
	Pane scorepane=new Pane();
	Button bt=new Button("确定");
	Label scorelabel=new Label();
	Label label1=new Label("请不要气馁，再接再厉！！");
	Label label2=new Label("不错，请继续加油！！");
	Label label3=new Label("太棒了！！希望你保持下去");
	public Scorepane(Integer sco){
		scorelabel=new Label("您的成绩为"+sco.toString()+"分");
		scorelabel.setTextFill(Color.RED);
		scorelabel.setFont(new Font(20));
		label1.setTextFill(Color.RED);
		label1.setFont(new Font(20));
		label2.setTextFill(Color.RED);
		label2.setFont(new Font(20));
		label3.setTextFill(Color.RED);
		label3.setFont(new Font(20));
		scorepane.getChildren().add(scorelabel);
		scorepane.getChildren().add(bt);
		scorelabel.setLayoutX(50);
		scorelabel.setLayoutY(50);
		label1.setLayoutX(20);
		label1.setLayoutY(100);
		label2.setLayoutX(20);
		label2.setLayoutY(100);
		label3.setLayoutX(20);
		label3.setLayoutY(100);
		bt.setLayoutX(120);
		bt.setLayoutY(170);
		if(sco<60){
			scorepane.getChildren().add(label1);
		}else if(sco>=60&&sco<80){
			scorepane.getChildren().add(label2);
		}else{
			scorepane.getChildren().add(label3);
		}
		bt.setOnAction(e->{
			scorestage.close();
		});
		Scene scorescene=new Scene(scorepane,270,210);
		scorestage.setScene(scorescene);
		scorestage.setTitle("成绩");
		scorestage.show();
	}
}
