
package com.emergentes.modelos;

public class gptmod {
    private int id;
    private String nombre;
    private String apellido;
    private String gptcon;

    public gptmod() {
    }

    public gptmod(int id, String nombre, String apellido, String gptcon) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.gptcon = gptcon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getGptcon() {
        return gptcon;
    }

    public void setGptcon(String gptcon) {
        this.gptcon = gptcon;
    }
    
    
}
