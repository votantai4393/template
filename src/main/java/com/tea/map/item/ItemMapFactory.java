/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.map.item;

import lombok.Builder;

/**
 *
 * @author kitakeyos - Hoàng Hữu Dũng
 */
public class ItemMapFactory {

    public static final byte NORMAL = 0;
    public static final byte MUSHROOM = 1;
    public static final byte ORE = 2;
    public static final byte ICE_CRYSTAL = 3;
    public static final byte MAP_ITEM = 4;
    public static final byte MAGIC_ITEM = 5;
    public static final byte GIFT_BOX = 6;
    public static final byte ENVELOPE = 7;

    private static final ItemMapFactory instance = new ItemMapFactory();

    public static ItemMapFactory getInstance() {
        return instance;
    }

    @Builder
    public ItemMap createItemMap(short id, byte type, short x, short y) {
        ItemMap item = null;
        switch (type) {

            case MUSHROOM:
                item = new Mushroom(id);
                break;

            case ORE:
                item = new Ore(id);
                break;

            case ICE_CRYSTAL:
                item = new IceCrystal(id);
                break;

            case MAP_ITEM:
                item = new MapItem(id);
                break;

            case MAGIC_ITEM:
                item = new MagicItem(id);
                break;

            case GIFT_BOX:
                item = new GiftBox(id);
                break;

            case ENVELOPE:
                item = new Envelope(id);
                break;

            case NORMAL:
            default:
                item = new ItemMap(id);
                break;
        }
        item.setX(x);
        item.setY(y);
        return item;
    }
}
