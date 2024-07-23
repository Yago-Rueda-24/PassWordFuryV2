/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author yago
 */
public class Entrada implements Serializable {
    
    private String app;
    private String user;
    private String password;

    public Entrada(String app, String user, String password) {
        this.app = app;
        this.user = user;
        this.password = password;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Entrada{" + "app=" + app + ", user=" + user + ", password=" + password + '}';
    }
    
    
    
            
}
