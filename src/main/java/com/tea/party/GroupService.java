/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.party;

import com.tea.constants.CMD;
import com.tea.model.Char;
import com.tea.network.AbsService;
import com.tea.network.Message;
import com.tea.util.Log;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class GroupService extends AbsService {

    private Group group;

    public GroupService(Group group) {
        this.group = group;
    }

    public void playerInParty() {
        try {
            Message ms = new Message(CMD.PLAYER_IN_PARTY);
            DataOutputStream ds = ms.writer();
            ds.writeBoolean(group.isLock);
            List<MemberGroup> partys = group.getMemberGroup();
            for (MemberGroup p : partys) {
                ds.writeInt(p.charId);
                ds.writeByte(p.classId);
                String name_new = p.getChar().getTongNap(p.getChar()) + p.getChar().name; // SVIP
                ds.writeUTF(name_new);
            }
            ds.flush();
            sendMessage(ms);
        } catch (IOException ex) {
            Log.error("playerInParty err:" + ex.getMessage(), ex);
        }
    }

    public void changeLeader(int index) {
        try {
            Message ms = messageSubCommand(CMD.CHANGE_TEAMLEADER);
            DataOutputStream ds = ms.writer();
            ds.writeByte(index);
            ds.flush();
            sendMessage(ms);
        } catch (IOException ex) {
            Log.error("changeLeader err:" + ex.getMessage(), ex);
        }
    }

    @Override
    public void chat(String name, String text) {
        try {
            Message mss = new Message(CMD.CHAT_PARTY);
            DataOutputStream ds = mss.writer();
            ds.writeUTF(name);
            ds.writeUTF(text);
            ds.flush();
            sendMessage(mss);
            mss.cleanup();
        } catch (IOException ex) {
            Log.error("chat err: " + ex.getMessage(), ex);
        }
    }

    public void lockParty(boolean isLock) {
        try {
            Message mss = messageSubCommand(CMD.LOCK_PARTY);
            DataOutputStream ds = mss.writer();
            ds.writeBoolean(isLock);
            ds.flush();
            sendMessage(mss);
        } catch (Exception ex) {
            Logger.getLogger(Char.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void sendMessage(Message ms) {
        List<Char> chars = group.getChars();
        for (Char _char : chars) {
            _char.getService().sendMessage(ms);
        }
    }
}
