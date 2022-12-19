package com.comtrade.model;

import com.comtrade.model.asocijacijamodel.AsocijacijaGame;
import com.comtrade.model.koznaznamodel.KoZnaZnaGame;
import com.comtrade.model.mojbrojmodel.MojBrojGame;
import com.comtrade.model.skockomodel.SkockoGame;
import com.comtrade.model.slagalicamodel.SlagalicaGame;
import com.comtrade.model.spojnicemodel.SpojniceGame;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
public class Games {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    SlagalicaGame slagalicaGame;

    @OneToOne
    MojBrojGame mojBrojGame;

    @OneToOne
    SkockoGame skockoGame;

    @OneToOne
    KoZnaZnaGame koZnaZnaGame;

    @OneToOne
    SpojniceGame spojniceGame;

    @OneToOne
    AsocijacijaGame asocijacijaGame;
}
