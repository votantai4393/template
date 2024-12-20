/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.bot;

import com.tea.ability.AbilityCustom;
import com.tea.fashion.FashionCustom;
import com.tea.map.Map;
import com.tea.map.item.ItemMap;
import com.tea.map.item.ItemMapFactory;
import com.tea.map.zones.Zone;
import com.tea.model.Char;
import com.tea.util.NinjaUtils;
import com.tea.util.TimeUtils;

/**
 *
 * @author kitakeyos - Hoàng Hữu Dũng
 */
public class SantaClaus extends Bot {

    private long lastTime, lastTimeDropItem, lastTimeChat;

    public SantaClaus(int id) {
        super(id, "Santa Claus", 150, Char.PK_NORMAL, (byte) 0);
        this.lastTime = this.lastTimeDropItem = System.currentTimeMillis();
    }

    @Override
    public void setDefault() {
        super.setDefault();
        FashionCustom fashionCustom = FashionCustom.builder()
                .head((short) 267)
                .body((short) 268)
                .leg((short) 269)
                .weapon((short) -1)
                .build();
        setFashionStrategy(fashionCustom);
        AbilityCustom abilityCustom = AbilityCustom.builder()
                .hp(2000000)
                .build();
        setAbilityStrategy(abilityCustom);
        setAbility();
        setFashion();
    }

    @Override
    public void updateEveryHalfSecond() {
        super.updateEveryHalfSecond();
        long now = System.currentTimeMillis();;
        if (TimeUtils.canDoWithTime(lastTime, 60000)) {
            lastTime = now;
            zone.getService().chat(this.id, "Bye bye");
            Map map = zone.map;
            Zone z = map.rand();
            outZone();
            z.join(this);
            return;
        }
        if (TimeUtils.canDoWithTime(lastTimeDropItem, 10000)) {
            lastTimeDropItem = now;
            int q = NinjaUtils.nextInt(1, 3);
            for (int i = 0; i < q; i++) {
                ItemMap item = ItemMapFactory.getInstance().builder()
                        .id(zone.numberDropItem++)
                        .type(ItemMapFactory.GIFT_BOX)
                        .x((short) (x + (i * 6 * (i % 2 == 0 ? 1 : -1))))
                        .y(y)
                        .build();
                zone.addItemMap(item);
                zone.getService().addItemMap(item);
            }
        }
        if (TimeUtils.canDoWithTime(lastTimeChat, 5000)) {
            lastTimeChat = now;
            zone.getService().chat(this.id, (String) NinjaUtils.randomObject("Giáng sinh vui vẻ!", "Hô hô hô", "Giáng sinh an lành"));
        }
    }

}
