/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.map.zones;

import com.tea.map.item.ItemMap;
import com.tea.map.Map;
import com.tea.map.TileMap;
import com.tea.map.item.ItemMapFactory;
import com.tea.mob.Mob;
import com.tea.model.Char;
import com.tea.util.NinjaUtils;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ChikatoyaTunnels extends AreaWithCountdownTime {

    private boolean isDroppedItem;

    public ChikatoyaTunnels(int id, TileMap tilemap, Map map) {
        super(id, tilemap, map);
        this.countDown = 15 * 60;// 15p
    }

    @Override
    public void createMonster() {
        super.createMonster();
        List<Mob> mobs = getMonsters();
        for (Mob mob : mobs) {
            mob.isCantRespawn = true;
        }
    }

    @Override
    public void update() {
        super.update();
        if (!isDroppedItem) {
            List<Mob> mobs = getLivingMonsters();
            if (mobs.isEmpty()) {
                isDroppedItem = true;
                ItemMap itemMap = ItemMapFactory.getInstance().builder()
                        .id(numberDropItem++)
                        .type(ItemMapFactory.MAP_ITEM)
                        .x((short) 364)
                        .y((short) 768)
                        .build();
                addItemMap(itemMap);
                getService().addItemMap(itemMap);
            }
        }
        List<Char> chars = getChars();
        if (chars.isEmpty()) {
            close();
        }
    }

    @Override
    public void close() {
        if (!isClosed()) {
            List<Char> chars = getChars();
            for (Char c : chars) {
                short[] xy = NinjaUtils.getXY(c.saveCoordinate);
                c.setXY(xy);
                c.changeMap(c.saveCoordinate);
            }
        }
        super.close();
    }

}
