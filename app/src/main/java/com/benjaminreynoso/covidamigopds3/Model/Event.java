package com.benjaminreynoso.covidamigopds3.Model;

public class Event {
    private  int id_evento,aforo, invitadosConfirmados,edad_minima;
    private String nombre,descripcion,id_usuario,fecha,direccion,estado,entorno;

    public String getEntorno() {
        return entorno;
    }

    public int getId_evento() {
        return id_evento;
    }

    public int getAforo() {
        return aforo;
    }

    public int getInvitadosConfirmados() {
        return invitadosConfirmados;
    }

    public int getEdad_min() {
        return edad_minima;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getEstado() {
        return estado;
    }

    public Event(int id_evento,String nombre, String descripcion, String fecha, String direccion, String entorno, int aforo, int invitadosConfirmados,String estado,int edad_minima, String id_usuario){
        this.id_evento = id_evento;
        this.aforo = aforo;
        this.invitadosConfirmados = invitadosConfirmados;
        this.edad_minima = edad_minima;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
        this.direccion = direccion;
        this.estado = estado;
        this.entorno = entorno;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id_evento=" + id_evento +
                ", aforo=" + aforo +
                ", invitadosConfirmados=" + invitadosConfirmados +
                ", edad_min=" + edad_minima +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", id_usuario='" + id_usuario + '\'' +
                ", fecha='" + fecha + '\'' +
                ", direccion='" + direccion + '\'' +
                ", estado='" + estado + '\'' +
                ", entorno='" + entorno + '\'' +
                '}';
    }
}
