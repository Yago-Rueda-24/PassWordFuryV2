/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
//a
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author yago
 */
public class Par implements Serializable {

    private ArrayList< String> par;

    public Par() {
        par = new ArrayList<>(2);
        
    }
    public Par(String n1 , String n2) {
        par = new ArrayList<>(2);
        par.add(0, n1);
        par.add(1, n2);
    }

    public void setFirst(String  n) {
        par.add(0, n);
    }

    public void setSecond(String n) {
        par.add(1, n);
    }

    public String getFirst() {
        return par.get(0);
    }

     public String getSecond() {
        return par.get(1);
    }

    @Override
    public String toString() {
        return par.get(0)+" "+par.get(1);
    }
     
}
