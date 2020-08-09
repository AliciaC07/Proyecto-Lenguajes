package visual;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.stage.Stage;
import modelos.Ingrediente;
import modelos.Receta;
import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.w3c.dom.Document;
import repositorio.PrologRepositorio;
import servicio.DataService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VentaPrincipalController extends Application {

    @FXML
    private ComboBox<String> cbxFiltro;

    @FXML
    private Button btnBuscar;

    @FXML
    private TextField cbxIngrediente1;

    @FXML
    private TextField cbxIngrediente2;

    @FXML
    private TextField cbxIngrediente3;

    @FXML
    private TableView<Receta> tablaResultados;

    @FXML
    private TableColumn<Receta, String> columnaReceta;

    @FXML
    private TableColumn<Receta, Float> columnaCarbos;

    @FXML
    private TableColumn<Receta, Float> columnaProteinas;

    @FXML
    private TableColumn<Receta, Float> columnaLipidos;

    @FXML
    private TableColumn<Receta, Float> columnaCalorias;

    private DataService dataService;

    private List<Receta> recetas = DataService.getInstance().getAll();

    private PrologRepositorio prologRepositorio = PrologRepositorio.prologRepository();;

    private Set<String> platos = new HashSet<>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("ventana_principal.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Calculadora de Dietas");
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
        stage.centerOnScreen();
    }

    public void initialize() {
        cbxFiltro.getItems().clear();
        cbxFiltro.getItems().addAll(
                "¿Cuáles platos se pueden preparar si tengo un ingrediente específico?",
                "¿Cuáles platos se pueden preparar si tengo 3 ingredientes específicos?",
                "¿Cuáles   platos   implican   usar   la   batidora?",
                "Si no tengo batidora, ¿cuáles platos puedo hacer?",
                "Si no tengo mantequilla, ¿qué plato puedo preparar?",
                "Si no tengo huevos ni tengo batidora, ¿qué platos puedo preparar?");
        cbxFiltro.getSelectionModel().selectFirst();
        this.cambiarEstadoPreguntas();
        columnaReceta.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaCarbos.setCellValueFactory(new PropertyValueFactory<>("carbohidratos"));
        columnaProteinas.setCellValueFactory(new PropertyValueFactory<>("proteinas"));
        columnaLipidos.setCellValueFactory(new PropertyValueFactory<>("lipidos"));
        ObservableList<Receta> modelo = FXCollections.observableArrayList(recetas);
        tablaResultados.setItems(modelo);

    }

    private Boolean primeraPreguntaSeleccionada() {
        return cbxFiltro.getSelectionModel().isSelected(0);
    }

    private Boolean segundaPreguntaSeleccionada() {
        return cbxFiltro.getSelectionModel().isSelected(1);
    }

    private Boolean otraPreguntaSeleccionada() {
        return !(primeraPreguntaSeleccionada() && segundaPreguntaSeleccionada());
    }

    private void seleccionarUnIngrediente() {
        cbxIngrediente1.setDisable(false);
        cbxIngrediente2.setDisable(true);
        cbxIngrediente3.setDisable(true);
    }

    private void seleccionarNingunIngrediente() {
        cbxIngrediente1.setDisable(true);
        cbxIngrediente2.setDisable(true);
        cbxIngrediente3.setDisable(true);
    }

    private void seleccionarTodosIngredientes() {
        cbxIngrediente1.setDisable(false);
        cbxIngrediente2.setDisable(false);
        cbxIngrediente3.setDisable(false);
    }


    @FXML
    private void buscarRecetas() {
        Set<Receta> filtro = new HashSet<>();
        List<String> nomres = new ArrayList<>();
        prologRepositorio = PrologRepositorio.prologRepository();
        switch (cbxFiltro.getSelectionModel().getSelectedItem()) {
            case "¿Cuáles platos se pueden preparar si tengo un ingrediente específico?":
                platos = prologRepositorio.getAllPlatos1Ingredient(cbxIngrediente1.getText());
                filtro.addAll(findrecetas(platos));

                break;
            case "¿Cuáles platos se pueden preparar si tengo 3 ingredientes específicos?":
                nomres.add(cbxIngrediente1.getText());
                nomres.add(cbxIngrediente2.getText());
                nomres.add(cbxIngrediente3.getText());
                platos = prologRepositorio.getAllPlatosIngredients(nomres);
                filtro.addAll(findrecetas(platos));
                break;
            case "¿Cuáles   platos   implican   usar   la   batidora?":
                platos = prologRepositorio.getAllPlatosConBatidora();
                filtro.addAll(findrecetas(platos));
                break;
            case "Si no tengo batidora, ¿cuáles platos puedo hacer?":
                platos = prologRepositorio.getAllPlatosSinBatidora();
                filtro.addAll(findrecetas(platos));
                break;
            case "Si no tengo mantequilla, ¿qué plato puedo preparar?":
                platos = prologRepositorio.getAllPlatosSinButter();
                filtro.addAll(findrecetas(platos));
                break;
            case "Si no tengo huevos ni tengo batidora, ¿qué platos puedo preparar?":
                platos = prologRepositorio.getAllSinEggBati();
                filtro.addAll(findrecetas(platos));
                break;
            default:
                break;
        }
        ObservableList<Receta> modelo = FXCollections.observableArrayList(filtro);
        tablaResultados.setItems(modelo);
    }
    private List<Receta> findrecetas(Set<String> nombres){
        List<Receta> response = new ArrayList<>();
        for (Receta aux: recetas) {
            for (String aux2: nombres) {
                if (aux.getNombre().equalsIgnoreCase(transformName(aux2))){
                    response.add(aux);
                }
            }
        }
        return response;
    }
    public  String transformName(String name){
        String namedone = name.replace("_", " ");
        return namedone.toLowerCase();
    }

    @FXML
    private void cambiarEstadoPreguntas() {
        if(primeraPreguntaSeleccionada()) {
            seleccionarUnIngrediente();
        } else if(segundaPreguntaSeleccionada()) {
            seleccionarTodosIngredientes();
        } else {
            seleccionarNingunIngrediente();
        }
    }
}
