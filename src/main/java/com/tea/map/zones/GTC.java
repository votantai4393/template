package com.tea.map.zones;


import com.tea.constants.MapName;
import com.tea.constants.MobName;
import com.tea.map.Map;
import com.tea.map.TileMap;
import com.tea.map.Waypoint;
import com.tea.mob.Mob;
import com.tea.map.WarClan;
import com.tea.model.Char;
import org.jetbrains.annotations.NotNull;

public class GTC extends Zone {
    public GTC(int id, TileMap tilemap, Map map) {
        super(id, tilemap, map);
    }
    @Override
    public void requestChangeMap(@NotNull Char p) {
        Waypoint wp = tilemap.findWaypoint(p.x, p.y);
        if (wp == null) {
            return;
        }
        int nextID = wp.next;
        if (map.warClan != null && map.warClan.status == 0 && (p.mapId == MapName.BAO_DANH_GIA_TOC || p.mapId == MapName.BAO_DANH_GIA_TOC_2)) {
            p.returnToPreviousPostion(() -> {
                p.serverDialog("Gia toc chien chưa bắt đầu.");
            });
            return;
        }
        if ((p.Clanfaction == 0 && nextID == MapName.BAO_DANH_GIA_TOC_2) || (p.Clanfaction == 1 && nextID == MapName.BAO_DANH_GIA_TOC)) {
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
        if (p.Clanfaction == 0) {
            mapID = 118;
            x = 242;
            y = 312;
        }
        if (p.Clanfaction == 1) {
            mapID = 119;
            x = 228;
            y = 216;
        }
        p.setXY(x, y);
        p.changeMap(mapID);
    }

    public void join(Char p) {
        super.join(p);
        if (tilemap.isGTC()) {
            if (p.Clanfaction == 0) {
                p.setTypePk(Char.PK_PHE1);
            }
            if (p.Clanfaction == 1) {
                p.setTypePk(Char.PK_PHE2);
            }
            if (!p.isNhanBan) {
                p.getService().warClanInfo();
                getService().changePk(p);
            }
        }
    }

    @Override
    public void mobDead(Mob mob, Char killer) {
        if (killer != null) {
            if (mob.levelBoss == 0 && killer.warClan != null){ 
                killer.addWarClanPoint((short) 1);
            }
            if (mob.levelBoss == 1 && killer.warClan != null){ 
                killer.addWarClanPoint((short) 5);
            }
            if (mob.levelBoss == 2 && killer.warClan != null){ 
                killer.addWarClanPoint((short) 20);
            }
            if (mob.template.id == MobName.BACH_LONG_TRU || mob.template.id == MobName.HAC_LONG_TRU) {
                if (killer.warClan != null) {
                    killer.warClan.addTurretPoint(killer.faction);
                }
            }
        }
    }

}