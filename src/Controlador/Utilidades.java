/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

/**
 *
 * @author juans
 */
public class Utilidades {

    static boolean mMatricula;

    public Utilidades() {
    }

    public static boolean comprobarMatricula(String matricula) {

        Pattern p = Pattern.compile("[0-9]{4}[BCDFGHIJKLMNPQRSTUVWXYZ]{3}");
        Matcher m = p.matcher(matricula);
        mMatricula = m.matches();
        return m.matches();
    }

    public static boolean comprobarDni(String dni) {
        Pattern p = Pattern.compile("([XYZ]{1}[0-9]{7})|([0-9]{8})");
        Matcher m = p.matcher(dni);
        return m.matches();

    }

    public static boolean comprobarCodigoPostal(String codigoPostal) {
        Pattern p = Pattern.compile("0[1-9][0-9]{3}|[1-4][0-9]{4}|5[0-2][0-9]{3}");
        Matcher m = p.matcher(codigoPostal);
        return m.matches();
    }

    public static String calcularLetraDni(String dni) {
        String letras[] = {"T", "R", "W", "A", "G", "M", "Y", "F", "P", "D", "X", "B", "N", "J", "Z", "S", "Q", "V", "H", "L", "C", "K", "E"};
        int resto;
        String letra = "";
        String dniNumeros = dni.substring(0, 8);

        resto = Integer.parseInt(dniNumeros) % 23;
        letra = letras[resto];
        return letra;
    }

    public static String pasarNieADni(String nie) {
        char letra = nie.charAt(0);
        switch (letra) {
            case 'X':
                nie = nie.replace("X", "0");
            case 'Y':
                nie = nie.replace("Y", "1");
            case 'Z':
                nie = nie.replace("Z", "2");
        }
        return nie;
    }
    
    public static boolean comprobarFecha(String fecha){
        
        Pattern p = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
        Matcher m = p.matcher(fecha);
        return m.matches();
    }
    
    public static void informacion(String _mensaje){
        
      JOptionPane.showMessageDialog( null, _mensaje, "Información",
                                JOptionPane.INFORMATION_MESSAGE);
   }
}

