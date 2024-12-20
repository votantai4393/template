
package com.tea.model;

import com.tea.constants.ItemName;
import com.tea.constants.TaskName;
import com.tea.convert.Converter;
import com.tea.item.Item;
import com.tea.item.ItemFactory;
import com.tea.store.ItemStore;
import com.tea.store.StoreManager;
import com.tea.util.NinjaUtils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class SelectCardVIP extends AbsSelectCard {

    private static final SelectCardVIP instance = new SelectCardVIP();

    public static SelectCardVIP getInstance() {
        return instance;
    }

    public static final long EXPIRE_3_DAY = 3 * 24 * 60 * 60 * 1000;
    public static final long EXPIRE_7_DAY = 7 * 24 * 60 * 60 * 1000;
    public static final long EXPIRE_20_DAY = 20 * 24 * 60 * 60 * 1000;

    @Override
    public void init() {
        StringBuilder objStr = new StringBuilder();
        try {
            String content = Files.readString(Paths.get("item_roi/LatHinh/Lat_Hinh_VIP.json"));
            objStr.append(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray js = (JSONArray) JSONValue.parse(objStr.toString());
            for (int i = 0; i < js.size(); i++) {
                JSONObject job1 = (JSONObject) JSONValue.parse(js.get(i).toString());
                int id = Integer.parseInt(job1.get("id").toString());
                double rate = Double.parseDouble(job1.get("rate").toString());
                int expire = Integer.parseInt(job1.get("expire").toString());
                int quantity = Integer.parseInt(job1.get("quantity").toString());
                 long expireMillis = convertDaysToMillis(expire);
                add(Card.builder().id(id).rate(rate).expire(expireMillis).quantity(quantity).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    private static long convertDaysToMillis(long days) {
        return days * 24 * 60 * 60 * 1000L;
    }
    @Override
     protected Card reward(@NotNull Char p, Card card) {
        int itemID = card.getId();
        int quantity = card.getQuantity();
        if (itemID == ItemName.YEN) {
            p.addYen(quantity);
            p.serverMessage("Bạn nhận được " + NinjaUtils.getCurrency(quantity) + " Yên");
        } else {
            Item item = ItemFactory.getInstance().newItem(itemID);
            long expire = card.getExpire();
            if (expire == -1) {
                item.expire = -1;
            } else {
                item.expire = System.currentTimeMillis() + expire;
            }
            p.themItemToBag(item);
        }
        return card;
    }

    @Override
    protected boolean isCanSelect(Char p) {
        int index = p.getIndexItemByIdInBag(1294);
        if (index == -1 || p.bag[index] == null || !p.bag[index].has()) {
            p.serverDialog("Bạn không có phiếu may mắn VIP!");
            return false;
        }
        if (p.getSlotNull() == 0) {
            p.serverDialog("Không đủ chỗ trống.");
            return false;
        }
        return true;
    }

    @Override
    protected void selecctCardSuccessful(@NotNull Char p) {
        int index = p.getIndexItemByIdInBag(1294);
        p.removeItem(index, 1, true);
        if (p.taskId == TaskName.NV_THU_TAI_MAY_MAN) {
            if (p.taskMain != null && p.taskMain.index == 3) {
                p.updateTaskCount(1);
            }
        }
    }

}
