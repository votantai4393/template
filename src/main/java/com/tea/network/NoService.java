/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.network;

import com.tea.db.jdbc.DbManager;
import com.tea.effect.Effect;
import com.tea.item.Equip;
import com.tea.item.Item;
import com.tea.item.ItemFactory;
import com.tea.map.item.ItemMap;
import com.tea.mob.Mob;
import com.tea.model.Card;
import com.tea.model.Char;
import com.tea.model.Trader;
import com.tea.party.Group;
import static com.tea.server.JFrameSendItem.checkNumber;
import com.tea.server.NinjaSchool;
import com.tea.server.Server;
import com.tea.store.ItemStore;
import com.tea.task.TaskOrder;
import com.tea.thiendia.Ranking;
import com.tea.util.Log;
import com.tea.util.NinjaUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author Admin
 */
public class NoService extends Service {

    private static final NoService noService = new NoService();

    public static NoService getInstance() {
        return noService;
    }

    public NoService() {
        super(null);
    }

    @Override
    public void showAlert(String title, String text) {

    }
    public static void sendMsg(Session session, String str) throws IOException {
        Message msg = new Message(0);
        msg.writer().writeUTF(str);
        session.sendMessage(msg);
        msg.cleanup();
    }

    @Override
    public void reviewCT(String text, boolean reward) {

    }

    @Override
    public void selectChar(Vector<Char> list) {
    }

    @Override
    public void loadInfo() {

    }

    @Override
    public void npcUpdate(int index, int status) {

    }

    @Override
    public void loadClass() {

    }

    @Override
    public void sendItemMap() {
    }

    @Override
    public void addYen(int add) {
    }

    @Override
    public void addLuong(int add) {
    }

    @Override
    public void loadGold() {
    }

    @Override
    public void exchangeYenForXu(int xu) {
    }

    @Override
    public void addXu(int xu) {
    }

    @Override
    public void buy() {

    }

    @Override
    public void convertUpgrade(Item... item) {

    }

    @Override
    public void addEffect(Effect effect) {
    }

    @Override
    public void removeEffect(Effect effect) {

    }

    @Override
    public void showWait(String title) {

    }

    @Override
    public void editEffect(Effect effect) {

    }

    @Override
    public void updateHp() {

    }

    @Override
    public void updateMp() {

    }

    @Override
    public void sendTaskOrder(TaskOrder task) {

    }

    @Override
    public void updateTaskOrder(TaskOrder task) {

    }

    @Override
    public void clearTaskOrder(TaskOrder task) {

    }

    @Override
    public void playerLoadAll(Char pl) {

    }

    @Override
    public void callEffectBall() {
    }

    @Override
    public void sendSkillShortcut(String key, byte[] data, byte type) {

    }

    @Override
    public void boxCoinIn(int xu) {

    }

    @Override
    public void boxCoinOut(int xu) {

    }

    @Override
    public void useBookSkill(byte itemIndex, short skillId) {

    }

    @Override
    public void openUIBox() {

    }

    @Override
    public void openUICollectionBox() {

    }

    @Override
    public void openUIMaskBox() {

    }

    @Override
    public void taoLinhAn() {

    }

    @Override
    public void nangLinhAn(Char c) {

    }

    @Override
    public void resetPoint() {

    }

    @Override
    public void reviewDungeon() {

    }

    @Override
    public void sendZone() {

    }

    @Override
    public void loadGlove(Char _char) {

    }

    @Override
    public void loadPet(Char _char) {

    }

    @Override
    public void loadHonor(Char _char) {

    }

    @Override
    public void loadCoat(Char _char) {

    }

    @Override
    public void loadMount(Char _char) {

    }

    @Override
    public void addFriend(String name, int type) {

    }

    @Override
    public void warInfo() {

    }

    @Override
    public void inviteFriend(String name) {

    }

    @Override
    public void itemMountToBag(int index1, int index2) {

    }

    @Override
    public void createClan() {

    }

    @Override
    public void changeClanAlert(String alert) {

    }

    @Override
    public void openUIZone() {

    }

    @Override
    public void updatePointPB() {

    }

    @Override
    public void sendTaskInfo() {

    }

    @Override
    public void loadAll() {

    }

    @Override
    public void loadMobMe() {

    }

    @Override
    public void updateInfoChar(Char pl) {

    }

    @Override
    public void updateInfoMe() {

    }

    @Override
    public void openUIConfirmID() {

    }

    @Override
    public void sendImgEffect(Message msg) {

    }

    @Override
    public void sendEffectData(Message msg) {

    }

    @Override
    public void sendImgEffectAuto(Message msg) {

    }

    @Override
    public void sendEffectAutoData(Message msg) {

    }

    @Override
    public void playerLoadInfo(Char pl) {

    }

    @Override
    public void openUI(byte typeUI, String... array) {

    }

    @Override
    public void openUI(byte typeUI, String title, String action) {
    }

    public void inputDlg(String title, int type) {
    }

    @Override
    public void endDlg(boolean isResetButton) {

    }

    @Override
    public void requestMapTemplate(Message ms) {

    }

    @Override
    public void clanInvite(Char _char) {

    }

    @Override
    public void requestMobTemplate(Message ms) {

    }

    @Override
    public void requestClanInfo() {

    }

    @Override
    public void requestClanMember() {

    }

    @Override
    public void requestClanItem() {

    }

    @Override
    public void writeLog() {

    }

    @Override
    public void addXuClan(int coin) {

    }

    @Override
    public void requestIcon(Message ms) {

    }

    @Override
    public void removeFriend(String name) {

    }

    @Override
    public void requestFriend() {

    }

    @Override
    public void requestEnemy() {

    }

    @Override
    public void removeEnemy(String name) {

    }

    @Override
    public void requestItemChar(Equip equip, int index) {
    }

    @Override
    public void itemBoxToBag(int index1, int index2) {
    }

    @Override
    public void itemBagToBox(int index1, int index2) {
    }

    @Override
    public void useItem(int index) {

    }

    @Override
    public void itemBodyToBag(int equipType, int index) {

    }

    @Override
    public void itemInfo(Item item, byte typeUI, byte indexUI) {

    }

    @Override
    public void itemStoreInfo(ItemStore item, byte typeUI, byte indexUI) {

    }

    @Override
    public void equipmentInfo(Equip equip, byte typeUI, byte indexUI) {
    }

    @Override
    public void openMenu(String text) {
    }

    @Override
    public void openUIMenu() {
    }

    @Override
    public void updatePotential() {
    }

    @Override
    public void levelUp() {

    }

    @Override
    public void expandBag(Item item) {
    }

    @Override
    public void loadSkill() {
    }

    @Override
    public void bagSort() {

    }

    @Override
    public void boxSort() {

    }

    @Override
    public void openUIConfirm(int npcId, String title) {
    }

    @Override
    public void menu(String... menu) {
    }

    @Override
    public void updateVersion() {
    }

    @Override
    public void updateMap() {
    }

    @Override
    public void updateData() {
    }

    @Override
    public void updateSkill() {
    }

    @Override
    public void updateItem() {
    }

    @Override
    public void serverDialog(String text) {
    }

    @Override
    public void serverAlert(String text) {
    }

    @Override
    public void updateItem(Item item) {
    }

    @Override
    public void useItemUpToUp(int index, int quantity) {
    }

    @Override
    public void removeItem(int index) {
    }

    @Override
    public void openWeb(String btnRight, String btnLeft, String url, String alert) {
    }

    @Override
    public void sendTimeInMap(int timeCountDown) {

    }

    @Override
    public void upgrade(byte type, Item item) {

    }

    @Override
    public void ngocKham(byte type, Item item) {

    }

    @Override
    public void chatGlobal(String name, String text) {

    }

    @Override
    public void chat(String name, String text) {

    }

    @Override
    public void showInputDialog() {

    }

    @Override
    public void playerAdd(Char pl) {

    }

    @Override
    public void npcAttackMe(Mob mob, int dameHp, int dameMp) {

    }

    @Override
    public void sendDataBox() {

    }

    @Override
    public void doShowRankedListUI(List<Ranking> list) {

    }

    @Override
    public void viewInfo(Char pl) {
    }

    @Override
    public void deleteItemBody(int index) {
    }

    @Override
    public void themItem(Item item) {
    }

    @Override
    public void addItemQuantity(int index, int quantity) {

    }

    @Override
    public void tradeAccept() {

    }

    @Override
    public void serverMessage(String text) {

    }

    @Override
    public void testInvite(int charId) {

    }

    @Override
    public void addCuuSat(int charId) {
    }

    @Override
    public void meCuuSat(int charId) {
    }

    @Override
    public void clearCuuSat(int charId) {
    }

    @Override
    public void clearMap() {
    }

    @Override
    public void endWait(String text) {
    }

    @Override
    public void pickItem(ItemMap item) {

    }

    @Override
    public void playerPickItem(Char _char, ItemMap item) {

    }

    @Override
    public void throwItem(byte index, ItemMap item) {

    }

    @Override
    public void playerThrowItem(Char _char, byte index, ItemMap item) {

    }

    @Override
    public void clearTask() {

    }

    @Override
    public void taskNext() {

    }

    @Override
    public void updateTaskCount(int count) {

    }

    @Override
    public void openUIShop(byte type, List<ItemStore> items) {

    }

    @Override
    public void outParty() {

    }

    @Override
    public void partyInvite(int id, String name) {

    }

    @Override
    public void splitItem(List<Item> list) {

    }

    @Override
    public void openFindParty(HashMap<String, Group> groups) {

    }

    @Override
    public void meDie() {

    }

    @Override
    public void meDieExpDown() {

    }

    @Override
    public void upPearl(boolean isCoin, byte type, Item item) {

    }

    @Override
    public void npcChat(int npcId, String text) {

    }

    @Override
    public void pleaseInputParty(String name) {

    }

    @Override
    public void taskFinish() {

    }

    @Override
    public void tradeCancel() {

    }

    @Override
    public void tradeOk() {

    }

    @Override
    public void viewItemInfo(Trader trader, byte type, byte index) {

    }

    @Override
    public void tradeItemLock(Trader trader) {

    }

    @Override
    public void openUITrade(String name) {

    }

    @Override
    public void charInfo(Message ms, Char _char) {

    }

    @Override
    public Message messageSubCommand(int command) {
        return null;
    }

    @Override
    public Message messageNotMap(int command) {
        return null;
    }

    @Override
    public Message newMessage(int command) {
        return null;
    }

    @Override
    public Message messageNotLogin(int command) {
        return null;
    }

    @Override
    public void sendMessage(Message ms) {
    }

    @Override
    public void endWait() {

    }

    @Override
    public void sendItemToAuction(int index) {

    }

    @Override
    public void requestViewDetails(int id, Item item) {

    }

    @Override
    public void addExp(long exp) {

    }

    @Override
    public void addExpDown(long exp) {

    }

    @Override
    public void requestPlayers(ArrayList<Char> chars) {

    }

    @Override
    public void saleItem(int indexUI, int quantity) {

    }

    @Override
    public void addMonster(Mob mob) {

    }

    @Override
    public void testDunageonList() {

    }

    @Override
    public void testDungeonInvite(int id) {

    }

    @Override
    public void meLive() {

    }

    @Override
    public void tradeInvite(int id) {

    }

    @Override
    public void openUIShopTrungThu(List<Item> items, String title, String caption) {

    }

    @Override
    public void turnOffAuto() {

    }

    @Override
    public void turnOnAuto() {

    }

    @Override
    public void selectCard(Card[] results) {

    }

    @Override
    public void setChar(Char pl) {

    }

    @Override
    public void playerRemove(int id) {

    }

    @Override
    public void changePk(Char p) {

    }

    @Override
    public void addEffectAuto(byte id, short x, short y, byte loop, short time) {

    }

}
