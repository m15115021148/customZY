package com.bluemobi.zhongyou.util;

import android.text.TextUtils;

import com.bluemobi.zhongyou.bean.PrinterBean;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ${chenM} on ${2017}.
 */
public class PrintContract {
    /**
     * 打印纸一行最大的字节
     */
    private static final int LINE_BYTE_SIZE = 32;

    /**
     * 打印三列时，中间一列的中心线距离打印纸左侧的距离
     */
    private static final int LEFT_LENGTH = 16;

    /**
     * 打印三列时，中间一列的中心线距离打印纸右侧的距离
     */
    private static final int RIGHT_LENGTH = 16;

    /**
     * 打印三列时，第一列汉字最多显示几个文字
     */
    private static final int LEFT_TEXT_MAX_LENGTH = 3;

    private static final int OFF_LINE = 10;

    /**
     * 打印内容
     */
    public static byte[] startPrinter(PrinterBean model) {

        StringBuilder builder = new StringBuilder();

        //设置大号字体以及加粗
        builder.append(PrintFormatUtils.getFontSizeCmd(PrintFormatUtils.FONT_MIDDLE));
        builder.append(PrintFormatUtils.getFontBoldCmd(PrintFormatUtils.FONT_BOLD));
        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_CENTER));

        // 标题
        builder.append(model.getmTitle());
        //换行，调用次数根据换行数来控制
        addLineSeparator(builder);
        addLineSeparator(builder);

        //设置普通字体大小、不加粗
        builder.append(PrintFormatUtils.getFontSizeCmd(PrintFormatUtils.FONT_NORMAL));
        builder.append(PrintFormatUtils.getFontBoldCmd(PrintFormatUtils.FONT_BOLD_CANCEL));
        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_LEFT));
        //划线
        addLine(builder);
        builder.append(printTwoData(model.getmOrderNumber(), String.valueOf(model.getOrderNumber())));
        addLineSeparator(builder);
        StringBuffer sb = new StringBuffer();
        sb.append(model.getmTime().charAt(0)).append(" ").append(" ").append(model.getmTime().charAt(1));
        builder.append(printTwoData(sb.toString(), String.valueOf(model.getTime())));
        addLineSeparator(builder);
        addLine(builder);

        builder.append(printFourData(model.getmType(), model.getmName(), model.getmNumber(), model.getmPrice()));
        addLineSeparator(builder);
        for (int i = 0; i < model.getData().size(); i++) {
            PrinterBean.Printers p = model.getData().get(i);
            builder.append(printFourData(p.getType(), p.getName(), String.valueOf(p.getNumber()), String.valueOf(p.getPrice())));
            addLineSeparator(builder);
        }
        addLine(builder);
        builder.append(printTwoData(model.getmTotalPrice(), String.valueOf(model.getTotalPrice())));
        addLineSeparator(builder);
        builder.append(printTwoData(model.getmOffer(), String.valueOf(model.getOffer())));
        addLineSeparator(builder);
        builder.append(printTwoData(model.getmActualAmount(), String.valueOf(model.getActualAmount())));
        addLineSeparator(builder);
        builder.append(printTwoData(model.getmPayment(), model.getPayment()));
        addLineSeparator(builder);
        addLine(builder);

        builder.append(PrintFormatUtils.getFontSizeCmd(PrintFormatUtils.FONT_MIDDLE));
        builder.append(PrintFormatUtils.getFontBoldCmd(PrintFormatUtils.FONT_BOLD));
        builder.append(model.getQrTitle());

        builder.append(PrintFormatUtils.getFontSizeCmd(PrintFormatUtils.FONT_NORMAL));
        builder.append(PrintFormatUtils.getFontBoldCmd(PrintFormatUtils.FONT_BOLD_CANCEL));
        builder.append(PrintFormatUtils.getAlignCmd(PrintFormatUtils.ALIGN_CENTER));
        addLineSeparator(builder);
        addLineSeparator(builder);
        builder.append(PrintFormatUtils.getQrCodeCmd(model.getQrContent()));
        addLineSeparator(builder);
        addLineSeparator(builder);
        addLine(builder);
        addLineSeparator(builder);
        addLineSeparator(builder);

        return PrintFormatUtils.stringToBytes(builder.toString());
    }

    private static void addLineSeparator(StringBuilder builder) {
        builder.append("\n");
    }

    private static void addLine(StringBuilder builder) {
        builder.append("--------------------------------" + "\n");
    }

    /**
     * 打印两列
     *
     * @param leftText  左侧文字
     * @param rightText 右侧文字
     * @return
     */
    public static String printTwoData(String leftText, String rightText) {
        StringBuilder sb = new StringBuilder();
        int leftTextLength = getBytesLength(leftText);
        int rightTextLength = getBytesLength(rightText);
        sb.append(leftText);

        // 计算两侧文字中间的空格
        int marginBetweenMiddleAndRight = LINE_BYTE_SIZE - leftTextLength - rightTextLength;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }
        sb.append(rightText);
        return sb.toString();
    }

    /**
     * 打印三列
     *
     * @param leftText   左侧文字
     * @param middleText 中间文字
     * @param rightText  右侧文字
     * @return
     */
    private static String printThreeData(String leftText, String middleText, String rightText) {
        StringBuilder sb = new StringBuilder();
        // 左边最多显示 LEFT_TEXT_MAX_LENGTH 个汉字 + 两个点
        if (leftText.length() > LEFT_TEXT_MAX_LENGTH) {
            leftText = leftText.substring(0, LEFT_TEXT_MAX_LENGTH);
        }
        int leftTextLength = getBytesLength(leftText);
        int middleTextLength = getBytesLength(middleText);
        int rightTextLength = getBytesLength(rightText);

        sb.append(leftText);
        // 计算左侧文字和中间文字的空格长度
        int marginBetweenLeftAndMiddle = LEFT_LENGTH - leftTextLength - middleTextLength / 2;

        for (int i = 0; i < marginBetweenLeftAndMiddle; i++) {
            sb.append(" ");
        }
        sb.append(middleText);

        // 计算右侧文字和中间文字的空格长度
        int marginBetweenMiddleAndRight = RIGHT_LENGTH - middleTextLength / 2 - rightTextLength;

        for (int i = 0; i < marginBetweenMiddleAndRight; i++) {
            sb.append(" ");
        }

        // 打印的时候发现，最右边的文字总是偏右一个字符，所以需要删除一个空格
        sb.delete(sb.length() - 1, sb.length()).append(rightText);
        return sb.toString();
    }

    /**
     * 打印四列
     */
    private static String printFourData(String one, String two, String three, String four) {
        StringBuilder sb = new StringBuilder();
        List<String> oneStrings = getSubString(one, 8);
        List<String> twoStrings = getSubString(two, 6);

        if (oneStrings != null && oneStrings.size() > 0) {
            one = oneStrings.get(0);
        }
        if (twoStrings != null && twoStrings.size() > 0) {
            two = twoStrings.get(0);
        }

        int oneLength = getBytesLength(one);
        int twoLength = getBytesLength(two);
        int threeLength = getBytesLength(three);
        int fourLength = getBytesLength(four);
        sb.append(one);
        int oneBetween = OFF_LINE - oneLength - twoLength / 2 + 2;
        for (int i = 0; i < oneBetween; i++) {
            sb.append(" ");
        }
        sb.append(two);

        int twoBetween = OFF_LINE - twoLength / 2 - threeLength / 2 - 2;
        for (int i = 0; i < twoBetween; i++) {
            sb.append(" ");
        }
        sb.append(three);

        int threeBetween = OFF_LINE - threeLength / 2 - fourLength;
        for (int i = 0; i < threeBetween; i++) {
            sb.append(" ");
        }
        sb.append(four);

        if (oneStrings != null && oneStrings.size() == 2 && twoStrings != null && twoStrings.size() == 2) {
            sb.append("\n");
            int len = oneBetween;
            if (TextUtils.isEmpty(oneStrings.get(1))){
                len = len+oneLength;
            }else {
                sb.append(oneStrings.get(1));
            }
            for (int i = 0; i < len; i++) {
                sb.append(" ");
            }
            sb.append(twoStrings.get(1));
        }
        if (oneStrings != null && oneStrings.size() == 1 && twoStrings != null && twoStrings.size() == 2) {
            sb.append("\n");
            for (int i = 0; i < oneLength + oneBetween; i++) {
                sb.append(" ");
            }
            sb.append(twoStrings.get(1));
        }

        return sb.toString();
    }

    /**
     * 获取数据长度
     *
     * @param msg
     * @return
     */
    private static int getBytesLength(String msg) {
        return msg.getBytes(Charset.forName("GB2312")).length;
    }

    private static List<String> getSubString(String string, int unitLength) {
        if (TextUtils.isEmpty(string)) {
            return null;
        }
        String str = string;
        int arraySize = 0;
        try {
            arraySize = str.getBytes("GBK").length / unitLength;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (str.getBytes().length % unitLength > 0) {
            arraySize++;
        }

        String[] result = new String[arraySize];


        for (int i = 0; i < arraySize; i++) {
            try {
                result[i] = subStr(str, unitLength);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            str = str.replace(result[i], "");
        }
        List<String> list = Arrays.asList(result);
        List<String> l = new ArrayList<>();
        for (String s:list){
            if(!TextUtils.isEmpty(s)){
                l.add(s);
            }
        }

        return l;
    }

    private static String subStr(String str, int subSLength) throws UnsupportedEncodingException {
        if (str == null)
            return "";
        else {
            int tempSubLength = subSLength;//截取字节数
            String subStr = str.substring(0, str.length() < subSLength ? str.length() : subSLength);//截取的子串
            int subStrByetsL = subStr.getBytes("GBK").length;//截取子串的字节长度
            // 说明截取的字符串中包含有汉字
            while (subStrByetsL > tempSubLength) {
                int subSLengthTemp = --subSLength;
                subStr = str.substring(0, subSLengthTemp > str.length() ? str.length() : subSLengthTemp);
                subStrByetsL = subStr.getBytes("GBK").length;
            }
            return subStr;
        }
    }

}
