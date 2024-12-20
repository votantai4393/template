package com.tea.model;

import com.tea.constants.ItemName;
import com.tea.event.Event;
import com.tea.item.ItemManager;
import com.tea.item.ItemTemplate;
import com.tea.lib.RandomCollection;
import com.tea.util.Log;
import com.tea.util.StringUtils;
import java.util.HashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

public class RandomItem {

    public static int[] DONG_XU = {50, 30, 20, 10, 10};

    public static final RandomCollection<Integer> KHI_BAO = new RandomCollection<>();
    public static final RandomCollection<Integer> LANG_BAO = new RandomCollection<>();
    public static final RandomCollection<Integer> RUONG_MAY_MAN_2 = new RandomCollection<>();
    public static final RandomCollection<Integer> BANH_KHUC_CAY_CHOCOLATE = new RandomCollection<>();
    public static final RandomCollection<Integer> BANH_KHUC_CAY_DAU_TAY = new RandomCollection<>();
    public static final RandomCollection<Integer> VUA_TUAN_LOC = new RandomCollection<>();
    public static final RandomCollection<Integer> DOI_DIEM_NGUOI_TUYET_XU = new RandomCollection<>();
    public static final RandomCollection<Integer> DOI_DIEM_NGUOI_TUYET_LUONG = new RandomCollection<>();
    public static final RandomCollection<Integer> QUA_TRANG_TRI = new RandomCollection<>();
    public static final RandomCollection<Integer> HOP_QUA_TRANG_TRI = new RandomCollection<>();
    public static final RandomCollection<Integer> BOSS_VDMQ = new RandomCollection<>();
    public static final RandomCollection<Integer> BOSS = new RandomCollection<>();
    public static final RandomCollection<Integer> BOSS_EVENT = new RandomCollection<>();
    public static final RandomCollection<Integer> TRUNG_THU = new RandomCollection<>();
    public static final RandomCollection<Integer> NOEL = new RandomCollection<>();
    public static final RandomCollection<Integer> ITEM = new RandomCollection<>();
    public static final RandomCollection<Integer> LANG_CO = new RandomCollection<>();
    public static final RandomCollection<Integer> BOSS_LANG_CO = new RandomCollection<>();
    public static final RandomCollection<Integer> UPYEN = new RandomCollection<>();
    public static final RandomCollection<Integer> UPluong = new RandomCollection<>();
    public static final RandomCollection<Integer> vt = new RandomCollection<>();
    public static final RandomCollection<Integer> LANG_TRUYEN_THUYET = new RandomCollection<>();
    public static final RandomCollection<Integer> VDMQ = new RandomCollection<>();
    public static final RandomCollection<Integer> LAT_HINH = new RandomCollection<>();
    public static final RandomCollection<Integer> SACH_VO_CONG_120 = new RandomCollection<>();
    public static final RandomCollection<Integer> BOSS_LANG_TRUYEN_THUYET = new RandomCollection<>();
    public static final RandomCollection<Integer> RUONG_CHIEN_TRUONG = new RandomCollection<>();
    public static final RandomCollection<Integer> TET = new RandomCollection<>();
    public static final RandomCollection<Integer> BANH_CHUNG = new RandomCollection<>();
    public static final RandomCollection<Integer> BANH_TET = new RandomCollection<>();
    public static final RandomCollection<Integer> BUA_MAY_MAN = new RandomCollection<>();
    public static final RandomCollection<Integer> THAN_TAI = new RandomCollection<>();
    public static final RandomCollection<Integer> WOMAN_DAY = new RandomCollection<>();
    public static final RandomCollection<Integer> HUNG_KING = new RandomCollection<>();
    public static final RandomCollection<Integer> SEA_GAME = new RandomCollection<>();
    public static final RandomCollection<Integer> TRE_XANH_TRAM_DOT = new RandomCollection<>();
    public static final RandomCollection<Integer> TRE_VANG_TRAM_DOT = new RandomCollection<>();
    public static final RandomCollection<Integer> THANH_VAT = new RandomCollection<>();
    public static final RandomCollection<Integer> CUP_VANG = new RandomCollection<>();
    public static final RandomCollection<Integer> LINH_VAT = new RandomCollection<>();
    public static final RandomCollection<Integer> BOSS_LDGT = new RandomCollection<>();
    public static final RandomCollection<Integer> LANH_DIA_GIA_TOC = new RandomCollection<>();
    public static final RandomCollection<Integer> SUMMER = new RandomCollection<>();
    public static final RandomCollection<Integer> NUOC_DIET_KHUAN = new RandomCollection<>();
    public static final RandomCollection<Integer> THAT_THU_BAO = new RandomCollection<>();
    public static final RandomCollection<Integer> RUONG_HAC_AM = new RandomCollection<>();
    public static final RandomCollection<Integer> LONG_DEN = new RandomCollection<>();
    public static final RandomCollection<Integer> HOP_BANH_THUONG = new RandomCollection<>();
    public static final RandomCollection<Integer> HOP_BANH_THUONG_HANG = new RandomCollection<>();
    public static final RandomCollection<Integer> RUONG_NGOC = new RandomCollection<>();
    public static final RandomCollection<Integer> DIEU_VAI = new RandomCollection<>();
    public static final RandomCollection<Integer> DIEU_GIAY = new RandomCollection<>();
    public static final RandomCollection<Integer> Halloween = new RandomCollection<>();
    public static final RandomCollection<Integer> HOP_MA_QUY = new RandomCollection<>();
    public static final RandomCollection<Integer> KEO_TAO = new RandomCollection<>();

    public static void init() {
        loadItems("item_roi/loai_khac/RUONG_NGOC.json", "RUONG_NGOC");
        loadItems("item_roi/loai_khac/LANG_BAO.json", "LANG_BAO");
        loadItems("item_roi/loai_khac/KHI_BAO.json", "KHI_BAO");
        loadItems("item_roi/loai_khac/VUA_TUAN_LOC.json", "VUA_TUAN_LOC");
        loadItems("item_roi/loai_khac/SACH_VO_CONG_120.json", "SACH_VO_CONG_120");
        loadItems("item_roi/loai_khac/RUONG_CHIEN_TRUONG.json", "RUONG_CHIEN_TRUONG");
        loadItems("item_roi/loai_khac/RUONG_HAC_AM.json", "RUONG_HAC_AM");
        loadItems("item_roi/loai_khac/LINH_VAT.json", "LINH_VAT");
        loadItems("item_roi/loai_khac/THAT_THU_BAO.json", "THAT_THU_BAO");

        loadItems("item_roi/event_SumMer/SUMMER.json", "SUMMER");
        loadItems("item_roi/event_SumMer/DIEU_VAI.json", "DIEU_VAI");
        loadItems("item_roi/event_SumMer/DIEU_GIAY.json", "DIEU_GIAY");

        loadItems("item_roi/map_langtruyenthuyet/BOSS_LANG_TRUYEN_THUYET.json", "BOSS_LANG_TRUYEN_THUYET");
        loadItems("item_roi/map_langtruyenthuyet/LANG_TRUYEN_THUYET.json", "LANG_TRUYEN_THUYET");

        loadItems("item_roi/map_langco/BOSS_LANG_CO.json", "BOSS_LANG_CO");
        loadItems("item_roi/map_langco/LANG_CO.json", "LANG_CO");

        loadItems("item_roi/event_Noel/NOEL.json", "NOEL");
        loadItems("item_roi/event_Noel/BANH_KHUC_CAY_CHOCOLATE.json", "BANH_KHUC_CAY_CHOCOLATE");
        loadItems("item_roi/event_Noel/BANH_KHUC_CAY_DAU_TAY.json", "BANH_KHUC_CAY_DAU_TAY");
        loadItems("item_roi/event_Noel/DOI_DIEM_NGUOI_TUYET_LUONG.json", "DOI_DIEM_NGUOI_TUYET_LUONG");
        loadItems("item_roi/event_Noel/DOI_DIEM_NGUOI_TUYET_XU.json", "DOI_DIEM_NGUOI_TUYET_XU");
        loadItems("item_roi/event_Noel/HOP_QUA_TRANG_TRI.json", "HOP_QUA_TRANG_TRI");
        loadItems("item_roi/event_Noel/QUA_TRANG_TRI.json", "QUA_TRANG_TRI");

        loadItems("item_roi/map_thuong/MAP.json", "MAP");
        loadItems("item_roi/map_thuong/BOSS.json", "BOSS");
        loadItems("item_roi/map_thuong/BOSS_EVENT.json", "BOSS_EVENT");
        loadItems("item_roi/map_thuong/UPYEN.json", "UPYEN");
        loadItems("item_roi/map_thuong/UPluong.json", "UPluong");
        loadItems("item_roi/map_thuong/MAP_VITHU.json", "MAP_VITHU");

        loadItems("item_roi/event_LunarNewYear/TET.json", "TET");
        loadItems("item_roi/event_LunarNewYear/BANH_CHUNG.json", "BANH_CHUNG");
        loadItems("item_roi/event_LunarNewYear/BANH_TET.json", "BANH_TET");
        loadItems("item_roi/event_LunarNewYear/BUA_MAY_MAN.json", "BUA_MAY_MAN");

        loadItems("item_roi/event_TrungThu/TRUNG_THU.json", "TRUNG_THU");
        loadItems("item_roi/event_TrungThu/HOP_BANH_THUONG.json", "HOP_BANH_THUONG");
        loadItems("item_roi/event_TrungThu/HOP_BANH_THUONG_HANG.json", "HOP_BANH_THUONG_HANG");
        loadItems("item_roi/event_TrungThu/LONG_DEN.json", "LONG_DEN");

        loadItems("item_roi/map_VDMQ/VDMQ.json", "VDMQ");
        loadItems("item_roi/map_VDMQ/BOSS_VDMQ.json", "BOSS_VDMQ");

        loadItems("item_roi/event_Halloween/Halloween.json", "Halloween");
        loadItems("item_roi/event_Halloween/HOP_MA_QUY.json", "HOP_MA_QUY");
        loadItems("item_roi/event_Halloween/KEO_TAO.json", "KEO_TAO");
    }

    private static void loadItems(String filename, String itemType) {
        StringBuilder objStr = new StringBuilder();
        try (FileReader frd = new FileReader(filename); BufferedReader brd = new BufferedReader(frd)) {
            String line;
            while ((line = brd.readLine()) != null) {
                objStr.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        clear(itemType);

        if (itemType.equals("Lat_Hinh_Thuong")) {
            SelectCard.getInstance().init();
        } else if (itemType.equals("Lat_Hinh_VIP")) {
            SelectCardVIP.getInstance().init();
        } else {
            try {
                JSONArray js = (JSONArray) JSONValue.parse(objStr.toString());
                for (int i = 0; i < js.size(); i++) {
                    JSONObject job1 = (JSONObject) JSONValue.parse(js.get(i).toString());
                    double percent = Double.parseDouble(job1.get("percent").toString());
                    int id = Integer.parseInt(job1.get("id").toString());

                    switch (itemType) {
                        case "RUONG_NGOC" ->
                            RUONG_NGOC.add(percent, id);
                        case "SUMMER" ->
                            SUMMER.add(percent, id);
                        case "DIEU_VAI" ->
                            DIEU_VAI.add(percent, id);
                        case "DIEU_GIAY" ->
                            DIEU_GIAY.add(percent, id);
                        case "BOSS_LANG_TRUYEN_THUYET" ->
                            BOSS_LANG_TRUYEN_THUYET.add(percent, id);
                        case "LANG_TRUYEN_THUYET" ->
                            LANG_TRUYEN_THUYET.add(percent, id);
                        case "BOSS_LANG_CO" ->
                            BOSS_LANG_CO.add(percent, id);
                        case "LANG_CO" ->
                            LANG_CO.add(percent, id);
                        case "NOEL" ->
                            NOEL.add(percent, id);
                        case "BANH_KHUC_CAY_CHOCOLATE" ->
                            BANH_KHUC_CAY_CHOCOLATE.add(percent, id);
                        case "BANH_KHUC_CAY_DAU_TAY" ->
                            BANH_KHUC_CAY_DAU_TAY.add(percent, id);
                        case "MAP" ->
                            ITEM.add(percent, id);
                        case "BOSS" ->
                            BOSS.add(percent, id);
                        case "BOSS_EVENT" ->
                            BOSS_EVENT.add(percent, id);
                        case "UPYEN" ->
                            UPYEN.add(percent, id);
                        case "UPluong" ->
                            UPluong.add(percent, id);
                        case "MAP_VITHU" ->
                            vt.add(percent, id);
                        case "TET" ->
                            TET.add(percent, id);
                        case "BANH_CHUNG" ->
                            BANH_CHUNG.add(percent, id);
                        case "BANH_TET" ->
                            BANH_TET.add(percent, id);
                        case "TRUNG_THU" ->
                            TRUNG_THU.add(percent, id);
                        case "HOP_BANH_THUONG" ->
                            HOP_BANH_THUONG.add(percent, id);
                        case "HOP_BANH_THUONG_HANG" ->
                            HOP_BANH_THUONG_HANG.add(percent, id);
                        case "VDMQ" ->
                            VDMQ.add(percent, id);
                        case "BOSS_VDMQ" ->
                            BOSS_VDMQ.add(percent, id);
                        case "KHI_BAO" ->
                            KHI_BAO.add(percent, id);
                        case "LANG_BAO" ->
                            LANG_BAO.add(percent, id);
                        case "VUA_TUAN_LOC" ->
                            VUA_TUAN_LOC.add(percent, id);
                        case "DOI_DIEM_NGUOI_TUYET_XU" ->
                            DOI_DIEM_NGUOI_TUYET_XU.add(percent, id);
                        case "DOI_DIEM_NGUOI_TUYET_LUONG" ->
                            DOI_DIEM_NGUOI_TUYET_LUONG.add(percent, id);
                        case "HOP_QUA_TRANG_TRI" ->
                            HOP_QUA_TRANG_TRI.add(percent, id);
                        case "QUA_TRANG_TRI" ->
                            QUA_TRANG_TRI.add(percent, id);
                        case "SACH_VO_CONG_120" ->
                            SACH_VO_CONG_120.add(percent, id);
                        case "RUONG_CHIEN_TRUONG" ->
                            RUONG_CHIEN_TRUONG.add(percent, id);
                        case "RUONG_HAC_AM" ->
                            RUONG_HAC_AM.add(percent, id);
                        case "BUA_MAY_MAN" ->
                            BUA_MAY_MAN.add(percent, id);
                        case "LINH_VAT" ->
                            LINH_VAT.add(percent, id);
                        case "THAT_THU_BAO" ->
                            THAT_THU_BAO.add(percent, id);
                        case "LONG_DEN" ->
                            LONG_DEN.add(percent, id);
                        case "Halloween" ->
                            Halloween.add(percent, id);
                        case "HOP_MA_QUY" ->
                            HOP_MA_QUY.add(percent, id);
                        case "KEO_TAO" ->
                            KEO_TAO.add(percent, id);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void clear(String type) {
        switch (type) {
            case "RUONG_NGOC" ->
                RUONG_NGOC.clearMap();
            case "SUMMER" ->
                SUMMER.clearMap();
            case "DIEU_VAI" ->
                DIEU_VAI.clearMap();
            case "DIEU_GIAY" ->
                DIEU_GIAY.clearMap();
            case "BOSS_LANG_TRUYEN_THUYET" ->
                BOSS_LANG_TRUYEN_THUYET.clearMap();
            case "LANG_TRUYEN_THUYET" ->
                LANG_TRUYEN_THUYET.clearMap();
            case "BOSS_LANG_CO" ->
                BOSS_LANG_CO.clearMap();
            case "LANG_CO" ->
                LANG_CO.clearMap();
            case "NOEL" ->
                NOEL.clearMap();
            case "BANH_KHUC_CAY_CHOCOLATE" ->
                BANH_KHUC_CAY_CHOCOLATE.clearMap();
            case "BANH_KHUC_CAY_DAU_TAY" ->
                BANH_KHUC_CAY_DAU_TAY.clearMap();
            case "MAP" ->
                ITEM.clearMap();
            case "BOSS" ->
                BOSS.clearMap();
            case "BOSS_EVENT" ->
                BOSS_EVENT.clearMap();
            case "UPYEN" ->
                UPYEN.clearMap();
            case "UPluong" ->
                UPluong.clearMap();
            case "MAP_VITHU" ->
                vt.clearMap();
            case "TET" ->
                TET.clearMap();
            case "BANH_CHUNG" ->
                BANH_CHUNG.clearMap();
            case "BANH_TET" ->
                BANH_TET.clearMap();
            case "TRUNG_THU" ->
                TRUNG_THU.clearMap();
            case "HOP_BANH_THUONG" ->
                HOP_BANH_THUONG.clearMap();
            case "HOP_BANH_THUONG_HANG" ->
                HOP_BANH_THUONG_HANG.clearMap();
            case "VDMQ" ->
                VDMQ.clearMap();
            case "BOSS_VDMQ" ->
                BOSS_VDMQ.clearMap();
            case "KHI_BAO" ->
                KHI_BAO.clearMap();
            case "LANG_BAO" ->
                LANG_BAO.clearMap();
            case "VUA_TUAN_LOC" ->
                VUA_TUAN_LOC.clearMap();
            case "DOI_DIEM_NGUOI_TUYET_XU" ->
                DOI_DIEM_NGUOI_TUYET_XU.clearMap();
            case "DOI_DIEM_NGUOI_TUYET_LUONG" ->
                DOI_DIEM_NGUOI_TUYET_LUONG.clearMap();
            case "HOP_QUA_TRANG_TRI" ->
                HOP_QUA_TRANG_TRI.clearMap();
            case "QUA_TRANG_TRI" ->
                QUA_TRANG_TRI.clearMap();
            case "SACH_VO_CONG_120" ->
                SACH_VO_CONG_120.clearMap();
            case "RUONG_CHIEN_TRUONG" ->
                RUONG_CHIEN_TRUONG.clearMap();
            case "RUONG_HAC_AM" ->
                RUONG_HAC_AM.clearMap();
            case "BUA_MAY_MAN" ->
                BUA_MAY_MAN.clearMap();
            case "LINH_VAT" ->
                LINH_VAT.clearMap();
            case "THAT_THU_BAO" ->
                THAT_THU_BAO.clearMap();
            case "LONG_DEN" ->
                LONG_DEN.clearMap();
            case "Halloween" ->
                Halloween.clearMap();
            case "HOP_MA_QUY" ->
                HOP_MA_QUY.clearMap();
            case "KEO_TAO" ->
                KEO_TAO.clearMap();
            case "Lat_Hinh_Thuong" ->
                SelectCard.getInstance().cards.clearMap();
            case "Lat_Hinh_VIP" ->
                SelectCardVIP.getInstance().cards.clearMap();
        }
    }

    public static void useTest(RandomCollection<Integer> rc, int times) {
        HashMap<Integer, Integer> result = rc.test(times);
        for (Map.Entry<Integer, Integer> e : result.entrySet()) {
            ItemTemplate template = ItemManager.getInstance().getItemTemplate(e.getKey());
            int value = e.getValue();
            double rate = ((double) value / times * 100);
            Log.info(String.format("name: %s rate: %.4f%% (%d)", StringUtils.removeAccent(template.name), rate, value));
        }
    }

    public static void abc(String url) {
        try {
            File file = new File(url);
            if (!file.isDirectory()) {
                return;
            }
            File[] files = file.listFiles();
            if (files == null || files.length == 0) {
                return;
            }

            long now = System.currentTimeMillis();
            long min_time = 1 * 60 * 1000;
            for (File file1 : files) {
                if (file1.isFile()) {
                    BasicFileAttributes fab = Files.readAttributes(file1.toPath(), BasicFileAttributes.class);
                    long time_edit = fab.lastModifiedTime().toMillis();
                    if (now - time_edit < min_time) {
                        loadItems(url + "/" + file1.getName(), file1.getName().split(".json")[0]);
                        System.out.println("LOAD OK " + file1.getName().split(".json")[0]);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
