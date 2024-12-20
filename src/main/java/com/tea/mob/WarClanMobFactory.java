/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tea.mob;

import com.tea.constants.MobName;
import com.tea.map.WarClan;
import com.tea.map.zones.Zone;

public class WarClanMobFactory extends MobFactory {

    public WarClanMobFactory(Zone zone) {
        super(zone);
    }

    @Override
    public Mob createMonster(int id, MobPosition mob) {
        MobTemplate template = MobManager.getInstance().find(mob.getId());
        int hp = template.hp;
        short level = template.level;
                hp = 700000;
                level = 70;
                if (template.id == MobName.BACH_LONG_TRU || template.id == MobName.HAC_LONG_TRU) {
                    hp = 20000000;
                    level = 99;
                }
        Mob monster = new Mob(id, mob.getId(), hp, level, mob.getX(), mob.getY(), mob.isBeast() && zone.id % 5 == 0, template.isBoss(), zone);
        return monster;
    }

}