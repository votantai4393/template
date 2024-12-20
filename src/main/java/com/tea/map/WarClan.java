package com.tea.map;

import com.tea.clan.Clan;
import com.tea.constants.ItemName;
import com.tea.effect.Effect;
import com.tea.effect.EffectManager;
import com.tea.event.Event;
import com.tea.event.KoroKing;
import com.tea.item.Item;
import com.tea.item.ItemFactory;
import com.tea.model.Char;
import com.tea.model.WarMember;
import com.tea.network.Service;
import com.tea.option.ItemOption;
import com.tea.server.GlobalService;
import com.tea.server.NinjaSchool;
import com.tea.server.Server;
import com.tea.util.Log;
import com.tea.util.NinjaUtils;
import org.jetbrains.annotations.Debug;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WarClan {


    protected EffectManager em;
    public int countDown;
    public int countDown1;
    public int coinTotal;
    public String whiteName;
    public String blackName;
    public ArrayList<String> mandatoryWhiteMemberNames;
    public ArrayList<String> mandatoryBlackMemberNames;
    public ArrayList<Char> whiteMembers;
    public ArrayList<Char> blackMembers;
    public int whitePoint;
    public int blackPoint;
    public int whiteTurretKill;
    public int blackTurretKill;
    public int numberJoinedWhite;
    public int numberJoinedBlack;
    public ArrayList<WarMember> members;
    public Clan clanWhite;
    public Clan clanBlack;
    public long time;
    public int status;
    public static Clan clan1;
    public static Clan clan2;
    public static boolean check = true;
    public static boolean IsStart = false;
    
    public WarClan warClan;
    

    public ReadWriteLock lock = new ReentrantReadWriteLock();
    private Service service;
    public void notify(String text) {
        GlobalService.getInstance().chat("Hệ Thống", text);
    }
    public WarClan(){
        this.blackMembers = new ArrayList<>();
        this.whiteMembers = new ArrayList<>();
        this.members = new ArrayList<>();
        this.numberJoinedWhite = 0;
        this.numberJoinedBlack = 0;
        this.time = System.currentTimeMillis();
        this.whiteTurretKill = 0;
        this.blackTurretKill = 0;
        this.whitePoint = 0;
        this.blackPoint = 0;
        this.whiteName ="";
        this.blackName = "";
    }
    public WarClan(Clan clanWhite, Clan clanBlack){
        this.blackMembers = new ArrayList<>();
        this.whiteMembers = new ArrayList<>();
        this.members = new ArrayList<>();
        this.numberJoinedWhite = 0;
        this.numberJoinedBlack = 0;
        this.time = System.currentTimeMillis();
        this.whiteTurretKill = 0;
        this.blackTurretKill = 0;
        this.whitePoint = 0;
        this.blackPoint = 0;
        this.whiteName =clanWhite.name;
        this.blackName = clanBlack.name;
        this.countDown = 3600;// 
        this.countDown1 = 600;// 
        this.notify("Gia tộc "+whiteName +" và gia tộc "+ blackName +" đang bắt đầu cuộc chiến!");
    }

    public void initMap() {
        for (Map map : MapManager.getInstance().getMaps()) {
            if (map.id >= 117 && map.id <= 124) {
                map.setWarClan(this);
                map.initZone();
            }
        }
    }

    public void register() {
        for (final WarMember member : members) {
            Char player = Char.findCharByName(member.name);
            if (player != null) {
               player.serverMessage("Bạn có 30s để chờ thành viên tham gia ");
            }
        }
        startCounting_2();
        this.status = 0;
    }

            public void viewTop(Char _char) {
            String info = "";
            int whitePointAdd = this.whiteTurretKill * 500;
            int blackPointAdd = this.blackTurretKill * 500;

            int pointGTC_white = this.whitePoint;
            int pointGTC_black = this.blackPoint;

            if (_char.Clanfaction == 0) {
                this.whitePoint = _char.member.clone().point;
            } else if (_char.Clanfaction == 1) {
                this.blackPoint = _char.member.clone().point;
            }

            if (whitePoint < 0) {
                whitePoint = 0;
            }

            if (blackPoint < 0) {
                blackPoint = 0;
            }

            info += clan1.name + " tích lũy " + pointGTC_white + " điểm";
            info += "\n";
            info += clan2.name + " tích lũy " + pointGTC_black + " điểm";

            boolean reward = false;

            int totalWhitePoints = 0;
            int totalBlackPoints = 0;

            for (WarMember mem : this.members) {
                WarMember clone = mem.clone();

                if (clone.faction == 0) {
                    clone.point += whitePointAdd - blackPointAdd;
                    totalWhitePoints += clone.point;
                } else if (clone.faction == 1) {
                    clone.point += blackPointAdd - whitePointAdd;
                    totalBlackPoints += clone.point;
                }

                if (clone.point < 0) {
                    clone.point = 0;
                }

            }

            this.whitePoint = totalWhitePoints;
            this.blackPoint = totalBlackPoints;

            _char.getService().reviewCT(info, reward);
        }




    public void finish(final byte type) {
        if (this.status == 2 ) {
            this.coinTotal = clan1.MoneyGTC;
            final int coin = this.coinTotal * 2 * 9 / 10;
            if (type == -1 || NinjaSchool.isStop) {
                clan1.addXu(coin / 2);
                clan2.addXu(coin / 2);
                for (final WarMember member : members) {
                    Char player = Char.findCharByName(member.name);
                    if (player != null) {
                        player.serverMessage("Hai gia tộc hoà nhau và gia tộc bạn nhận lại " + coin /2 + " xu gia tộc.");
                    }
                }
            } else if (type == 0) {
                clan1.addXu(coin);
                for (final WarMember member : members) {
                    Char player = Char.findCharByName(member.name);
                    if (player != null) {
                        player.serverMessage("Gia tộc " + clan1.name + " giành chiến thắng và nhận được " + coin + " xu gia tộc.");
                    }
                }
            } else if (type == 1) {
                clan2.addXu(coin);
                for (final WarMember member : members) {
                    Char player = Char.findCharByName(member.name);
                    if (player != null) {
                        player.serverMessage("Gia tộc " + clan2.name + " giành chiến thắng và nhận được " + coin + " xu gia tộc.");
                    }
                }

            }
        }
    }
    public static void setMoney(int Money,Char pl){
        if(pl.clan.typeGTC == 0){
            clan1.MoneyGTC = Money;
        }else{
            clan2.MoneyGTC = Money;
        }
        if(clan1 != null && clan2 !=null) {
            if (clan1.MoneyGTC == clan2.MoneyGTC) {
                clan1.addXu(-clan1.MoneyGTC);
                clan2.addXu(-clan1.MoneyGTC);
                WarClan.initWarClan(clan1, clan2);
                check = false;
            }
        }
    }

    public void end() {
        this.status = 2;
        lock.writeLock().lock();
        try {
            for (final WarMember member : members) {
            Char player = Char.findCharByName(member.name);
            if (player != null) {
                finishBasedOnPoints();
                    try {
                        short[] xy = NinjaUtils.getXY(1);
                        player.setXY(xy);
                        player.changeMap(1);
                        clan1.typeGTC = clan2.typeGTC = -1;
                        clan1.MoneyGTC = clan2.MoneyGTC = 0; 
                        check = true;
                        IsStart = false;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void finishBasedOnPoints() {
        if (whitePoint == blackPoint) {
            this.finish((byte) (-1));
        } else if (whitePoint > blackPoint) {
            this.finish((byte) (0));
        } else {
            this.finish((byte) (1));
        }
    }

    private void relocateChars(ArrayList<Char> charList) {
        for (Char _char : charList) {
            try {
                _char.member.save();
                short[] xy = NinjaUtils.getXY(1);
                _char.setXY(xy);
                _char.changeMap(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void addMember(Char _char) {
        lock.writeLock().lock();
        try {
            if (_char.faction == 0) {
                if (!this.whiteMembers.contains(_char)) {
                    this.whiteMembers.add(_char);
                }
            }
            if (_char.faction == 1) {
                if (!this.blackMembers.contains(_char)) {
                    this.blackMembers.add(_char);
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
    public void addTurretPoint(int faction) {
        if (faction == 0) {
            this.whiteTurretKill += 1;
        }
        if (faction == 1) {
            this.blackTurretKill += 1;
        }
    }
    public void addMember(WarMember mem) {
        lock.writeLock().lock();
        try {
            this.members.add(mem);
        } finally {
            lock.writeLock().unlock();
        }
    }
    public void removeMember(Char _char) {
        lock.writeLock().lock();
        try {
            if (_char.faction == 0) {
                this.whiteMembers.remove(_char);
            }
            if (_char.faction == 1) {
                this.blackMembers.remove(_char);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }
    
    
    public void startCounting() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(countDown > 0) {
                    try {
                        Thread.sleep(1000); 
                        countDown--;
                        for (final WarMember member : members) {
                            Char _char = Char.findCharByName(member.name);
                            if(_char != null) {
                                _char.service.sendTimeInMap(countDown);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    public void startCounting_2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(countDown1 > 0) {
                    try {
                        Thread.sleep(1000); 
                        countDown1--; 
                        for (final WarMember member : members) {
                            Char _char = Char.findCharByName(member.name);
                            if(_char != null) {
                                _char.service.sendTimeInMap(countDown1);
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    
    
    public void start() {
        for (final WarMember member : members) {
            Char player = Char.findCharByName(member.name);
            if (player != null) {
               player.serverMessage("Gia tộc chiến đã bắt đầu");
            }
        }
        this.status = 1;
        startCounting();
    }
   
    public static void initWarClan(Clan clanWhite, Clan clanBlack) {
    Runnable runnable;
    runnable = new Runnable() {
        public void run() {
            try {
                WarClan warClan = MapManager.getInstance().warClan = new WarClan(clanWhite, clanBlack);
                warClan.initMap();
                warClan.register();
                long sleepTime = 600000; // 10 phút
                for (long slept = 0; slept < sleepTime && !NinjaSchool.isStop; slept += 1000) {
                    Thread.sleep(1000);
                }
                if (NinjaSchool.isStop) {
                    warClan.end();
                    return;
                }
                warClan.start();
                sleepTime = 3600000; // 1 giờ
                for (long slept = 0; slept < sleepTime && !NinjaSchool.isStop; slept += 1000) {
                    Thread.sleep(1000);
                }
                warClan.end();
                
            } catch (InterruptedException ex) {
                Logger.getLogger(WarClan.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate(runnable, 0, 1 * 24 * 60 * 60, TimeUnit.SECONDS);
}

}
