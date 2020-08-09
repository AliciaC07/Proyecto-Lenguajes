:- dynamic plato/1.
:- dynamic receta/4.
:- dynamic accion/1.
:- dynamic utensilio/1.
:- dynamic procemiento/1.

%En esta funcion va a devolver true cuando el elemento que se quiere buscar esta en la lista.
miembro(X,[X|_]):- !.
miembro(X,[_|L]):- miembro(X,L).

utensilio(batidora).


procedimiento(batir).
procedimiento(mezclar).

%plato(nombre)
plato(cake).
plato(brownie).
plato(cupcake).

%Se tiene que la receta(plato, ingredientes, utencilio, procedimiento).
%receta(cake, [vainilla, huevo, harina],[batidora], [batir]).
%receta(brownie, [huevo, harina, chocolate, polvo_hornear, mantequilla], [colador], mezclar).
%receta(cupcake, [huevo, harina, polvo_hornear, mantequilla, vainilla],[batidora], batir).

%Regla para que devuelva los platos que tienen ese ingrediente especifico.
plato_de1_ingrediente(Plato, Ingrediente):- plato(Plato), receta(Plato, Ingredientes,_,_), miembro(Ingrediente,Ingredientes).

%Esta regla devuelve los platos con mas de un ingrediente dado.
plato_con_mas_de_1_ingrediente(_,[]).
plato_con_mas_de_1_ingrediente(Plato,[Cabeza|Cola]):-
        plato(Plato), receta(Plato, Ingredientes,_, _),
        miembro(Cabeza, Ingredientes),
        plato_con_mas_de_1_ingrediente(Plato, Cola).

%En esta regla va a devolver todos los platos que necesiten de una batidora.
platos_con_batidora(Plato):- plato(Plato), receta(Plato, _, Utensilio,_), miembro(batidora, Utensilio).

%En esta regla va a devolver todos los platos que no necesiten de una batidora.
sin_batidora(Plato):- plato(Plato), receta(Plato, _, Utensilio,_), not(miembro(batidora, Utensilio)).

%En esta regla va a devolver todos los platos que no tienen mantequilla.
sin_butter(Plato):- plato(Plato), receta(Plato, Ingredientes, _, _), not(miembro(mantequilla, Ingredientes)).

%En esta regla va a devolver todos los platos que no tienen ni huevo ni se usa una batidora.
sin_eggs_bati(Plato):- plato(Plato), receta(Plato, Ingredientes, Utensilio, _), not(miembro(huevo, Ingredientes)), not(miembro(batidora, Utensilio)).