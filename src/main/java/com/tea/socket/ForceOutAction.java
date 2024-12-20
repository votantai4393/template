/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.socket;

import com.tea.model.User;
import com.tea.server.ServerManager;
import com.tea.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import com.tea.network.Service;
import com.tea.server.Config;

/**
 *
 * @author PC
 */
public class ForceOutAction implements IAction {

    @Override
    public void call(JSONObject json) {
        try {
            int userId = json.getInt("user_id");
            if (json.has("current_server")) {
                int currentServer = json.getInt("current_server");
                if (currentServer == Config.getInstance().getServerID()) { // ignore current server
                    return;
                }
            }
            User user = ServerManager.findUserByUserID(userId);
            if (user != null && user.sltChar != null) {
                if (!user.isCleaned) {
                    ((Service) user.session.getService()).serverDialog("xxxxx");
                    user.session.disconnect();
                }
            }
        } catch (JSONException ex) {
            Log.error("Error get socket", ex);
        }
    }

}