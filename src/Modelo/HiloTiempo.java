/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.text.SimpleDateFormat;

/**
 *
 * @author yago
 */
public class HiloTiempo implements Runnable {

    private static HiloTiempo reloj = null;
    private SimpleDateFormat dateFormat;
    private long now;

    private HiloTiempo() {
        this.dateFormat = new SimpleDateFormat("HH:mm:ss");
        this.now = System.currentTimeMillis();

    }

    public static HiloTiempo getInstance() {
        if (reloj == null) {
            reloj = new HiloTiempo();
        }
        return reloj;

    }

    @Override
    public void run() {

        while (true) {
            this.now = System.currentTimeMillis();
            System.out.println(this.dateFormat.format(now));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
