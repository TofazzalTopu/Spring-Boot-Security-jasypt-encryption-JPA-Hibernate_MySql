package com.info.prescription.utility;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class StringUtil {

	public static String[] getStrArrayBySpit(String srcStr, String delimiters) {
		String[] result = null;
		int index = 0;
		if (srcStr != null) {
			StringTokenizer stk = new StringTokenizer(srcStr, delimiters);
			if (stk.hasMoreTokens()) {
				result = new String[stk.countTokens()];
			}

			while (stk.hasMoreTokens()) {
				result[index] = stk.nextToken();
				++index;
			}
		}

		return result;
	}

	public static final String replace(String line, String oldString, String newString) {
		if (line == null) {
			return null;
		} else {
			byte i = 0;
			int i1 = line.indexOf(oldString, i);
			if (i1 < 0) {
				return line;
			} else {
				char[] line2 = line.toCharArray();
				char[] newString2 = newString.toCharArray();
				int oLength = oldString.length();
				StringBuffer buf = new StringBuffer(line2.length);
				buf.append(line2, 0, i1).append(newString2);
				i1 += oLength;

				int j;
				for (j = i1; (i1 = line.indexOf(oldString, i1)) > 0; j = i1) {
					buf.append(line2, j, i1 - j).append(newString2);
					i1 += oLength;
				}

				buf.append(line2, j, line2.length - j);
				return buf.toString();
			}
		}
	}

	public static final boolean isEmptyString(String s) {
		return s == null || s.trim().equals("") || s.equals("null") || s.equals("NULL") || s.trim().equals("undefined");
	}

	public static byte[] getUTF8Bytes(String src) {
		try {
			return src.getBytes("UTF-8");
		} catch (UnsupportedEncodingException var2) {
			return new byte[0];
		}
	}

	public static String escape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);

		for (int i = 0; i < src.length(); ++i) {
			char j = src.charAt(i);
			if (!Character.isDigit(j) && !Character.isLowerCase(j) && !Character.isUpperCase(j)) {
				if (j < 256) {
					tmp.append("%");
					if (j < 16) {
						tmp.append("0");
					}

					tmp.append(Integer.toString(j, 16));
				} else {
					tmp.append("%u");
					tmp.append(Integer.toString(j, 16));
				}
			} else {
				tmp.append(j);
			}
		}

		return tmp.toString();
	}

	public static String unescape(String src) {
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length());
		int lastPos = 0;
		boolean pos = false;

		while (lastPos < src.length()) {
			int pos1 = src.indexOf("%", lastPos);
			if (pos1 == lastPos) {
				char ch;
				if (src.charAt(pos1 + 1) == 117) {
					ch = (char) Integer.parseInt(src.substring(pos1 + 2, pos1 + 6), 16);
					tmp.append(ch);
					lastPos = pos1 + 6;
				} else {
					ch = (char) Integer.parseInt(src.substring(pos1 + 1, pos1 + 3), 16);
					tmp.append(ch);
					lastPos = pos1 + 3;
				}
			} else if (pos1 == -1) {
				tmp.append(src.substring(lastPos));
				lastPos = src.length();
			} else {
				tmp.append(src.substring(lastPos, pos1));
				lastPos = pos1;
			}
		}

		return tmp.toString();
	}

	public static String doubleFormat(double number, int decimalDigits) {
		if (number == 0.0D) {
			number = 0.0D;
		}

		boolean flag = false;
		if (decimalDigits < 0) {
			return "";
		} else {
			String pattern = "##################";
			if (decimalDigits > 0) {
				flag = true;
				pattern = pattern + ".";

				for (int df = 0; df < decimalDigits; ++df) {
					pattern = pattern + "0";
				}
			}

			DecimalFormat var6 = new DecimalFormat(pattern);
			return number <= -1.0D ? var6.format(number)
					: (number > -1.0D && number < 0.0D ? "-0" + var6.format(number).substring(1)
							: (number >= 0.0D && number < 1.0D
									? (flag ? "0" + var6.format(number) : var6.format(number))
									: var6.format(number)));
		}
	}

	public static byte[] zipString(String s) {
		if (s == null) {
			return null;
		} else {
			BufferedInputStream in = null;
			ByteArrayOutputStream baos = null;
			BufferedOutputStream out = null;

			Object buffer;
			try {
				byte[] e = s.getBytes("GBK");
				in = new BufferedInputStream(new ByteArrayInputStream(e));
				baos = new ByteArrayOutputStream();
				out = new BufferedOutputStream(new GZIPOutputStream(baos));
				byte[] buffer1 = new byte[1024];
				boolean e1 = false;

				int e2;
				while ((e2 = in.read(buffer1)) != -1) {
					out.write(buffer1, 0, e2);
				}

				return baos != null ? baos.toByteArray() : null;
			} catch (Exception var19) {
				var19.printStackTrace();
				buffer = null;
			} finally {
				try {
					in.close();
				} catch (IOException var18) {
					var18.printStackTrace();
				}

				try {
					out.close();
				} catch (IOException var17) {
					var17.printStackTrace();
				}

			}

			return (byte[]) buffer;
		}
	}

	public static String unzipString(byte[] bytes) {
		if (bytes != null && bytes.length != 0) {
			BufferedInputStream in = null;
			ByteArrayOutputStream baos = null;
			BufferedOutputStream out = null;

			label124: {
				Object len;
				try {
					in = new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(bytes)));
					baos = new ByteArrayOutputStream();
					out = new BufferedOutputStream(baos);
					byte[] e = new byte[1024];
					boolean len1 = false;

					int len2;
					while ((len2 = in.read(e)) != -1) {
						out.write(e, 0, len2);
					}

					bytes = baos.toByteArray();
					break label124;
				} catch (Exception var21) {
					var21.printStackTrace();
					len = null;
				} finally {
					try {
						in.close();
					} catch (IOException var19) {
						var19.printStackTrace();
					}

					try {
						out.close();
					} catch (IOException var18) {
						var18.printStackTrace();
					}

				}

				return (String) len;
			}

			if (bytes != null) {
				try {
					return new String(bytes, "GBK");
				} catch (UnsupportedEncodingException var20) {
					var20.printStackTrace();
					return null;
				}
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	public static String stringToHtmlFormat(String s) {
		if (s == null) {
			return null;
		} else {
			String temp = replace(s, "\n", "<br>");
			temp = replace(temp, "\t", "&nbsp;&nbsp;&nbsp;&nbsp;");
			return replace(temp, " ", "&nbsp;");
		}
	}

	public static String leftTrimZero(String str) {
		int len = str.length();
		int st = 0;

		for (char[] val = str.toCharArray(); st < len && val[st] == 48; ++st) {
			;
		}

		return st < len + 1 ? str.substring(st) : str;
	}

	public static String leftTrimZeroS(String str) {
		if (!"0".equals(str) && !isEmptyString(str)) {
			String result = leftTrimZero(str);
			if (result.length() == 0 || result.charAt(0) == 46) {
				result = "0" + result;
			}

			return result;
		} else {
			return str;
		}
	}

	public static String getSubStrByToken(String srcStr, String headToken, String tailToken) {
		String result = "";
		int startPos = srcStr.indexOf(headToken);
		int endPos = srcStr.indexOf(tailToken);
		if (startPos >= 0 && endPos > 0 && startPos < endPos) {
			result = srcStr.substring(startPos + headToken.length(), endPos);
		}

		return result;
	}

	public static String iPToHexStr(String ip) {
		String hexStr = null;
		String[] ips = getStrArrayBySpit(ip, ".");

		for (int i = 0; i < ips.length; ++i) {
			hexStr = hexStr.concat(str2Hex(ips[i]));
		}

		return hexStr;
	}

	public static String hexStrToIP(String hexStr) {
		String ip = null;
		String subIP = "";
		if (hexStr.length() == 8) {
			for (int i = 0; i < 4; ++i) {
				subIP = hexStr.substring(i * 2, (i + 1) * 2);
				if (i == 0) {
					ip = String.valueOf(Integer.parseInt(subIP, 16));
				} else {
					ip = ip + "." + hex2Str(subIP);
				}
			}
		}

		return ip;
	}

	public static boolean isSub(String str, String[] aStr) {
		boolean isSub = false;
		if (aStr != null) {
			for (int i = 0; i < aStr.length; ++i) {
				if (str.equalsIgnoreCase(aStr[i])) {
					isSub = true;
					break;
				}
			}
		}

		return isSub;
	}

	public static String array2String(String[] strArray) {
		String result = "";
		if (strArray != null && strArray.length > 0) {
			for (int i = 0; i < strArray.length; ++i) {
				if (i > 0) {
					result = result + "," + strArray[i];
				} else {
					result = strArray[i];
				}
			}
		}

		return result;
	}

	public static String quoted(String s) {
		return s != null ? "\'" + s + "\'" : null;
	}

	public static String randomNumber(int scale) {
		int max = (new Double(Math.pow(10.0D, (double) scale))).intValue();
		int min = (new Double(Math.pow(10.0D, (double) (scale - 1)))).intValue();
		Random r = new Random();

		int result;
		do {
			result = r.nextInt(max) % max;
		} while (result <= min);

		return String.valueOf(result);
	}

	public static String removeSubstring(String main, String sub) {
		int pos = main.indexOf(sub);
		return pos != -1 ? main.substring(0, pos) + main.substring(pos + sub.length()) : main;
	}

	public static String getValue(Object obj) {
		return obj == null ? ""
				: (obj instanceof BigDecimal ? String.valueOf(obj.toString())
						: (obj instanceof Integer ? String.valueOf(obj.toString())
								: (obj instanceof String ? (isEmptyString(obj.toString()) ? "" : obj.toString())
										: String.valueOf(obj))));
	}

	public static String getValue(Object obj, boolean isNumber) {
		String res = getValue(obj);
		return res != null ? res.replaceAll(",", "") : res;
	}

	public static String hex2Str(String hexStr) {
		String str = null;
		str = String.valueOf(Integer.parseInt(hexStr, 16));
		return str;
	}

	public static String str2Hex(String str) {
		String hexStr = "";
		hexStr = Integer.toHexString(Integer.parseInt(str));
		if (hexStr.length() < 2) {
			hexStr = "0" + hexStr;
		}

		return hexStr;
	}

	public static String bin2Hex(String str, int len) {
		long i = Long.valueOf(str, 2).longValue();
		return leftZero(Long.toHexString(i).toUpperCase(), len);
	}

	public static String hex2Bin(String hex) {
		long i = Long.valueOf(hex, 16).longValue();
		return Long.toBinaryString(i);
	}

	public static String leftZero(String instr, int len) {
		String str = "";
		int j;
		if (isEmptyString(instr)) {
			for (j = 0; j < len; ++j) {
				str = "0" + str;
			}

			return str;
		} else {
			if (instr.length() < len) {
				for (j = instr.length(); j < len; ++j) {
					instr = "0" + instr;
				}
			}

			return instr;
		}
	}

	public static String rightZero(String instr, int len) {
		String str = "";
		int j;
		if (isEmptyString(instr)) {
			for (j = 0; j < len; ++j) {
				str = str + "0";
			}

			return str;
		} else {
			if (instr.length() < len) {
				for (j = instr.length(); j < len; ++j) {
					instr = instr + "0";
				}
			}

			return instr;
		}
	}

	public static String bigDecimal2String(BigDecimal bg) {
		String result = "";
		if (bg != null) {
			result = bg.toString();
		}

		return result;
	}

	public static String binStrToStr(String str, int start, int end) {
		return Integer.toString(Integer.parseInt(str.substring(start, end), 2));
	}

	public static String strToBinStr(String instr, int len) {
		String str = "";
		int i;
		if (isEmptyString(instr)) {
			for (i = 0; i < len; ++i) {
				str = "0" + str;
			}

			return str;
		} else {
			i = Integer.parseInt(instr);
			str = Integer.toBinaryString(i);
			if (str.length() < len) {
				for (int j = str.length(); j < len; ++j) {
					str = "0" + str;
				}
			}

			return str;
		}
	}

	public static String convertStr(String str) {
		String res = "";

		for (int i = str.length() - 1; i > -1; --i) {
			res = res + str.charAt(i);
		}

		return res;
	}

	public static String convertObisToHex(String classId, String obis, String attr) {
		String res = "";
		String[] obisItems = obis.split("\\.");
		res = res + "00" + str2Hex(classId);

		for (int i = 0; i < obisItems.length; ++i) {
			res = res + str2Hex(obisItems[i]);
		}

		res = res + str2Hex(attr);
		return res;
	}

	public static String[] strDateToGrid(String[] zdjhs, int count, int size) {
		ArrayList result = new ArrayList();
		int counts = 0;

		for (int newString = count * size; newString < zdjhs.length; ++newString) {
			++counts;
			if (counts > size) {
				break;
			}

			result.add(0, zdjhs[newString]);
		}

		String[] var7 = new String[result.size()];

		for (int j = 0; j < result.size(); ++j) {
			var7[j] = (String) result.get(j);
		}

		return var7;
	}

	public static String[] strDateToGridForString(String zdjh, int count, int size) {
		ArrayList result = new ArrayList();
		String[] zdjhs = zdjh.split(",");
		int counts = 0;

		for (int newString = count * size; newString < zdjhs.length; ++newString) {
			++counts;
			if (counts > size) {
				break;
			}

			result.add(0, zdjhs[newString]);
		}

		String[] var8 = new String[result.size()];

		for (int j = 0; j < result.size(); ++j) {
			var8[j] = (String) result.get(j);
		}

		return var8;
	}

	public static int[] strToIntArray(String[] strArr) {
		if (null != strArr && 0 != strArr.length) {
			if (isEmptyString(strArr[0])) {
				return null;
			} else {
				int[] intArr = null;

				try {
					intArr = new int[strArr.length];

					for (int e = 0; e < strArr.length; ++e) {
						intArr[e] = Integer.parseInt(strArr[e]);
					}
				} catch (Exception var3) {
					var3.printStackTrace();
				}

				return intArr;
			}
		} else {
			return null;
		}
	}

	public static int indexOfArray(String[] strArray, String compString) {
		int result = -1;

		for (int i = 0; strArray != null && i < strArray.length; ++i) {
			if (!isEmptyString(strArray[i]) && strArray[i].equals(compString)) {
				result = i;
				break;
			}
		}

		return result;
	}

	public static String ConvertUTF(String s) throws Exception {
		byte[] temp = s.getBytes("iso-8859-1");
		String result = new String(temp, "UTF-8");
		return result;
	}

	public static String getString(Object s) {
		return s == null ? "" : String.valueOf(s);
	}

	public static String removeEndChar(String str, int n) {
		return isEmptyString(str) ? str : str.substring(0, str.length() - n);
	}

	public static String[] getInterval(int num) {
		String[] sjrq = new String[num];

		for (int i = 0; i < num; ++i) {
			if (i < 10) {
				sjrq[i] = "0" + String.valueOf(i);
			} else {
				sjrq[i] = String.valueOf(i);
			}
		}

		return sjrq;
	}

	public static String getExceptionDetailInfo(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		return sw.toString();
	}

	public static String convertString(String s, String character_s, String character_d) {
		s = s == null ? "" : s;
		String s_unicode = "";

		try {
			s = s.trim();
			byte[] e = s.getBytes(character_s);
			s_unicode = new String(e, character_d);
		} catch (Exception var5) {
			var5.printStackTrace();
		}

		return s_unicode;
	}

	public static String ISO8859_1ToGBK(String s) {
		return convertString(s, "ISO8859_1", "GBK");
	}

	public static String GBKToISO8859_1(String s) {
		return convertString(s, "GBK", "ISO8859_1");
	}

	public static String UTF8ToGBK(String s) {
		return convertString(s, "UTF-8", "GBK");
	}

	public static String GBKToUTF8(String s) {
		return convertString(s, "GBK", "UTF-8");
	}

	public static String replaceStr(String source, String oldString, String newString) {
		if (oldString != null && oldString.length() != 0) {
			if (source == null) {
				return "";
			} else {
				if (newString == null) {
					newString = "";
				}

				StringBuffer output = new StringBuffer();
				int lengthOfSource = source.length();
				int lengthOfOld = oldString.length();

				int posStart;
				int pos;
				for (posStart = 0; (pos = source.indexOf(oldString, posStart)) >= 0; posStart = pos + lengthOfOld) {
					output.append(source.substring(posStart, pos));
					output.append(newString);
				}

				if (posStart < lengthOfSource) {
					output.append(source.substring(posStart));
				}

				return output.toString();
			}
		} else {
			return source;
		}
	}

	public static List getWeeksList() {
		ArrayList ls = new ArrayList();
		HashMap first = new HashMap();
		first.put("BM", "1");
		first.put("MC", "The first week");
		HashMap second = new HashMap();
		second.put("BM", "2");
		second.put("MC", "The second week");
		HashMap third = new HashMap();
		third.put("BM", "3");
		third.put("MC", "The third week");
		HashMap fourth = new HashMap();
		fourth.put("BM", "4");
		fourth.put("MC", "The fourth week");
		HashMap fifth = new HashMap();
		fifth.put("BM", "5");
		fifth.put("MC", "The fifth week");
		ls.add(first);
		ls.add(second);
		ls.add(third);
		ls.add(fourth);
		ls.add(fifth);
		return ls;
	}

	public static String getPageField(List ls) {
		StringBuilder sb = new StringBuilder();
		if (null != ls && ls.size() > 0) {
			for (int i = 0; i < ls.size(); ++i) {
				sb.append(";");
				Map map = (Map) ls.get(i);
				sb.append(map.get("SJX")).append(",").append(map.get("SJXMC"));
				if (!isEmptyString((String) map.get("DW"))) {
					sb.append("(").append(map.get("DW")).append(")");
				}

				sb.append(",").append(map.get("WIDTH"));
			}

			return sb.toString().substring(1);
		} else {
			return "";
		}
	}

	public static String getChartField(List ls) {
		StringBuilder sb = new StringBuilder();
		if (null != ls && ls.size() > 0) {
			for (int i = 0; i < ls.size(); ++i) {
				Map map = (Map) ls.get(i);
				if (!isEmptyString((String) map.get("TXID"))) {
					sb.append(";");
					sb.append(map.get("SJX")).append(",").append(map.get("SJXMC"));
					if (!isEmptyString((String) map.get("DW"))) {
						sb.append("(").append(map.get("DW")).append(")");
					}
				}
			}

			if (sb.length() != 0) {
				return sb.toString().substring(1);
			}
		}

		return "";
	}

	public static String getRuleName(String[] yRule, String[] allRules) {
		if (null == yRule) {
			return "";
		} else {
			int[] nRule = new int[yRule.length];

			for (int sbRule = 0; sbRule < yRule.length; ++sbRule) {
				if (!isEmptyString(yRule[sbRule])) {
					nRule[sbRule] = Integer.parseInt(yRule[sbRule].substring(yRule[sbRule].lastIndexOf("_") + 1));
				}
			}

			StringBuilder var5 = new StringBuilder();

			for (int j = 0; j < nRule.length; ++j) {
				var5.append(j + 1).append(". ").append(allRules[nRule[j] - 1]);
				var5.append("<br/>");
			}

			return var5.toString();
		}
	}

	public static String getSjxName(String[] yEnergy, List energyLs) {
		if (null != energyLs && energyLs.size() != 0) {
			StringBuilder sbEnergy = new StringBuilder();
			if (null != energyLs && energyLs.size() > 0) {
				for (int m = 0; m < yEnergy.length; ++m) {
					for (int n = 0; n < energyLs.size(); ++n) {
						Map map = (Map) energyLs.get(n);
						if (yEnergy[m].toUpperCase().equals((String) map.get("BM"))) {
							sbEnergy.append(map.get("MC")).append("<br/>");
						}
					}
				}
			}

			return sbEnergy.toString();
		} else {
			return "";
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
