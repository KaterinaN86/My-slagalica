package com.comtrade.backend.services;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class SlagalicaServiceImp implements SlagalicaService {


    @Override
    public String slovaZaPronalazakReci() {

        Random random = new Random();
        String[] samoglasnici = {"A","E","I","O","U"};
        String[] suglasnici = {"C","Č","Ć","D","Dž","Đ","F","G","H","J","K","L","Lj","M","N","Nj","P","R","S","Š","T","Z","Ž","V"};

        String s = "";
        int brS = 0, brSamo = 0, brSug = 0;

        while(brS < 12) {

            if(brSamo != 6 && brSug != 6) {
                s += (samoglasnici[random.nextInt(samoglasnici.length)] + suglasnici[random.nextInt(suglasnici.length)]);
                brSamo++;
                brSug++;
            } else{
                s += suglasnici[random.nextInt(suglasnici.length)];
            }

            brS = s.length();

        }

        return s;
    }

    @Override
    public Integer obradaFajla(String slovaZaPronalazak, String izabranaRecKorisnika) {

        int konacanRezultat = 0;

        slovaZaPronalazak = slovaZaPronalazakReci();
        System.out.println(slovaZaPronalazak);

        Scanner sc = new Scanner(System.in);
        izabranaRecKorisnika = sc.nextLine();

        //URL url = getClass().getResource("serbian-latin.txt");

        try {

            File file = ResourceUtils.getFile("classpath:static/serbian-latin.txt");
            List<String> recnikReci = Files.readAllLines(file.toPath());
            int rezultat = 0;

            Map<Character, Integer> recKorisnikaSlova = new HashMap<>();

            for (char slova : izabranaRecKorisnika.toUpperCase().toCharArray()) {
                if (!recKorisnikaSlova.containsKey(slova)) {
                    recKorisnikaSlova.put(slova, 0);
                }
                int currentCounter = recKorisnikaSlova.get(slova);
                recKorisnikaSlova.put(slova, currentCounter + 1);
            }

            int occurrences = 0;

            Map<Character, Integer> numberOfUsedCharacters = new HashMap<>(recKorisnikaSlova);

            for (char availableChar : slovaZaPronalazak.toCharArray()) {
                if (numberOfUsedCharacters.containsKey(availableChar)) {
                    int currentCounter = numberOfUsedCharacters.get(availableChar);
                    currentCounter--;
                    if (currentCounter == 0) {
                        numberOfUsedCharacters.remove(availableChar);
                    } else {
                        numberOfUsedCharacters.put(availableChar, currentCounter);
                    }
                    if (numberOfUsedCharacters.isEmpty()) {
                        occurrences++;
                        numberOfUsedCharacters = new HashMap<>(recKorisnikaSlova);
                    }
                }
            }

            System.out.println(occurrences);

            for(String rec : recnikReci.subList(1, recnikReci.size())) {
                if (rec.equalsIgnoreCase(izabranaRecKorisnika) && occurrences > 0){
                    rezultat = izabranaRecKorisnika.length()*2;
                }


            }

            System.out.println(rezultat);
            konacanRezultat = rezultat;

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return konacanRezultat;
    }
}
