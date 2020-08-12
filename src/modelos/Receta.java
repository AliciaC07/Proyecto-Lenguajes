package modelos;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;

public class Receta {
    private String nombre;
    private String procedimiento;
    private List<Ingrediente> ingrediente;
    private List<String> utensilios;
    private List<String> accion;

    public Receta() {
        this.ingrediente = new ArrayList<>();
        this.utensilios = new ArrayList<>();
        this.accion = new ArrayList<>();
    }

    public Receta(String nombre, String procedimiento) {
        this.nombre = nombre;
        this.procedimiento = procedimiento;
        this.ingrediente = new ArrayList<>();
        this.utensilios = new ArrayList<>();
        this.accion = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public List<Ingrediente> getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(List<Ingrediente> ingrediente) {
        this.ingrediente = ingrediente;
    }

    public List<String> getUtensilios() {
        return utensilios;
    }

    public void setUtensilios(List<String> utensilios) {
        this.utensilios = utensilios;
    }

    public List<String> getAccion() {
        return accion;
    }

    public void setAccion(List<String> accion) {
        this.accion = accion;
    }

    public String getNombreProlog() {
        String procesado = nombre.replaceAll(" ", "_");
        return procesado.toLowerCase();
    }

    public Float getCarbohidratos() {
        Float carbos = 0.0f;
        for(Ingrediente i : this.getIngrediente()) {
            carbos += i.getCarbohidratos();
        }
        return carbos;
    }

    public Float getLipidos() {
        Float lipidos = 0.0f;
        for(Ingrediente i : this.getIngrediente()) {
            lipidos += i.getLipidos();
        }
        return lipidos;
    }

    public Float getProteinas() {
        Float proteinas = 0.0f;
        for(Ingrediente i : this.getIngrediente()) {
            proteinas += i.getProteinas();
        }
        return proteinas;
    }

    public Float getCalorias(){
        Float calorias = 0.0f;
        Float carbo = 0.0f;
        Float prote = 0.0f;
        Float grasa = 0.0f;
        for(Ingrediente i : this.getIngrediente()) {
            carbo += i.getCarbohidratos() * 4;
            prote += i.getProteinas() * 4;
            grasa += i.getLipidos() * 9;
            calorias += carbo + prote + grasa;
        }
        return calorias;
    }
}