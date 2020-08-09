package repositorio;

import modelos.Ingrediente;
import modelos.Receta;
import org.jpl7.Atom;
import org.jpl7.Query;
import org.jpl7.Term;

import java.util.*;

public class PrologRepositorio {

    private static Query Platos;
    private static Query Plato;
    private static PrologRepositorio prologRepositorio = null;

    public static PrologRepositorio prologRepository(){
        if (prologRepositorio == null){
            prologRepositorio = new PrologRepositorio();
        }
        return prologRepositorio;
    }


    public  Set<String> getAllPlatosConBatidora(){
        Set<String> All = new HashSet<>();
        Query Platos;
        String t1 = "platos_con_batidora(X)";
        Platos = new Query(t1);
        System.out.println("consulta: "+(Platos.hasSolution() ? "succeded":"failed"));
        while(Platos.hasMoreSolutions()){
            Map aux = Platos.nextSolution();
            System.out.println(aux.get("X"));
            All.add(aux.get("X").toString());

        }
        return All;
    }

    public  Set<String> getAllPlatosSinBatidora(){
        Set<String> All = new HashSet<>();
        String t1 = "sin_batidora(Platos)";
        Query Platos;
        Platos = new Query(t1);
        System.out.println("consulta: "+(Platos.hasSolution() ? "succeded":"failed"));
        while(Platos.hasMoreSolutions()){
            Map aux = Platos.nextSolution();
            System.out.println(aux.get("Platos"));
            All.add(aux.get("Platos").toString());

        }
        return All;
    }

    public  Set<String> getAllPlatosSinButter(){
        Set<String> All = new HashSet<>();
        String t1 = "sin_butter(Platos)";
        Query Platos;
        Platos = new Query(t1);
        System.out.println("consulta: "+(Platos.hasSolution() ? "succeded":"failed"));
        while(Platos.hasMoreSolutions()){
            Map aux = Platos.nextSolution();
            System.out.println(aux.get("Platos"));
            All.add(aux.get("Platos").toString());

        }
        return All;
    }
    public  Set<String> getAllSinEggBati(){
        Set<String> All = new HashSet<>();
        String t1 = "sin_eggs_bati(Platos)";
        Query Platos;
        Platos = new Query(t1);
        System.out.println("consulta: "+(Platos.hasSolution() ? "succeded":"failed"));
        while(Platos.hasMoreSolutions()){
            Map aux = Platos.nextSolution();
            System.out.println(aux.get("Platos"));
            All.add(aux.get("Platos").toString());

        }
        return All;
    }

    public  Set<String> getAllPlatos1Ingredient(Ingrediente ingrediente){
        Set<String> All = new HashSet<>();
        String t1 = "plato_de1_ingrediente(Platos,"+ingrediente.getNombre()+")";
        Platos = new Query(t1);
        System.out.println("consulta: "+(Platos.hasSolution() ? "succeded":"failed"));
        while(Platos.hasMoreSolutions()){
            Map aux = Platos.nextSolution();
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

    public  Set<String> getAllPlatosIngredients(List<Ingrediente> ingrediente){
        Set<String> All = new HashSet<>();
        List<String> ingredi = Stringnombres(ingrediente);
        String ingredientes = transformLista(ingredi);

        String t1 = "plato_con_mas_de_1_ingrediente(Platos,"+ingredientes+")";
        Platos = new Query(t1);
        System.out.println("consulta: "+(Platos.hasSolution() ? "succeded":"failed"));
        while(Platos.hasMoreSolutions()){
            Map aux = Platos.nextSolution();
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
        String t2 = "assertz(plato("+receta.getNombre()+"))";
        Platos = new Query(t2);
        ///System.out.println("consulta: "+(Platos.hasSolution() ? "succeded":"failed"));
        String t1 = "assertz("+consulta+")";
        Platos = new Query(t1);
        //System.out.println("consulta: "+(Platos.hasSolution() ? "succeded":"failed"));


    }

}
