package com.turismoalojamenduakandroid;

import java.io.Serializable;

public class Bezeroa implements Serializable {
   private String NAN;
    private String BEZERO_IZENA;
    private String BEZERO_TELEFONOA;
    private String BEZERO_EMAIL;

    @Override
    public String toString() {
        return NAN;
    }

    public Bezeroa(String nan, String izena,String telef, String email) {
        this.NAN = nan;
        this.BEZERO_IZENA = izena;
        this.BEZERO_TELEFONOA = telef;
        this.BEZERO_EMAIL = email;
    }

    public Bezeroa() {
    }

    public String getNAN() {
        return NAN;
    }

    public void setNAN(String NAN) {
        this.NAN = NAN;
    }

    public String getBEZERO_IZENA() {
        return BEZERO_IZENA;
    }

    public void setBEZERO_IZENA(String BEZERO_IZENA) {
        this.BEZERO_IZENA = BEZERO_IZENA;
    }

    public String getBEZERO_EMAIL() {
        return BEZERO_EMAIL;
    }

    public void setBEZERO_EMAIL(String BEZERO_EMAIL) {
        this.BEZERO_EMAIL = BEZERO_EMAIL;
    }

    public String getBEZERO_TELEFONOA() {
        return BEZERO_TELEFONOA;
    }

    public void setBEZERO_TELEFONOA(String BEZERO_TELEFONOA) {
        this.BEZERO_TELEFONOA = BEZERO_TELEFONOA;
    }

}
