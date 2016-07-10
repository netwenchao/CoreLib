package cn.dragon2.common.util;

import android.graphics.Color;

public class ColorUtlis {
	public static String GetWebFormat(int color){
		int red=(color & 0xFF0000)>>16;
		int green=(color & 0x00FF00)>>8;
		int blue=(color & 0x0000FF);
		return "#"+StringUtils.paddLeft(Integer.toHexString(red),"0",2)+
				StringUtils.paddLeft(Integer.toHexString(green),"0",2)+
				StringUtils.paddLeft(Integer.toHexString(blue),"0",2);
	}
}
