package com.taxidriver.santos.utils.pinyin;

import android.text.TextUtils;



import java.util.ArrayList;

import com.taxidriver.santos.utils.pinyin.HanziToPinyin;
import com.taxidriver.santos.utils.pinyin.HanziToPinyin.Token;

public class PinYin {
	//汉字返回拼音，字母原样返回，都转换为小写
	private static String getPinYin(String input) {
		ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance().get(input);
		StringBuilder sb = new StringBuilder();
		if (tokens != null && tokens.size() > 0) {
			for (Token token : tokens) {
				if (Token.PINYIN == token.type) {
					sb.append(token.target);
				} else {
					sb.append(token.source);
				}
			}
		}
		return sb.toString().toLowerCase();
	}
	
    public static String getFirstPinYin(String inputString) {
        if (inputString == null) {
            return "#";
        }

        int nLength = inputString.length();
        if (nLength == 0) {
            return "#";
        }

        StringBuilder sb = new StringBuilder();
        try {
            ArrayList<Token> tokens = HanziToPinyin.getInstance().get(inputString);
            if (tokens != null && tokens.size() > 0) {
                for (Token token : tokens) {
                    if (Token.PINYIN == token.type) {
                        String target = token.target;
                        if (!TextUtils.isEmpty(target)) {
                            sb.append(token.target.charAt(0));
                        } else {
                            sb.append(token.source);
                        }
                    } else {
                        sb.append(token.source);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (sb.length() == 0) {
            sb.append('#');
        }

        return sb.toString().toUpperCase();
    }
}
