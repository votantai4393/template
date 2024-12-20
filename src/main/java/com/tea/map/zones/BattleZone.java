/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.map.zones;

import com.mongodb.lang.Nullable;
import com.tea.map.Map;
import com.tea.map.TileMap;
import com.tea.map.world.Arena;
import com.tea.model.Char;

/**
 *
 * @author Admin
 */
public class BattleZone extends ZWorld {

    public BattleZone(int id, TileMap tilemap, Map map) {
        super(id, tilemap, map);
    }

    public void join(Char p) {
        super.join(p);
        p.hp = p.maxHP;
        p.isDead = false;
        p.getService().loadInfo();
    }

    public void out(Char p) {
        super.out(p);
        p.hp = p.maxHP;
        p.isDead = false;
        p.getService().loadInfo();
        p.setTypePk(Char.PK_NORMAL);
    }

    @Override
    public boolean isCanMove(@Nullable Char _char, short x, short y) {
        Arena arena = (Arena) getWorld();
        if (!arena.isViewer(_char)) { // is player
            if (y > 288 || y < 191) {
                return false;
            }
        } else { // is viewer
            if (y <= 288) {
                return false;
            }
        }
        return true;
    }

}
