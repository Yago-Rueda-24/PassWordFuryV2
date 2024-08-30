/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Modelo.excepciones.EXEntradaRepetida;
import java.io.Serializable;
import java.util.Iterator;

import Modelo.excepciones.*;
import java.util.ArrayList;

/**
 *
 * @author yago
 */
public class SafeBox implements Serializable {

    private String masterpassword;
    private ArrayList<Entrada> entradas;

    public SafeBox(String password, String repeatpassword) throws EXErrorCreacion {

        if (!password.equals(repeatpassword)) {
            throw new EXErrorCreacion("Las contraseñas no son iguales, imposible crear la boveda");
        }
        this.masterpassword = password;
        this.entradas = new ArrayList<>();
    }

    public String getPassword() {
        return masterpassword;
    }

    public void setPassword(String password) {
        this.masterpassword = password;
    }

    public ArrayList<Entrada> getEntradas() {
        return entradas;
    }

    public void setEntradas(ArrayList<Entrada> entradas) {
        this.entradas = entradas;
    }

    /**
     * Añade una nueva entrada a la boveda siempre y cuando esta entrada no este
     * contenida en ella
     *
     * @param entry La entrada a añadir
     * @throws Modelo.excepciones.EXEntradaRepetida
     */
    public void add(Entrada entry) throws EXEntradaRepetida {
        for (Object e : entradas) {
            Entrada aux = (Entrada) e;
            if (aux.getApp().equals(entry.getApp())) {
                throw new EXEntradaRepetida("La entrada que quieres añadir ya se encuentra en la boveda");
            }
        }
        this.entradas.add(entry);

    }

    /**
     * Elimina una entrada de la boveda
     *
     * @param entry La entrada a eliminar
     */
    public void remove(Entrada entry) {

        this.entradas.remove(entry);
    }

    /**
     * Modifica una entrada existente en la boveda
     *
     * @param entryremoved La entrada que se va a ser modificada
     * @param entryadded La entrada efectiva despues de la modificación
     * @throws EXEntradaRepetida
     */
    public void modify(Entrada entryremoved, Entrada entryadded) throws EXEntradaRepetida {

        this.remove(entryremoved);
        this.add(entryadded);

    }

    public boolean unlock(String password) {

        return masterpassword.equals(password);

    }

    public Iterator<Entrada> getIterator() {

        return this.entradas.iterator();

    }

    public void prueba() {

        Iterator<Entrada> itr = getIterator();
        while (itr.hasNext()) {
            System.out.println(itr.next().toString());
        }
    }
}