package ogs.temis.servicios;

public class ServicioExcepcion extends Exception{
    public int codErr =0;
    public String mensErr ="Aceptada";
    public String moduloErr="";
    public ServicioExcepcion(int codErr, String mensErr, String moduloErr){
        this.codErr=codErr;
        this.mensErr=mensErr;
        this.moduloErr=moduloErr;
    }
}
