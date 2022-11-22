package com.comtrade.model.skockomodel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SkockoSubmit {

    private Long gameId;
    private List<Integer> combination;
    private Integer attempt;
}
