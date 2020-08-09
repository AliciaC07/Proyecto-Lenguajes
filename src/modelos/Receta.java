package modelos;

import java.util.List;

public class Receta {
    private String nombre;
    private String procedimiento;
    private List<Ingrediente> ingrediente;
    private List<String> utensilios;
    private List<String> accion;

    public Receta(String nombre, String procedimiento, List<Ingrediente> ingrediente, List<String> utensilios, List<String> accion) {
        this.nombre = nombre;
        this.procedimiento = procedimiento;
        this.ingrediente = ingrediente;
        this.utensilios = utensilios;
        this.accion = accion;
    }

    public Receta() {

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
}
