/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package planifica;

/**
 *
 * @author p.rodriguezvic
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Planifica {

    File entrada, salida;
    int numeroDatos;
    List<Reunion> reuniones = new ArrayList<>();
    List<Integer> solucion = new ArrayList<>();
    Scanner teclado = new Scanner(System.in);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Planifica().exec(args);  
    }

    public void AlgoritmoVoraz() {
        int i = 0;
        while(!reuniones.isEmpty()){
            Reunion reunion = reuniones.get(i);
            solucion.add(reunion.getID());
            reuniones.remove(reunion);
        }
        
    }
    
    public void exec(String[] args){
        
        if(args.length == 0){
            System.out.println("No se han detectado fichero de entrada, por favor"
                    + "introduce los datos por teclado con el siguiente formato:[numero de tareas, "
                    + "inicio de reunion espacio en blanco final de reunion]");
            
            System.out.println("Introduce el numero de tareas");
            numeroDatos = Integer.parseInt(teclado.next());
            
            for(int i=0; i<numeroDatos; i++){
                System.out.println("Introduce el dato "+(i+1));
                
                System.out.println("Introduce la hora de entrada");
                int inicio = Integer.parseInt(teclado.next());
                
                System.out.println("Introduce la hora de salida");
                int fin = Integer.parseInt(teclado.next());
                
                Reunion reunion = new Reunion(inicio,fin,i);
                
                reuniones.add(reunion);
            }
        }else if(args.length > 0){
            System.out.println(args[0]);
            FileReader f = null;
            try {
                entrada = new File(args[0]);
                f = new FileReader(entrada);
                BufferedReader b = new BufferedReader(f);
                
                numeroDatos = Integer.parseInt(b.readLine());
                
                for(int i = 0; i< numeroDatos; i++){
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
            
            //Para debuggear
            /*for(int i = 0; i< numeroDatos; i++){
                
                System.out.println(reuniones.get(i).getHoraInicio()+" "+ reuniones.get(i).getHoraFin());
                 
            }*/
            
        }
        
        AlgoritmoVoraz();
        
        if(args.length < 2){
            for(int i = 0; i< solucion.size();i++){
                System.out.println(solucion.get(i));
            }
        }else{
            System.out.println(args[1]);
            salida = new File(args[1]);
            FileWriter f = null;
            try {
                f = new FileWriter(salida);
                PrintWriter p = new PrintWriter(f);  
                
                for(int i = 0; i<solucion.size(); i++){
                    p.println(solucion.get(i));
                }
                
            } catch (IOException ex) {
                Logger.getLogger(Planifica.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                try {
                    f.close();
                } catch (IOException ex) {
                    Logger.getLogger(Planifica.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
    }
}
