package javabighomework;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Testpane {
	  String driverName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	  String dbURL="jdbc:sqlserver://localhost:1433;DatabaseName=ZPC_SQL";
	  String userName="ZPC1997";
	  String userPwd="zpc534324239";
	  Stage teststage=new Stage();
	  Pane mainpane=new Pane();
	  ScrollPane scpane=new ScrollPane();
	  VBox vpane=new VBox();
	  String exe[]=new String[30];
	  String answer[]=new String[30];
	  TextArea ta[]=new TextArea[30];
	  RadioButton A[]=new RadioButton[20];
	  RadioButton B[]=new RadioButton[20];
	  RadioButton C[]=new RadioButton[20];
	  ToggleGroup group[]=new ToggleGroup[20];
	  HBox hpane[]=new HBox[20];
	  TextField tf[]=new TextField[30];
	  int n=2;
	  Integer j=1;
	  int truecount=0;
	  Button kobt=new Button("提交");
	  Pane btpane=new Pane();
	
	public Testpane(String sub,String gra,String dan) throws ClassNotFoundException, SQLException{
		
		//连接数据库
		Class.forName(driverName);
		Connection dbcon=DriverManager.getConnection(dbURL,userName,userPwd);
		String sql1="use ZPC_SQL; select Num from catalog where Sub='"+sub.trim()+"' and Gra='"+gra.trim()+"';";
		PreparedStatement pst1=dbcon.prepareStatement(sql1);
		ResultSet rs=pst1.executeQuery();
		rs.next();
		int num=rs.getInt(1);
		String sql2="use ZPC_SQL;select * from Te where Num="+num+" and Unit='"+dan+"';";
		PreparedStatement pst2=dbcon.prepareStatement(sql2);
		ResultSet rs2=pst2.executeQuery();
		Label tit=new Label("                 "+gra.trim()+sub.trim()+dan+"测试题");
		Label exetypelabel=new Label("一 .选择题");
		Label exetypelabel1=new Label('\n'+"二.填空题");
		
		tit.setFont(new Font(20));
		exetypelabel.setFont(new Font(15));
		vpane.getChildren().add(tit);
		vpane.getChildren().add(exetypelabel);
		for(int i=0;i<30;i++){
			ta[i] = new TextArea();
			ta[i].setWrapText(true);
			ta[i].setEditable(false);
			ta[i].setMaxSize(480, 50);
		}
		
		while(rs2.next()){
			exe[n]=rs2.getString(4);//题目
			answer[n]=rs2.getString(6);
			if(n<12){
				ta[n].appendText(j.toString()+". "+exe[n]+'\n'+rs2.getString(5).trim());
				vpane.getChildren().add(ta[n]);
				group[n]=new ToggleGroup();
				A[n]=new RadioButton("A");
				B[n]=new RadioButton("B");
				C[n]=new RadioButton("C");
				A[n].setUserData("A");
				B[n].setUserData("B");
				C[n].setUserData("C");
				A[n].setToggleGroup(group[n]);
				B[n].setToggleGroup(group[n]);
				C[n].setToggleGroup(group[n]);
				hpane[n]=new HBox();
				hpane[n].getChildren().addAll(A[n],B[n],C[n]);
				hpane[n].setSpacing(100);
				vpane.getChildren().add(hpane[n]);
			}else if(n>=12&&n<22){
				if(n==12){
					exetypelabel1.setFont(new Font(15));
					vpane.getChildren().add(exetypelabel1);
				}
				ta[n].appendText(j.toString()+". "+exe[n]);
				vpane.getChildren().add(ta[n]);
				tf[n]=new TextField();
				vpane.getChildren().add(tf[n]);
			}
			n++;
			j++;
		}
		
		vpane.getChildren().add(btpane);
		btpane.getChildren().add(kobt);
		kobt.setLayoutX(235);
		
		kobt.setOnAction(e->{
			for(int i=2;i<22;i++){
				if(i<12){
				
					if(group[i].getSelectedToggle().getUserData().toString().trim().equals(answer[i].trim())){
						truecount++;
					}
				}else{
					if(tf[i].getText().toString().trim().equals(answer[i].trim())){
						truecount++;
					}
				}
			}
			Integer score=5*truecount;
			new Scorepane(score);
			String sql3="use ZPC_SQL;insert into score values('"+gra+"','"+sub+"','"+dan+"',"+score+")";
			try {
				PreparedStatement pst3=dbcon.prepareStatement(sql3);
				pst3.executeUpdate();
			} catch (SQLException e1) {
				// TODO 自动生成的 catch 块
				e1.printStackTrace();
			}
			
		});
		
		mainpane.getChildren().addAll(scpane,vpane);
		scpane.setContent(vpane);
		scpane.setPrefHeight(500);
		scpane.setPrefWidth(500);
		scpane.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		scpane.setHbarPolicy(ScrollBarPolicy.NEVER);
		
		Scene testscene=new Scene(mainpane,500,500);
		teststage.setScene(testscene);
		teststage.setTitle("测试");
		teststage.show();
	}
}
