/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.newpoo.project;

/**
 *
 * @author danrcm
 */
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Juego {

    private Scanner sc = new Scanner(System.in);
    private ArrayList<Ficha> lineaJuego;
    private ArrayList<Jugador> jugadores;
    private int turnoActual;
    Random rd = new Random();

    public Juego() {
        lineaJuego = new ArrayList<>();
        jugadores = new ArrayList<>();
    }

    public void EjecutarJuego() {
        //Elige el modo de Juego el cual estara clasificado 
        //en las siguientes condicionales
        int opcion = elegirModoJuego();
        boolean juego = true;
        if (opcion == 1) {
            //crea dos jugadores
            crearJugadores();

            while (juego) {
                obtenerJugadorActual().imprimirMano();
                System.out.println("");
                mostrarLinea();
                //el usuario ingresa una ficha por indice
                if (usuarioIngresaFicha()) {
                    //se verifica si al menos una ficha es jugable por mano
                    if (verificarJugadas()) {
                        System.out.println("Movimiento Valido");
                    } else {
                        System.out.println("\nNo tienes mas movimientos " + obtenerJugadorActual().getNombre());
                        obtenerJugadorActual().imprimirMano();
                        System.out.println("");
                        mostrarLinea();
                        pasarAlSiguienteTurno();
                        System.out.println("El ganador es " + obtenerJugadorActual().getNombre());
                        System.out.println("Felicidades!!!");
                        juego = false;
                    }
                    //en caso de que alguna de las manos se queden sin fichas
                    //el juego finaliza y se presenta al ganador
                    if (obtenerJugadorActual().getMano().isEmpty()) {
                        System.out.println("El ganador es " + obtenerJugadorActual().getNombre());
                        juego = false;
                    }
                    //en caso de elegir una ficha fuera de rango
                } else {
                    System.out.println("Parece que haz elegido una ficha no valida"
                            + "\nIntenta de nuevo :D");
                }
            }
        } else if (opcion == 2) {
            System.out.println("Ingresa tu nombre");
            agregarJugador(sc.next());
            jugadores.add(new Maquina("maquina", Utilitaria.crearManoJugador()));

            while (juego) {
                obtenerJugadorActual().imprimirMano();
                System.out.println("");
                mostrarLinea();
                //el usuario ingresa una ficha por indice
                if (usuarioIngresaFicha()) {
                    //se verifica si al menos una ficha es jugable por mano
                    if (verificarJugadas()) {
                        System.out.println("Movimiento Valido");
                    } else {
                        System.out.println("\nNo tienes mas movimientos " + obtenerJugadorActual().getNombre());
                        obtenerJugadorActual().imprimirMano();
                        System.out.println("");
                        mostrarLinea();
                        pasarAlSiguienteTurno();
                        System.out.println("El ganador es " + obtenerJugadorActual().getNombre());
                        System.out.println("Felicidades!!!");
                        juego = false;
                    }
                    //en caso de que alguna de las manos se queden sin fichas
                    //el juego finaliza y se presenta al ganador
                    if (obtenerJugadorActual().getMano().isEmpty()) {
                        System.out.println("El ganador es " + obtenerJugadorActual().getNombre());
                        juego = false;
                    }
                    //en caso de elegir una ficha fuera de rango
                } else {
                    System.out.println("Parece que haz elegido una ficha no valida"
                            + "\nIntenta de nuevo :D");
                }
            }

        }
    }

    public boolean usuarioIngresaFicha() {
        if (obtenerJugadorActual() instanceof Maquina) {
            int num = rd.nextInt(obtenerJugadorActual().getMano().size());
            if (agregarFichaALinea(obtenerJugadorActual().getFicha(num), obtenerJugadorActual())) {
                if (obtenerJugadorActual() instanceof Maquina) {
                    obtenerJugadorActual().removerFicha(obtenerJugadorActual().getFicha(num));
                    pasarAlSiguienteTurno();
                    return true;
                } else {
                    return true;
                }
            } else {
                return false;
            }
        } else {
            int opcion;
            //se verifica que la ficha este dentro del rango 
            System.out.println("Ingresa tu ficha " + obtenerJugadorActual().getNombre());
            opcion = sc.nextInt();
            //se verifica que la ficha este dentro del rango 
            if (opcion <= obtenerJugadorActual().getMano().size()) {
                //se agrega la ficha a la linea
                agregarFichaALinea(obtenerJugadorActual().getFicha(opcion - 1), obtenerJugadorActual());
                //removemos la ficha ingresada de la mano del usuario
                obtenerJugadorActual().removerFicha(obtenerJugadorActual().getFicha(opcion - 1));
                pasarAlSiguienteTurno();
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean agregarFichaALinea(Ficha ficha, Jugador jugador) {
        if (obtenerJugadorActual() instanceof Maquina) {
            if (ficha instanceof FichaComodin) {
                validacionFichaComodinMaquina(ficha);
                return true;
            } else {
                validacionFichaComunMaquina(ficha);
                return true;
            }
        } else {
            if (validarFichaEnLineaVacia(ficha)) {
                return true;
            } else if (ficha instanceof FichaComodin) {
                return validacionFichaComodin(ficha);
            } else {
                return validacionFichaComun(ficha);
            }
        }
    }

    public boolean validacionFichaComodinMaquina(Ficha ficha) {
        FichaComodin fc = new FichaComodin();
        fc.setLado1(lineaJuego.get(lineaJuego.size() - 1).getLado2());
        fc.setLado2(rd.nextInt(6) + 1);
        lineaJuego.add(fc);
        return true;
    }

    public boolean validacionFichaComunMaquina(Ficha ficha) {
        boolean bucle = true;
        while (bucle) {
            if (lineaJuego.get(0).getLado1() == ficha.getLado2()) {
                lineaJuego.add(0, ficha);
                return true;

            } else if (lineaJuego.get(lineaJuego.size() - 1).getLado2() == ficha.getLado1()) {
                lineaJuego.add(ficha);
                return true;

            } else {
                usuarioIngresaFicha();
                return true;
            }
        }
        return false;
    }

    public boolean validarFichaEnLineaVacia(Ficha ficha) {
        if (lineaJuego.isEmpty()) {
            if (ficha instanceof FichaComodin) {
                validacionFichaComodin(ficha);
            } else {
                lineaJuego.add(ficha);
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean validacionFichaComun(Ficha ficha) {
        boolean bucle = true;
        while (bucle) {
            if (lineaJuego.get(0).getLado1() == ficha.getLado2()) {
                lineaJuego.add(0, ficha);
                return true;

            } else if (lineaJuego.get(lineaJuego.size() - 1).getLado2() == ficha.getLado1()) {
                lineaJuego.add(ficha);
                return true;

            } else {
                System.out.println("La ficha ingresada no es valida para este juego");
                usuarioIngresaFicha();
                return true;
            }
        }
        return false;
    }

    public Boolean validacionFichaComodin(Ficha ficha) {
        int lado1 = 1;
        int lado2 = 1;
        String pos;
        boolean rango = true;
        FichaComodin f = new FichaComodin();
        while (rango) {
            if (!lineaJuego.isEmpty()) {

                System.out.println("Ingresa la posicion de la ficha \"Inicio\" o \"Fin\"");
                pos = sc.next();
                if (pos.equalsIgnoreCase("Inicio")) {
                    f.setLado2(lineaJuego.get(0).getLado1());
                    System.out.println("Ingresa el valor de lado 1 para esta ficha");
                    lado1 = sc.nextInt();
                    f.setLado1(lado1);

                } else if (pos.equalsIgnoreCase("Fin")) {
                    f.setLado1(lineaJuego.get(lineaJuego.size() - 1).getLado2());
                    System.out.println("Ingresa el valor de lado 2 para esta ficha");
                    lado2 = sc.nextInt();
                    f.setLado2(lado2);

                } else {
                    System.out.println("Ingresa una posicion valida");
                    continue;
                }
                //se verifica que los valores de las fichas esten dentro del rango
                //requerido, en caso de que no se reinicia el ciclo
                if (lado2 > 0 && lado1 > 0 && lado1 < 7 && lado2 < 7) {
                    rango = false;
                    if (pos.equalsIgnoreCase("Inicio")) {
                        lineaJuego.add(0, f);
                    } else {
                        lineaJuego.add(f);
                    }
                } else {
                    System.out.println("Parece que haz ingresado valores fuera del rango"
                            + " permitido para las fichas (de 1 a 6)");
                    lado1 = 1;
                    lado2 = 1;
                }
            } else {
                System.out.println("Ingresa el valor de lado 1 para esta ficha");
                lado1 = sc.nextInt();
                f.setLado1(lado1);
                System.out.println("Ingresa el valor de lado 2 para esta ficha");
                lado2 = sc.nextInt();
                f.setLado2(lado2);
                if (lado2 > 0 && lado1 > 0 && lado1 < 7 && lado2 < 7) {
                    rango = false;
                    lineaJuego.add(f);
                } else {
                    System.out.println("Parece que haz ingresado valores fuera del rango"
                            + " permitido para las fichas (de 1 a 6)");
                }
            }
        }

        return true;
    }

    public boolean verificarJugadas() {
        for (int i = 0; i < obtenerJugadorActual().getMano().size(); i++) {
            //se verifica si es posible la jugada y se excluye la ficha comodin
            if (obtenerJugadorActual().getFicha(i).getLado2() == lineaJuego.get(0).getLado1()
                    || obtenerJugadorActual().getFicha(i).getLado1() == lineaJuego.get(lineaJuego.size() - 1).getLado2()
                    || obtenerJugadorActual().getFicha(i) instanceof FichaComodin) {
                return true;
            }
        }
        return false;
    }

    public int elegirModoJuego() {
        boolean seleccion = true;
        int opcion = 0;
        while (seleccion) {
            System.out.println("Bienvenido al domino Politecnico");
            System.out.println("Por favor selecciona el modo de juego que deseas");
            System.out.println("1.Jugar contra oponente");
            System.out.println("2.Jugar contra maquina");
            System.out.println("3.Salir");
            opcion = sc.nextInt();
            if (opcion < 4 && opcion > 0) {
                seleccion = false;
            } else {
                System.out.println("Por favor ingrese un indice valido");
            }
        }
        return opcion;
    }

    public void crearJugadores() {
        System.out.println("Ingresar nombre de Jugador 1");
        agregarJugador(sc.next());
        System.out.println("Ingresar nombre de Jugador 2");
        agregarJugador(sc.next());

    }

    public Jugador obtenerJugadorActual() {
        return jugadores.get(turnoActual);
    }

    public void pasarAlSiguienteTurno() {
        turnoActual = (turnoActual + 1) % jugadores.size();
    }

    public void agregarJugador(String nombre) {
        Jugador jugador = new Jugador(nombre, Utilitaria.crearManoJugador());
        jugadores.add(jugador);
    }

    public int obtenerValorInicioLinea() {
        return lineaJuego.get(0).getLado1();
    }

    public int obtenerValorFinLinea() {
        return lineaJuego.get(lineaJuego.size() - 1).getLado2();
    }

    public void mostrarLinea() {
        System.out.print("Línea de juego -> ");
        for (int i = 0; i < lineaJuego.size(); i++) {
            if (i != (lineaJuego.size() - 1)) {
                System.out.print(lineaJuego.get(i) + " - ");
            } else {
                System.out.println(lineaJuego.get(i));
            }
        }
    }
}
