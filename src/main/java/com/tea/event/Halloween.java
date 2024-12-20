/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.event;

import com.tea.constants.CMDInputDialog;
import com.tea.constants.CMDMenu;
import com.tea.constants.ConstTime;
import com.tea.constants.ItemName;
import com.tea.constants.ItemOptionName;
import com.tea.constants.MapName;
import com.tea.constants.NpcName;
import com.tea.effect.Effect;
import com.tea.effect.EffectAutoDataManager;
import com.tea.event.eventpoint.EventPoint;
import com.tea.item.Item;
import com.tea.item.ItemFactory;
import com.tea.lib.RandomCollection;
import com.tea.map.Map;
import com.tea.map.Tree;
import com.tea.map.zones.Zone;
import com.tea.model.Char;
import com.tea.model.InputDialog;
import com.tea.model.Menu;
import com.tea.model.RandomItem;
import com.tea.option.ItemOption;
import com.tea.server.Config;
import com.tea.store.ItemStore;
import com.tea.store.StoreManager;
import com.tea.util.NinjaUtils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author kitakeyos - Hoàng Hữu Dũng
 */
public class Halloween extends Event {

    private static final int HOP_MA_QUY = 0;
    private static final int KEO_TAO = 1;
    private static final int CHIA_KHOA = 2;
    private static final int MA_VAT = 3;
    private static final int THOI_TRANG = 4;

    public static final String TOP_DEVIL_BOX = "devil_box";
    public static final String INVITATION_NUMBER = "invitation_number";
    private RandomCollection<Integer> vipItems = new RandomCollection<>();

   public Halloween() {
        setId(Event.HALLOWEEN);
        endTime = Calendar.getInstance();
        endTime.set(Config.getInstance().getEventYear(),
                    Config.getInstance().getEventMonth() - 1, 
                    Config.getInstance().getEventDay(),
                    Config.getInstance().getEventHour(),
                    Config.getInstance().getEventMinute(),
                    Config.getInstance().getEventSecond());
        keyEventPoint.add(EventPoint.DIEM_TIEU_XAI);
        keyEventPoint.add(TOP_DEVIL_BOX);
        keyEventPoint.add(INVITATION_NUMBER);

        StringBuilder objStr = new StringBuilder();
        try {
            String content = Files.readString(Paths.get("item_roi/event_Halloween/Halloween.json"));
            objStr.append(content);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            JSONArray js = (JSONArray) JSONValue.parse(objStr.toString());
            for (int i = 0; i < js.size(); i++) {
                JSONObject job1 = (JSONObject) JSONValue.parse(js.get(i).toString());
                double percent = Double.parseDouble(job1.get("percent").toString());
                int id = Integer.parseInt(job1.get("id").toString());
                itemsThrownFromMonsters.add(percent, id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        vipItems.add(1, ItemName.TA_LINH_MA);
        vipItems.add(1, ItemName.PHONG_THUONG_MA);
        vipItems.add(1, ItemName.XICH_TU_MA);
        vipItems.add(2, ItemName.MAT_NA_SHIN_AH);
        vipItems.add(2, ItemName.MAT_NA_VO_DIEN);
        vipItems.add(2, ItemName.MAT_NA_ONI);
        vipItems.add(2, ItemName.MAT_NA_KUMA);
        vipItems.add(2, ItemName.MAT_NA_INU);
        vipItems.add(2, ItemName.HAKAIRO_YOROI);
        vipItems.add(2, ItemName.LAN_SU_VU);
    }


    @Override
    public void useItem(Char _char, Item item) {
        if (item.id == ItemName.THU_MOI_LE_HOI) {
            _char.getEventPoint().addPoint(INVITATION_NUMBER, 1);
            _char.serverMessage(
                    "Số lượt tham gia lễ hội hoá trang: " + _char.getEventPoint().find(INVITATION_NUMBER).getPoint());
            _char.removeItem(item.index, 1, true);
            return;
        } 
        if (item.id == ItemName.BI_MA) {
            int time = 8 * 60 * 60 * 1000;
            short param = 2;
            byte templateID = 43;
            Effect eff = _char.getEm().findByID(templateID);
            if (eff != null) {
                eff.addTime(time);
                _char.getEm().setEffect(eff);
            } else {
                Effect effect = new Effect(templateID, time, param);
                effect.param2 = item.id;
                _char.getEm().setEffect(effect);
            }
            _char.removeItem(item.index, 1, true);
            return;
        } 
        if (item.id == ItemName.KEO_TAO) {
           if (_char.getSlotNull() == 0) {
                _char.warningBagFull();
                return;
                }
             int indexUI = _char.getIndexItemByIdInBag(ItemName.KEO_TAO);
            if (indexUI == -1) {
                _char.serverMessage("Không có item.");
                return;
            }
                RandomCollection<Integer> rand = RandomItem.KEO_TAO;
                int id = rand.next();
                Item itm = ItemFactory.getInstance().newItem(id);
                _char.themItemToBag(itm);
                _char.removeItem(indexUI, 1, true);
            return;
        } 
        if (item.id == ItemName.HOP_MA_QUY) {
            int indexItem = _char.getIndexItemByIdInBag(ItemName.CHIA_KHOA);
            if (indexItem == -1) {
                _char.serverMessage("Cần có chìa khoá mới có thể mở hộp ma quỷ");
                return;
            }
           if (_char.getSlotNull() == 0) {
                _char.warningBagFull();
                return;
                }
             int indexUI = _char.getIndexItemByIdInBag(ItemName.HOP_MA_QUY);
            if (indexUI == -1) {
                _char.serverMessage("Không có HỘP MA QUỶ.");
                return;
            }
                RandomCollection<Integer> rand = RandomItem.HOP_MA_QUY;
                int id = rand.next();
                Item itm = ItemFactory.getInstance().newItem(id);
                _char.themItemToBag(itm);
                _char.removeItem(indexItem, 1, true);
                 _char.removeItem(indexUI, 1, true);
            
            _char.getEventPoint().addPoint(TOP_DEVIL_BOX, 1);
            return;
        } 
        if (item.id == ItemName.GAY_PHEP || item.id == ItemName.CHOI_BAY) {
            if (_char.getSlotNull() == 0) {
                _char.warningBagFull();
                return;
            }
            Event.useVipEventItem(_char, item.id == ItemName.GAY_PHEP ? 1 : 2, vipItems);
            _char.removeItem(item.index, 1, true);
            return;
        }
    }
    @Override
    public void action(Char p, int type, int amount) {
        if (isEnded()) {
            p.serverMessage("Sự kiện đã kết thúc");
            return;
        }
        switch (type) {
            case CHIA_KHOA:
                makeKey(p, amount);
                break;

            case HOP_MA_QUY:
                makeDevilBox(p, amount);
                break;

            case KEO_TAO:
                makeAppleCandy(p, amount);
                break;

            case MA_VAT:
                makeMagicItem(p, amount);
                break;

            case THOI_TRANG:
                makeFashionItem(p);
                break;
        }
    }

    public void makeDevilBox(Char p, int amount) {
        int[][] itemRequires = new int[][]{{ItemName.XUONG_THU, 5}, {ItemName.TAN_LINH, 2},
        {ItemName.MA_VAT, 1}};
        int itemIdReceive = ItemName.HOP_MA_QUY;
        boolean isDone = makeEventItem(p, amount, itemRequires, 20, 0, 0, itemIdReceive);
        if (isDone) {
            p.getEventPoint().addPoint(EventPoint.DIEM_TIEU_XAI, amount);
        }
    }

    public void makeAppleCandy(Char p, int amount) {
        int[][] itemRequires = new int[][]{{ItemName.QUA_TAO, 1}, {ItemName.MAT_ONG, 3}};
        int itemIdReceive = ItemName.KEO_TAO;
        makeEventItem(p, amount, itemRequires, 0, 100000, 0, itemIdReceive);
    }

    public void makeKey(Char p, int amount) {
        int[][] itemRequires = new int[][]{{ItemName.H, 1}, {ItemName.A, 1}, {ItemName.L, 2}, {ItemName.O, 1},
        {ItemName.W, 1}, {ItemName.E, 2}, {ItemName.N, 1}};
        int itemIdReceive = ItemName.CHIA_KHOA;
        makeEventItem(p, amount, itemRequires, 0, 0, 0, itemIdReceive);
    }

    public void makeMagicItem(Char p, int amount) {
        int[][] itemRequires = new int[][]{{ItemName.H, 1}, {ItemName.A, 1}, {ItemName.L, 2}, {ItemName.O, 1},
        {ItemName.W, 1}, {ItemName.E, 2}, {ItemName.N, 1}};
        int itemIdReceive = ItemName.MA_VAT;
        makeEventItem(p, amount, itemRequires, 0, 0, 0, itemIdReceive);
    }

    public void makeFashionItem(Char p) {
        if (p.user.gold < 500) {
            p.getService().npcChat(NpcName.TIEN_NU, "Cần 500 lượng để đổi.");
            return;
        }
        int index = p.getIndexItemByIdInBag(ItemName.KEO_TAO);
        Item itm = null;
        if (index != -1) {
            itm = p.bag[index];
        }
        if (itm == null || !itm.has(50)) {
            p.getService().npcChat(NpcName.TIEN_NU, "Không đủ kẹo táo.");
            return;
        }
        p.addLuong(-500);
        p.removeItem(index, 50, true);
        int maskId = p.gender == 1 ? ItemName.SHIRAIJI : ItemName.HAJIRO;
        Item item = ItemFactory.getInstance().newItem(maskId);
        item.expire = System.currentTimeMillis() + (long) (86400000 * 15);
        item.isLock = true;
        p.themItemToBag(item);
    }

    public void makeMagicWeapon(Char p, int type) {
        int point = type == 1 ? 5000 : 20000;
        if (p.getEventPoint().getPoint(EventPoint.DIEM_TIEU_XAI) < point) {
            p.getService().npcChat(NpcName.TIEN_NU,
                    "Ngươi cần tối thiểu " + NinjaUtils.getCurrency(point)
                    + " điểm sự kiện mới có thể đổi được vật này.");
            return;
        }

        if (p.getSlotNull() == 0) {
            p.getService().npcChat(NpcName.TIEN_NU, p.language.getString("BAG_FULL"));
            return;
        }

        Item item = ItemFactory.getInstance().newItem(type == 1 ? ItemName.GAY_PHEP : ItemName.CHOI_BAY);
        p.themItemToBag(item);
        p.getEventPoint().subPoint(EventPoint.DIEM_TIEU_XAI, point);
    }

    @Override
    public void menu(Char p) {
        if (!isEnded()) {
            p.menus.add(new Menu(CMDMenu.EXECUTE, "Làm Hộp ma quỷ", () -> {
                p.setInput(new InputDialog(CMDInputDialog.EXECUTE, "Hộp ma quỷ", () -> {
                    InputDialog input = p.getInput();
                    try {
                        int number = input.intValue();
                        action(p, HOP_MA_QUY, number);
                    } catch (Exception e) {
                        if (!input.isEmpty()) {
                            p.inputInvalid();
                        }
                    }
                }));
                p.getService().showInputDialog();
            }));
            p.menus.add(new Menu(CMDMenu.EXECUTE, "Làm Kẹo táo", () -> {
                p.setInput(new InputDialog(CMDInputDialog.EXECUTE, "Kẹo táo", () -> {
                    InputDialog input = p.getInput();
                    try {
                        int number = input.intValue();
                        action(p, KEO_TAO, number);
                    } catch (Exception e) {
                        if (!input.isEmpty()) {
                            p.inputInvalid();
                        }
                    }
                }));
                p.getService().showInputDialog();
            }));
            p.menus.add(new Menu(CMDMenu.EXECUTE, "Đổi chìa khóa", () -> {
                p.setInput(new InputDialog(CMDInputDialog.EXECUTE, "Chìa khóa", () -> {
                    InputDialog input = p.getInput();
                    try {
                        int number = input.intValue();
                        action(p, CHIA_KHOA, number);
                    } catch (Exception e) {
                        if (!input.isEmpty()) {
                            p.inputInvalid();
                        }
                    }
                }));
                p.getService().showInputDialog();
            }));
            p.menus.add(new Menu(CMDMenu.EXECUTE, "Đổi ma vật", () -> {
                p.setInput(new InputDialog(CMDInputDialog.EXECUTE, "Ma vật", () -> {
                    InputDialog input = p.getInput();
                    try {
                        int number = input.intValue();
                        action(p, MA_VAT, number);
                    } catch (Exception e) {
                        if (!input.isEmpty()) {
                            p.inputInvalid();
                        }
                    }
                }));
                p.getService().showInputDialog();
            }));
            p.menus.add(new Menu(CMDMenu.EXECUTE, "Đổi đồ thời trang", () -> {
                action(p, THOI_TRANG, 1);
            }));
            p.menus.add(new Menu(CMDMenu.EXECUTE, "Pháp khí", () -> {
                p.menus.clear();
                p.menus.add(new Menu(CMDMenu.EXECUTE, "Gậy phép", () -> {
                    makeMagicWeapon(p, 1);
                }));
                p.menus.add(new Menu(CMDMenu.EXECUTE, "Chổi bay", () -> {
                    makeMagicWeapon(p, 2);
                }));
                p.menus.add(new Menu(CMDMenu.EXECUTE, "Điểm sự kiện", () -> {
                    p.getService().showAlert("Hướng dẫn", "- Điểm sự kiện: "
                            + NinjaUtils.getCurrency(p.getEventPoint().getPoint(EventPoint.DIEM_TIEU_XAI))
                            + "\n\nBạn có thể quy đổi điểm sự kiện như sau\n- Gậy phép: 5.000 điểm\n- Chổi bay: 20.000 điểm\n");
                }));
                p.getService().openUIMenu();
            }));
        }
        p.menus.add(new Menu(CMDMenu.EXECUTE, "Đua TOP", () -> {
            p.menus.clear();
            p.menus.add(new Menu(CMDMenu.EXECUTE, "Hộp Ma Quỷ", () -> {
                viewTop(p, TOP_DEVIL_BOX, "Hộp Ma Quỷ", "%d. %s đã mở %s hộp ma quỷ");
            }));
            p.menus.add(new Menu(CMDMenu.EXECUTE, "Phần Thưởng", () -> {
                StringBuilder sb = new StringBuilder();
                sb.append("Top 1:").append("\n");
                sb.append("- Xích Tử Mã v.v MCS\n");
                sb.append("- SHIRAIJI/HAJIRO v.v MCS\n");
                sb.append("- 3 rương huyền bí\n");
                sb.append("- 10 pháp khí\n\n");
                sb.append("Top 2:").append("\n");
                sb.append("- Xích Tử Mã v.v\n");
                sb.append("- SHIRAIJI/HAJIRO v.v\n");
                sb.append("- 1 rương huyền bí\n");
                sb.append("- 5 pháp khí\n\n");
                sb.append("Top 3 - 5:").append("\n");
                sb.append("- Xích Tử Mã 3 tháng\n");
                sb.append("- SHIRAIJI/HAJIRO 3 tháng\n");
                sb.append("- 2 rương bạch ngân\n");
                sb.append("- 3 pháp khí\n\n");
                sb.append("Top 6 - 10:").append("\n");
                sb.append("- Xích Tử Mã 1 tháng\n");
                sb.append("- 1 rương bạch ngân\n");
                p.getService().showAlert("Phần thưởng", sb.toString());
            }));
            if (isEnded()) {
                int ranking = getRanking(p, TOP_DEVIL_BOX);
                if (ranking <= 10 && p.getEventPoint().getRewarded(TOP_DEVIL_BOX) == 0) {
                    p.menus.add(new Menu(CMDMenu.EXECUTE, String.format("Nhận Thưởng TOP %d", ranking), () -> {
                        receiveReward(p, TOP_DEVIL_BOX);
                    }));
                }
            }
            p.getService().openUIMenu();
        }));
        p.menus.add(new Menu(CMDMenu.EXECUTE, "Hướng dẫn", () -> {
            StringBuilder sb = new StringBuilder();
            sb.append("- Điểm tiêu xài: ")
                    .append(NinjaUtils.getCurrency(p.getEventPoint().getPoint(EventPoint.DIEM_TIEU_XAI))).append("\n");
            sb.append("- Cách làm Hộp ma quỷ: 5 xương thú + 2 tàn linh + 1 ma vật.").append("\n");
            sb.append("- Cách làm kẹo táo: Quả táo + 3 Mật ong + 100.000 xu.").append("\n");
            sb.append("- Cách đổi chìa khóa: 1 bộ HALLOWEEN.").append("\n");
            sb.append("- Cách đổi ma vật: 1 bộ HALLOWEEN.").append("\n");
            sb.append("- Đổi đồ thời trang: 50 kẹo táo + 500 lượng");
            p.getService().showAlert("Hướng Dẫn", sb.toString());
        }));
    }

    @Override
    public void initStore() {
        StoreManager.getInstance().themItem((byte) StoreManager.TYPE_MISCELLANEOUS, ItemStore.builder()
                .id(998)
                .itemID(ItemName.BI_MA)
                .gold(20)
                .expire(ConstTime.FOREVER)
                .build());

        List<ItemOption> options = new ArrayList<ItemOption>();
        options.add(new ItemOption(ItemOptionName.HP_TOI_DA_POINT_TYPE_1, NinjaUtils.nextInt(4000, 5000)));
        options.add(new ItemOption(ItemOptionName.TAN_CONG_POINT_TYPE_1, NinjaUtils.nextInt(4000, 5000)));
        StoreManager.getInstance().themItem((byte) StoreManager.TYPE_MISCELLANEOUS, ItemStore.builder()
                .id(1000)
                .itemID(ItemName.BI_RE_HANH)
                .gold(500)
                .expire(ConstTime.WEEK)
                .options(options)
                .build());

        List<ItemOption> options2 = new ArrayList<ItemOption>();
        options2.add(new ItemOption(ItemOptionName.HP_TOI_DA_POINT_TYPE_1, 2000));
        options2.add(new ItemOption(ItemOptionName.CONG_THEM_TIEM_NANG_ADD_POINT_PERCENT_TYPE_0, 25));
        StoreManager.getInstance().themItem((byte) StoreManager.TYPE_MISCELLANEOUS, ItemStore.builder()
                .id(1001)
                .itemID(ItemName.JACK_HOLLOW)
                .gold(500)
                .expire(ConstTime.WEEK)
                .options(options2)
                .build());

        StoreManager.getInstance().themItem((byte) StoreManager.TYPE_MISCELLANEOUS, ItemStore.builder()
                .id(1002)
                .itemID(ItemName.THU_MOI_LE_HOI)
                .gold(20)
                .expire(ConstTime.FOREVER)
                .build());
    }

    public void receiveReward(Char p, String key) {
        int ranking = getRanking(p, key);
        if (ranking > 10) {
            p.getService().serverDialog("Bạn không đủ điều kiện nhận phần thưởng");
            return;
        }
        if (p.getEventPoint().getRewarded(key) == 1) {
            p.getService().serverDialog("Bạn đã nhận phần thưởng rồi");
            return;
        }
        if (p.getSlotNull() < 10) {
            p.getService().serverDialog("Bạn cần để hành trang trống tối thiểu 10 ô");
            return;
        }

        Item mount = ItemFactory.getInstance().newItem(ItemName.XICH_TU_MA);
        Item choiBay = ItemFactory.getInstance().newItem(ItemName.CHOI_BAY);
        int maskId = p.gender == 1 ? ItemName.SHIRAIJI : ItemName.HAJIRO;
        Item mask = ItemFactory.getInstance().newItem(maskId);

        if (ranking == 1) {
            mount.options.add(new ItemOption(ItemOptionName.NE_DON_ADD_POINT_TYPE_1, 200));
            mount.options.add(new ItemOption(ItemOptionName.CHINH_XAC_ADD_POINT_TYPE_1, 100));
            mount.options.add(new ItemOption(ItemOptionName.TAN_CONG_KHI_DANH_CHI_MANG_POINT_PERCENT_TYPE_1, 100));
            mount.options.add(new ItemOption(ItemOptionName.CHI_MANG_ADD_POINT_TYPE_1, 100));
            mount.options.add(new ItemOption(ItemOptionName.CONG_THEM_TIEM_NANG_ADD_POINT_PERCENT_TYPE_0, 20));
            mount.options.add(new ItemOption(ItemOptionName.TAN_CONG_ADD_POINT_PERCENT_TYPE_8, 20));

            mask.options.add(new ItemOption(125, 3000));
            mask.options.add(new ItemOption(117, 3000));
            mask.options.add(new ItemOption(94, 10));
            mask.options.add(new ItemOption(136, 20));
            mask.options.add(new ItemOption(127, 10));
            mask.options.add(new ItemOption(130, 10));
            mask.options.add(new ItemOption(131, 10));

            choiBay.setQuantity(10);
            p.themItemToBag(choiBay);
            for (int i = 0; i < 3; i++) {
                Item mysteryChest = ItemFactory.getInstance().newItem(ItemName.RUONG_HUYEN_BI);
                p.themItemToBag(mysteryChest);
            }
        } else if (ranking == 2) {
            choiBay.setQuantity(5);
            p.themItemToBag(choiBay);
            Item mysteryChest = ItemFactory.getInstance().newItem(ItemName.RUONG_HUYEN_BI);
            p.themItemToBag(mysteryChest);
        } else if (ranking >= 3 && ranking <= 5) {
            mount.expire = System.currentTimeMillis() + 86400000 * 90;
            choiBay.setQuantity(3);
            p.themItemToBag(choiBay);
            for (int i = 0; i < 2; i++) {
                Item blueChest = ItemFactory.getInstance().newItem(ItemName.RUONG_HUYEN_BI);
                p.themItemToBag(blueChest);
            }
        } else {
            mount.expire = System.currentTimeMillis() + 86400000 * 30;
            Item blueChest = ItemFactory.getInstance().newItem(ItemName.RUONG_HUYEN_BI);
            p.themItemToBag(blueChest);
        }

        p.themItemToBag(mount);
        p.themItemToBag(mask);

        p.getEventPoint().setRewarded(key, 1);
    }

    @Override
    public void initMap(Zone zone) {
        Map map = zone.map;
        int mapID = map.id;
        switch (mapID) {
            case MapName.KHU_LUYEN_TAP:
                break;
            case MapName.TRUONG_OOKAZA:
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 1426).y((short) 552).build());
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 784).y((short) 648).build());
                break;
            case MapName.TRUONG_HARUNA:
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 502).y((short) 408).build());
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 1863).y((short) 360).build());
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 2048).y((short) 360).build());
                break;
            case MapName.TRUONG_HIROSAKI:
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 1207).y((short) 168).build());
                break;

            case MapName.LANG_TONE:
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 1427).y((short) 264).build());
                break;

            case MapName.LANG_KOJIN:
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 621).y((short) 288).build());
                break;

            case MapName.LANG_CHAI:
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 1804).y((short) 384).build());
                break;

            case MapName.LANG_SANZU:
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 320).y((short) 288).build());
                break;

            case MapName.LANG_CHAKUMI:
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 626).y((short) 312).build());
                break;

            case MapName.LANG_ECHIGO:
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 360).y((short) 360).build());
                break;

            case MapName.LANG_OSHIN:
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 921).y((short) 408).build());
                break;

            case MapName.LANG_SHIIBA:
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 583).y((short) 408).build());
                break;

            case MapName.LANG_FEARRI:
                zone.addTree(Tree.builder().id(EffectAutoDataManager.CAY_HALLOWEEN).x((short) 611).y((short) 312).build());
                break;
        }
    }

}
