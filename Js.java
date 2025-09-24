
package com.cocos.javascript;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.cocos.lib.CocosHelper;
import com.cocos.lib.CocosJavascriptJavaBridge;
import com.cocos.lib.GlobalObject;

public class Js {

    /**
     * 调用 Cocos 回调函数
     *
     * @param fName 函数名称
     * @param args 参数列表
     */
    public static void call(String fName, Object... args) {
        if (fName == null || fName.isEmpty()) {
            return;
        }

        StringBuilder str = new StringBuilder("native.callback('" + fName + "', ");

        for (Object o : args) {
            if (o instanceof String) {
                str.append("'").append(o).append("'");
            } else {
                str.append(o.toString());
            }

            str.append(",");
        }

        eval(str + "undefined)");
    }

    /**
     * 调用 Cocos 全局函数, 不经过 Native.callback
     *
     * @param fName 函数名称
     * @param args 参数列表
     */
    public static void callGlobal(String fName, Object... args) {
        if (fName == null || fName.isEmpty()) {
            return;
        }

        StringBuilder str = new StringBuilder(fName + "(");

        for (Object o : args) {
            if (o instanceof String) {
                str.append("'").append(o).append("' ");
            } else {
                str.append(o.toString());
            }

            str.append(", ");
        }

        eval(str + "undefined)");
    }

    public static void eval(final String str) {
        CocosHelper.runOnGameThread(
                () -> {
                    print(str);
                    CocosJavascriptJavaBridge.evalString(str);
                });
    }

    public static void print(String s) {
        Log.e("@.@", s);
    }

    private static Activity getCtx() {
        return GlobalObject.getActivity();
    }

    public static void tip(final String s) {
        GlobalObject.runOnUiThread(
                () -> {
                    print(s);
                    Toast.makeText(getCtx(), s, Toast.LENGTH_LONG).show();
                });
    }
}

