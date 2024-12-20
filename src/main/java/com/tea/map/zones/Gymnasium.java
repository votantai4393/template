/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.map.zones;

import com.tea.bot.Bot;
import com.tea.map.Map;
import com.tea.constants.MapName;
import com.tea.map.TileMap;
import com.tea.model.Char;
import com.tea.npc.Npc;
import com.tea.constants.NpcName;
import com.tea.util.NinjaUtils;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
public abstract class Gymnasium extends AreaWithCountdownTime {

    @Getter
    @Setter
    protected Bot bot;

    @Getter
    @Setter
    protected Char player;

    public Gymnasium(int id, TileMap tilemap, Map map) {
        super(id, tilemap, map);
        this.countDown = 300;
        initBot();
    }

    @Override
    public void out(Char p) {
        super.out(p);
        if (!isClosed()) {
            if (p == player) {
                setTimeMap(10); //10 giây sau sẽ close
            }
        }
    }

    public void setWin() {
        if (bot.isDead) {
            out(bot);
            int npcTemplateID = NpcName.THAY_OOKAMESAMA;
            if (map.id == MapName.NHA_THI_DAU_HARUNA) {
                npcTemplateID = NpcName.THAY_KAZETO;
            } else if (map.id == MapName.NHA_THI_DAU_HIROSAKI) {
                npcTemplateID = NpcName.CO_TOYOTOMI;
            }
            Npc npc = getNpc(npcTemplateID);
            if (npc != null) {
                npc.setStatus(1);
            }
            player.taskNext();
        }
    }

    public abstract void initBot();

    @Override
    public void close() {
        if (!isClosed()) {
            List<Char> chars = getChars();
            for (Char c : chars) {
                if (c.isNhanBan) {
                    continue;
                }
                short[] xy = NinjaUtils.getXY(c.mapBeforeEnterPB);
                c.setXY(xy);
                c.changeMap(c.mapBeforeEnterPB);
            }
        }
        super.close();
        this.bot = null;
        this.player = null;
    }

}
