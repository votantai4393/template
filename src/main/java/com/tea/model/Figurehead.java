/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.model;

/**
 *
 * @author Administrator
 */
public class Figurehead {

    public Figurehead(String name, short x, short y, int countDown) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.countDown = countDown;
    }

    public String name;
    public short x;
    public short y;
    public int countDown;
}
