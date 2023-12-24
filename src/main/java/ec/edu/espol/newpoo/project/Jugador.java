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

public class Jugador {
    private String nombre;
    private ArrayList<Ficha> mano;

    public Jugador(String nombre, ArrayList<Ficha> mano) {
        this.nombre = nombre;
        this.mano = mano;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Ficha> getMano() {
        return mano;
    }

    public Ficha getFicha(int i) {
        if(i >= 0 && i < this.mano.size())
            return mano.get(i);
        else
            return null;
    }

    public void imprimirMano() {
        System.out.print("Jugador " + nombre + ": Mano -> ");
        for (int i = 0; i < mano.size(); i++) {
            if(i != (mano.size()-1))
                System.out.print(mano.get(i)+ " - ");
            else{
            System.out.print(mano.get(i));
            }
        }
    }

    public void removerFicha(Ficha ficha) {
        mano.remove(ficha);
    }
}