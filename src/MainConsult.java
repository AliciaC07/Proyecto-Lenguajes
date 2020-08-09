import modelos.Ingrediente;
import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import repositorio.PrologRepositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainConsult {
    private static Query Platos;
    public static void main(String[] args) {
        PrologRepositorio prologRepositorio = PrologRepositorio.prologRepository();
        Query q1 = new Query("consult", new Term[]{new Atom("src\\pp.pl")});
        System.out.println("consulta: "+(q1.hasSolution() ? "succeded":"failed"));
        Ingrediente ingrediente = new Ingrediente();
        ingrediente.setNombre("mantequilla");
        ingrediente.setCarbohidratos(Float.parseFloat("10"));
        ingrediente.setProteinas(Float.parseFloat("10"));
        ingrediente.setLipidos(Float.parseFloat("10"));
        Ingrediente ingrediente1 = new Ingrediente();
        ingrediente1.setNombre("huevo");
        ingrediente1.setCarbohidratos(Float.parseFloat("10"));
        ingrediente1.setProteinas(Float.parseFloat("10"));
        ingrediente1.setLipidos(Float.parseFloat("10"));
        Ingrediente ingrediente2 = new Ingrediente();
        ingrediente2.setNombre("harina");
        ingrediente2.setCarbohidratos(Float.parseFloat("10"));
        ingrediente2.setProteinas(Float.parseFloat("10"));
        ingrediente2.setLipidos(Float.parseFloat("10"));
        List<Ingrediente> ingredientes = new ArrayList<>();
        ingredientes.add(ingrediente);
        ingredientes.add(ingrediente1);
        ingredientes.add(ingrediente2);
        Set<String> plates = prologRepositorio.getAllPlatosIngredients(ingredientes);


    }
}
