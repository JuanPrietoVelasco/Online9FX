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
public abstract class Mercancias extends Vehiculo {

    private int pma;
    private int volumen;

    public Mercancias(String matricula, String marca, String modelo, int cilindrada,  boolean alta, int pma, int volumen) {
        super(matricula, marca, modelo, cilindrada, alta);
        this.pma = pma;
        this.volumen = volumen;
    }

    public Mercancias(Vehiculo vehiculo, int pma, int volumen) {
        super(vehiculo.getMatricula(), vehiculo.getMarca(), vehiculo.getModelo(), vehiculo.getCilindrada(), vehiculo.getAltaVehiculo()); //Corregido
        this.pma = pma;
        this.volumen = volumen;
    }

    public int getPma() {
        return pma;
    }

    public int getVolumen() {
        return volumen;
    }

    //Corregido
    @Override
    public String escribirFichero() {
        return super.escribirFichero() + "#" + this.pma + "#" + this.volumen;
    }

    @Override
    public String toString() {
        return super.toString() + "\t\tPMA: " + pma + "\t\tVolumen: " + volumen;
    }

}
