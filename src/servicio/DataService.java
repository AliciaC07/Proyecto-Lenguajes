package servicio;

import modelos.Ingrediente;
import modelos.Receta;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DataService {

    private File archivoXml;
    private Document documentoXml;
    private static DataService dataService = null;

    public static DataService getInstance() {
        if(dataService == null) {
            dataService = new DataService();
        }
        return dataService;
    }

    public List<Receta> getAll() {
        List<Receta> resultado = new ArrayList<>();
        NodeList recetas = documentoXml.getElementsByTagName("receta");
        NodeList procedimientos = documentoXml.getElementsByTagName("procedimiento");
        NodeList ingredientes = documentoXml.getElementsByTagName("ingredientes");
        NodeList acciones = documentoXml.getElementsByTagName("acciones");
        NodeList utensilios = documentoXml.getElementsByTagName("utensilios");
        for(int i = 0; i < recetas.getLength(); i++) {
            Receta nuevaReceta = new Receta();
            Node recetaActual = recetas.item(i);
            Node procedimientoActual = procedimientos.item(i);
            Node ingredientesActual = ingredientes.item(i);
            Node accionesActual = acciones.item(i);
            Node utensiliosActual = utensilios.item(i);
            Element elementoRecetaActual = (Element) recetaActual;
            Element elementoProcedimientoActual = (Element) procedimientoActual;
            Element elementoIngredientesActual = (Element) ingredientesActual;
            Element elementoAccionesActual = (Element) acciones.item(i);
            Element elementoUtensiliosActual = (Element) utensilios.item(i);
            nuevaReceta.setNombre(elementoRecetaActual.getElementsByTagName("nombre").item(0).getTextContent());
            nuevaReceta.setProcedimiento(elementoProcedimientoActual.getElementsByTagName("descripcion").item(0).getTextContent());

            NodeList nodosIngrediente = elementoIngredientesActual.getElementsByTagName("ingrediente");
            for(int j = 0; j < nodosIngrediente.getLength(); j++) {
                Node nodoIngredienteActual = nodosIngrediente.item(j);
                Element elementoIngredienteActual = (Element) nodoIngredienteActual;
                Ingrediente nuevoIngrediente = new Ingrediente();
                nuevoIngrediente.setNombre(elementoIngredienteActual.getElementsByTagName("nombre").item(0).getTextContent());
                nuevoIngrediente.setProteinas(Float.valueOf(elementoIngredienteActual.getElementsByTagName("proteinas").item(0).getTextContent()));
                nuevoIngrediente.setLipidos(Float.valueOf(elementoIngredienteActual.getElementsByTagName("lipidos").item(0).getTextContent()));
                nuevoIngrediente.setCarbohidratos(Float.valueOf(elementoIngredienteActual.getElementsByTagName("carbohidratos").item(0).getTextContent()));
                nuevaReceta.getIngrediente().add(nuevoIngrediente);
            }

            NodeList nodosAcciones = elementoAccionesActual.getElementsByTagName("accion");
            for(int j = 0; j < nodosAcciones.getLength(); j++) {
                Node nodoAccionActual = nodosAcciones.item(j);
                Element elementAccionActual = (Element) nodoAccionActual;
                nuevaReceta.getAccion().add(elementAccionActual.getTextContent());
            }

            NodeList nodosUtensilios = elementoUtensiliosActual.getElementsByTagName("utensilio");
            for(int j = 0; j < nodosUtensilios.getLength(); j++) {
                Node nodoUtensilioActual = nodosUtensilios.item(j);
                Element elementoUtensilioActual = (Element) nodoUtensilioActual;
                nuevaReceta.getUtensilios().add(elementoUtensilioActual.getTextContent());
            }
            resultado.add(nuevaReceta);
        }
        return resultado;
    }

    public File getArchivoXml() {
        return archivoXml;
    }

    public void setArchivoXml(File archivoXml) {
        this.archivoXml = archivoXml;
    }

    public Document getDocumentoXml() {
        return documentoXml;
    }

    public void setDocumentoXml(Document documentoXml) {
        this.documentoXml = documentoXml;
    }
}
