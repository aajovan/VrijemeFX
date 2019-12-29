package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Stage stage=primaryStage;
	         Parent root;
	      root = FXMLLoader.load(getClass().getResource("MainFXML.fxml"));
	          Scene scene = new Scene(root);
	          stage.setScene(scene);
	          stage.setTitle("DHMZ");
	          //stage.initStyle(StageStyle.UNDECORATED);
	          stage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
