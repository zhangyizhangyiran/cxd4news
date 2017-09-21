package com.cxd.cxd4android.widget.share;

import android.content.Context;
import android.text.ClipboardManager;
import android.widget.Toast;

/**
 * @version V1.0
 * @ClassName:fu复制剪切类
 * @Description:
 * @Author：xiaofa
 * @Date：2016/5/4 11:43
 */
public class CopyUtils {

    /**
     * 实现文本复制功能
     * add by wangqianzhou
     *
     * @param content
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        cmb.setText(content.trim());
        Toast.makeText(context, "已复制到剪贴板", Toast.LENGTH_SHORT).show();

    }
}
