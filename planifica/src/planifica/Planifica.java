/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planifica;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pablo
 */
public class Planifica {

    long[] tiempos = new long[50];
    File entrada, salida;
    int numeroDatos;
    List<Reunion> reuniones = new ArrayList<>();
    List<Integer> solucion = new ArrayList<>();
    Scanner teclado = new Scanner(System.in);
    boolean archivoDeSalida = true;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Planifica().exec(args);
    }

    /**
     * Este es el algoritmo voraz, consiste un un bucle que elige un candidato,
     * lo saca de la lista de candidatos, actualiza las horas del resto de
     * candidatos restandoles la hora de fin del candidato y elimina los
     * candidatos que ya no sea posible usar por las condiciones del problema,
     * por último introduce el candidato en la solucion.
     */
    public void AlgoritmoVoraz() {
        while (!reuniones.isEmpty()) {

            Reunion candidato = SeleccionarCandidato();     //Funcion de seleccion.

            reuniones.remove(candidato);                    //Eliminacion del candidato de la lista

            for (int i = 0; i < reuniones.size(); i++) {                  //Bucle de eliminacion de los cnadidatos no posibles
                if (reuniones.get(i).getHoraInicio() < candidato.getHoraFin()) {
                    reuniones.remove(reuniones.get(i));
                    i--;
                }
            }

            solucion.add(candidato.getID());                //Introduccion del candidato en la solución

        }

    }

    /**
     * Este es el método encargado de las lecturas y escrituras de ficheros y de
     * las entradas por teclado y salidas por pantalla.
     *
     * @param args
     */
    public void exec(String[] args) {

        if (args.length > 1) { //Se comprueba si el archivo de salida ya existe y se pideo o elegir uno nuevo o no elegir ninguno
            salida = new File(args[1] + ".txt");
            if (salida.exists()) {
                archivoDeSalida = false;
            }
        }

        if (args.length == 0) { // Se comprueba si se ha elegido un archivo de entrada, en caso negativo se
            // piden datos por teclado, en caso positivo se lee el archivo
            System.out.println(
                    "No se han detectado fichero de entrada, por favor indique si quiere reuniones aleatorias (Y para si, N para no)");

            String seleccion = teclado.nextLine();
            if (seleccion.equals("y")) {

                System.out.println("Introduce el numero de tareas");
                numeroDatos = Integer.parseInt(teclado.nextLine());
                GenerateReunionesAleatorias(numeroDatos);

            } else {

                System.out.println("No se han detectado fichero de entrada, por favor"
                        + "introduce los datos por teclado con el siguiente formato:[numero de tareas, "
                        + "inicio de reunion espacio en blanco final de reunion]");

                System.out.println("Introduce el numero de tareas");
                numeroDatos = Integer.parseInt(teclado.nextLine());

                for (int i = 0; i < numeroDatos; i++) {
                    System.out.println("\nIntroduce el dato " + (i + 1));

                    System.out.println("\nIntroduce la hora de entrada");
                    int inicio = Integer.parseInt(teclado.nextLine());
                    int fin = -1;
                    while (fin < inicio) {
                        System.out.println("\nIntroduce la hora de salida");
                        fin = Integer.parseInt(teclado.nextLine());
                    }

                    Reunion reunion = new Reunion(inicio, fin, i);

                    reuniones.add(reunion);
                }
            }
        } else if (args.length > 0) {
            System.out.println(args[0]);
            FileReader f = null;
            try {
                entrada = new File(args[0] + ".txt");
                f = new FileReader(entrada);
                BufferedReader b = new BufferedReader(f);

                numeroDatos = Integer.parseInt(b.readLine());

                for (int i = 0; i < numeroDatos; i++) {
                    String[] horasString = b.readLine().split(" ");
                    Integer[] horas = new Integer[horasString.length];

                    horas[0] = Integer.parseInt(horasString[0]);
                    horas[1] = Integer.parseInt(horasString[1]);

                    Reunion reunion = new Reunion(horas[0], horas[1], i);
                    
                    reuniones.add(reunion);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Planifica.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Planifica.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    f.close();
                } catch (IOException ex) {
                    Logger.getLogger(Planifica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        
        //for (int i = 0; i < 50; i++) {
            //long media = 0;
            //for (int j = 0; j < 50; j++) {
                //reuniones.clear();
                //GenerateReunionesAleatorias((i + 1) * 1000);
                //long start = System.nanoTime();
                AlgoritmoVoraz();
                //media += System.nanoTime() - start;
                //solucion.clear();
            //}
            //media = media / 50;
            //tiempos[i] = media;
        //}
        if (args.length < 2 || !archivoDeSalida) { //Se comprueba si se ha elegido un archivo de salida, en caso negativo se imprime por pantalla en caso positivo se escribe en el fichero

            System.out.print("La solución es: ");

            for (int i = 0; i < solucion.size(); i++) {
                System.out.print(solucion.get(i) + ", ");
            }
        } else if (args.length >= 2 && archivoDeSalida) {
            System.out.println(args[1]);
            salida = new File(args[1] + ".txt");

            FileWriter f = null;
            try {
                f = new FileWriter(salida);
                PrintWriter p = new PrintWriter(f);

                for (int i = 0; i < solucion.size(); i++) {
                    p.println(solucion.get(i));
                }

            } catch (IOException ex) {
                Logger.getLogger(Planifica.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    f.close();
                } catch (IOException ex) {
                    Logger.getLogger(Planifica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }

    /**
     * Esta funcion se encarga de elegir el mejor condidato de la lista de
     * candidatos para ello se escoge el candidato con menor hora de
     * finalizacion.
     *
     * @param ini principio del array
     * @param fin final del array
     *
     * @return devuelve un objeto de la clase Reunion
     */
    private Reunion SeleccionarCandidato() {
        Reunion candidato = new Reunion(0, 25, 0);                        //Creación de un candidato para las primeras comparaciones
        for (int i = 0; i < reuniones.size(); i++) {
            if (reuniones.get(i).getHoraFin() < candidato.getHoraFin()) { //Condicion para elegir el candidato
                candidato = reuniones.get(i);
            }

        }

        return candidato;
    }

    private void GenerateReunionesAleatorias(int numeroDatos) {
        Random random = new Random();
        for (int i = 0; i < numeroDatos; i++) {
            int inicio = random.nextInt(24);
            int horaFinal = inicio + 1 + random.nextInt(24 - inicio);
            Reunion reunion = new Reunion(inicio, horaFinal, i);
            reuniones.add(reunion);
        }

    }

}
