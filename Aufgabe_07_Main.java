package aufgabe_07;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

//Klasse zum Erstellen des Fensters und mit der Main-Methode für den Start
public class Aufgabe_07_Main extends Application {
	
	@Override
	public void start (Stage meineStage) throws Exception {
		//eine Instanz von FXMLLoader erzeugen und Verbindung zur FXML-Datei herstellen
				FXMLLoader meinLoader = new FXMLLoader(getClass().getResource("sb_taschenrechner.fxml"));
				//die Datei laden
				Parent root = meinLoader.load();
				//der Controller wird explizit verbunden, um spezielle Interaktionen zu ermöglichen
				FXMLController meinController = meinLoader.getController();
				//und die Bühne übergeben
				meinController.setMeineStage(meineStage);

				//die Szene erzeugen
				//an den Konstruktor werden der oberste Knoten und die Groesse uebergeben
				Scene meineScene = new Scene(root, 316, 322);
				
				//den Titel ueber stage setzen
				meineStage.setTitle("Aufgabe_07 - F. Hemsen");
				//die Szene setzen
				meineStage.setScene(meineScene);
				
				//und anzeigen
				meineStage.show();
	}

	public static void main(String[] args) {
		// TODO Automatisch generierter Methodenstub
		//der Start
		launch(args);
	}

}
