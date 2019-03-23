package com.darsco.tjam;

import java.io.Serializable;

public class Entidad implements Serializable {
    private String imgFoto;
    private String titulo;
    private String resumen;
    private String contenido;
    private String fechaAcdo;
    private String extAcdo;

    public Entidad(){
        this.imgFoto = imgFoto;
        this.titulo = titulo;
        this.contenido = contenido;
        this.resumen = resumen;
        this.fechaAcdo = fechaAcdo;
        this.extAcdo = extAcdo;
    }

    public String getImgFoto() {return imgFoto;}

    public String getTitulo() {return titulo;}

    public String getResumen() {return resumen;}

    public String getContenido() {return contenido;}

    public String getFechaAcdo() {return fechaAcdo;}

    public String getExtAcdo() {return extAcdo;}

    public void setImgFoto(String imgFoto){
        this.imgFoto = imgFoto;
    }

    public void setTitulo(String titulo){
        this.titulo = titulo;
    }

    public void setResumen(String resumen){
        this.resumen = resumen;
    }

    public void setContenido(String contenido){
        this.contenido = contenido;
    }

    public void setFechaAcdo(String fechaAcdo){
        this.fechaAcdo = fechaAcdo;
    }

    public void setExtAcdo(String extAcdo){
        this.extAcdo = extAcdo;
    }
}