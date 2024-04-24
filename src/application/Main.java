package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import model.entities.CSTR;


import java.io.IOException;


public class Main extends Application {

	private static Scene mainScene;

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainView.fxml"));
			ScrollPane scrollPane = loader.load();

			scrollPane.setFitToHeight(true);
			scrollPane.setFitToWidth(true);

			mainScene = new Scene(scrollPane);
			primaryStage.setScene(mainScene);
			primaryStage.setTitle("Sample JavaFX application");
			//primaryStage.setMaximized(true);
			primaryStage.show();

			CSTR cstr = new CSTR();

			/*
			double [] results = cstr.CSTRTest();

			System.out.println("Fourth order Runge-Kutta procedure");
			System.out.println("The value of y[0] at x =  is " + results[0]);
			System.out.println("The value of y[1] at x =  is " + results[1]);
			System.out.println("The value of y[2] at x =  is " + results[2]);
			System.out.println("The value of y[3] at x =  is " + results[3]);

						 */

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static Scene getMainScene() {
		return mainScene;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
