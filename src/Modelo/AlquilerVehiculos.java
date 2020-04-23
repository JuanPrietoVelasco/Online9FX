/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import static Controlador.ES.*;
import static Controlador.Utilidades.*;
import static Modelo.Cliente.*;
import static Modelo.Enumerados.*;
import static Modelo.Mercancias.*;
import static Modelo.Alquiler.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

/**
 * @author juan
 */
public class AlquilerVehiculos {

    //guardar datos al salir cuando haya cambios que guardar
    private static ArrayList<Vehiculo> vehiculos = new ArrayList<>();
    private static ArrayList<Cliente> clientes = new ArrayList<>();
    private static ArrayList<Alquiler> alquileres = new ArrayList<>();

    //
    //
//
    //---------------------------------------MAIN------------------------------------------------------//  
    public static void main(String[] args) throws FileNotFoundException {

        //Cargamos los datos desde los archivos.
        leerDatos("");
        //guardar datos al salir cuando haya cambios que guardar
        boolean guardarDatos = false;
        int opcion = 0;
        Scanner sc = new Scanner(System.in);
        escribirLn("-------------------------------------------------------");
        escribirLn("Bienvenido al programa de gestión de Alquileres Xuanin.");
        escribirLn("-------------------------------------------------------");
        escribirLn("Información al usuario:\n- Una vez elegida una opción del menu, en el caso de haber escogido erroneamente,\n"
                + "  podrás volver al menu dando un dato con formato erroneo en el primer paso de la opción.\n"
                + "- Si va a introducir un alquiler, se recomienda listar previamente con las opciones 3,6 y 9\n"
                + "  los clientes, vehículos y alquileres ya guardados para poder consultar datos.\n"
                + "- Cuando listemos los alquileres, el dato 'Disponible' nos indicará: si es Si que\n"
                + "  se trata de un alquiler cerrado; si es No que es un alquiler activo.");

        do {

            escribirLn("Opciones: ");
            escribirLn("1. Añadir cliente.\n2. Borrar cliente.\n3. Listar clientes.\n"
                    + "4. Añadir vehiculo.\n5. Borrar vehiculo.\n6. Listar vehiculos.\n"
                    + "7. Nuevo alquiler.\n8. Cerrar alquiler.\n9. Listar alquileres.\n"
                    + "10. Guardar datos.\n11. Crear copia de seguridad.\n12. Cargar copia de seguridad.\n"
                    + "13. Guardar info en XML.\n14. Leer info de XML.\n15. Salir");

            opcion = leerEntero("\nIntroduce opción: ");

            switch (opcion) {
                case 1:
                    anadirCliente();
                    guardarDatos = true;
                    break;
                case 2:
                    borrarCliente();
                    guardarDatos = true;
                    break;
                case 3:
                    listarClientes();
                    break;
                case 4:
                    anadirVehiculo();
                    guardarDatos = true;
                    break;
                case 5:
                    borrarVehiculo();
                    guardarDatos = true;
                    break;
                case 6:
                    listarVehiculos();
                    break;
                case 7:
                    nuevoAlquiler();
                    guardarDatos = true;
                    break;
                case 8:
                    cerrarAlquiler();
                    guardarDatos = true;
                    break;
                case 9:
                    listarAlquileres();
                    break;
                case 10:
                    guardarDatos("");
                    //Al pasarle por parámetro un String vacío, los datos se guardaran en el directorio raíz del proyecto
                    guardarDatos = false;
                    break;
                case 11:
                    crearCopiaSeg();
                    break;
                case 12:
                    vaciarArrays();
                    cargarCopiaSeg();
                    guardarDatos = true;
                    break;
                case 13:
                    guardarDatosXML();
                    break;
                case 14:
                    vaciarArrays();
                    leerDatosXML();
                    guardarDatos = true;
                    break;
                case 15:
                    //guardar datos cuando haya cambios que guardar
                    if (guardarDatos) {
                        confirmarGuardarDatos();
                    }

                    //Vaciamos los arrays
                    vaciarArrays();

                    escribirLn("\n               Fin de programa");
                    escribirLn("------------------------------------------------\n");
                    escribirLn("------------------------------------------------\n");
                    break;
                default:
                    escribirLn("********************ATENCION********************");
                    escribirLn("              Opción incorrecta.");
                    escribirLn("          Elija una opción del menu.");
                    escribirLn("------------------------------------------------");
                    break;
            }

        } while (opcion != 15);

    }
//    
//
//---------------------------------------METODOS OPCIONES MENU-------------------------------------//
//
//
//    

    public static void anadirCliente() {

        //Utilizamos dniAux parahacer las comprobaciones en el caso de que se trate de un NIE
        String dni = leerCadena("\nIntroduce Dni/Nie sin la letra final: ").toUpperCase();
        boolean value = false;

        dni = procesarDni(dni);

        System.out.println("como llega el dni " + dni);
        if (getCliente(dni) != null) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Dni/Nie ya registrado. Cliente no añadido.");
            escribirLn("------------------------------------------------\n");
        } else {
            //escribirLn("DNI correcto");
            String nombre = leerCadena("\nIntroduce nombre de cliente: ").toUpperCase();
            String direccion = leerCadena("\nIntroduce dirección de cliente: ").toUpperCase();
            String localidad = leerCadena("\nIntroduce localidad de cliente: ").toUpperCase();

            //utilizar un while para volver a pedir el cp si fuera erroneo
            while (!value) {
                String cod_postal = leerCadena("\nIntroduce el código postal de cliente: ");
                if (comprobarCodigoPostal(cod_postal)) {
                    value = true;
                    Cliente c = new Cliente(dni, nombre, direccion, localidad, cod_postal);

                    clientes.add(c);
                    escribirLn(c.toString());

                    //Ordenamos el array una vez introducido un nuevo cliente.
                    Collections.sort(clientes);

                    escribirLn("\n      Cliente añadido correctamente.");
                    escribirLn("------------------------------------------------\n");

                } else {
                    escribirLn("\n********************ATENCION********************");
                    escribirLn("          Código postal incorrecto.");
                    escribirLn("------------------------------------------------");
                }
            }
        }
    }

    public static void borrarCliente() {

        boolean procesado = false;
        String dni = leerCadena("\nIntroduce Dni/Nie de cliente a borrar sin la letra final: ").toUpperCase();

        dni = procesarDni(dni);

        if (getCliente(dni) != null) {
            //borrar cliente
            Cliente cliente = getCliente(dni);

            for (int j = 0; j < alquileres.size() && !procesado; j++) {
                if (alquileres.get(j).getCliente() == cliente) {
                    procesado = true;
                    if (!alquileres.get(j).getVehiculo().getDisponible()) {
                        escribirLn("\n********************ATENCION********************");
                        escribirLn("  El cliente tiene un alquiler activo. Debe cerrar\n"
                                + "         primero el alquiler asociado.");
                        escribirLn("------------------------------------------------\n");

                    } else {
                        clientes.remove(cliente);
                        escribirLn("\n        Cliente eliminado correctamente.");
                        escribirLn("------------------------------------------------\n");

                    }
                }
            }

            if (!procesado) {
                clientes.remove(cliente);
                escribirLn("\n       Cliente borrado correctamente.");
                escribirLn("------------------------------------------------\n");
            }

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("            Cliente no registrado");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void listarClientes() {

        if (clientes.isEmpty()) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("               No existen clientes.");
            escribirLn("------------------------------------------------\n");
        } else {
            for (int i = 0; i < clientes.size(); i++) {
                escribirLn(clientes.get(i).toString());
            }
        }
    }

    public static void anadirVehiculo() {

        String matricula = (leerCadena("\nIntroduce matrícula del vehículo: ")).toUpperCase();

        if (comprobarMatricula(matricula)) {

            if (getVehiculo(matricula) == null) {

                //Si la matricula no está registrada, comenzamos a pedir los datos del vehiculo.
                String marca = (leerCadena("\nIntroduce marca del vehículo: ")).toUpperCase();
                String modelo = (leerCadena("\nIntroduce modelo del vehículo: ")).toUpperCase();
                int cilindrada = leerEntero("\nIntroduce cilindrada del vehículo: ");
                int seleccion = leerEntero(1, 3, "\nSelecciona tipo de vehículo.\n1.Furgoneta.\n2.Familiar.\n3.Deportivo");

                if (seleccion == 1) {

                    int pma = leerEntero("\nVas a añadir una furgoneta.\nIntroduce pma: ");
                    int volumen = leerEntero("\nIntroduce volumen: ");
                    boolean refrigerado = leerBoolean("\nVehículo refrigerado S/N");

                    int posicion = leerEntero(1, 3, "\nSelecciona un tamaño:\n1.Grande\n2.Mediano\n3.Pequeño");

                    Tamanio tamanio = Tamanio.values()[posicion - 1];

                    Furgoneta furgoneta = new Furgoneta(matricula, marca, modelo, cilindrada, pma, volumen, refrigerado, tamanio);

                    vehiculos.add(furgoneta);

                    escribirLn("\n" + furgoneta.toString());
                    escribirLn("\nVehiculo furgoneta añadido correctamente.");
                    escribirLn("------------------------------------------------\n");

                }
                if (seleccion == 2) {

                    int numPuertas = leerEntero(3, 5, "\nVas a añadir un familiar.\n\nIntroduce número de puertas entre 3 y 5:");

                    int posicion = leerEntero(1, 4, "\nSelecciona tipo de combustible:\n1.Gasolina.\n2.Diesel.\n3.Híbrido.\n4.Eléctrico.");

                    Combustible combustible = Combustible.values()[posicion - 1];

                    int numPlazas = leerEntero(4, 7, "\nElija el número de plazas entre 4 y 7.");

                    boolean sillaBebe = leerBoolean("\n¿Tiene silla de bebe? S/N");

                    Familiar familiar = new Familiar(matricula, marca, modelo, cilindrada, numPuertas, combustible, numPlazas, sillaBebe);

                    vehiculos.add(familiar);

                    escribirLn("\n" + familiar.toString());
                    escribirLn("\nVehículo familiar añadido correctamente.");
                    escribirLn("------------------------------------------------\n");

                }

                if (seleccion == 3) {
                    int numPuertas = leerEntero(3, 5, "\nVas a añadir un deportivo.\n\nIntroduce número de puertas entre 3 y 5:");

                    int posicion = leerEntero(1, 4, "\nSelecciona tipo de combustible:\n1.Gasolina.\n2.Diesel.\n3.Híbrido.\n4.Eléctrico.");

                    Combustible combustible = Combustible.values()[posicion - 1];

                    boolean descapotable = leerBoolean("\n¿Descapotable? S/N");

                    int opcion = leerEntero(1, 2, "\nSelecciona tipo de caja de cambios: \n1.Automático.\n2.Manual.");

                    CajaCambios cambio = CajaCambios.values()[opcion - 1];

                    Deportivo deportivo = new Deportivo(matricula, marca, modelo, cilindrada, numPuertas, combustible, cambio, descapotable);

                    vehiculos.add(deportivo);

                    escribirLn("\n" + deportivo.toString());
                    escribirLn("\nVehículo deportivo añadido correctamente.");
                    escribirLn("------------------------------------------------\n");

                }
            } else {
                escribirLn("\n********************ATENCION********************");
                escribirLn("Matrícula ya registrada. Vehiculo no añadido.");
                escribirLn("------------------------------------------------\n");
            }

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("Formato de matrícula incorrecto. Formato requerido tipo '1234BCD'. Vocales no aceptadas."
                    + "\nEscoja de nuevo una opción del menu principal.");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void borrarVehiculo() {

        boolean procesado = false;

        String matricula = (leerCadena("\nIntroduce matrícula del vehiculo a borrar: ")).toUpperCase();

        if (comprobarMatricula(matricula)) {
            //borrarVehiculo(matricula);
            if (getVehiculo(matricula) != null) {

                Vehiculo vehiculo = getVehiculo(matricula);

                for (int j = 0; j < alquileres.size() && !procesado; j++) {

                    if (alquileres.get(j).getVehiculo() == vehiculo) {

                        procesado = true;

                        if (!alquileres.get(j).getVehiculo().getDisponible()) {
                            escribirLn("\n********************ATENCION********************");
                            escribirLn("El vehiculo tiene un alquiler activo. Debe cerrar\n"
                                    + "        primero el alquiler asociado.");
                            escribirLn("------------------------------------------------\n");
                        } else {
                            vehiculos.remove(vehiculo);
                            escribirLn("\n        Vehiculo eliminado correctamente.");
                            escribirLn("------------------------------------------------\n");
                        }
                    }
                }

                if (!procesado) {
                    vehiculos.remove(vehiculo);
                    escribirLn("\n        Vehiculo eliminado correctamente.");
                    escribirLn("------------------------------------------------\n");
                }

            } else {
                escribirLn("\n********************ATENCION********************");
                escribirLn("        No existe vehículo con dicha matrícula    ");
                escribirLn("------------------------------------------------\n");
            }

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("        Formato de matrícula incorrecto.");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void listarVehiculos() {
        boolean vacio = vehiculos.isEmpty();

        if (vacio) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("             No existen vehículos.");
            escribirLn("------------------------------------------------\n");
        } else {
            for (Vehiculo elemento : vehiculos) {
                escribirLn(elemento.toString());
            }
        }
    }

    public static void nuevoAlquiler() {

        String matricula;
        int posCliente;
        int posVehiculo;
        boolean mat_ok = false;

        String dni = (leerCadena("\nIntroduce Dni/Nie del cliente sin la letra: ")).toUpperCase();

        dni = procesarDni(dni);

        if (getCliente(dni) != null) {

            Cliente cliente = getCliente(dni);

            while (!mat_ok) {

                matricula = leerCadena("\nIntroduce matricula del vehículo: ").toUpperCase();

                if (comprobarMatricula(matricula)) {

                    mat_ok = true;

                    if (getVehiculo(matricula) != null) {

                        Vehiculo vehiculo = getVehiculo(matricula);

                        if (vehiculo.getDisponible() == true) {

                            for (int i = 0; i < vehiculos.size(); i++) {
                                if (vehiculos.get(i) == vehiculo) {
                                    vehiculo.setDisponible(false);
                                    vehiculos.get(i).setDisponible(false);
                                }
                            }

                            Alquiler nuevoAl = new Alquiler(cliente, vehiculo);
                            alquileres.add(nuevoAl);
                            System.out.println(nuevoAl);

                            escribirLn("Alquiler registrado correctamente");
                            escribirLn("------------------------------------------------\n");

                        } else {
                            escribirLn("\n********************ATENCION********************");
                            escribirLn(" El vehiculo no está disponible en este momento.");
                            escribirLn("------------------------------------------------\n");
                        }

                    } else {
                        escribirLn("\n********************ATENCION********************");
                        escribirLn("       El vehículo no está registrado.");
                        escribirLn("------------------------------------------------\n");
                    }

                } else {
                    escribirLn("\n********************ATENCION********************");
                    escribirLn("       Formato de matrícula incorrecto.");
                    escribirLn("------------------------------------------------\n");
                }

            }

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("No hay ningún cliente registrado con ese Dni/Nie.");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void cerrarAlquiler() {
        //Crea un método cerrarAlquiler que cierre el alquiler dado un cliente y un vehiculo.

        String matricula;
        boolean value = false;
        Cliente cliente;
        Vehiculo vehiculo;
        double precio = 0.0;

        String dni = (leerCadena("\nIntroduce Dni/Nie sin la letra final: ")).toUpperCase();

        dni = procesarDni(dni);

        cliente = getCliente(dni);

        if (cliente != null) {

            matricula = leerCadena("\nIntroduce matricula del vehículo: ").toUpperCase();

            if (comprobarMatricula(matricula)) {

                vehiculo = getVehiculo(matricula);

                if (vehiculo != null) {

                    if (!vehiculo.getDisponible()) {

                        for (int i = 0; i < alquileres.size() && !value; i++) {

                            if (alquileres.get(i).getCliente().equals(cliente) && alquileres.get(i).getVehiculo().equals(vehiculo)) {

                                alquileres.get(i).cerrar(vehiculos);
                                value = true;

                                System.out.println("\nPrecio alquiler: " + alquileres.get(i).precioAlquiler() + "€ ");

                            }

                        }
                        if (!value) {
                            escribirLn("\n********************ATENCION********************");
                            escribirLn("No hay alquileres que contengan el cliente y el vehiculo indicado.");
                            escribirLn("------------------------------------------------\n");

                        } else {
                            escribirLn("\n      Alquiler cerrado correctamente");
                            escribirLn("------------------------------------------------\n");
                        }//FIN

                    } else {
                        escribirLn("\n********************ATENCION********************");
                        escribirLn("        El vehiculo no está alquilado.");
                        escribirLn("------------------------------------------------\n");
                    }

                } else {
                    escribirLn("\n********************ATENCION********************");
                    escribirLn("        El vehículo no está registrado.");
                    escribirLn("------------------------------------------------\n");
                }

            } else {
                escribirLn("\n********************ATENCION********************");
                escribirLn("      Formato de matrícula incorrecto.");
                escribirLn("------------------------------------------------\n");
            }

        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("No hay ningún cliente registro con el Dni/Nie proporciado");
            escribirLn("------------------------------------------------\n");
        }

    }

    public static void listarAlquileres() {

        if (alquileres.isEmpty()) {
            escribirLn("\n********************ATENCION********************");
            escribirLn("            No existen alquileres.");
            escribirLn("------------------------------------------------\n");
        } else {
            for (Alquiler a : alquileres) {
                System.out.println(a);
            }
        }

    }

    public static void guardarDatos(String ruta) {

        //Archivo para array clientes.
        String rutaC = (ruta == "") ? "clientes.txt" : ruta + "/clientes.txt";

        String datosCliente = "";

        for (int i = 0; i < clientes.size(); i++) {

            //Corregido
            datosCliente += clientes.get(i).escribirFichero();

        }

        if (escribirArchivo(rutaC, datosCliente, true)) {
            escribirLn("\nDatos de clientes guardados correctamente.");
            escribirLn("------------------------------------------------\n");
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("         Error en escritura de datos.");
            escribirLn("------------------------------------------------\n");
        }

        //Archivo para array vehículos
        String rutaV = (ruta == "") ? "vehiculos.txt" : ruta + "/vehiculos.txt";

        String datosVehiculos = "";

        for (int i = 0; i < vehiculos.size(); i++) {

            if (vehiculos.get(i) instanceof Deportivo) {

                Deportivo aux = (Deportivo) vehiculos.get(i);

                //Corregido
                datosVehiculos += "Deportivo#" + aux.escribirFichero();

            }

            if (vehiculos.get(i) instanceof Familiar) {

                Familiar aux = (Familiar) vehiculos.get(i);

                //Corregido
                datosVehiculos += "Familiar#" + aux.escribirFichero();

            }

            if (vehiculos.get(i) instanceof Furgoneta) {

                Furgoneta aux = (Furgoneta) vehiculos.get(i);

                //Corregido
                datosVehiculos += "Furgoneta#" + aux.escribirFichero();

            }

        }

        if (escribirArchivo(rutaV, datosVehiculos, true)) {
            escribirLn("\nDatos de vehículos guardados correctamente.");
            escribirLn("------------------------------------------------\n");
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("         Error en escritura de datos.");
            escribirLn("------------------------------------------------\n");
        }

        //Archivo para array alquileres
        String rutaA = (ruta == "") ? "alquileres.txt" : ruta + "/alquileres.txt";

        String datosAlquileres = "";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (int i = 0; i < alquileres.size(); i++) {

            datosAlquileres += alquileres.get(i).getCliente().getDni() + "#"
                    + alquileres.get(i).getVehiculo().getMatricula() + "#"
                    + sdf.format(alquileres.get(i).getFecha().getTime())
                    + "#" + alquileres.get(i).getDias() + "\n";

        }
        if (escribirArchivo(rutaA, datosAlquileres, true)) {
            escribirLn("\nDatos de alquileres guardados correctamente.");
            escribirLn("------------------------------------------------\n");
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("        Error en escritura de datos.");
            escribirLn("------------------------------------------------\n");
        }

    }

    public static void leerDatos(String ruta) throws FileNotFoundException {

        int tipoVehiculo = 0; //Esta variable la utilizaremos para posteriormente saber qué tipo de vehiculo almacenamos en el array de alquileres

        String rutaCliente = (ruta.isEmpty()) ? "clientes.txt" : ruta + "\\clientes.txt";
        //CLIENTES
        String clientesTxt = leerArchivo(rutaCliente);

        if (!clientesTxt.isEmpty()) {

            String[] datosClientes = clientesTxt.split("\n");

            for (int i = 0; i < datosClientes.length; i++) {

                String[] datos = datosClientes[i].split("#");

                Cliente nuevoCliente = new Cliente(datos[0], datos[1], datos[2], datos[3], datos[4]);

                clientes.add(nuevoCliente);

            }

            Collections.sort(clientes);

        }

        //VEHICULOS
        String rutaVehiculo = (ruta.isEmpty()) ? "vehiculos.txt" : ruta + "\\vehiculos.txt";

        String vehiculosTxt = leerArchivo(rutaVehiculo);

        if (!vehiculosTxt.isEmpty()) {

            String[] datosVehiculos = vehiculosTxt.split("\n");

            for (int i = 0; i < datosVehiculos.length; i++) {

                String[] datos = datosVehiculos[i].split("#");

                //Vehiculo vehiculo = null;
                String matricula = datos[1];
                String marca = datos[2];
                String modelo = datos[3];
                int cilindrada = Integer.parseInt(datos[4]);
                boolean disponible = (datos[5].equalsIgnoreCase("true")) ? true : false;

                switch (datos[0]) {

                    case "Furgoneta":
                        tipoVehiculo = 1;
                        //int pma, int volumen, boolean refrigerado, Tamanio tamanio
                        int pma = Integer.parseInt(datos[6]);
                        int volumen = Integer.parseInt(datos[7]);
                        boolean refrigerado = (datos[8].equalsIgnoreCase("true")) ? true : false;
                        Tamanio tamanio = null;

                        if (datos[9].equalsIgnoreCase("GRANDE")) {
                            tamanio = Enumerados.Tamanio.GRANDE;
                        } else if (datos[9].equalsIgnoreCase("MEDIANO")) {
                            tamanio = Enumerados.Tamanio.MEDIANO;
                        } else {
                            tamanio = Enumerados.Tamanio.PEQUENIO;
                        }

                        vehiculos.add(new Furgoneta(matricula, marca, modelo, cilindrada,
                                pma, volumen, refrigerado, tamanio));
                        vehiculos.get(i).setDisponible(disponible);
                        break;

                    case "Deportivo":

                        tipoVehiculo = 2;

                        //protected int numPuertas;
                        // protected Combustible combustible;
                        // private CajaCambios cambio;
                        //  private boolean descapotable;
                        int numPuertas = Integer.parseInt(datos[6]);
                        Combustible combustible = null;

                        if (datos[7].equalsIgnoreCase("GASOLINA")) {
                            combustible = Enumerados.Combustible.GASOLINA;
                        } else if (datos[7].equalsIgnoreCase("DIESEL")) {
                            combustible = Enumerados.Combustible.DIESEL;
                        } else if (datos[7].equalsIgnoreCase("HIBRIDO")) {
                            combustible = Enumerados.Combustible.HIBRIDO;
                        } else {
                            combustible = Enumerados.Combustible.ELECTRICO;
                        }

                        CajaCambios cambio = null;

                        if (datos[8].equalsIgnoreCase("AUTOMATICO")) {
                            cambio = Enumerados.CajaCambios.AUTOMATICO;
                        } else {
                            cambio = Enumerados.CajaCambios.MANUAL;
                        }

                        boolean descapotable = (datos[9].equalsIgnoreCase("true")) ? true : false;

                        vehiculos.add(new Deportivo(matricula, marca, modelo, cilindrada, numPuertas,
                                combustible, cambio, descapotable));
                        vehiculos.get(i).setDisponible(disponible);
                        break;

                    case "Familiar":

                        tipoVehiculo = 3;

                        int numPuertasFami = Integer.parseInt(datos[6]);
                        Combustible combustibleFami = null;

                        if (datos[7].equalsIgnoreCase("GASOLINA")) {
                            combustibleFami = Enumerados.Combustible.GASOLINA;
                        } else if (datos[7].equalsIgnoreCase("DIESEL")) {
                            combustibleFami = Enumerados.Combustible.DIESEL;
                        } else if (datos[7].equalsIgnoreCase("HIBRIDO")) {
                            combustibleFami = Enumerados.Combustible.HIBRIDO;
                        } else {
                            combustibleFami = Enumerados.Combustible.ELECTRICO;
                        }

                        //int numPlazas, boolean sillaBebe
                        int numPlazas = Integer.parseInt(datos[8]);
                        boolean sillaBebe = (datos[9].equalsIgnoreCase("true")) ? true : false;

                        vehiculos.add(new Familiar(matricula, marca, modelo, cilindrada, numPuertasFami,
                                combustibleFami, numPlazas, sillaBebe));
                        vehiculos.get(i).setDisponible(disponible);
                        break;
                }

            }

        }
        //ALQUILERES

        String rutaAlquileres = (ruta.isEmpty()) ? "alquileres.txt" : ruta + "\\alquileres.txt";
        String alquileresTxt = leerArchivo(rutaAlquileres);

        if (!alquileresTxt.isEmpty()) {

            String[] datosAlquileres = alquileresTxt.split("\n");

            for (int i = 0; i < datosAlquileres.length; i++) {

                String[] datos = datosAlquileres[i].split("#");

                String dni = datos[0];
                String matricula = datos[1];
                String fecha = datos[2];
                int dias = Integer.parseInt(datos[3]);

                if (buscarCliente(dni) != -1) {

                    Cliente nuevoCliente = clientes.get(buscarCliente(dni));

                    if (buscarVehiculo(matricula) != -1) {

                        Vehiculo nuevoVehiculo = vehiculos.get(buscarVehiculo(matricula));

                        String[] datosFecha = fecha.split("[/ :]+");

                        int day = Integer.parseInt(datosFecha[0]);
                        int month = Integer.parseInt(datosFecha[1]);
                        int year = Integer.parseInt(datosFecha[2]);
                        int hour = Integer.parseInt(datosFecha[3]);
                        int minute = Integer.parseInt(datosFecha[4]);

                        Calendar fechaAlquiler = new GregorianCalendar(year, month - 1, day, hour, minute);

                        Alquiler nuevoAlquiler = new Alquiler(nuevoCliente, nuevoVehiculo);
                        nuevoAlquiler.setFecha(fechaAlquiler);
                        nuevoAlquiler.setDias(dias);

                        alquileres.add(nuevoAlquiler);

                    }
                }
            }

        }
        System.out.println("\nDatos cargados desde los archivos correctamente.");
    }

    public static void confirmarGuardarDatos() {

        if (leerBoolean("¿Desea guardar cambios? S/N.")) {
            guardarDatos("");
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("         No se han guardado los datos.");
            escribirLn("------------------------------------------------");
        }

    }

    public static void crearCopiaSeg() {

        Calendar date = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        String fecha = sdf.format(date.getTime());

        String ruta = "copias_seguridad/" + fecha;

        File directorio = new File(ruta);

        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                escribirLn("\n    Directorio creado satisfactoriamente en");
                escribirLn("         la carpeta raiz del proyecto.");
                escribirLn("------------------------------------------------\n");
                guardarDatos(ruta);
            } else {
                escribirLn("********************ATENCION********************");
                escribirLn("           Error al crear directorio.");
                escribirLn("------------------------------------------------\n");
            }
        } else {
            escribirLn("\n     El directorio ya existe.");

            borrarFicherosDeDirectorio(directorio);

            if (directorio.delete()) {
                escribirLn("   El directorio existente ha sido borrado.");
                escribirLn("------------------------------------------------\n");
                if (directorio.mkdirs()) {
                    System.out.println("Directorio creado satisfactoriamente");
                    guardarDatos(ruta);
                } else {
                    escribirLn("********************ATENCION********************");
                    escribirLn("           Error al crear directorio");
                    escribirLn("------------------------------------------------\n");
                }
            } else {
                escribirLn("********************ATENCION********************");
                escribirLn("Error al intentar borrar el directorio existente.\n"
                        + "         Copia de seguridad no creada. ");
                escribirLn("------------------------------------------------\n");
            }
        }
    }

    public static void cargarCopiaSeg() {

        String fecha = leerCadena("Introduce fecha de copia a cargar en formato dd-mm-aaaa: ");

        if (comprobarFecha(fecha)) {

            String ruta = "copias_seguridad/" + fecha;

            File directorio = new File(ruta);

            if (!directorio.exists()) {

                escribirLn("\n********************ATENCION********************");
                escribirLn("No existe copia de seguridad de la fecha especificada");
                escribirLn("------------------------------------------------\n");
            } else {

                clientes.clear();
                vehiculos.clear();
                alquileres.clear();

                try {
                    leerDatos(ruta);

                } catch (FileNotFoundException ex) {
                    Logger.getLogger(AlquilerVehiculos.class.getName()).log(Level.SEVERE, null, ex);
                }

                escribirLn("\nCopia de seguridad restablecida correctamente.");
                escribirLn("------------------------------------------------\n");

            }
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("         Formato de fecha incorrecto.");
            escribirLn(" Escoja de nuevo una opción del menu principal.");
            escribirLn("------------------------------------------------\n");
        }
    }

    public static void borrarFicherosDeDirectorio(File directorio) {
        //Con este método borramos todos los ficheros contenidos en el directorio

        File[] ficheros = directorio.listFiles();

        for (int i = 0; i < ficheros.length; i++) {
            ficheros[i].delete();
        }
    }

    public static void guardarDatosXML() {
        guardarDatosXMLClientes();
        guardarDatosXMLVehiculos();
        guardarDatosXMLAlquileres();

    }

    private static void guardarDatosXMLClientes() {

        String nombreFichero = "clientes.xml";

        String nodo = "Clientes";

        try {

            //Creamos una instancia de DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            //Creamos el documentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Crear un DOMImplementation
            DOMImplementation implementation = builder.getDOMImplementation();

            //Creamos el documento con un elemento raíz
            Document documento = implementation.createDocument(null, nodo, null);

            //Indicamos la versión del documento
            documento.setXmlVersion("1.0");

            //Element clientes_XML = documento.createElement("Clientes");
            for (int i = 0; i < clientes.size(); i++) {

                Element cliente_ = documento.createElement("Cliente");

                // Insertamos el DNI del cliente
                Element elemento = documento.createElement("dni");
                Text textoElemento = documento.createTextNode(clientes.get(i).getDni());
                elemento.appendChild(textoElemento);
                cliente_.appendChild(elemento);

                // Insertamos el nombre del cliente
                elemento = documento.createElement("nombre");
                textoElemento = documento.createTextNode(clientes.get(i).getNombre());
                elemento.appendChild(textoElemento);
                cliente_.appendChild(elemento);

                // Insertamos la direccion del cliente
                elemento = documento.createElement("direccion");
                textoElemento = documento.createTextNode(clientes.get(i).getDireccion());
                elemento.appendChild(textoElemento);
                cliente_.appendChild(elemento);

                // Insertamos la localidad del cliente
                elemento = documento.createElement("localidad");
                textoElemento = documento.createTextNode(clientes.get(i).getLocalidad());
                elemento.appendChild(textoElemento);
                cliente_.appendChild(elemento);

                // Insertamos el código postal del cliente
                elemento = documento.createElement("cod_postal");
                textoElemento = documento.createTextNode(clientes.get(i).getCodigoPostal());
                elemento.appendChild(textoElemento);
                cliente_.appendChild(elemento);

                //Añadimos al elemento Clientes el elemento Cliente
                //clientes_XML.appendChild(cliente_);
                documento.getDocumentElement().appendChild(cliente_);

            }

            //Añadimos el elemento raíz al documento
            //documento.getDocumentElement().appendChild(clientes_XML);
            //Asociar el source con el Document
            Source fuente = new DOMSource(documento);

            //Creamos el Result, indicandole el fichero a crear
            Result resultado = new StreamResult(new File(nombreFichero));

            //Creamos un transformer para crear finalmente el archivo XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            transformer.transform(fuente, resultado);

            escribirLn("\n    Guardado clientes.xml correctamente.");
            escribirLn("------------------------------------------------\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void guardarDatosXMLVehiculos() {

        String nombreFichero = "vehiculos.xml";

        String nodo = "Vehiculos";

        try {

            //Creamos una instancia de DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            //Creamos el documentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Crear un DOMImplementation
            DOMImplementation implementation = builder.getDOMImplementation();

            //Creamos el documento con un elemento raíz
            Document documento = implementation.createDocument(null, nodo, null);

            //Indicamos la versión del documento
            documento.setXmlVersion("1.0");

            for (int i = 0; i < vehiculos.size(); i++) {

                Element vehiculo_ = documento.createElement("Vehiculo");

                //Insertamos la matricula
                Element matricula = documento.createElement("matricula");
                Text textoMatricula = documento.createTextNode(vehiculos.get(i).getMatricula());
                matricula.appendChild(textoMatricula);

                //Insertamos la marca
                Element marca = documento.createElement("marca");
                Text textoMarca = documento.createTextNode(vehiculos.get(i).getMarca());
                marca.appendChild(textoMarca);

                Element modelo = documento.createElement("modelo");
                Text textoModelo = documento.createTextNode(vehiculos.get(i).getModelo());
                modelo.appendChild(textoModelo);

                //Insertamos la cilindrada
                Element cilindrada = documento.createElement("cilindrada");
                Text textoCilindrada = documento.createTextNode(String.valueOf(vehiculos.get(i).getCilindrada()));
                cilindrada.appendChild(textoCilindrada);

                if (vehiculos.get(i) instanceof Turismo) {

                    Turismo aux = (Turismo) vehiculos.get(i);

                    //Insertamos el numero de puertas
                    Element num_puertas = documento.createElement("num_puertas");
                    Text textoNum_puertas = documento.createTextNode(String.valueOf(aux.getNumPuertas()));
                    num_puertas.appendChild(textoNum_puertas);

                    //Insertamos el combustible
                    Element combustible = documento.createElement("combustible");
                    Text textoCombustible = documento.createTextNode(String.valueOf(aux.getCombustible()));
                    combustible.appendChild(textoCombustible);

                    if (aux instanceof Deportivo) {

                        Deportivo deportivo = (Deportivo) aux;

                        //Insertamos el Tipo de vehiculo
                        Element tipo = documento.createElement("tipo");
                        Text textoTipo = documento.createTextNode("DEPORTIVO");
                        tipo.appendChild(textoTipo);

                        //Insertamos el cambio
                        Element cambio = documento.createElement("cambio");
                        Text cambioTexto = documento.createTextNode(String.valueOf(deportivo.getCambio()));
                        cambio.appendChild(cambioTexto);

                        //Insertamos el descapotable
                        Element descapotable = documento.createElement("descapotable");
                        Text textoDescapotable = documento.createTextNode(String.valueOf(deportivo.getDescapotable()));
                        descapotable.appendChild(textoDescapotable);

                        vehiculo_.appendChild(tipo);
                        vehiculo_.appendChild(matricula);
                        vehiculo_.appendChild(marca);
                        vehiculo_.appendChild(modelo);
                        vehiculo_.appendChild(cilindrada);
                        vehiculo_.appendChild(num_puertas);
                        vehiculo_.appendChild(combustible);
                        vehiculo_.appendChild(cambio);
                        vehiculo_.appendChild(descapotable);

                    }

                    if (vehiculos.get(i) instanceof Familiar) {

                        Familiar familiar = (Familiar) aux;

                        //Insertamos el Tipo de vehiculo
                        Element tipo = documento.createElement("tipo");
                        Text textoTipo = documento.createTextNode("FAMILIAR");
                        tipo.appendChild(textoTipo);

                        Element num_plazas = documento.createElement("num_plazas");
                        Text textoNum_plazas = documento.createTextNode(String.valueOf(familiar.getNumPlazas()));
                        num_plazas.appendChild(textoNum_plazas);

                        Element sillaBebe = documento.createElement("silla_bebe");
                        Text textoSillaBebe = documento.createTextNode(String.valueOf(familiar.getSillaBebe()));
                        sillaBebe.appendChild(textoSillaBebe);

                        vehiculo_.appendChild(tipo);
                        vehiculo_.appendChild(matricula);
                        vehiculo_.appendChild(marca);
                        vehiculo_.appendChild(modelo);
                        vehiculo_.appendChild(cilindrada);
                        vehiculo_.appendChild(num_puertas);
                        vehiculo_.appendChild(combustible);
                        vehiculo_.appendChild(num_plazas);
                        vehiculo_.appendChild(sillaBebe);
                    }

                }

                if (vehiculos.get(i) instanceof Mercancias) {

                    Mercancias aux1 = (Mercancias) vehiculos.get(i);

                    //Insertamos el pma
                    Element pma = documento.createElement("pma");
                    Text textoPma = documento.createTextNode(String.valueOf(aux1.getPma()));
                    pma.appendChild(textoPma);

                    //Insertamos el volumen
                    Element volumen = documento.createElement("volumen");
                    Text textoVolumen = documento.createTextNode(String.valueOf(aux1.getVolumen()));
                    volumen.appendChild(textoVolumen);

                    if (aux1 instanceof Furgoneta) {

                        Furgoneta furgoneta = (Furgoneta) aux1;

                        //Insertamos el Tipo de vehiculo
                        Element tipo = documento.createElement("tipo");
                        Text textoTipo = documento.createTextNode("FURGONETA");
                        tipo.appendChild(textoTipo);

                        //Insertamos si refrigerado
                        Element refrigerado = documento.createElement("refrigerado");
                        Text textoRefrigerado = documento.createTextNode(String.valueOf(furgoneta.getRefrigerado()));
                        refrigerado.appendChild(textoRefrigerado);

                        //Insertamos el tamaño
                        Element tamanio = documento.createElement("tamanio");
                        Text textoTamanio = documento.createTextNode(String.valueOf(furgoneta.getTamanio()));
                        tamanio.appendChild(textoTamanio);

                        vehiculo_.appendChild(tipo);
                        vehiculo_.appendChild(matricula);
                        vehiculo_.appendChild(marca);
                        vehiculo_.appendChild(modelo);
                        vehiculo_.appendChild(cilindrada);
                        vehiculo_.appendChild(pma);
                        vehiculo_.appendChild(volumen);
                        vehiculo_.appendChild(refrigerado);
                        vehiculo_.appendChild(tamanio);

                    }

                }

                //Agregamos el elemento vehículo al nodo raíz del XML.
                documento.getDocumentElement().appendChild(vehiculo_);

            }

            //Asociar el source con el Document
            Source fuente = new DOMSource(documento);

            //Creamos el Result, indicandole el fichero a crear
            Result resultado = new StreamResult(new File(nombreFichero));

            //Creamos un transformer para crear finalmente el archivo XML
            Transformer transformer = TransformerFactory.newInstance().newTransformer();

            transformer.transform(fuente, resultado);

            escribirLn("    Guardado vehiculos.xml correctamente.");
            escribirLn("------------------------------------------------\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private static void guardarDatosXMLAlquileres() {

        String nombreFichero = "alquileres.xml";

        String nodo = "Alquileres";

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        try {

            //Creamos una instancia de DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            //Creamos el documentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Crear un DOMImplementation
            DOMImplementation implementation = builder.getDOMImplementation();

            //Creamos el documento con un elemento raíz
            Document documento = implementation.createDocument(null, nodo, null);

            //Indicamos la versión del documento
            documento.setXmlVersion("1.0");

            for (int i = 0; i < alquileres.size(); i++) {

                Element alquiler = documento.createElement("Alquiler");

                // Insertamos el DNI del cliente
                Element elemento = documento.createElement("dni");
                Text textoElemento = documento.createTextNode(alquileres.get(i).getCliente().getDni());
                elemento.appendChild(textoElemento);
                alquiler.appendChild(elemento);

                elemento = documento.createElement("matricula");
                textoElemento = documento.createTextNode(alquileres.get(i).getVehiculo().getMatricula());
                elemento.appendChild(textoElemento);
                alquiler.appendChild(elemento);

                elemento = documento.createElement("fechaYhora");
                textoElemento = documento.createTextNode(sdf.format(alquileres.get(i).getFecha().getTime()));
                elemento.appendChild(textoElemento);
                alquiler.appendChild(elemento);

                elemento = documento.createElement("dias");
                textoElemento = documento.createTextNode(String.valueOf(alquileres.get(i).getDias()));
                elemento.appendChild(textoElemento);
                alquiler.appendChild(elemento);

                //Agregamos el elemento alquiler al nodo raíz del XML.
                documento.getDocumentElement().appendChild(alquiler);

                //Asociar el source con el Document
                Source fuente = new DOMSource(documento);

                //Creamos el Result, indicandole el fichero a crear
                Result resultado = new StreamResult(new File(nombreFichero));

                //Creamos un transformer para crear finalmente el archivo XML
                Transformer transformer = TransformerFactory.newInstance().newTransformer();

                transformer.transform(fuente, resultado);

//                escribirLn("    Guardado alquileres.xml correctamente.");
//                escribirLn("------------------------------------------------\n");
            }
            escribirLn("    Guardado alquileres.xml correctamente.");
            escribirLn("------------------------------------------------\n");

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public static void leerDatosXML() {

        leerDatosXMLClientes();
        leerDatosXMLVehiculos();
        leerDatosXMLAlquileres();

    }

    private static void leerDatosXMLClientes() {

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Cargar el documento XML de entrada y parsearlo
            Document doc = builder.parse(new File("clientes.xml"));

            NodeList nodeList = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) node;

                    //Obtenemos el valor de DNI
                    String dni = elemento.getElementsByTagName("dni").item(0).getChildNodes().item(0).getTextContent();

                    //Obtenemos el valor de Nombre
                    String nombre = elemento.getElementsByTagName("nombre").item(0).getChildNodes().item(0).getTextContent();

                    //Obtenemos el valor de Direccion
                    String direccion = elemento.getElementsByTagName("direccion").item(0).getChildNodes().item(0).getTextContent();

                    //Obtenemos el valor de Localidad
                    String localidad = elemento.getElementsByTagName("localidad").item(0).getChildNodes().item(0).getTextContent();

                    //Obtenemos el valor de cod_postal
                    String cod_postal = elemento.getElementsByTagName("cod_postal").item(0).getChildNodes().item(0).getTextContent();

                    Cliente cliente = new Cliente(dni, nombre, direccion, localidad, cod_postal);

                    clientes.add(cliente);

                }
            }
            escribirLn("Clientes guardados en el ArrayList.");

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }
    }

    private static void leerDatosXMLVehiculos() {

        Combustible c = null;
        CajaCambios cajacambios = null;
        Tamanio tamanio_ = null;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Cargar el documento XML de entrada y parsearlo
            Document doc = builder.parse(new File("vehiculos.xml"));

            NodeList nodeList = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) node;

                    //Obtenemos el valor de tipo
                    String matricula = elemento.getElementsByTagName("matricula").item(0).getChildNodes().item(0).getTextContent();

                    //Obtenemos el valor de tipo
                    String marca = elemento.getElementsByTagName("marca").item(0).getChildNodes().item(0).getTextContent();

                    //Obtenemos el valor de tipo
                    String modelo = elemento.getElementsByTagName("modelo").item(0).getChildNodes().item(0).getTextContent();

                    //Obtenemos el valor de tipo
                    int cilindrada = Integer.parseInt(elemento.getElementsByTagName("cilindrada").item(0).getChildNodes().item(0).getTextContent());

                    //Obtenemos el valor de tipo
                    String tipo = elemento.getElementsByTagName("tipo").item(0).getChildNodes().item(0).getTextContent();

                    switch (tipo) {

                        case "DEPORTIVO":

                            int num_puertas = Integer.parseInt(elemento.getElementsByTagName("num_puertas").item(0).getChildNodes().item(0).getTextContent());

                            String combustible = elemento.getElementsByTagName("combustible").item(0).getChildNodes().item(0).getTextContent();

                            switch (combustible) {
                                case "GASOLINA":
                                    c = Combustible.GASOLINA;
                                    break;
                                case "DIESEL":
                                    c = Combustible.DIESEL;
                                    break;
                                case "HIBRIDO":
                                    c = Combustible.HIBRIDO;
                                    break;
                                case "ELECTRICO":
                                    c = Combustible.ELECTRICO;
                                    break;
                            }

                            String cambio = elemento.getElementsByTagName("cambio").item(0).getChildNodes().item(0).getTextContent();

                            switch (cambio) {
                                case "MANUAL":
                                    cajacambios = CajaCambios.MANUAL;
                                    break;
                                case "AUTOMATICO":
                                    cajacambios = CajaCambios.AUTOMATICO;
                                    break;
                            }

                            String descapotable = elemento.getElementsByTagName("descapotable").item(0).getChildNodes().item(0).getTextContent();
                            boolean desc = (descapotable.equalsIgnoreCase("TRUE") ? true : false);

                            vehiculos.add(new Deportivo(matricula, marca, modelo, cilindrada, num_puertas, c, cajacambios, desc));

                            break;

                        case "FAMILIAR":

                            num_puertas = Integer.parseInt(elemento.getElementsByTagName("num_puertas").item(0).getChildNodes().item(0).getTextContent());

                            combustible = elemento.getElementsByTagName("combustible").item(0).getChildNodes().item(0).getTextContent();

                            switch (combustible) {
                                case "GASOLINA":
                                    c = Combustible.GASOLINA;
                                    break;
                                case "DIESEL":
                                    c = Combustible.DIESEL;
                                    break;
                                case "HIBRIDO":
                                    c = Combustible.HIBRIDO;
                                    break;
                                case "ELECTRICO":
                                    c = Combustible.ELECTRICO;
                                    break;
                            }

                            int numPlazas = Integer.parseInt(elemento.getElementsByTagName("num_plazas").item(0).getChildNodes().item(0).getTextContent());

                            String silla_bebe = elemento.getElementsByTagName("silla_bebe").item(0).getChildNodes().item(0).getTextContent();
                            boolean sillaBebe = (silla_bebe.equalsIgnoreCase("TRUE")) ? true : false;

                            vehiculos.add(new Familiar(matricula, marca, modelo, cilindrada, num_puertas, c, numPlazas, sillaBebe));

                            break;

                        case "FURGONETA":

                            int pma = Integer.parseInt(elemento.getElementsByTagName("pma").item(0).getChildNodes().item(0).getTextContent());

                            int volumen = Integer.parseInt(elemento.getElementsByTagName("volumen").item(0).getChildNodes().item(0).getTextContent());

                            String refrigerado = elemento.getElementsByTagName("refrigerado").item(0).getChildNodes().item(0).getTextContent();
                            boolean refrigerado_ = (refrigerado.equalsIgnoreCase("TRUE")) ? true : false;

                            String tamanio = elemento.getElementsByTagName("tamanio").item(0).getChildNodes().item(0).getTextContent();
                            switch (tamanio) {

                                case "PEQUENIO":
                                    tamanio_ = Tamanio.PEQUENIO;
                                    break;
                                case "MEDIANO":
                                    tamanio_ = Tamanio.MEDIANO;
                                    break;
                                case "GRANDE":
                                    tamanio_ = Tamanio.GRANDE;
                                    break;
                            }

                            vehiculos.add(new Furgoneta(matricula, marca, modelo, cilindrada, pma, volumen, refrigerado_, tamanio_));

                            break;

                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        escribirLn("Vehiculos guardados en el ArrayList.");
    }

    private static void leerDatosXMLAlquileres() {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            //Cargar el documento XML de entrada y parsearlo
            Document doc = builder.parse(new File("alquileres.xml"));

            NodeList nodeList = doc.getDocumentElement().getChildNodes();

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) node;

                    //Obtenemos el valor de DNI
                    String dni = elemento.getElementsByTagName("dni").item(0).getChildNodes().item(0).getTextContent();

                    //Obtenemos el valor de Matrícula
                    String matricula = elemento.getElementsByTagName("matricula").item(0).getChildNodes().item(0).getTextContent();

                    //Obtenemos el valor de Fecha
                    String fechaYhora = elemento.getElementsByTagName("fechaYhora").item(0).getChildNodes().item(0).getTextContent();

                    //Obtenemos el valor de Dias
                    String dias_ = elemento.getElementsByTagName("dias").item(0).getChildNodes().item(0).getTextContent();
                    int dias = Integer.valueOf(dias_);

                    //Buscamos cliente y vehiculo por dni y matricula para generar el alquiler
                    if (buscarCliente(dni) != -1) {

                        Cliente nuevoCliente = clientes.get(buscarCliente(dni));

                        if (buscarVehiculo(matricula) != -1) {

                            Vehiculo nuevoVehiculo = vehiculos.get(buscarVehiculo(matricula));

                            String[] datosFecha = fechaYhora.split("[/ :]+");

                            int day = Integer.parseInt(datosFecha[0]);
                            int month = Integer.parseInt(datosFecha[1]);
                            int year = Integer.parseInt(datosFecha[2]);
                            int hour = Integer.parseInt(datosFecha[3]);
                            int minute = Integer.parseInt(datosFecha[4]);

                            Calendar fechaAlquiler = new GregorianCalendar(year, month - 1, day, hour, minute);

                            Alquiler nuevoAlquiler = new Alquiler(nuevoCliente, nuevoVehiculo);
                            nuevoAlquiler.setFecha(fechaAlquiler);
                            nuevoAlquiler.setDias(dias);

                            alquileres.add(nuevoAlquiler);
//meter boolean /pasarlo aqui atrue y que dispare el sout final
                        }
                    }
                }
            }
            escribirLn("Alquileres guardados en el ArrayList.");

        } catch (Exception e) {
            System.out.println(e.getMessage());

        }

    }

//    
//
//---------------------------------------METODOS AUXILIARES-------------------------------------//
//
//
//   
    public AlquilerVehiculos() {
    }

    public static Cliente getCliente(String dni) {

        Cliente c = null;
        boolean encontrado = false;

        for (int i = 0; i < clientes.size() && !encontrado; i++) {
            //si la posicion array no es nula y coincide el dni existente lo devuelve
            if (clientes.get(i).getDni().equalsIgnoreCase(dni)) {
                c = clientes.get(i);
                encontrado = true;
            }
        }

        return c;

    }

    public static int buscarCliente(String dni) {
        //Devuelve la posición del array de un DNI determinado.

        int pos = -1;

        for (int i = 0; i < clientes.size(); i++) {
            if (dni.equalsIgnoreCase(clientes.get(i).getDni())) {
                pos = i;
            }
        }
        return pos;
    }

    public static Vehiculo getVehiculo(String matricula) {
        /*Crea un método getVehiculo que se le pase la matrícula de un turismo y nos
lo devuelva si este existe o null en caso contrario.*/

        Vehiculo v = null;

        if ((Controlador.Utilidades.comprobarMatricula(matricula))) {
            for (int i = 0; i < vehiculos.size(); i++) {
                //for (Vehiculo vehiculo : vehiculos){
                //si la posicion array no es nula y coincide el dni existente lo devuelve        
                if (vehiculos.get(i).getMatricula().equalsIgnoreCase(matricula)) {
                    //if (vehiculo.getMatricula().equalsIgnoreCase(matricula)){
                    //utilizar método get de arryLIst

                    v = vehiculos.get(i);

                    break;
                }
            }
        }
        return v;
    }

    public static int buscarVehiculo(String matricula) {
        //Devuelve la posición del array de una matrícula determinada.

        int pos = -1;

        for (int i = 0; i < vehiculos.size(); i++) {
            if (vehiculos.get(i).getMatricula().equalsIgnoreCase(matricula)) {
                pos = i;
            }
        }
        return pos;
    }

    public static String procesarDni(String dni) {

        String dniAux = dni;

        if (comprobarDni(dniAux)) {

            //Comprobamos si es un NIE, y en caso de serlo lo convertimos a DNI para posteriormente
            //comprobar la letra final.
            if (dniAux.substring(0, 1).equalsIgnoreCase("X")
                    || dniAux.substring(0, 1).equalsIgnoreCase("Y")
                    || dniAux.substring(0, 1).equalsIgnoreCase("Z")) {
                dniAux = pasarNieADni(dniAux);
            }

            dni += calcularLetraDni(dniAux);
        } else {
            escribirLn("\n********************ATENCION********************");
            escribirLn("           Formato de Dni/Nie incorrecto."
                    + "\nEscoja de nuevo una opción del menu principal.");
            escribirLn("------------------------------------------------\n");
        }
        return dni;
    }

    public static void vaciarArrays() {
        clientes.clear();
        vehiculos.clear();
        alquileres.clear();
    }

}
