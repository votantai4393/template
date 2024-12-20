/*
 * To change owner license header, choose License Headers in Project Properties.
 * To change owner template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tea.ability;

import com.tea.item.ItemManager;
import com.tea.item.Equip;
import com.tea.item.Item;
import com.tea.item.ItemTemplate;
import com.tea.item.Mount;
import com.tea.model.Bijuu;
import com.tea.model.Char;
import com.tea.skill.Skill;
import com.tea.option.ItemOption;
import com.tea.option.SkillOption;
import com.tea.server.GameData;

/**
 *
 * @author Admin
 */
public class AbilityFromEquip implements AbilityStrategy {

    @Override
    public void setAbility(Char owner) {
        int length = ItemManager.getInstance().getOptionSize();
        owner.options = new int[length];
        owner.haveOptions = new boolean[length];
        length = GameData.getInstance().getOptionTemplates().size();
        owner.optionsSupportSkill = new int[length];
        for (Equip equip : owner.equipment) {
            if (equip != null) {
                if (!equip.isExpired()) {
                    for (ItemOption o : equip.getOptions()) {
                        int optionID = o.optionTemplate.id;
                        if (o.optionTemplate.type >= 3 && o.optionTemplate.type <= 7) {
                            if ((o.optionTemplate.type == 3 && equip.upgrade >= 4)
                                    || (o.optionTemplate.type == 4 && equip.upgrade >= 8)
                                    || (o.optionTemplate.type == 5 && equip.upgrade >= 12)
                                    || (o.optionTemplate.type == 6 && equip.upgrade >= 14)
                                    || (o.optionTemplate.type == 7 && equip.upgrade >= 16)) {
                                owner.options[optionID] += o.param;
                                owner.haveOptions[optionID] = true;
                            }
                        } else {
                            owner.options[optionID] += o.param;
                            owner.haveOptions[optionID] = true;
                        }
                    }
                }

            }
        }
        for (Equip equip : owner.fashion) {
            if (equip != null) {
                if (!equip.isExpired()) {
                    for (ItemOption o : equip.getOptions()) {
                        int optionID = o.optionTemplate.id;
                        if (o.optionTemplate.type >= 3 && o.optionTemplate.type <= 7) {
                            if ((o.optionTemplate.type == 3 && equip.upgrade >= 4)
                                    || (o.optionTemplate.type == 4 && equip.upgrade >= 8)
                                    || (o.optionTemplate.type == 5 && equip.upgrade >= 12)
                                    || (o.optionTemplate.type == 6 && equip.upgrade >= 14)
                                    || (o.optionTemplate.type == 7 && equip.upgrade >= 16)) {
                                owner.options[optionID] += o.param;
                                owner.haveOptions[optionID] = true;
                            }
                        } else {
                            owner.options[optionID] += o.param;
                            owner.haveOptions[optionID] = true;
                        }
                    }
                }

            }
        }
        if (owner.mount[4] != null && (!owner.mount[4].isExpired())) {
            for (Mount mount : owner.mount) {
                if (mount != null) {
                    for (ItemOption o : mount.getOptions()) {
                        int optionID = o.optionTemplate.id;
                        owner.options[optionID] += o.param;
                        owner.haveOptions[optionID] = true;
                    }
                }
            }
        }
        if (owner.bijuu[4] != null && (!owner.bijuu[4].isExpired())) {
            for (Item bijuu : owner.bijuu) {
                if (bijuu != null) {
                    for (ItemOption o : bijuu.getOptions()) {
                        int optionID = o.optionTemplate.id;
                        owner.options[optionID] += o.param;
                        owner.haveOptions[optionID] = true;
                    }
                }
            }
        }

        Item mask = owner.getMask();
        if (mask != null) {
            for (ItemOption o : mask.getOptions()) {
                int optionID = o.optionTemplate.id;
                owner.options[optionID] += o.param;
                owner.haveOptions[optionID] = true;
            }
        }
        Skill selectedSkill = owner.selectedSkill;
        int skillOptions[] = new int[length];
        if (selectedSkill != null) {
            for (SkillOption option : selectedSkill.options) {
                int optionId = option.optionTemplate.id;
                int param = option.param;
                skillOptions[optionId] += param;
            }
        }
        for (Skill skill : owner.vSupportSkill) {
            for (SkillOption option : skill.options) {
                owner.optionsSupportSkill[option.optionTemplate.id] += option.param;
            }
        }
        int[] skillOptions2 = new int[length];
        for (Skill skill : owner.vSkillFight) {
            if (skill.template.id == Skill.SKILL_CLICK_USE_ATTACK) {
                for (SkillOption option : skill.options) {
                    skillOptions2[option.optionTemplate.id] += option.param;
                }
            }
        }
        int basicHp = 0;
        int basicMp = 0;
        int potentialDame = 0;
        boolean isSkill40 = false;
        boolean isSkill30 = false;
        boolean isSkill50 = false;
        if (selectedSkill != null) {
            int level = selectedSkill.template.skills.get(0).level;
            isSkill30 = level == 30;
            isSkill40 = level == 40;
            isSkill50 = level == 50;
        }
        owner.exactly = owner.potential[1] + (owner.potential[1] * owner.options[58] / 100) + owner.options[145] + owner.options[57] + owner.options[12];
        owner.miss = (owner.potential[1] + owner.options[57] + (owner.potential[1] * owner.options[58] / 100)) * 150 / 100;
        switch (owner.getSideClass()) {
            case 0:
                potentialDame = ((owner.potential[0] + owner.options[57] + owner.options[144]) + (owner.potential[0] * owner.options[58] / 100));
                owner.basicAttack = potentialDame + owner.options[38] + (potentialDame * skillOptions[11] / 100);
                if (owner.incrDame > 0) {
                    owner.basicAttack += potentialDame * owner.incrDame / 100;
                }
                owner.damage = (owner.basicAttack + owner.options[0] + owner.options[21] + owner.options[23] + owner.options[25])
                        + (potentialDame * owner.options[8] / 100) + potentialDame;
                break;

            case 1:
                potentialDame = (owner.potential[3] + owner.options[57] + owner.options[146] + (owner.potential[3] * owner.options[58] / 100));
                owner.basicAttack = potentialDame + owner.options[38] + (potentialDame * skillOptions[11] / 100);
                if (owner.incrDame > 0) {
                    owner.basicAttack += potentialDame * owner.incrDame / 100;
                }
                if (owner.incrDame2 > 0) {
                    owner.basicAttack += potentialDame * owner.incrDame2 / 100;
                }
                owner.damage = (owner.basicAttack + owner.options[1] + owner.options[22] + owner.options[24] + owner.options[26])
                        + (potentialDame * owner.options[9] / 100) + potentialDame;
                break;
        }
        if (owner.incrDame3 > 0) {
            owner.damage += potentialDame * owner.incrDame3 / 100;
        }
        owner.damage += owner.options[73] + owner.options[87] + owner.options[168]+ owner.options[188] + owner.options[76];
        owner.damage += ((potentialDame * 4) *  owner.options[94] / 100);
        owner.damage += ((potentialDame * 4) * owner.options[182] / 100);
        owner.damage += owner.optionsSupportSkill[8] + owner.optionsSupportSkill[6] + owner.optionsSupportSkill[9] + owner.optionsSupportSkill[7]
                + owner.optionsSupportSkill[1];
        owner.damage += skillOptions[2] + skillOptions[3] + skillOptions[4] + skillOptions[5] + skillOptions[6]
                + skillOptions[7] + skillOptions[8] + skillOptions[9] + skillOptions[10];
        if (isSkill30) {
            owner.damage += owner.basicAttack * skillOptions2[58] / 100;
        }
        if (isSkill40) {
            owner.damage += owner.basicAttack * skillOptions2[59] / 100;
        }
        if (isSkill50) {
            owner.damage += owner.basicAttack * skillOptions2[60] / 100;
        }
        switch (owner.getSys()) {
            case 1:
                owner.damage += owner.options[88] + owner.options[189];
                break;

            case 2:
                owner.damage += owner.options[89] + owner.options[190];
                break;

            case 3:
                owner.damage += owner.options[90] + owner.options[191] ;
                break;
        }
        owner.damage2 = owner.damage - owner.damage / 10;
        basicHp = (owner.potential[2] + owner.options[57] + owner.options[147] + (owner.potential[2] * owner.options[58] / 100)) * 10;
        owner.maxHP = basicHp + (basicHp * (owner.options[31] + owner.options[61] + owner.optionsSupportSkill[17]) / 100);
        owner.maxHP += owner.options[6];
        owner.maxHP += owner.options[32];
        owner.maxHP += owner.options[77];
        owner.maxHP += owner.options[82];
        owner.maxHP += owner.options[125];
        owner.maxHP += owner.options[158];
        owner.maxHP += owner.options[184];
       
        owner.maxHP += (owner.maxHP * owner.options[128] / 100); // tăng % hp sau khi mặc đồ
        owner.maxHP += (owner.maxHP * owner.options[160] / 100); // tăng % hp sau khi mặc đồ
        if (issethacam(owner)|| issetlucbao(owner)) {
            owner.maxHP += 10000;
        }
        owner.maxHP += owner.incrHP;
        if (owner.maxHP <= 0) {
            owner.maxHP = 50;
        }
        if (owner.hp > owner.maxHP) {
            owner.hp = owner.maxHP;
        }
        basicMp = (owner.potential[3] + owner.options[57] + owner.options[146] + (owner.potential[3] * owner.options[58] / 100)) * 10;
        owner.maxMP = basicMp + (basicMp + (owner.options[28] + owner.options[60] + owner.optionsSupportSkill[18]) / 100);
        owner.maxMP += owner.options[7];
        owner.maxMP += owner.options[19];
        owner.maxMP += owner.options[29];
        owner.maxMP += owner.options[83];
        owner.maxMP += owner.options[117];
        owner.maxMP += owner.options[159];
        owner.maxMP += owner.options[185];
        if (issethacam(owner)|| issetlucbao(owner)) {
            owner.maxMP += 10000;
        }
        if (owner.maxMP <= 0) {
            owner.maxMP = 50;
        }
        if (owner.mp > owner.maxMP) {
            owner.mp = owner.maxMP;
        }
        owner.dameDown = owner.options[47] + owner.options[74] + owner.options[80] + owner.options[124];
        owner.miss += owner.options[5] + owner.options[17] + owner.options[62] + owner.options[68] + owner.options[78] + owner.options[84] + owner.options[115] + owner.options[162]
                + owner.options[186] + owner.optionsSupportSkill[13];
        owner.exactly += owner.options[10] + owner.options[18] + owner.options[166] + owner.options[75] + owner.options[86] + owner.options[116]+ owner.options[183] + owner.optionsSupportSkill[12];
        owner.resFire = owner.options[2] + owner.options[11] + owner.options[33] + owner.options[70] + owner.options[96] + owner.options[163] + owner.optionsSupportSkill[19]
                + owner.optionsSupportSkill[20] + owner.options[36] + owner.options[118] + owner.options[178];
        owner.resIce = owner.options[3] + owner.options[12] + owner.options[34] + owner.options[71] + owner.options[95] + owner.options[164] + owner.optionsSupportSkill[19]
                + owner.optionsSupportSkill[21] + owner.options[36] + owner.options[118] + owner.options[178];
        owner.resWind = owner.options[4] + owner.options[13] + owner.options[35] + owner.options[72] + owner.options[97] + owner.options[165] + owner.optionsSupportSkill[19]
                + owner.optionsSupportSkill[22] + owner.options[36] + owner.options[118] + owner.options[178];
        owner.fatal = owner.options[14] + owner.options[37] + owner.options[69] + owner.options[167]+ owner.options[187] + owner.options[92] + owner.options[114] + owner.optionsSupportSkill[14];
        owner.fatalDame = owner.options[105]; 
        owner.percentFatalDame = owner.options[39] + owner.options[67];
        owner.reactDame = owner.options[15] + owner.options[91] + owner.options[126]+ owner.options[181] + owner.optionsSupportSkill[15];
        owner.sysDown = 0;
        owner.sysUp = owner.optionsSupportSkill[33];

        owner.miss += owner.incrMiss;
        owner.exactly += owner.incrExactly;

        owner.resFire += owner.incrRes1;
        owner.resIce += owner.incrRes1;
        owner.resWind += owner.incrRes1;
        owner.resFire += owner.incrRes2;
        owner.resIce += owner.incrRes2;
        owner.resWind += owner.incrRes2;
        owner.speed = 7;
        if (owner.mount[4] != null) {
            owner.speed += 2;
        }
        owner.speed += owner.options[93];
        
         if (issethacam(owner)) {
            owner.resWind += 3000;
            owner.resIce += 3000;
            owner.resFire += 3000;
            owner.miss += 3000;
        }
         if (issethuyet(owner)) {
            owner.damage += 10000;
            owner.exactly += 3000;
            owner.fatal += 300;
            owner.reactDame += 300;
            owner.percentFatalDame +=70;
            
        }
         if (issetlucbao(owner)) {
            owner.resWind += 3000;
            owner.resIce += 3000;
            owner.resFire += 3000;
            owner.miss += 3000;
            owner.damage += 10000;
            owner.exactly += 3000;
            owner.fatal += 300;
            owner.reactDame += 300;
            owner.percentFatalDame +=70;
            
        }
    }
    
    public boolean issethacam(Char owner) {
    return (owner.equipment[ItemTemplate.TYPE_NON] != null && owner.equipment[ItemTemplate.TYPE_NON].id == 1227) &&
           (owner.equipment[ItemTemplate.TYPE_AO] != null && owner.equipment[ItemTemplate.TYPE_AO].id == 1228) &&
           (owner.equipment[ItemTemplate.TYPE_GANGTAY] != null && owner.equipment[ItemTemplate.TYPE_GANGTAY].id == 1229) &&
           (owner.equipment[ItemTemplate.TYPE_QUAN] != null && owner.equipment[ItemTemplate.TYPE_QUAN].id == 1230) &&
           (owner.equipment[ItemTemplate.TYPE_GIAY] != null && owner.equipment[ItemTemplate.TYPE_GIAY].id == 1231) &&
           (owner.equipment[ItemTemplate.TYPE_PHU] != null && owner.equipment[ItemTemplate.TYPE_PHU].id == 1232) &&
           (owner.equipment[ItemTemplate.TYPE_NGOCBOI] != null && owner.equipment[ItemTemplate.TYPE_NGOCBOI].id == 1233) &&
           (owner.equipment[ItemTemplate.TYPE_NHAN] != null && owner.equipment[ItemTemplate.TYPE_NHAN].id == 1234) &&
           (owner.equipment[ItemTemplate.TYPE_LIEN] != null && owner.equipment[ItemTemplate.TYPE_LIEN].id == 1235);
}
    public boolean issethuyet(Char owner) {
    return (owner.equipment[ItemTemplate.TYPE_NON] != null && owner.equipment[ItemTemplate.TYPE_NON].id == 1236) &&
           (owner.equipment[ItemTemplate.TYPE_AO] != null && owner.equipment[ItemTemplate.TYPE_AO].id == 1237) &&
           (owner.equipment[ItemTemplate.TYPE_GANGTAY] != null && owner.equipment[ItemTemplate.TYPE_GANGTAY].id == 1238) &&
           (owner.equipment[ItemTemplate.TYPE_QUAN] != null && owner.equipment[ItemTemplate.TYPE_QUAN].id == 1239) &&
           (owner.equipment[ItemTemplate.TYPE_GIAY] != null && owner.equipment[ItemTemplate.TYPE_GIAY].id == 1240) &&
           (owner.equipment[ItemTemplate.TYPE_PHU] != null && owner.equipment[ItemTemplate.TYPE_PHU].id == 1241) &&
           (owner.equipment[ItemTemplate.TYPE_NGOCBOI] != null && owner.equipment[ItemTemplate.TYPE_NGOCBOI].id == 1242) &&
           (owner.equipment[ItemTemplate.TYPE_NHAN] != null && owner.equipment[ItemTemplate.TYPE_NHAN].id == 1243) &&
           (owner.equipment[ItemTemplate.TYPE_LIEN] != null && owner.equipment[ItemTemplate.TYPE_LIEN].id == 1244);
}
    public boolean issetlucbao(Char owner) {
    return (owner.equipment[ItemTemplate.TYPE_NON] != null && owner.equipment[ItemTemplate.TYPE_NON].id == 1245) &&
           (owner.equipment[ItemTemplate.TYPE_AO] != null && owner.equipment[ItemTemplate.TYPE_AO].id == 1246) &&
           (owner.equipment[ItemTemplate.TYPE_GANGTAY] != null && owner.equipment[ItemTemplate.TYPE_GANGTAY].id == 1247) &&
           (owner.equipment[ItemTemplate.TYPE_QUAN] != null && owner.equipment[ItemTemplate.TYPE_QUAN].id == 1248) &&
           (owner.equipment[ItemTemplate.TYPE_GIAY] != null && owner.equipment[ItemTemplate.TYPE_GIAY].id == 1249) &&
           (owner.equipment[ItemTemplate.TYPE_PHU] != null && owner.equipment[ItemTemplate.TYPE_PHU].id == 1250) &&
           (owner.equipment[ItemTemplate.TYPE_NGOCBOI] != null && owner.equipment[ItemTemplate.TYPE_NGOCBOI].id == 1251) &&
           (owner.equipment[ItemTemplate.TYPE_NHAN] != null && owner.equipment[ItemTemplate.TYPE_NHAN].id == 1252) &&
           (owner.equipment[ItemTemplate.TYPE_LIEN] != null && owner.equipment[ItemTemplate.TYPE_LIEN].id == 1253);
}

}
