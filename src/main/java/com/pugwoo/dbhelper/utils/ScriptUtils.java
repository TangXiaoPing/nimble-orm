package com.pugwoo.dbhelper.utils;

import com.pugwoo.dbhelper.exception.ScriptErrorException;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ScriptUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScriptUtils.class);

    /**
     * 执行mvel脚本，并设置到到对象t中
     * @param t 对象
     * @param field 字段
     * @param ignoreScriptError 是否忽略脚本出错
     * @param script mvel脚本
     */
    public static void setValueFromScript(Object t, Field field,
                                          Boolean ignoreScriptError, String script) {
        Map<String, Object> vars = new HashMap<String, Object>();
        vars.put("t", t);
        try {
            Object value = MVEL.eval(script, vars);
            DOInfoReader.setValue(field, t, value);
        } catch (Throwable e) {
            LOGGER.error("execute script fail: {}", script, e);
            if(!ignoreScriptError) {
                throw new ScriptErrorException(e);
            }
        }
    }


}
