/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.map.world;

import com.tea.constants.MapName;
import com.tea.map.Map;
import com.tea.map.MapManager;
import com.tea.map.zones.BlackCandy;
import com.tea.map.zones.WaitingRoom;
import com.tea.map.zones.WarCandy;
import com.tea.map.zones.WhiteCandy;
import com.tea.map.zones.Zone;
import com.tea.model.Char;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author kitakeyos - Hoàng Hữu Dũng
 */
public class CandyBattlefield extends World {

    @Setter
    @Getter
    private int maxPlayer;
    @Setter
    @Getter
    private boolean opened;

    public CandyBattlefield(int countDown) {
        setType(World.CANDY_BATTLEFIELD);
        this.name = "CandyBattlefield";
        generateId();
        this.countDown = countDown;
        initZone();
        initFinished = true;
    }

    public int getNumberPlayer() {
        synchronized (members) {
            return members.size();
        }
    }

    public void initZone() {
        Map map = MapManager.getInstance().find(MapName.PHONG_CHO);
        addZone(new WaitingRoom(map, this));
    }

    public void open() {
        setOpened(true);
        Map map = MapManager.getInstance().find(MapName.KEO_DEN);
        addZone(new BlackCandy(map, this));
        map = MapManager.getInstance().find(MapName.KEO_TRANG);
        addZone(new WhiteCandy(map, this));
        map = MapManager.getInstance().find(MapName.KEO_CHIEN);
        addZone(new WarCandy(map, this));
    }

    @Override
    public boolean enterWorld(Zone pre, Zone next) {
        return !pre.tilemap.isCandyBattlefield() && next.tilemap.isCandyBattlefield();
    }

    @Override
    public boolean leaveWorld(Zone pre, Zone next) {
        return pre.tilemap.isCandyBattlefield() && !next.tilemap.isCandyBattlefield();
    }

    public void join(@NotNull Char p) {
        Zone z = find(MapName.PHONG_CHO);
        p.setXY((short) 100, (short) 264);
        p.outZone();
        z.join(p);
    }

    @Override
    public void update() {
        if (isOpened()) {
            super.update();
        }
    }

    @Override
    public void close() {
        MapManager.getInstance().setCandyBattlefield(null);
        super.close();
    }
}
