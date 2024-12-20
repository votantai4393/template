package com.tea.server;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.ImageIcon;

import com.tea.model.Char;
import com.tea.stall.StallManager;
import com.tea.clan.Clan;
import com.tea.db.jdbc.DbManager;
import com.tea.model.RandomItem;
import com.tea.model.SelectCard;
import com.tea.model.SelectCardVIP;
import com.tea.util.Log;
import com.tea.util.NinjaUtils;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class NinjaSchool extends WindowAdapter implements ActionListener {

    private Frame frame;

    public static boolean isStop = false;

    public NinjaSchool() {
        try {
            frame = new Frame("Manger");
            InputStream is = getClass().getClassLoader().getResourceAsStream("icon.png");
            byte[] data = new byte[is.available()];
            is.read(data);
            ImageIcon img = new ImageIcon(data);
            frame.setIconImage(img.getImage());
            frame.setSize(200, 500);
            frame.setBackground(Color.BLACK);
            frame.setResizable(false);
            frame.addWindowListener(this);
            Button b = new Button("Bảo trì");
            b.setBounds(30, 60, 140, 30);
            b.setActionCommand("stop");
            b.addActionListener(this);
            frame.add(b);
            Button b2 = new Button("Lưu Shinwa");
            b2.setBounds(30, 100, 140, 30);
            b2.setActionCommand("shinwa");
            b2.addActionListener(this);
            frame.add(b2);
            Button b3 = new Button("Lưu dữ liệu gia tộc");
            b3.setBounds(30, 140, 140, 30);
            b3.setActionCommand("clan");
            b3.addActionListener(this);
            frame.add(b3);
            Button b4 = new Button("Lưu dữ liệu người chơi");
            b4.setBounds(30, 180, 140, 30);
            b4.setActionCommand("player");
            b4.addActionListener(this);
            frame.add(b4);
            Button b5 = new Button("Làm mới TOP");
            b5.setBounds(30, 220, 140, 30);
            b5.setActionCommand("rank");
            b5.addActionListener(this);
            frame.add(b5);
            Button b6 = new Button("Restart DB");
            b6.setBounds(30, 260, 140, 30);
            b6.setActionCommand("restartDB");
            b6.addActionListener(this);
            frame.add(b6);
            Button b7 = new Button("Gui Do");
            b7.setBounds(30, 300, 140, 30);
            b7.setActionCommand("sendItem");
            b7.addActionListener(this);
            frame.add(b7);
            Button b8 = new Button("kích all khoi sv");
            b8.setBounds(30, 340, 140, 30);
            b8.setActionCommand("kickmen");
            b8.addActionListener(this);
            frame.add(b8);
            Button b9 = new Button("xem nhân vật olnine");
            b9.setBounds(30, 380, 140, 30);
            b9.setActionCommand("xem");
            b9.addActionListener(this);
            frame.add(b9);
            Button b10 = new Button("Cập Nhật Item rơi");
            b10.setBounds(30, 420, 140, 30);
            b10.setActionCommand("update");
            b10.addActionListener(this);
            frame.add(b10);
            frame.setLocationRelativeTo(null);
            frame.setLayout(null);
            frame.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(NinjaSchool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) {
        if (Config.getInstance().load()) {
            if (!DbManager.getInstance().start()) {
                return;
            }
            if (NinjaUtils.availablePort(Config.getInstance().getPort())) {
                new NinjaSchool();
                if (!Server.init()) {
                    Log.error("Khoi tao that bai!");
                    return;
                }
                Server.start();
            } else {
                Log.error("Port " + Config.getInstance().getPort() + " da duoc su dung!");
            }
        } else {
            Log.error("Vui long kiem tra lai cau hinh!");
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("shinwa")) {
            if (Server.start) {
                Log.info("Lưu Shinwa");
                StallManager.getInstance().save();
                Log.info("Lưu xong");
            } else {
                Log.info("Mãy chủ chưa bật");
            }
        }
        if (e.getActionCommand().equals("stop")) {
            if (Server.start) {
                if (!isStop) {
                    (new Thread(new Runnable() {
                        public void run() {
                            try {
                                Server.maintance();
                                System.exit(0);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    })).start();
                }

            } else {
                Log.info("Máy chủ chưa bật.");
            }
        }
        if (e.getActionCommand().equals("clan")) {
            Log.info("Lưu dữ liệu gia tộc.");
            List<Clan> clans = Clan.getClanDAO().getAll();
            synchronized (clans) {
                for (Clan clan : clans) {
                    Clan.getClanDAO().update(clan);
                }
            }
            Log.info("Lưu xong");
        }
        if (e.getActionCommand().equals("rank")) {
            List<Char> chars = ServerManager.getChars();
            for (Char _char : chars) {
                _char.saveData();
            }
            Log.info("Làm mới bảng xếp hạng");
            Ranked.refresh();
        }
        if (e.getActionCommand().equals("player")) {
            Server.saveAll();
            Log.info("Lưu dữ liệu người chơi");
            List<Char> chars = ServerManager.getChars();
            for (Char _char : chars) {
                try {
                    if (_char != null && !_char.isCleaned) {
                        _char.saveData();
                        if (_char.clone != null && !_char.clone.isCleaned) {
                            _char.clone.saveData();
                        }
                        if (_char.user != null && !_char.user.isCleaned) {
                            if (_char.user != null) {
                                _char.user.saveData();
                            }

                        }

                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            Log.info("Lưu xong");
        }
        if (e.getActionCommand().equals("restartDB")) {
            Log.info("Bắt đầu khởi động lại!");
            DbManager.getInstance().shutdown();
            DbManager.getInstance().start();
            Log.info("Khởi động xong!");
        }
        if (e.getActionCommand().equals("sendItem")) {
            JFrameSendItem.run();
        }
        if (e.getActionCommand().equals("kickmen")) {
            Server.closemmen();
        }
        if (e.getActionCommand().equals("xem")) {
            OnlinePlayersFrame.display();
        }
        if (e.getActionCommand().equals("update")) {
            RandomItem.abc("item_roi/event_Halloween");
            RandomItem.abc("item_roi/event_LunarNewYear");
            RandomItem.abc("item_roi/event_Noel");
            RandomItem.abc("item_roi/event_SumMer");
            RandomItem.abc("item_roi/event_TrungThu");
            RandomItem.abc("item_roi/loai_khac");
            RandomItem.abc("item_roi/map_LDGT");
            RandomItem.abc("item_roi/map_VDMQ");
            RandomItem.abc("item_roi/map_langco");
            RandomItem.abc("item_roi/map_langtruyenthuyet");
            RandomItem.abc("item_roi/map_thuong");
            RandomItem.abc("item_roi/LatHinh");
        }
    }

    public void windowClosing(WindowEvent e) {
        frame.dispose();
        if (Server.start) {
            Log.info("Đóng máy chủ.");
            Server.saveAll();

            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
            Server.stop();
            System.exit(0);
        }
    }
}
