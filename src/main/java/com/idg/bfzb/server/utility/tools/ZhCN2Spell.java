package com.idg.bfzb.server.utility.tools;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;

/**
 * 汉字转换成拼音工具类
 */
@SuppressWarnings("unused")
public class ZhCN2Spell {
    //拼音中的u:转成v
    private static final String UToV="u:";

    /**
     * 生成汉字的简拼即只有声母
     * @param chines 汉字字符串
     * @return 汉字简拼
     */
    public static String converterToFirstSpell(String chines){
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
            	if(!ValidatorUtil.isContainsChinese(String.valueOf(nameChar[i])))continue;
                try {
                    String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    if (pinyins != null) {
                        pinyinName += pinyins[0].charAt(0);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                pinyinName += nameChar[i];
            }
        }
        return pinyinName;
    }


    /**
     * 生成汉字全拼
     * @param chines 汉字内容
     * @return 汉字全拼
     */
    public static String converterToSpell(String chines){
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < nameChar.length; i++) {
            if (nameChar[i] > 128) {
            	if(!ValidatorUtil.isContainsChinese(String.valueOf(nameChar[i])))continue;
                try {
                    String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat);
                    if(pinyins != null){
                        pinyinName += pinyins[0];
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            }else{
                pinyinName += nameChar[i];
            }
        }
        if(StringUtils.isNotBlank(pinyinName) && pinyinName.contains(UToV)){
            pinyinName = pinyinName.replaceAll(UToV,"v");
        }
        return pinyinName;
    }

}
