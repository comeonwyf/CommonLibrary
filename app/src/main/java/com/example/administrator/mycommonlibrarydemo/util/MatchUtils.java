package com.example.administrator.mycommonlibrarydemo.util;

import android.text.TextUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @auther: 吴锐
 * @date: 2018-11-13 14:34
 * @describe: 匹配相关的
 */
public class MatchUtils {

    // 判断是否是手机号
    public static boolean isPhone(String text) {
        String pattern = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        //String pattern = "^1[3456789]\\d{9}$";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(text);
        return m.matches();
    }

    //正则替换手机号码的中间4位 13800138000 138****8000
    public static String place(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String regex = "(\\d{3})\\d{4}(\\d{4})";
        String replacement = "$1****$2";
        return str.replaceAll(regex, replacement);
    }

    /**
     * 18位身份证校验,粗略的校验
     *
     * @param idCard
     * @return
     * @author lyl
     */
    public static boolean is18ByteIdCard1(String idCard) {
        Pattern pattern = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)" +
                "?$"); //粗略的校验
        Matcher matcher = pattern.matcher(idCard);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 18位身份证校验,比较严格校验
     *
     * @param idCard
     * @return
     * @author lyl
     */
    public static boolean is18ByteIdCardComplex(String idCard) {
        Pattern pattern1 = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)" +
                "?$");
        Matcher matcher = pattern1.matcher(idCard);
        int[] prefix = new int[]{7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};
        int[] suffix = new int[]{1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2};
        if (matcher.matches()) {
            Map<String, String> cityMap = initCityMap();
            if (cityMap.get(idCard.substring(0, 2)) == null) {
                return false;
            }
            int idCardWiSum = 0; //用来保存前17位各自乖以加权因子后的总和
            for (int i = 0; i < 17; i++) {
                idCardWiSum += Integer.valueOf(idCard.substring(i, i + 1)) * prefix[i];
            }

            int idCardMod = idCardWiSum % 11;//计算出校验码所在数组的位置
            String idCardLast = idCard.substring(17);//得到最后一位身份证号码

            //如果等于2，则说明校验码是10，身份证号码最后一位应该是X
            if (idCardMod == 2) {
                if (idCardLast.equalsIgnoreCase("x")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                //用计算出的验证码与最后一位身份证号码匹配，如果一致，说明通过，否则是无效的身份证号码
                if (idCardLast.equals(suffix[idCardMod] + "")) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private static Map<String, String> initCityMap() {
        Map<String, String> cityMap = new HashMap<String, String>();
        cityMap.put("11", "北京");
        cityMap.put("12", "天津");
        cityMap.put("13", "河北");
        cityMap.put("14", "山西");
        cityMap.put("15", "内蒙古");

        cityMap.put("21", "辽宁");
        cityMap.put("22", "吉林");
        cityMap.put("23", "黑龙江");

        cityMap.put("31", "上海");
        cityMap.put("32", "江苏");
        cityMap.put("33", "浙江");
        cityMap.put("34", "安徽");
        cityMap.put("35", "福建");
        cityMap.put("36", "江西");
        cityMap.put("37", "山东");

        cityMap.put("41", "河南");
        cityMap.put("42", "湖北");
        cityMap.put("43", "湖南");
        cityMap.put("44", "广东");
        cityMap.put("45", "广西");
        cityMap.put("46", "海南");

        cityMap.put("50", "重庆");
        cityMap.put("51", "四川");
        cityMap.put("52", "贵州");
        cityMap.put("53", "云南");
        cityMap.put("54", "西藏");

        cityMap.put("61", "陕西");
        cityMap.put("62", "甘肃");
        cityMap.put("63", "青海");
        cityMap.put("64", "宁夏");
        cityMap.put("65", "新疆");

        //          cityMap.put("71", "台湾");
        //          cityMap.put("81", "香港");
        //          cityMap.put("82", "澳门");
        //          cityMap.put("91", "国外");
        //          System.out.println(cityMap.keySet().size());
        return cityMap;
    }


    /**
     * 判断字符串是否全部为中文字符组成
     *
     * @param str 检测的文字
     * @return true：为中文字符串，false:含有非中文字符
     */
    public static boolean isChineseStr(String str) {
        Pattern pattern = Pattern.compile("[\u4e00-\u9fa5]");
        char c[] = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            Matcher matcher = pattern.matcher(String.valueOf(c[i]));
            if (!matcher.matches()) {
                return false;
            }
        }
        return true;
    }

    public static boolean is18ByteIdCard(String idCard) {
        // 17 位加权因子
        int[] RATIO_ARR = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

        // 校验码列表
        char[] CHECK_CODE_LIST = {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

        int NUM_0 = '0';

        int ID_LENGTH = 17;
        if (TextUtils.isEmpty(idCard)) {
            return false;
        }
        idCard = idCard.trim();
        if (idCard.length() != 18) {
            return false;
        }
        // 获取身份证号字符数组
        char[] idCharArr = idCard.toCharArray();
        // 获取最后一位（身份证校验码）
        char verifyCode = idCharArr[ID_LENGTH];
        // 身份证号第1-17加权和
        int idSum = 0;
        // 余数
        int residue;

        for (int i = 0; i < ID_LENGTH; i++) {
            int value = idCharArr[i] - NUM_0;
            idSum += value * RATIO_ARR[i];
        }
        // 取得余数
        residue = idSum % 11;
        return Character.toUpperCase(verifyCode) == CHECK_CODE_LIST[residue];
    }

    /**
     * 获取加密的车牌号码
     *
     * @param plateNumber
     * @return
     */
    public static String getPlateNumber(String plateNumber) {
        if (plateNumber.length() < 3) {
            return plateNumber;
        }
        StringBuilder stringBuilder = new StringBuilder(plateNumber);
        stringBuilder.replace(2, 3, "*");
        return stringBuilder.toString();
    }
}
