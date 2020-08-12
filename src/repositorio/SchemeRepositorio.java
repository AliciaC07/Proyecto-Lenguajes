package repositorio;

import jscheme.JScheme;

public class SchemeRepositorio {

    private static SchemeRepositorio schemeRepositorio = null;

    public static SchemeRepositorio SchemeRepository(){
        if (schemeRepositorio == null){
            schemeRepositorio = new SchemeRepositorio();
        }
        return schemeRepositorio;
    }

    public Float IMC(Float peso, Float altura){
        JScheme scheme = new JScheme();
        Float alturametro = altura / 100;
        String query = "";
        query = "(/ "+peso+" (expt "+alturametro+" 2))";
        String resultado =  scheme.eval(query).toString();
        return Float.valueOf(resultado);
    }
    public Float CalculargrasaCorporalHombre(Float imc, Integer edad){
        JScheme scheme = new JScheme();
        String query = "";
        query = "(-(- (+ (* "+imc+" 1.2) (* 0.23 "+edad+")) 10.8)5.4)";
        String resultado = scheme.eval(query).toString();
        return Float.valueOf(resultado);
    }
    public Float CalculargrasaCorporalMujer(Float imc, Integer edad){
        JScheme scheme = new JScheme();
        String query = "";
        query = "(- (+ (* "+imc+" 1.2) (* 0.23 "+edad+"))5.4)";
        String resultado =  scheme.eval(query).toString();
        return Float.valueOf(resultado);
    }

    public Float CalcularCaloriasMantenerPesoFemenino(String estado, Float peso, Float alturacm, Integer edad){
        JScheme scheme = new JScheme();
        String query = "";
        String resultado = "";
        switch (estado) {
            case "Sedentario":
                query = "(*(- (+ 655.1 (* 9.463 " + peso + ") (* 1.8 " + alturacm + ")) (* 4.6756 " + edad + "))1.2)";
                resultado = scheme.eval(query).toString();
                break;

            case "Leve Activo":
                query = "(*(- (+ 655.1 (* 9.463 " + peso + ") (* 1.8 " + alturacm + ")) (* 4.6756 " + edad + ")) 1.375)";
                resultado = scheme.eval(query).toString();
                break;

            case "Moderado Activo":
                query = "(*(- (+ 655.1 (* 9.463 " + peso + ") (* 1.8 " + alturacm + ")) (* 4.6756 " + edad + ")) 1.55)";
                resultado = scheme.eval(query).toString();
                break;

            case "Muy Activo":
                query = "(*(- (+ 655.1 (* 9.463 " + peso + ") (* 1.8 " + alturacm + ")) (* 4.6756 " + edad + ")) 1.725)";
                resultado = scheme.eval(query).toString();
                break;

            case "Hiperactivo":
                query = "(*(- (+ 655.1 (* 9.463 " + peso + ") (* 1.8 " + alturacm + ")) (* 4.6756 " + edad + ")) 1.9)";
                resultado = scheme.eval(query).toString();
                break;
        }
        return  Float.valueOf(resultado);
    }

    public Float CalcularCaloriasMantenerPesoMasculino(String estado, Float peso, Float alturacm, Integer edad){
        JScheme scheme = new JScheme();
        String query = "";
        String resultado = "";
        switch (estado) {
            case "Sedentario":
                query = "(*(- (+ 66.473 (* 13.751 "+peso+") (* 5.0033 "+alturacm+")) (* 6.7550 "+edad+"))1.2)";
                resultado = scheme.eval(query).toString();
                break;

            case "Leve Activo":
                query = "(*(- (+ 66.473 (* 13.751 "+peso+") (* 5.0033 "+alturacm+")) (* 6.7550 "+edad+")) 1.375)";
                resultado = scheme.eval(query).toString();
                break;

            case "Moderado Activo":
                query = "(*(- (+ 66.473 (* 13.751 "+peso+") (* 5.0033 "+alturacm+")) (* 6.7550 "+edad+")) 1.55)";
                resultado = scheme.eval(query).toString();
                break;

            case "Muy Activo":
                query = "(*(- (+ 66.473 (* 13.751 "+peso+") (* 5.0033 "+alturacm+")) (* 6.7550 "+edad+")) 1.725)";
                resultado = scheme.eval(query).toString();
                break;

            case "Hiperactivo":
                query = "(*(- (+ 66.473 (* 13.751 "+peso+") (* 5.0033 "+alturacm+")) (* 6.7550 "+edad+")) 1.9)";
                resultado = scheme.eval(query).toString();
                break;
        }
        return Float.valueOf(resultado);
    }

    public Float CalcularCaloriasDisminuirPesoMasculino(String estado, Float peso, Float alturacm, Integer edad){
        JScheme scheme = new JScheme();
        String query = "";
        Float pesomantener = CalcularCaloriasMantenerPesoMasculino(estado,  peso,  alturacm,  edad);
        String resultado = "";
        query = "(- "+pesomantener+" 500)";
        resultado =  scheme.eval(query).toString();
        return Float.valueOf(resultado);
    }

    public Float CalcularCaloriasDisminuirPesoFemenino(String estado,  Float peso, Float alturacm, Integer edad){
        JScheme scheme = new JScheme();
        String query = "";
        Float pesomantener = CalcularCaloriasMantenerPesoFemenino(estado,   peso,  alturacm,  edad);
        String resultado = "";
        query = "(- "+pesomantener+" 500)";
        resultado =  scheme.eval(query).toString();
        return Float.valueOf(resultado);
    }
    public Float CalcularCaloriasAumentarPesoFemenino(String estado,  Float peso, Float alturacm, Integer edad){
        JScheme scheme = new JScheme();
        String query = "";
        Float pesomantener = CalcularCaloriasMantenerPesoFemenino(estado,  peso,  alturacm,  edad);
        String resultado = "";
        query = "(+ "+pesomantener+" 500)";
        resultado =  scheme.eval(query).toString();
        return Float.valueOf(resultado);
    }

    public Float CalcularCaloriasAumentarPesoMasculino(String estado,  Float peso, Float alturacm, Integer edad){
        JScheme scheme = new JScheme();
        String query = "";
        Float pesomantener = CalcularCaloriasMantenerPesoMasculino(estado,  peso,  alturacm,  edad);
        String resultado = "";
        query = "(+ "+pesomantener+" 500)";
        resultado =  scheme.eval(query).toString();
        return Float.valueOf(resultado);
    }

}
