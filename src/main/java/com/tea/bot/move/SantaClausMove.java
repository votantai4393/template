/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.bot.move;

import com.tea.bot.IMove;
import com.tea.map.TileMap;
import com.tea.map.zones.Zone;
import com.tea.model.Char;
import com.tea.npc.Npc;
import com.tea.util.NinjaUtils;

/**
 *
 * @author kitakeyos - Hoàng Hữu Dũng
 */
public class SantaClausMove implements IMove {
    
    private Npc npc;
    
    public SantaClausMove(Npc npc) {
        this.npc = npc;
    }

    @Override
    public void move(Char owner) {
        if (owner.isDead) {
            return;
        }
        if (owner.isDontMove()) {
            return;
        }
        Zone zone = owner.zone;
        short preX = owner.x;
        short preY = owner.y;
        int dir = (NinjaUtils.nextBoolean() ? -1 : 1);
        int x = NinjaUtils.nextInt(10, 50) * dir;
        short oX = (short) npc.cx;
        short oY = (short) npc.cy;
        oX += x;
        if (oX < 24) {
            oX = 24;
        }
        if (oX > owner.zone.tilemap.pxw - 24) {
            oX = (short) (owner.zone.tilemap.pxw - 24);
        }
        if (owner.isCrossMap(oX, oY)) {
            oX -= x * 2;
        }
        short tempX = (short) (oX + 11);
        short tempY = (short) (oY - 16);
        boolean isJump = NinjaUtils.nextInt(5) == 0;
        if (zone.tilemap.isInWaypoint(tempX, tempY)) {
            oX = preX;
            oY = preY;
        }
        if (!isJump) {
            if (zone.tilemap.isRock(tempX, tempY) || owner.zone.tilemap.isWood(tempX, tempY)) {
                isJump = true;
            }
        }
        oY -= 24;
        oY = owner.zone.tilemap.collisionY(oX, oY);
        if (isJump) {
            oY -= 72;
        }
        if (!owner.zone.tilemap.tileTypeAt(tempX, tempY, TileMap.T_TOP)) {
            oY = owner.zone.tilemap.collisionY(oX, oY);
        }
        if (owner.isCrossMap(oX, oY)) {
            oX = preX;
            oY = preY;
        }
        owner.zone.move(owner, oX, oY);
    }

}
