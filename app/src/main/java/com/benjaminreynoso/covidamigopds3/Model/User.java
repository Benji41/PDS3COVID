package com.benjaminreynoso.covidamigopds3.Model;

public class User {

    private String nombre,apellidos,edad,email;


    public User(String nombre, String apellidos, String age, String email){
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.email = email;

    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getEdad() {
        return edad;
    }

    public String getEmail() {
        return email;
    }
}

