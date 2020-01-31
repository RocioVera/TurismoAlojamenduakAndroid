package com.turismoalojamenduakandroid;

public class erabiltzaileak {
    private String NAN;
    private String ERABIL_IZENA;
    private String ABIZENAK;
    private String ERABIL_EMAIL;
    private String ERABIL_TELEFONO;


    public erabiltzaileak(String NAN, String ERABIL_IZENA, String ABIZENAK, String ERABIL_EMAIL, String ERABIL_TELEFONO) {
        this.NAN = NAN;
        this.ERABIL_IZENA = ERABIL_IZENA;
        this.ABIZENAK = ABIZENAK;
        this.ERABIL_EMAIL = ERABIL_EMAIL;
        this.ERABIL_TELEFONO = ERABIL_TELEFONO;
    }


    public String getNAN() {
        return NAN;
    }

    public void setNAN(String NAN) {
        this.NAN = NAN;
    }

    public String getERABIL_IZENA() {
        return ERABIL_IZENA;
    }

    public void setERABIL_IZENA(String ERABIL_IZENA) {
        this.ERABIL_IZENA = ERABIL_IZENA;
    }

    public String getABIZENAK() {
        return ABIZENAK;
    }

    public void setABIZENAK(String ABIZENAK) {
        this.ABIZENAK = ABIZENAK;
    }

    public String getERABIL_EMAIL() {
        return ERABIL_EMAIL;
    }

    public void setERABIL_EMAIL(String ERABIL_EMAIL) {
        this.ERABIL_EMAIL = ERABIL_EMAIL;
    }

    public String getERABIL_TELEFONO() {
        return ERABIL_TELEFONO;
    }

    public void setERABIL_TELEFONO(String ERABIL_TELEFONO) {
        this.ERABIL_TELEFONO = ERABIL_TELEFONO;
    }
}
