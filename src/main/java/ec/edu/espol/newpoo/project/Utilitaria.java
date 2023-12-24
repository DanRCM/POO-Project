/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ec.edu.espol.newpoo.project;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author danrcm
 */
public class Utilitaria {
    public static ArrayList<Ficha> crearManoJugador(){
        ArrayList<Ficha> mano = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 5; i++) {
            Ficha f = new Ficha(random.nextInt(6)+1,random.nextInt(6)+1);
            mano.add(f);
        }
        mano.add(new FichaComodin());
        return mano;
    }
}
