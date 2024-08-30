/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Singleton.java to edit this template
 */
package Modelo;

import java.util.Random;

/**
 *
 * @author yago
 */
public class Generador {

    private static Generador generator;
    private int caracteres;
    private final Random random;
    private final String[] simbolos = {"%", "$", "!", "#", "*", "@", "^"};

    private Generador() {
        caracteres = 14;
        random = new Random();
    }

    public static Generador getInstance() {
        if (generator == null) {
            generator = new Generador();
        }
        return generator;
    }

    public int getCaracteres() {
        return caracteres;
    }

    public void setCaracteres(int caracteres) {
        this.caracteres = caracteres;
    }

    /**
     *
     * @return Un caracter en mayuscula aleatorio
     */
    private char mayusculas() {

        int aux = 65 + random.nextInt(26);
        char c = (char) aux;
        return c;

    }

    /**
     *
     * @return Un caracter en minuscula aleatorio
     */
    private char minusculas() {

        int aux = 97 + random.nextInt(26);
        char c = (char) aux;
        return c;
        
    }

    /**
     *
     * @return un numero (tipo char)
     */
    private char numeros() {

        int aux = 48 + random.nextInt(10);
        char c = (char) aux;
        return c;
    }

    private String simbolos() {
        int index = random.nextInt(simbolos.length);
        return simbolos[index];
    }

    /**
     * Genera una contraseña aleatoria, los parametros de generacion se guardan
     * en las varibles de la clase generador
     *
     * @return Devuelve la contraseña en un string
     */
    public String generate() {

        String password = "";

        for (int i = 0; i < caracteres; i++) {
            switch (random.nextInt(4)) {
                case 0:
                    password = password + mayusculas();
                    break;

                case 1:
                    password = password + numeros();
                    break;
                default:
                    password = password + minusculas();
            }

        }
        int indexdivision =random.nextInt(password.length());
        String firshalf = password.substring(0, indexdivision);
        String secondhalf = password.substring(indexdivision+1);
        return firshalf+simbolos()+secondhalf;
    }

}
