package visual;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import servicio.DataService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class CargarXmlController extends Application {

    @FXML
    private TextField inputRutaArchivo;

    @FXML
    private Button btnSeleccionarArchivo;

    @FXML
    private Button btnCargar;

    private Alert alert = null;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("cargar_xml.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Cargar archivo XML");
        stage.setScene(new Scene(root));
        stage.show();
        stage.centerOnScreen();
    }

    @FXML
    private void buscarArchivoXML() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione su archivo XML de recetas.");
        File archivoXml = fileChooser.showOpenDialog(null);
        if(archivoXml == null) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Ha ocurrido un problema al cargar su archivo. Asegurese que no este corrompido.");
            alert.showAndWait();
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
        inputRutaArchivo.setText(archivoXml.getAbsolutePath());
        DataService.getInstance().setArchivoXml(archivoXml);
    }

    @FXML
    private void cargarArchivoXML() {
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            DataService.getInstance().setDocumentoXml(documentBuilder.parse(DataService.getInstance().getArchivoXml()));
            Stage stage = (Stage) btnCargar.getScene().getWindow();
            stage.close();
            VentaPrincipalController app = new VentaPrincipalController();
            app.start(new Stage());
        } catch (Exception e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
