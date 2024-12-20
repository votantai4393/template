/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.thiendia;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Getter
@Setter
public class Ranking {

    private int id;
    private int playerId;
    private int ranked;
    private String name;
    private String stt;
    private boolean fighting;

    @Builder
    public Ranking(int id, int playerId, String name, int ranked, String stt) {
        this.id = id;
        this.playerId = playerId;
        this.name = name;
        this.ranked = ranked;
        this.stt = stt;
    }
}
