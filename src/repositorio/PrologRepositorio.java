package repositorio;

import modelos.Ingrediente;
import modelos.Receta;
import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;
import servicio.DataService;

import java.util.*;

public class PrologRepositorio {

    private Query platos;
    private Query platos2;

    private static PrologRepositorio prologRepositorio = null;
    private List<Receta> recetas = DataService.getInstance().getAll();

    public PrologRepositorio() {
        this.platos = new Query("consult", new Term[]{new Atom("src\\pp.pl")});
        platos.hasSolution();
        InsertAllRecetas(recetas);

    }

    public static PrologRepositorio prologRepository(){
        if (prologRepositorio == null){
            prologRepositorio = new PrologRepositorio();
        }
        return prologRepositorio;
    }

    private void InsertAllRecetas(List<Receta> recetasrecieve){
        for (Receta aux: recetasrecieve) {
            InsertRecipe(aux);
        }
    }

    public  Set<String> getAllPlatosConBatidora(){
        Set<String> All = new HashSet<>();
        String t1 = "platos_con_batidora(X)";
        platos2 = new Query(t1);
        System.out.println("consulta: "+(platos2.hasSolution() ? "succeded":"failed"));
        while(platos2.hasMoreSolutions()){
            Map aux = platos2.nextSolution();
            System.out.println(aux.get("X"));
            All.add(aux.get("X").toString());

        }
        return All;
    }

    public  Set<String> getAllPlatosSinBatidora(){
        Set<String> All = new HashSet<>();
        String t1 = "sin_batidora(Platos)";

        platos2 = new Query(t1);
        System.out.println("consulta: "+(platos2.hasSolution() ? "succeded":"failed"));
        while(platos2.hasMoreSolutions()){
            Map aux = platos2.nextSolution();
            System.out.println(aux.get("Platos"));
            All.add(aux.get("Platos").toString());

        }
        return All;
    }

    public  Set<String> getAllPlatosSinButter(){
        Set<String> All = new HashSet<>();
        String t1 = "sin_butter(Platos)";
        platos2 = new Query(t1);
        System.out.println("consulta: "+(platos2.hasSolution() ? "succeded":"failed"));
        while(platos2.hasMoreSolutions()){
            Map aux = platos2.nextSolution();
            System.out.println(aux.get("Platos"));
            All.add(aux.get("Platos").toString());

        }
        return All;
    }
    public  Set<String> getAllSinEggBati(){
        Set<String> All = new HashSet<>();
        String t1 = "sin_eggs_bati(Platos)";
        platos2 = new Query(t1);
        System.out.println("consulta: "+(platos2.hasSolution() ? "succeded":"failed"));
        while(platos2.hasMoreSolutions()){
            Map aux = platos2.nextSolution();
            System.out.println(aux.get("Platos"));
            All.add(aux.get("Platos").toString());

        }
        return All;
    }

    public  Set<String> getAllPlatos1Ingredient(String ingrediente){
        Set<String> All = new HashSet<>();

        String t1 = "plato_de1_ingrediente(Platos,"+ingrediente+")";
        platos2 = new Query(t1);
        System.out.println("consulta: "+(platos2.hasSolution() ? "succeded":"failed"));
        while(platos2.hasMoreSolutions()){
            Map aux = platos2.nextSolution();
            System.out.println(aux.get("Platos"));
            All.add(aux.get("Platos").toString());

        }
        return All;
    }

    public  List<String> Stringnombres(List<Ingrediente> ingredientes){
        List<String> nombres = new ArrayList<>();
        for (int i = 0; i < ingredientes.size(); i++){
            nombres.add(ingredientes.get(i).getNombre());
        }
        return nombres;
    }
    public  String transformName(String name){
        String namedone = name.replace(" ", "_");
        return namedone.toLowerCase();
    }

    public  Set<String> getAllPlatosIngredients(List<String> ingrediente){
        Set<String> All = new HashSet<>();
//        List<String> ingredi = Stringnombres(ingrediente);
        String ingredientes = transformLista(ingrediente);

        String t1 = "plato_con_mas_de_1_ingrediente(Platos,"+ingredientes+")";
        platos2 = new Query(t1);
        System.out.println("consulta: "+(platos2.hasSolution() ? "succeded":"failed"));
        while(platos2.hasMoreSolutions()){
            Map aux = platos2.nextSolution();
            System.out.println(aux.get("Platos"));
            All.add(aux.get("Platos").toString());

        }
        return All;
    }

    public  String transformLista(List<String> names){
        String fact ="";
        fact += "[";
        for (int i =0; i < names.size(); i++){
            fact += transformName(names.get(i));
            if (i == names.size()-1){
                fact += "]";
            }else {
                fact += ", ";
            }
        }
        return fact;
    }

    public String ConsultInsert(Receta receta){
        String fact = "receta(";
        fact += transformName(receta.getNombre());
        fact += ", ";
        List<String> ingredi = Stringnombres(receta.getIngrediente());
        fact += transformLista(ingredi);
        fact += ", ";
        fact += transformLista(receta.getUtensilios());
        fact += ", ";
        fact += transformName(receta.getProcedimiento());
        fact += ")";
        //System.out.println(fact);
        return fact;
    }

    public void InsertRecipe(Receta receta){
        String consulta = ConsultInsert(receta);

        String t2 = "assert(plato("+transformName(receta.getNombre())+"))";
        platos = new Query(t2);
        System.out.println("consulta: "+(platos.hasSolution() ? "succeded":"failed"));
        String t1 = "assert("+consulta+")";
        platos = new Query(t1);
        System.out.println("consulta: "+(platos.hasSolution() ? "succeded":"failed"));


    }

}
