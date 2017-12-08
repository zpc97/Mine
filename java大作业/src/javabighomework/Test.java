package javabighomework;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.sun.javafx.font.freetype.FTFactory;

import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Test extends Application{

	public static void main(String []args) {
		 launch();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO 自动生成的方法存根
		Pane pane=new Pane();
		ScrollPane sp=new ScrollPane();
		VBox vb=new VBox();
		sp.setPrefHeight(200);
		sp.setPrefWidth(200);
		sp.setVbarPolicy(ScrollBarPolicy.ALWAYS);
		Label la[]=new Label[20];
		for(Integer i=0;i<20;i++){
			la[i]=new Label(i.toString());
			vb.getChildren().add(la[i]);
		}
		pane.getChildren().add(sp);
		pane.getChildren().add(vb);
		sp.setLayoutX(0);
		sp.setLayoutY(0);
		sp.setContent(vb);
		
	
		
		Scene scene=new Scene(pane,200,200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}
