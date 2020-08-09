package modelos;

public class Ingrediente {
    private String nombre;
    private Float lipidos;
    private Float proteinas;
    private Float carbohidratos;

    public Ingrediente() {

    }

    public Ingrediente(String nombre, Float lipidos, Float proteinas, Float carbohidratos) {
        this.nombre = nombre;
        this.lipidos = lipidos;
        this.proteinas = proteinas;
        this.carbohidratos = carbohidratos;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Float getLipidos() {
        return lipidos;
    }

    public void setLipidos(Float lipidos) {
        this.lipidos = lipidos;
    }

    public Float getProteinas() {
        return proteinas;
    }

    public void setProteinas(Float proteinas) {
        this.proteinas = proteinas;
    }

    public Float getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(Float carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public String getNombreProlog() {
        String procesado = nombre.replaceAll(" ", "_");
        return procesado.toLowerCase();
    }
}