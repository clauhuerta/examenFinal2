package org.vaadin.example;

public class Usuario {
    private String id;
    private String nombre;
    private String apellidos;
    private String nif;
    private Direccion direccion;
    private String email;
    private MetodoPago metodoPago;

    // Getters & Setters

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }

    // Clases anidadas

    public static class Direccion {
        private String calle;
        private int numero;
        private String codigoPostal;
        private String pisoLetra;
        private String ciudad;

        public String getCalle() {
            return calle;
        }

        public void setCalle(String calle) {
            this.calle = calle;
        }

        public int getNumero() {
            return numero;
        }

        public void setNumero(int numero) {
            this.numero = numero;
        }

        public String getCodigoPostal() {
            return codigoPostal;
        }

        public void setCodigoPostal(String codigoPostal) {
            this.codigoPostal = codigoPostal;
        }

        public String getPisoLetra() {
            return pisoLetra;
        }

        public void setPisoLetra(String pisoLetra) {
            this.pisoLetra = pisoLetra;
        }

        public String getCiudad() {
            return ciudad;
        }

        public void setCiudad(String ciudad) {
            this.ciudad = ciudad;
        }
    }

    public static class MetodoPago {
        private long numeroTarjeta;
        private String nombreAsociado;

        public long getNumeroTarjeta() {
            return numeroTarjeta;
        }

        public void setNumeroTarjeta(long numeroTarjeta) {
            this.numeroTarjeta = numeroTarjeta;
        }

        public String getNombreAsociado() {
            return nombreAsociado;
        }

        public void setNombreAsociado(String nombreAsociado) {
            this.nombreAsociado = nombreAsociado;
        }
    }
}
