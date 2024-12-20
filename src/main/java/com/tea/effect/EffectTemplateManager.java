/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.effect;

import com.tea.db.jdbc.DbManager;
import com.tea.network.Message;
import com.tea.util.Log;
import java.io.DataOutputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

/**
 *
 * @author kitakeyos - Hoàng Hữu Dũng
 */
public class EffectTemplateManager {

    private static final EffectTemplateManager instance = new EffectTemplateManager();

    public static EffectTemplateManager getInstance() {
        return instance;
    }

    private final List<EffectTemplate> list = new ArrayList<>();
    @Getter
    private byte[] data;

    public void init() {
        load();
        setData();
    }

    public void load() {
        try {
            PreparedStatement stmt = DbManager.getInstance().getConnection(DbManager.SERVER).prepareStatement("SELECT * FROM `effect`;");
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                EffectTemplate eff = new EffectTemplate();
                eff.id = resultSet.getByte("id");
                eff.name = resultSet.getString("name");
                eff.type = resultSet.getByte("type");
                eff.icon = resultSet.getShort("icon");
                add(eff);
            }
            resultSet.close();
            stmt.close();
        } catch (SQLException ex) {
            Log.error(ex.getMessage(), ex);
        }
    }

    public void setData() {
        try {
            Message ms = new Message();
            DataOutputStream dos = ms.writer();
            dos.writeByte(list.size());
            for (EffectTemplate eff : list) {
                dos.writeByte(eff.id);
                dos.writeByte(eff.type);
                dos.writeUTF(eff.name);
                dos.writeShort(eff.icon);
            }
            dos.flush();
            data = ms.getData();
            ms.cleanup();
        } catch (IOException ex) {
            Log.error(ex.getMessage(), ex);
        }
    }

    public int size() {
        return list.size();
    }

    public void add(EffectTemplate template) {
        list.add(template);
    }

    public void remove(EffectTemplate template) {
        list.remove(template);
    }

    public EffectTemplate find(int id) {
        for (EffectTemplate eff : list) {
            if (eff.id == id) {
                return eff;
            }
        }
        return null;
    }
}
