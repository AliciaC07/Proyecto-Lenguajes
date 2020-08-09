
import org.jpl7.Atom;
import org.jpl7.JPL;
import org.jpl7.Query;
import org.jpl7.Term;

import java.util.ArrayList;
import java.util.Map;

public class Prueba {
    private static Query Platos;

    public static void main(String[] args) {
        Query q1 = new Query("consult", new Term[]{new Atom("src\\pp.pl")});
        System.out.println("consulta: "+(q1.hasSolution() ? "succeded":"failed"));
        ArrayList lista = getAllFriends();

    }

    public static ArrayList<String> getAllFriends(){
        ArrayList<String> All = new ArrayList();
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
}
