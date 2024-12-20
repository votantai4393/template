/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.map.zones;

import com.tea.map.Map;
import com.tea.constants.MapName;
import com.tea.constants.MobName;
import com.tea.map.TileMap;
import com.tea.map.War;
import com.tea.map.Waypoint;
import com.tea.mob.Mob;
import com.tea.model.Char;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author Admin
 */
public class Battlefield extends Zone {

    public Battlefield(int id, TileMap tilemap, Map map) {
        super(id, tilemap, map);
    }

    @Override
    public void requestChangeMap(@NotNull Char p) {
        Waypoint wp = tilemap.findWaypoint(p.x, p.y);
        if (wp == null) {
            return;
        }
        int nextID = wp.next;
        if (map.war != null && map.war.status == 0 && (p.mapId == MapName.CAN_CU_DIA || p.mapId == MapName.CAN_CU_DIA_2)) {
            p.returnToPreviousPostion(() -> {
                p.serverDialog("Chiến trường chưa bắt đầu.");
            });
            return;
        }
        if ((p.faction == 0 && nextID == MapName.CAN_CU_DIA_2) || (p.faction == 1 && nextID == MapName.CAN_CU_DIA)) {
            p.returnToPreviousPostion(() -> {
                p.serverDialog("Không thể vào khu vực này.");
            });
            return;
        }
        p.setXY(wp.x, wp.y);
        p.changeMap(nextID);
    }

    @Override
    public void returnTownFromDead(@NotNull Char p) {
        int mapID = -1;
        short x = -1;
        short y = -1;
        if (p.faction == 0) {
            mapID = 98;
            x = 104;
            y = 336;
        }
        if (p.faction == 1) {
            mapID = 104;
            x = 104;
            y = 240;
        }
        p.setXY(x, y);
        p.changeMap(mapID);
    }

    public void join(Char p) {
        super.join(p);
        if (tilemap.isChienTruong()) {
            if (p.faction == 0) {
                p.setTypePk(Char.PK_PHE1);
            }
            if (p.faction == 1) {
                p.setTypePk(Char.PK_PHE2);
            }
            p.getService().warInfo();
            getService().changePk(p);
        }
    }

    @Override
    public void mobDead(Mob mob, Char killer) {
        if (killer != null) {
            if (mob.levelBoss == 0 && killer.war != null && killer.war.type != War.TYPE_ALL_LEVEL) {
                killer.addWarPoint((short) 1);
            }
            if (mob.levelBoss == 1 && killer.war != null && killer.war.type != War.TYPE_ALL_LEVEL) {
                killer.addWarPoint((short) 5);
            }
            if (mob.levelBoss == 2 && killer.war != null && killer.war.type != War.TYPE_ALL_LEVEL) {
                killer.addWarPoint((short) 20);
            }
            if (mob.template.id == MobName.BACH_LONG_TRU || mob.template.id == MobName.HAC_LONG_TRU) {
                if (killer.war != null) {
                    killer.war.addTurretPoint(killer.faction);
                }
            }
        }
    }

}
