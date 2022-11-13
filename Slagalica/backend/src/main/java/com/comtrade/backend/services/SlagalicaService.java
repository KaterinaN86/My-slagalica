package com.comtrade.backend.services;

public interface SlagalicaService {

    String slovaZaPronalazakReci();
    Integer obradaFajla(String slovaZaPronalazak, String izabranaRecKorisnika);
}
