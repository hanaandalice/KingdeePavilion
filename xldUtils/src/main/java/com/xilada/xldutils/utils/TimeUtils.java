package com.xilada.xldutils.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
/**
 * 一些时间相关的方法
 * @author Administrator
 *
 */
public class TimeUtils {
	public static String getTime(String user_time) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d;
		try {
			d = sdf.parse(user_time);
			long l = d.getTime();
			String str = String.valueOf(l);
			re_time = str.substring(0, str.length());


		} catch (ParseException e) {
// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re_time;
	}

	public static String getSimpleTime(String time) {

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);

		SimpleDateFormat formater = new SimpleDateFormat("yy-MM-dd HH:mm",Locale.CHINA);
		Date date = null;
		try {
			date = f.parse(time);
			return formater.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return time;
		}
	}

	public static String getChatSimpleTime(String time) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
		SimpleDateFormat formater = new SimpleDateFormat("yy-MM-dd",Locale.CHINA);
		Date date = null;
		try {
			date = f.parse(time);
			return formater.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

	public static String getTimeHM(String time) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
		SimpleDateFormat formater = new SimpleDateFormat("HH:mm",Locale.CHINA);
		Date date = null;
		try {
			date = f.parse(time);
			return formater.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 获取时分
	 * @param time
	 * @return
	 */
	public static String getTimeHM(long time) throws ParseException {

		SimpleDateFormat formater = new SimpleDateFormat("HH:mm",Locale.CHINA);
		Date date = null;
		date = new Date(time);
		return formater.format(date);
	}

	/**
	 * 时间戳转换日期格式
	 * @param timestamp 单位毫秒
	 * @return
	 */
	public static String  getCurrentTimeMillisecond(long timestamp) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
		return f.format(new Date(timestamp));

	}

	/**
	 * 时间戳转换日期格式
	 * @param timestamp 单位毫秒
	 * @return
	 */
	public static String getCurrentTimeDDMM(long timestamp) {

		SimpleDateFormat f = new SimpleDateFormat("ddMM月",Locale.CHINA);

		return f.format(new Date(timestamp));

	}

	/**
	 * 时间戳转换日期格式
	 * @param timestamp 单位毫秒
	 * @return
	 */
	public static String getCurrentTimeMMDD(long timestamp) {

		SimpleDateFormat f = new SimpleDateFormat("MM-dd",Locale.CHINA);

		return f.format(new Date(timestamp));

	}
	/**
	 * 时间戳转换日期格式
	 * @param timestamp 单位毫秒
	 * @return
	 */
	public static String getCurrentTimeMMDDhhmm(long timestamp) {
		SimpleDateFormat f = new SimpleDateFormat("MM-dd HH:mm",Locale.CHINA);

		return f.format(new Date(timestamp));

	}
	/**
	 * 时间戳转换日期格式
	 * @param timestamp 单位毫秒
	 * @return
	 */
	public static String getCurrentTimeHHmm(long timestamp) {
		SimpleDateFormat formart = new SimpleDateFormat("HH:mm",Locale.CHINA);
		return formart.format(new Date(timestamp));

	}

	/**
	 * 时间戳转换日期格式
	 * @param timestamp 单位秒
	 * @return
	 */
	public static String getCurrentTime(long timestamp) {

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
		return f.format(new Date(timestamp));

	}

	/**
	 * 时间戳转换日期格式
	 * @param timestamp 单位秒
	 * @return
	 */
	public static String getTimeYMD(long timestamp) {

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);

		return f.format(new Date(timestamp*1000));

	}

	/**
	 * 时间戳转换日期格式
	 * @param timestamp 单位秒
	 * @return
	 */
	public static String getTimeYMDCN(long timestamp) {

		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss",Locale.CHINA);

		return f.format(new Date(timestamp));

	}
	/**
	 * 时间戳转换日期格式
	 * @param timestamp 单位秒
	 * @return
	 */
	public static String getTimeYMD2(long timestamp) {

		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日",Locale.CHINA);

		return f.format(new Date(timestamp));

	}

	/**
	 * 时间戳转换日期格式
	 * @param timestamp 毫秒
	 * @return －年－月－日－时－分
	 */
	public static String getTimeYMDHMCN(long timestamp) {

		SimpleDateFormat f = new SimpleDateFormat("yyyy年MM月dd日HH时mm分",Locale.CHINA);

		return f.format(new Date(timestamp));

	}
	/**
	 * 时间戳转换日期格式
	 * @param timestamp 毫秒
	 * @return YYYY年
	 */
	public static int getTimeYear(long timestamp) {

		SimpleDateFormat f = new SimpleDateFormat("yyyy",Locale.CHINA);
		int year;
		try {
			year = Integer.parseInt(f.format(new Date(timestamp)));
		}catch (Exception e){
			year=0;
		}

		return year;
	}

	/**
	 * 时间戳转换日期格式
	 * @param timestamp 毫秒
	 * @return MM月
	 */
	public static int getTimeMonth(long timestamp) {

		SimpleDateFormat f = new SimpleDateFormat("MM",Locale.CHINA);
		int month ;
		try {
			month =Integer.parseInt(f.format(new Date(timestamp)));
		}catch (Exception e){
			month = 0;
		}
		return month;
	}

	/**
	 * 时间戳转换日期格式
	 * @param timestamp 毫秒
	 * @return dd日
	 */
	public static int getTimeDay(long timestamp) {

		SimpleDateFormat f = new SimpleDateFormat("dd",Locale.CHINA);
		int day ;
		try {
			day =Integer.parseInt(f.format(new Date(timestamp)));
		}catch (Exception e){
			day = 0;
		}
		return day;
	}

	/**
	 * 时间戳转换日期格式
	 * @param timestamp 毫秒
	 * @return HH时
	 */
	public static int getTimeHour(long timestamp) {

		SimpleDateFormat f = new SimpleDateFormat("HH",Locale.CHINA);
		int hour ;
		try {
			hour =Integer.parseInt(f.format(new Date(timestamp)));
		}catch (Exception e){
			hour = 0;
		}
		return hour;
	}

	/**
	 * 时间戳转换日期格式
	 * @param timestamp 毫秒
	 * @return mm分
	 */
	public static int getTimeMinute(long timestamp) {

		SimpleDateFormat f = new SimpleDateFormat("mm",Locale.CHINA);
		int minute ;
		try {
			minute =Integer.parseInt(f.format(new Date(timestamp)));
		}catch (Exception e){
			minute = 0;
		}
		return minute;
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurrentTime() {

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);

		return f.format(new Date(System.currentTimeMillis()));

	}

	/**
	 * 获取当前年份时间
	 * @return
	 */
	public static String getCurrentYYYYTime() {
		SimpleDateFormat f = new SimpleDateFormat("yyyy",Locale.CHINA);
		return f.format(new Date(System.currentTimeMillis()));

	}

	/**
	 * 获取时间的年份
	 * @return
	 */
	public static String getTimeYear(String time) {

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);
		SimpleDateFormat formater = new SimpleDateFormat("yyyy",Locale.CHINA);
		Date date = null;
		try {
			date = f.parse(time);
			return formater.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 获取时间的月份
	 * @return
	 */
	public static String getTimeMonth(String time) {

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);

		SimpleDateFormat formater = new SimpleDateFormat("MM",Locale.CHINA);
		Date date = null;
		try {
			date = f.parse(time);
			return formater.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}

	}

	/**
	 * 获取时间的日
	 * @return
	 */
	public static String getTimeDay(String time) {

		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);

		SimpleDateFormat formater = new SimpleDateFormat("dd",Locale.CHINA);
		Date date = null;
		try {
			date = f.parse(time);
			return formater.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}

	}

	@SuppressWarnings("deprecation")
	public static int getYear(String dateString) {

		SimpleDateFormat formart = new SimpleDateFormat("yyyy/MM/dd",Locale.CHINA);
		try {
			Date d = formart.parse(dateString);
			return d.getYear();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("deprecation")
	public static int getMonth(String dateString) {

		SimpleDateFormat formart = new SimpleDateFormat("yyyy/MM/dd",Locale.CHINA);

		try {
			Date d = formart.parse(dateString);

			return d.getMonth();

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@SuppressWarnings("deprecation")
	public static int getDayOfMonth(String dateString) {

		SimpleDateFormat formart = new SimpleDateFormat("yyyy/MM/dd",Locale.CHINA);

		try {
			Date d = formart.parse(dateString);

			return d.getDate();

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@SuppressWarnings("deprecation")
	public static int getHours(String timeString) {
		SimpleDateFormat formart = new SimpleDateFormat("HH:mm",Locale.CHINA);

		try {
			Date date = formart.parse(timeString);

			return date.getHours();

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;
	}

	@SuppressWarnings("deprecation")
	public static int getMinutes(String timeString) {

		SimpleDateFormat formart = new SimpleDateFormat("HH:mm",Locale.CHINA);

		try {
			Date date = formart.parse(timeString);

			return date.getMinutes();

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return 0;

	}

	public static Date parseTime(String time) {
		SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.CHINA);
		try {
			Date d = formart.parse(time);

			return d;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static long parseTimeMillisecond(String time) {

		SimpleDateFormat formart = new SimpleDateFormat("yyyy-MM-dd",Locale.CHINA);

		try {
			Date d = formart.parse(time);

			return d.getTime();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return 0;
	}

	public static Date parseTaskTime(String time) {

		SimpleDateFormat formart = new SimpleDateFormat("yyyy/MM/dd HH:mm",Locale.CHINA);

		try {
			Date d = formart.parse(time);

			return d;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public static String getLastUpdateTimeDesc(String time) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss",Locale.CHINA);

		try {

			String desc = "";

			Date d = simpleDateFormat.parse(time);

			Date n = new Date();

			long delay = n.getTime() - d.getTime();

			long secondsOfHour = 60 * 60;

			long secondsOfDay = secondsOfHour * 24;

			long secondsOfTwoDay = secondsOfDay * 2;

			long secondsOfThreeDay = secondsOfDay * 3;

			// 相差的秒数
			long delaySeconds = delay / 1000;

			if (delaySeconds < 10) {
				desc = "刚刚";
			} else if (delaySeconds <= 60) {
				desc = delaySeconds + "秒前";
			} else if (delaySeconds < secondsOfHour) {
				desc = (delaySeconds / 60) + "分前";
			} else if (delaySeconds < secondsOfDay) {
				desc = (delaySeconds / 60 / 60) + "小时前";
			} else if (delaySeconds < secondsOfTwoDay) {
				desc = "一天前";
			} else if (delaySeconds < secondsOfThreeDay) {
				desc = "两天前";
			}

			return desc;

		} catch (ParseException e) {

			e.printStackTrace();
		}

		return time;

	}
	/**
	 * 获取时间和当前间隔。
	 * @param time
	 * @return
	 */
	public static String getTimeDesc(long time) {

		String desc = "";
		try {
			Date n = new Date();
			long delay = n.getTime() - time;
			long secondsOfHour = 60 * 60;
			long secondsOfDay = secondsOfHour * 24;
			// 相差的秒数
			long delaySeconds = delay / 1000;

			if (delaySeconds < 10) {
				desc = "刚刚";
			} else if (delaySeconds <= 60) {
				desc = delaySeconds + "秒前";
			} else if (delaySeconds < secondsOfHour) {
				desc = (delaySeconds / 60) + "分前";
			} else if (delaySeconds < secondsOfDay) {
				desc = getTimeHM(time);
			}else {
				desc =getCurrentTimeMMDD(time);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return desc;
	}

	/**
	 * 获取两个时间的间隔
	 * @param time
	 * @param nowTime
	 * @return
	 */
	public static String getTimeInterval(long time,long nowTime) {
		String desc = "";
		Date d = new Date(time);

		Date n = new Date(nowTime);

		long delay = n.getTime() - d.getTime();

		long secondsOfHour = 60 * 60;

		long secondsOfDay = secondsOfHour * 24;

		long secondsOfTwoDay = secondsOfDay * 2;

		long secondsOfThreeDay = secondsOfDay * 3;

		// 相差的秒数
		long delaySeconds = delay / 1000;

		if (delaySeconds < 10) {
			desc = "刚刚";
		} else if (delaySeconds <= 60) {
			desc = delaySeconds + "秒前";
		} else if (delaySeconds < secondsOfHour) {
			desc = (delaySeconds / 60) + "分前";
		} else if (delaySeconds < secondsOfDay) {
			desc = (delaySeconds / 60 / 60) + "小时前";
		} else if (delaySeconds < secondsOfTwoDay) {
			desc = "一天前";
		} else if (delaySeconds < secondsOfThreeDay) {
			desc = "两天前";
		}else {
			desc =getCurrentTimeMillisecond(time);
		}
		return desc;

	}

	/**
	 * 获取两个时间的间隔
	 * @param time
	 * @param nowTime
	 * @return
	 */
	public static String getTimeDDMM(long time,long nowTime) {
		String desc = "";
		Date d = new Date(time);

		Date n = new Date(nowTime);

		long delay = n.getTime() - d.getTime();

		long secondsOfHour = 60 * 60;

		long secondsOfDay = secondsOfHour * 24;

		long secondsOfTwoDay = secondsOfDay * 2;

		long secondsOfThreeDay = secondsOfDay * 3;

		// 相差的秒数
		long delaySeconds = delay / 1000;

		if (delaySeconds < secondsOfDay) {
			desc = "今天";
		} else if (delaySeconds < secondsOfTwoDay) {
			desc = "昨天";
		} else if (delaySeconds < secondsOfThreeDay) {
			desc = "前天";
		}else {
			desc =getCurrentTimeDDMM(time);
		}

		return desc;
	}
	/**
	 * 调此方法输入所要转换的时间输入例如（"2014-06-14-16-09-00"）返回时间戳
	 *
	 * @param time
	 * @return
	 */
	public static String getBirthdayData(String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return String.valueOf(sdf.parse(time).getTime()/1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public static String getTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}
	public static String getTimeHS(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
	public static String getTimeMD(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm:ss");
		return format.format(date);
	}
	public static String getTimeYYYY(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		try {
			return String.valueOf(format.parse(date).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**倒计时处理*/
	public static String formatTime(int time) {
		int timeminute =time/60;
		int timeSec2 =time%60;
		String timeSecStr =timeSec2>=10?""+timeSec2:"0"+timeSec2;
		return timeminute+":"+timeSecStr;
	}
}
