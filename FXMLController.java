package aufgabe_07;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class FXMLController {
	//--------------------------------------------------------------------------- die Komponenten
	//Verbindungen zu den Komponenten der FXML-Datei

	//für die VBox
	@FXML private Label ausgabe;

	//für die Buttons
	@FXML private Button zero, eins, zwei, drei, vier, fuenf, sechs, sieben, acht, neun, komma, ergebnis, addition, subtraktion, multiplikation, division, zurueck, clear;

	//Instanzvariable für die Bühne
	private Stage meineStage;

	//für die Werte als String und die Auswahl des Rechenwegs
	private String wertAktuellString, wert1String, wert2String, rechenWahl;

	//Maximalwert für das Ausgabefeld, um den Nutzer darauf aufmerksam zu machen, dass vermutlich nicht alle Zeichen angezeigt werden
	private static final int MAX_CHAR_LENGTH = 16;

	//"geklickt" soll "true" sein, sobald ein Operator geklickt wurde (+,-,* oder /) und somit der erste Teil der Rechenaufgabe sicher ist
	//"keinZurueck" soll "true" sein, wenn der Zurück-Button kein "zurück" ermöglichen soll.
	//"folgeRechnung" soll "true" sein, wenn der Nutzer direkt nach "=" und der Anzeige des Ergebnisses damit weiterrechnet indem er auf einen Operator klickt und einen zweiten Wert eingibt und wieder "=" klickt
	private boolean operatorGeklickt, zurueckGesperrt, folgeRechnung;


	//--------------------------------------------------------------- Methode zum Setzen von Initialwerten
	//sie wird automatisch ausgeführt
	@FXML private void initialize() {
		wertAktuellString = "";
		rechenWahl = "";
		operatorGeklickt = false;
		zurueckGesperrt = false;
		folgeRechnung = false;
	}

	//---------------------------------------------------------------- Methode setzt die Bühne auf den übergebenen Wert
	public void setMeineStage(Stage meineStage) {
		this.meineStage = meineStage;
	}

	//---------------------------------------------------------------- Die Methoden für das ActionEvent bei den Zahlen-Buttons
	@FXML private void einsKlick() { verarbeiteZahl("1"); } 
	@FXML private void zweiKlick() { verarbeiteZahl("2"); } 
	@FXML private void dreiKlick() { verarbeiteZahl("3"); } 
	@FXML private void vierKlick() { verarbeiteZahl("4"); } 
	@FXML private void fuenfKlick() { verarbeiteZahl("5"); } 
	@FXML private void sechsKlick() { verarbeiteZahl("6"); } 
	@FXML private void siebenKlick() { verarbeiteZahl("7"); } 
	@FXML private void achtKlick() { verarbeiteZahl("8"); } 
	@FXML private void neunKlick() { verarbeiteZahl("9"); } 
	@FXML private void zeroKlick() { verarbeiteZahl("0"); } 

	private void verarbeiteZahl(String zahl) { 
		//wenn die Eingabe direkt auf das vorherige Ergebnis folgt, soll eine neue Rechnung gestartet werden und Werte sollen dafür zurückgesetzt werden.
		if (folgeRechnung) { 
			clearKlick(); 
		}
		//die Zahl dem String hinzufügen
		zahlUebergeben(zahl); 
		//die Ausgabe damit aktualisieren
		ausgabeAktualisieren(wertAktuellString); 
	}

	@FXML private void kommaKlick() {
		//das vorige Ergebnis einer Rechenaufgabe soll nicht verändert werden
		if (folgeRechnung) {
			return;
		}
		//gibt der User am Anfang gleich ein Komma ein, machen wir daraus ein "0,"
		if (wertAktuellString.isEmpty()) {
			wertAktuellString = "0,";
		}
		//hat der Wert im Ausgabefeld bereits ein Komma, dann den Nutzer darauf aufmerksam machen
		else if (wertAktuellString.contains(",")) {
			alertErstellen("Sie können nur ein Komma eingeben.");
		}
		else {
			//in allen anderen Fällen hängen wir ein Komma an den String an
			wertAktuellString = wertAktuellString.concat(",");
		}
		zurueckGesperrt = false;
		ausgabeAktualisieren(wertAktuellString);
	}

	//---------------------------------------------------------------- Die Methoden für die möglichen Rechenoperationen
	@FXML private void additionKlick() {
		rechnungVorbereiten("addition");
	}
	@FXML private void subtraktionKlick() {
		rechnungVorbereiten("subtraktion");
	}
	@FXML private void multiplikationKlick() {
		rechnungVorbereiten("multiplikation");
	}
	@FXML private void divisionKlick() {
		rechnungVorbereiten("division");
	}

	//---------------------------------------------------------------- Methode für die Aktionen sobald ein Operator geklickt wurde
	private void rechnungVorbereiten(String operator) {
		//wenn nicht gerade schon auf einen Operator geklickt wurde
		//ein zweiter Klick auf einen Operator soll keinen Effekt haben
		if (!operatorGeklickt) {
			//den String im Ausgabefeld als ersten Wert übernehmen
			wert1String = wertAktuellString;
			//den gepeicherten String vom Ausgabefeld leeren (Ausgabefeld selbst braucht nicht geleert zu werden)
			wertAktuellString = "";
			//Rechenwahl speichern
			rechenWahl = operator;
			//hiermit hat ein weiterer Klick auf einen Rechenoperator keinen Effekt
			operatorGeklickt = true;
			//die Zurückfunktion soll nur während der Eingabe von Wert 1 oder Wert 2 möglich sein
			zurueckGesperrt = true;
			//jetzt soll beim Eingeben der nächsten Ziffer nicht mehr clearKlick() ausgeführt werden.
			folgeRechnung = false;
		}
	}

	//---------------------------------------------------------------- Die Methode für das Zurücksetzen
	@FXML private void clearKlick() {
		wertAktuellString = "0";
		wert1String = "";
		wert2String = "";
		rechenWahl = "";
		zurueckGesperrt = false;
		operatorGeklickt = false;
		folgeRechnung = false;
		ausgabeAktualisieren(wertAktuellString);
	}

	//---------------------------------------------------------------- Die Methode für das Löschen der zuletzt getippten Zahl
	//während der Eingabe von Wert1 oder Wert2 soll eine Korrektur möglich sein
	@FXML private void zurueckKlick() {
		if (zurueckGesperrt) {
			alertErstellen("Zurück-Funktion nur währen der Eingabe der Werte möglich");
			return;
		}

		//Alle bis auf das letzte Zeichen im String behalten
		//durch die Methode substring() können wir einen Teil des Strings auswählen - hier den gesamten String bis auf das letzte Zeichen
		wertAktuellString = wertAktuellString.substring(0, wertAktuellString.length() - 1);
		ausgabeAktualisieren(wertAktuellString);	
	}

	//---------------------------------------------------------------- Die Methoden für das Berechnen, sobald auf das Gleichheitszeichen geklickt wurde
	@FXML private void ergebnisKlick() {
		//für die Werte als Zahlenwerte nach der Konvertierung vom Typ String
		double wert1 = 0;
		double wert2 = 0;
		double ergebnis = 0;

		//wenn nicht direkt nach dem letzten Klick auf "=" wieder "=" geklickt wurde
		if(!folgeRechnung) {
			wert2String = wertAktuellString;
		} 

		//Umwandlung mit Exception Handling (Fehlerbehandlung)
		try {
			//Text einlesen und deutsches Format beruecksichtigen (Komma statt Punkt)
			//und in ein Objekt der Klasse Number speichern
			Number wert1Number = NumberFormat.getNumberInstance(Locale.GERMANY).parse(wert1String);
			//das Objekt in einen double aendern
			wert1 = wert1Number.doubleValue();	
		}
		//ParseException behandeln, wenn .parse() -Methode benutzt wurde
		catch (Exception ParseException) {
			alertErstellen("Erster Wert wurde nicht erfasst.");
			//Methode beenden
			return;
		}

		try {
			Number wert2Number = NumberFormat.getNumberInstance(Locale.GERMANY).parse(wert2String);
			wert2 = wert2Number.doubleValue();
		}
		catch (Exception ParseException) {
			alertErstellen("Zweiter Wert wurde nicht erfasst.");
			return;
		}

		switch (rechenWahl) {
		case "addition": ergebnis = wert1 + wert2; break;
		case "subtraktion": ergebnis = wert1 - wert2; break;
		case "multiplikation": ergebnis = wert1 * wert2; break;
		case "division": 
			if (wert2 != 0)
				ergebnis = wert1 / wert2;
			else {
				alertErstellen ("Division durch Null nicht möglich.\nWähle einen anderen Divisor.");
				//Methode verlassen
				return;
			}
			break;
		}

		//DecimalFormat um ggf Nachkommastellen anzuzeigen und Tausendertrennzeichen
		DecimalFormat formatFolge = new DecimalFormat("#,##0.##########");		
		//Ergebnis in String umwandeln und in dem String für die Ausgabe speichern
		wertAktuellString = formatFolge.format(ergebnis);
		ausgabeAktualisieren(wertAktuellString);
		//für den Fall des Weiterrechnens mit dem Ergebnis dieses in wert1String speichern
		wert1String = wertAktuellString;
		//das Ergebnis soll nicht durch "zurück" verändert werden
		zurueckGesperrt = true;
		//für den Fall, dass mit dem Ergebnis weitergerechnet wird
		folgeRechnung = true;
	}

	//---------------------------------------------------------------------------------------------------- Methode zahlUebergeben
	private void zahlUebergeben (String uebergebeneZahl) {

		//da eine Zahl geklickt wurde, wurde nicht zweimal hintereinander eine Rechenoperation geklickt und der boolean kann wieder auf false gesetzt werden.
		operatorGeklickt = false;

		if (wertAktuellString.isEmpty() || wertAktuellString.equals("0")) {
			wertAktuellString = uebergebeneZahl;
		}
		else {
			wertAktuellString = wertAktuellString.concat(uebergebeneZahl);
		}

		//werden neue Zahlen eingegeben ist direkt darauf "zurück" nicht gesperrt
		zurueckGesperrt = false;
	}


	//---------------------------------------------------------------------------------------------------- Methode AusgabeAktualisieren
	private void ausgabeAktualisieren(String ausgabeString) {
		if (ausgabeString.length() > MAX_CHAR_LENGTH) { 
			alertErstellen("Die maximale Anzahl von " + MAX_CHAR_LENGTH + " Zeichen wird überschritten."); 
		}
		ausgabe.setText(ausgabeString);
	}

	//---------------------------------------------------------------------------------------------------- Methode zum Anzeigen eines Alerts
	//als Argument wird der Text übergeben, der in der Alert-Mitteilung erscheinen soll
	private void alertErstellen(String alertText) {
		Alert alert = new Alert(AlertType.ERROR, alertText);
		alert.setTitle("Fehler");
		alert.setHeaderText(null);
		alert.initOwner(meineStage);
		alert.showAndWait();
	}
}
