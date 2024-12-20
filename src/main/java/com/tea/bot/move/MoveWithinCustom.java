/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.bot.move;

import com.tea.model.Char;
import lombok.Builder;

/**
 *
 * @author Admin
 */
public class MoveWithinCustom extends MoveToTarget {

    private int minX, maxX;
    private int minY, maxY;

    @Builder
    public MoveWithinCustom(Char p, int minX, int minY, int maxX, int maxY) {
        super(p);
        this.minX = minX;
        this.minY = minY;
        this.maxX = maxX;
        this.maxY = maxY;
    }

    @Override
    public void move(Char owner) {
        if (target == null) {
            return;
        }
        if (target.x < minX || target.x > maxX || target.y < minY || target.y > maxY) {
            return;
        }
        super.move(owner);
    }

}
