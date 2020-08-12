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
import jscheme.JScheme;
import modelos.Ingrediente;
import modelos.Receta;
import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import org.w3c.dom.Document;
import repositorio.PrologRepositorio;
import repositorio.SchemeRepositorio;
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
    @FXML
    private RadioButton rdbtnMasculino;

    @FXML
    private ToggleGroup groupSexo;

    @FXML
    private RadioButton rdbtnFemenino;

    @FXML
    private TextField inputEdad;

    @FXML
    private TextField inputAltura;

    @FXML
    private TextField alturaPeso;

    @FXML
    private ComboBox<String> cbxActividad;

    @FXML
    private TextField inputGrasaCorporal;

    @FXML
    private TextField inputMantenerPeso;

    @FXML
    private TextField inputBajarPeso;

    @FXML
    private TextField inputSubirPeso;

    @FXML
    private ToggleGroup groupPesos;

    @FXML
    private TableView<Receta> tablaPlatos;

    @FXML
    private TableColumn<Receta, String> columnaNombrePlato;

    @FXML
    private TableColumn<Receta, Integer> columnaCaloriasPlato;

    @FXML
    private RadioButton rdbtnAumentarPeso;

    @FXML
    private RadioButton rdbtnBajarPeso;

    @FXML
    private RadioButton rdbtnMantenerPeso;

    private DataService dataService;

    private List<Receta> recetas = DataService.getInstance().getAll();

    private PrologRepositorio prologRepositorio = PrologRepositorio.prologRepository();

    private SchemeRepositorio schemeRepositorio = SchemeRepositorio.SchemeRepository();

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
        cbxActividad.getItems().addAll(
                "Sedentario",
                "Leve Activo",
                "Moderado Activo",
                "Muy Activo",
                "Hiperactivo"
        );
        cbxFiltro.getSelectionModel().selectFirst();
        cbxActividad.getSelectionModel().selectFirst();
        this.cambiarEstadoPreguntas();
        columnaReceta.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaCarbos.setCellValueFactory(new PropertyValueFactory<>("carbohidratos"));
        columnaProteinas.setCellValueFactory(new PropertyValueFactory<>("proteinas"));
        columnaLipidos.setCellValueFactory(new PropertyValueFactory<>("lipidos"));
        columnaNombrePlato.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        columnaCaloriasPlato.setCellValueFactory(new PropertyValueFactory<>("calorias"));
        ObservableList<Receta> modeloPlatos = FXCollections.observableArrayList(recetas);
        ObservableList<Receta> modelo = FXCollections.observableArrayList(recetas);
        tablaResultados.setItems(modelo);
        tablaPlatos.setItems(modeloPlatos);

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
    private Boolean validarCamposCalculo() {
        return inputAltura.getText().length() > 0 && inputEdad.getText().length() > 0 &&
                alturaPeso.getText().length() > 0;
    }

    @FXML
    private void calcularResultados() {
        Float altura = Float.valueOf(inputAltura.getText());
        Integer edad = Integer.valueOf(inputEdad.getText());
        Float peso = Float.valueOf(alturaPeso.getText());
        Float grasa_corporal = 0.0f;
        Float caloriasMantenerPeso = 0.0f;
        Float caloriasBajarPeso = 0.0f;
        Float caloriasSubirPeso = 0.0f;
        String estado = cbxActividad.getSelectionModel().getSelectedItem();
        if(rdbtnFemenino.isSelected()) {
            // Hacer calculos femeninos
            Float imc = schemeRepositorio.IMC(peso, altura);
            grasa_corporal = schemeRepositorio.CalculargrasaCorporalMujer(imc, edad);
            String sexo = "Femenino";
            caloriasMantenerPeso = schemeRepositorio.CalcularCaloriasMantenerPesoFemenino(estado,peso,altura, edad);
            caloriasBajarPeso = schemeRepositorio.CalcularCaloriasDisminuirPesoFemenino(estado,peso,altura, edad);
            caloriasSubirPeso = schemeRepositorio.CalcularCaloriasAumentarPesoFemenino(estado,peso,altura, edad);
        } else {
            // Hacer calculos masculinos
            Float imc = schemeRepositorio.IMC(peso, altura);
            grasa_corporal = schemeRepositorio.CalculargrasaCorporalHombre(imc, edad);
            String sexo = "Masculino";
            caloriasMantenerPeso = schemeRepositorio.CalcularCaloriasMantenerPesoMasculino(estado,peso,altura, edad);
            caloriasBajarPeso = schemeRepositorio.CalcularCaloriasDisminuirPesoMasculino(estado,peso,altura, edad);
            caloriasSubirPeso = schemeRepositorio.CalcularCaloriasAumentarPesoMasculino(estado,peso,altura, edad);
        }

        inputBajarPeso.setText(String.valueOf(caloriasBajarPeso));
        inputGrasaCorporal.setText(String.valueOf(grasa_corporal));
        inputMantenerPeso.setText(String.valueOf(caloriasMantenerPeso));
        inputSubirPeso.setText(String.valueOf(caloriasSubirPeso));
    }


    @FXML
    private void filtrarTablaPlatos(){
        // Lista para filtrar!!!
        List<Receta> filtro = new ArrayList<>();
        if(rdbtnAumentarPeso.isSelected()) {
            // logica de filtrado para aumentar peso aqui!!!
            filtro = PlatosCaloriasMantener(recetas);
        } else if(rdbtnMantenerPeso.isSelected()) {
            // logica de filtrado mantener peso aqui!!!
            filtro = PlatosCaloriasMantener(recetas);
        } else {
            // logica de filtrado pra bajar peso aqui!!!
            filtro = PlatosCaloriasMantener(recetas);
        }
        ObservableList<Receta> modelo = FXCollections.observableArrayList(filtro);
        tablaPlatos.setItems(modelo);
    }

    public List<Receta> PlatosCaloriasMantener(List<Receta> recetaList){
        List<Receta> response = new ArrayList<>();
        Float calorias = 0.0f;
        Float carbo = 0.0f;
        Float prote = 0.0f;
        Float grasa = 0.0f;
        if (rdbtnAumentarPeso.isSelected()){
            for (Receta aux: recetaList) {
                calorias += aux.getCalorias();
                if (calorias < Float.parseFloat(inputSubirPeso.getText())){
                    response.add(aux);
               }
        }

        }else if(rdbtnMantenerPeso.isSelected()){
            for (Receta aux: recetaList) {
                calorias += aux.getCalorias();
                if (calorias <= Float.parseFloat(inputMantenerPeso.getText())){
                    response.add(aux);
                }
            }
        }else {
            for (Receta aux: recetaList) {
                calorias += aux.getCalorias();
                if (calorias < Float.parseFloat(inputBajarPeso.getText())){
                    response.add(aux);
                }
            }
        }
        return response;
    }
}
