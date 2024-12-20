/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.npc;

import com.tea.map.MapService;
import lombok.Builder;
import lombok.Setter;

/**
 *
 * @author PC
 */
public class Npc {

    public int id;
    public int cx, cy;
    public NpcTemplate template;
    public boolean isFocus = true;
    public int status;
    @Setter
    private MapService service;

    @Builder
    public Npc(int id, int status, int cx, int cy, int templateId) {
        this.id = id;
        this.cx = cx;
        this.cy = cy;
        this.status = status;
        this.template = NpcManager.getInstance().find(templateId);
    }

    public void setStatus(int status) {
        this.status = status;
        service.npcUpdate(this);
    }
}
