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
class Reunion {
    private int horaInicio;
    private int horaFin;
    private int ID;

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(int horaFin) {
        this.horaFin = horaFin;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public Reunion(int horaInicio, int horaFin, int ID) {
        this.horaInicio = horaInicio;
        this.ID = ID;
        this.horaFin = horaFin;
    }

    @Override
    public String toString() {
        return ID+" "+ horaInicio + " " + horaFin ;
    }
    

}
