package com.turismoalojamenduakandroid;

import java.io.Serializable;

public class Ostatu implements Serializable {
   private String ID_SIGNATURA;
    private String OSTATU_IZENA;
    private String DESKRIBAPENA;
    private String OSTATU_HELBIDEA;
    private String MARKA;
    private String OSTATU_EMAIL;
    private String OSTATU_TELEFONOA;
    private int PERTSONA_TOT;
    private double LATITUDE;
    private double LONGITUDE;
    private String MOTA;
    private String WEB_URL;
    private String ADISKIDETSU_URL;
    private String ZIP_URL;

    @Override
    public String toString() {
        return  OSTATU_IZENA;
    }

    public Ostatu(double LATITUDE, double LONGITUDE) {
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
    }

    public Ostatu(String ID_SIGNATURA, String OSTATU_IZENA, String DESKRIBAPENA, String OSTATU_HELBIDEA, String MARKA, String OSTATU_EMAIL, String OSTATU_TELEFONOA, int PERTSONA_TOT, double LATITUDE, double LONGITUDE, String MOTA, String WEB_URL, String ADISKIDETSU_URL, String ZIP_URL, int POSTA_KODEA, String HERRI_KODEA) {

        this.ID_SIGNATURA = ID_SIGNATURA;
        this.OSTATU_IZENA = OSTATU_IZENA;
        this.DESKRIBAPENA = DESKRIBAPENA;
        this.OSTATU_HELBIDEA = OSTATU_HELBIDEA;
        this.MARKA = MARKA;
        this.OSTATU_EMAIL = OSTATU_EMAIL;
        this.OSTATU_TELEFONOA = OSTATU_TELEFONOA;
        this.PERTSONA_TOT = PERTSONA_TOT;
        this.LATITUDE = LATITUDE;
        this.LONGITUDE = LONGITUDE;
        this.MOTA = MOTA;
        this.WEB_URL = WEB_URL;
        this.ADISKIDETSU_URL = ADISKIDETSU_URL;
        this.ZIP_URL = ZIP_URL;
        this.POSTA_KODEA = POSTA_KODEA;
        this.HERRI_KODEA = HERRI_KODEA;
    }

    public Ostatu() {
    }

    public String getID_SIGNATURA() {
        return ID_SIGNATURA;
    }

    public void setID_SIGNATURA(String ID_SIGNATURA) {
        this.ID_SIGNATURA = ID_SIGNATURA;
    }

    public String getOSTATU_IZENA() {
        return OSTATU_IZENA;
    }

    public void setOSTATU_IZENA(String OSTATU_IZENA) {
        this.OSTATU_IZENA = OSTATU_IZENA;
    }

    public String getDESKRIBAPENA() {
        return DESKRIBAPENA;
    }

    public void setDESKRIBAPENA(String DESKRIBAPENA) {
        this.DESKRIBAPENA = DESKRIBAPENA;
    }

    public String getOSTATU_HELBIDEA() {
        return OSTATU_HELBIDEA;
    }

    public void setOSTATU_HELBIDEA(String OSTATU_HELBIDEA) {
        this.OSTATU_HELBIDEA = OSTATU_HELBIDEA;
    }

    public String getMARKA() {
        return MARKA;
    }

    public void setMARKA(String MARKA) {
        this.MARKA = MARKA;
    }

    public String getOSTATU_EMAIL() {
        return OSTATU_EMAIL;
    }

    public void setOSTATU_EMAIL(String OSTATU_EMAIL) {
        this.OSTATU_EMAIL = OSTATU_EMAIL;
    }

    public String getOSTATU_TELEFONOA() {
        return OSTATU_TELEFONOA;
    }

    public void setOSTATU_TELEFONOA(String OSTATU_TELEFONOA) {
        this.OSTATU_TELEFONOA = OSTATU_TELEFONOA;
    }

    public int getPERTSONA_TOT() {
        return PERTSONA_TOT;
    }

    public void setPERTSONA_TOT(int PERTSONA_TOT) {
        this.PERTSONA_TOT = PERTSONA_TOT;
    }

    public double getLATITUDE() {
        return LATITUDE;
    }

    public void setLATITUDE(double LATITUDE) {
        this.LATITUDE = LATITUDE;
    }

    public double getLONGITUDE() {
        return LONGITUDE;
    }

    public void setLONGITUDE(double LONGITUDE) {
        this.LONGITUDE = LONGITUDE;
    }

    public String getMOTA() {
        return MOTA;
    }

    public void setMOTA(String MOTA) {
        this.MOTA = MOTA;
    }

    public String getWEB_URL() {
        return WEB_URL;
    }

    public void setWEB_URL(String WEB_URL) {
        this.WEB_URL = WEB_URL;
    }

    public String getADISKIDETSU_URL() {
        return ADISKIDETSU_URL;
    }

    public void setADISKIDETSU_URL(String ADISKIDETSU_URL) {
        this.ADISKIDETSU_URL = ADISKIDETSU_URL;
    }


    public String getZIP_URL() {
        return ZIP_URL;
    }

    public void setZIP_URL(String ZIP_URL) {
        this.ZIP_URL = ZIP_URL;
    }

    public int getPOSTA_KODEA() {
        return POSTA_KODEA;
    }

    public void setPOSTA_KODEA(int POSTA_KODEA) {
        this.POSTA_KODEA = POSTA_KODEA;
    }

    public String getHERRI_KODEA() {
        return HERRI_KODEA;
    }

    public void setHERRI_KODEA(String HERRI_KODEA) {
        this.HERRI_KODEA = HERRI_KODEA;
    }

    private int POSTA_KODEA;
    private String HERRI_KODEA;


}
