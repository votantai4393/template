/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.map.zones;

import com.tea.map.Map;
import com.tea.map.Waypoint;
import com.tea.map.world.World;
import com.tea.model.Char;
import org.jetbrains.annotations.NotNull;

/**
 *
 * @author kitakeyos - Hoàng Hữu Dũng
 */
public class WhiteCandy extends ZWorld {

    public WhiteCandy(Map map, World world) {
        super(0, map.tilemap, map);
        setWorld(world);
    }

    @Override
    public void returnTownFromDead(@NotNull Char p) {
        p.setXY((short) 35, (short) 360);
        out(p);
        join(p);
    }

    @Override
    public void requestChangeMap(@NotNull Char p) {
        Waypoint wp = tilemap.findWaypoint(p.x, p.y);
        if (wp == null) {
            return;
        }
        Zone z = world.find(wp.next);
        p.outZone();
        p.setXY(wp.x, wp.y);
        z.join(p);
    }

}
