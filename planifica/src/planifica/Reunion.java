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
    private int duracion;

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

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public Reunion(int horaInicio, int horaFin, int ID) {
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.ID = ID;
        if(horaFin != 0){
            this.duracion = horaFin - horaInicio;
        }else if(horaFin == 0){
            this.duracion = 24 - horaInicio;
        }
    }
    
    public void ActualizarHoras(Reunion candidato){
        this.horaInicio -= candidato.getHoraFin();
        this.horaFin -= candidato.getHoraFin();
    }
}
