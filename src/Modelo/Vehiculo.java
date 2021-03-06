/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author juans
 */
public abstract class Vehiculo {

    private String matricula;
    private String marca;
    private String modelo;
    private int cilindrada;
    private boolean disponible;
    private boolean altaVehiculo;

    public Vehiculo() {
    }

    public Vehiculo(String matricula, String marca, String modelo, int cilindrada, boolean alta) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.cilindrada = cilindrada;
        this.disponible = true;
        this.altaVehiculo = alta;
    }

    public Vehiculo(Vehiculo vehiculo) {
        this.matricula = vehiculo.matricula;
        this.marca = vehiculo.marca;
        this.modelo = vehiculo.modelo;
        this.cilindrada = vehiculo.cilindrada;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getMatricula() {
        return this.matricula;
    }

    public String getMarca() {
        return this.marca;
    }

    public String getModelo() {
        return this.modelo;
    }

    public int getCilindrada() {
        return this.cilindrada;
    }

    public boolean getDisponible() {

        return disponible;
    }

    public boolean getAltaVehiculo() {
        return altaVehiculo;
    }

    public void setAltaVehiculo(boolean alta) {
        this.altaVehiculo = alta;
    }

    //Corregido
    public String escribirFichero() {
        return this.matricula + "#" + this.marca + "#" + this.modelo + "#" + this.cilindrada + "#" + this.altaVehiculo + "#" + this.disponible + "\n";
    }

    @Override
    public String toString() {

        String disponible = (this.disponible) ? "SI" : "NO";
        String alta = (this.altaVehiculo) ? "SI" : "NO";

        return "Matrícula: " + this.matricula + "\t\tMarca: " + this.marca + "\t\tModelo: "
                + this.modelo + "\t\tCilindrada: " + this.cilindrada + "\t\tAlta: " + alta + "\t\tDisponible: " + disponible;
    }
}
