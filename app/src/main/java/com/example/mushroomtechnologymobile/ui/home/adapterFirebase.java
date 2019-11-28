package com.example.mushroomtechnologymobile.ui.home;

public class adapterFirebase {
    int kelembapan;
    int suhu;
    String pompa;

    public adapterFirebase(int kelembapan, int suhu, String pompa) {
        this.kelembapan = kelembapan;
        this.suhu = suhu;
        this.pompa = pompa;
    }

    public int getKelembapan() {
        return kelembapan;
    }

    public int getSuhu() {
        return suhu;
    }

    public String getPompa() {
        return pompa;
    }
}
