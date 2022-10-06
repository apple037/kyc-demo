package com.wanda.kyc.utils;

import com.wanda.kyc.exception.Validator;
import com.wanda.kyc.exception.enumeration.SystemExceptionEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class IbatisUtil {
    /**
     * 轉換 Integer List 至 SQL in 的格式
     * transform list to sql [in] format
     * @param list 資料
     * @param varName 變數名稱
     * @return
     */
    public static String IntFormat(List<Integer> list, String varName){
        String res = "(";
        Validator.isFalseThrow(list.size() > 0, SystemExceptionEnum.PARAM_ERROR);
        for(int i = 0; i < list.size(); i++){
            if(null != list.get(i)) {
                res += "#{param." + varName + "[" + i + "]},";
            }
        }
        res = res.substring(0, res.length()-1) + ")";
        return res;
    }
    /**
     * 轉換 String List 至 SQL in 的格式
     * transform list to sql [in] format
     * @param list 資料
     * @param varName 變數名稱
     * @return
     */
    public static String StringFormat(List<String> list, String varName){
        String res = "(";
        Validator.isFalseThrow(list.size() > 0, SystemExceptionEnum.PARAM_ERROR);
        for(int i = 0; i < list.size(); i++){
            if(null != list.get(i)) {
                res += "#{param." + varName + "[" + i + "]},";
            }
        }
        res = res.substring(0, res.length()-1) + ")";
        return res;
    }
}
