/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import Modelo.Enumerados.Combustible;

/**
 *
 * @author juans
 */
public abstract class Turismo extends Vehiculo {

    protected int numPuertas;
    protected Combustible combustible;

    public Turismo(String matricula, String marca, String modelo, int cilindrada, int numPuertas, Combustible combustible) {
        super(matricula, marca, modelo, cilindrada);
        this.numPuertas = numPuertas;
        this.combustible = combustible;
    }

    public int getNumPuertas() {
        return numPuertas;
    }

    public void setNumPuertas(int numPuertas) {
        this.numPuertas = numPuertas;
    }

    public Combustible getCombustible() {
        return combustible;
    }

    public void setCombustible(Combustible combustible) {
        this.combustible = combustible;
    }

    //Corregido
    @Override
    public String escribirFichero() {
        return super.escribirFichero() + "#" + this.numPuertas + "#" + this.combustible;
    }

    @Override
    public String toString() {
        return super.toString() + "\t\tNúmero de puertas: " + this.numPuertas + "\t\tCombustible: " + this.combustible;
    }
}
