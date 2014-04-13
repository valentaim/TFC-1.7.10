package TFC.Core;

import net.minecraft.world.World;
import TFC.API.TFCOptions;
import TFC.Core.Util.StringUtil;


public class TFC_Time
{
	private static World worldObj;

	public static String[] seasons = { StringUtil.localize("gui.Calendar.EarlySpring"),
		StringUtil.localize("gui.Calendar.Spring"), StringUtil.localize("gui.Calendar.LateSpring"),
		StringUtil.localize("gui.Calendar.EarlySummer"), StringUtil.localize("gui.Calendar.Summer"),
		StringUtil.localize("gui.Calendar.LateSummer"), StringUtil.localize("gui.Calendar.EarlyAutumn"),
		StringUtil.localize("gui.Calendar.Autumn"), StringUtil.localize("gui.Calendar.LateAutumn"),
		StringUtil.localize("gui.Calendar.EarlyWinter"), StringUtil.localize("gui.Calendar.Winter"),
		StringUtil.localize("gui.Calendar.LateWinter")};
	public static String[] months  = { StringUtil.localize("gui.Calendar.March"),
		StringUtil.localize("gui.Calendar.April"),StringUtil.localize("gui.Calendar.May"),
		StringUtil.localize("gui.Calendar.June"), StringUtil.localize("gui.Calendar.July"),
		StringUtil.localize("gui.Calendar.August"), StringUtil.localize("gui.Calendar.September"),
		StringUtil.localize("gui.Calendar.October"), StringUtil.localize("gui.Calendar.November"),
		StringUtil.localize("gui.Calendar.December"), StringUtil.localize("gui.Calendar.January"),
		StringUtil.localize("gui.Calendar.February")};
	public static String[] Days = { StringUtil.localize("gui.Calendar.Sunday"),
		StringUtil.localize("gui.Calendar.Monday"), StringUtil.localize("gui.Calendar.Tuesday"),
		StringUtil.localize("gui.Calendar.Wednesday"), StringUtil.localize("gui.Calendar.Thursday"),
		StringUtil.localize("gui.Calendar.Friday"), StringUtil.localize("gui.Calendar.Saturday")};

	public static int currentDay = 0;
	public static int lastMonth = 11;
	public static int currentMonth = 0;
	public static int currentYear = 0;
	private static long time = 0;

	public static final int January = 10;
	public static final int February = 11;
	public static final int March = 0;
	public static final int April = 1;
	public static final int May = 2;
	public static final int June = 3;
	public static final int July = 4;
	public static final int August = 5;
	public static final int September = 6;
	public static final int October = 7;
	public static final int November = 8;
	public static final int December = 9;

	public static final long hourLength = 1000;
	public static final int dayLength = 24000;

	/**
	 * This is the year length ratio for use when your numbers are based on a 360 day year and 
	 * you want to bring it in line with the current year length.
	 */
	public static float timeRatio360 = TFCOptions.yearLength/360f;
	/**
	 * This is the year length ratio for use when your numbers are based on a 96 day year and 
	 * you want to bring it in line with the current year length.
	 */
	public static float timeRatio96 = TFCOptions.yearLength/96f;

	public static int daysInYear = TFCOptions.yearLength;
	public static int daysInMonth = daysInYear/12;
	public static long ticksInYear = daysInYear * dayLength;
	public static long ticksInMonth = daysInMonth * dayLength;
	public static long startTime = ticksInMonth * 3;

	public static void UpdateTime(World world)
	{
		time = world.getWorldInfo().getWorldTime();

		if(time < startTime)
			world.getWorldInfo().setWorldTime(startTime);

		int m = getMonth();
		int m1 = m - 1;

		if(m1 < 0) {
			m1 = 11;
		}

		lastMonth = m1;
		currentDay = getDayOfYear();
		currentMonth = m;
		currentYear = getYear();
	}

	public static int getHoursInMonth()
	{
		return 24*daysInMonth;
	}

	public static String getSeason()
	{
		return seasons[getMonth()];
	}

	public static long getTotalTicks()
	{
		return time;
	}

	public static int getDayOfWeek()
	{
		long day = getTotalDays()+1;

		long days = day / 7;
		long days2 = day - (days*7);
		return (int)days2;
	}

	public static int getDayOfMonth()
	{
		long month = getTotalMonths();
		long days = daysInMonth*month;
		long days2 = getTotalDays() - days;
		return 1+(int)days2;
	}

	public static int getDayOfYear()
	{
		long year = getYear();
		long years = (ticksInYear)*year;
		long years2 = time - years;
		return (int) (years2/dayLength);
	}

	public static int getDayOfYearFromTick(long tick)
	{
		long years = (tick / (ticksInYear));
		long years2 = tick - (ticksInYear * years);
		return (int) (years2/dayLength);
	}

	public static int getDayOfYearFromDays(long days)
	{
		long years = (days / daysInYear);
		long years2 = days - (daysInYear * years);
		return (int)years2;
	}

	/**Explicit month value, use getSeason(int zCoord) for anything related to a season, ie Summer, Winter etc.
	 * 
	 * @return		explicit month value
	 */
	public static int getMonth()
	{
		long totalmonths = getTotalMonths();
		long totalmonths2 = totalmonths / 12;
		long totalmonths3 = totalmonths-(totalmonths2*12);
		return (int)totalmonths3;
	}
	/**Southern hemisphere reverses the season. Use getMonth() for the explicit month
	 * 
	 * @param z		integer z-coordinate
	 * @return		adjusted season value
	 */
	public static int getSeasonAdjustedMonth(int z)
	{
		if(z > 0){
			return (getMonth()+6)%12;
		}
		return getMonth();
	}

	public static int getYear()
	{
		long totalmonths = getTotalMonths();
		long totalmonths2 = totalmonths / 12;
		return (int)totalmonths2;
	}

	public static long getTotalDays()
	{
		return (time/dayLength);
	}

	public static long getTotalHours()
	{
		return (time/hourLength);
	}

	public static long getTotalMonths()
	{
		return getTotalDays() / daysInMonth;
	} 

	public static long getTotalYears()
	{
		return getTotalMonths() / 12;
	}

	public static int getHour()
	{
		int h = (int)((time - ((time / dayLength)*dayLength))/hourLength);
		h -= 6;
		if(h < 0) {
			h = 23 + h;
		}
		h -= 12;
		if(h < 0) {
			h = 23+h;
		}
		return  h;
	}

	public static int getHourOfDayFromTotalHours()
	{
		return  getHourOfDayFromTotalHours((int)getTotalHours());
	}

	public static int getHourOfDayFromTotalHours(int th)
	{
		int h = (th-(th/24)*24);//gives us the remainder
		h -= 6;
		if(h < 0) {
			h = 23 + h;
		}
		h -= 12;
		if(h < 0) {
			h = 23+h;
		}
		return  h;
	}

	public static int getDayFromTotalHours(int th)
	{
		return th/24;
	}

	public static boolean isSpring(int z)
	{
		int day = (getDayOfYear() + (z > 0? (daysInYear)/2:0))%daysInYear;
		if(day >= 20 && day <= 111) {
			return true;
		}

		return false;
	}
	public static boolean isSummer(int z)
	{
		int day = (getDayOfYear() + (z > 0? (daysInYear)/2:0))%daysInYear;
		if(day >= 112 && day <= 202) {
			return true;
		}

		return false;
	}
	public static boolean isFall(int z)
	{
		int day = (getDayOfYear() + (z > 0? (daysInYear)/2:0))%daysInYear;
		if(day >= 203 && day <= 293) {
			return true;
		}

		return false;
	}
	public static boolean isWinter(int z)
	{
		int day = (getDayOfYear() + (z > 0? (daysInYear)/2:0))%daysInYear;
		if(day >= 294 || day < 20) {
			return true;
		}

		return false;
	}

	public static int getMonthFromDayOfYear(int day)
	{
		if(day < 0) {
			day = daysInYear + day;
		}
		return day / (daysInMonth);
	}
	/**
	 * Season is reversed in southern Hemisphere
	 * @param day		day of year
	 * @param z			integer z-coordinate
	 * @return			adjusted season value
	 */
	public static int getSeasonFromDayOfYear(int day, int z)
	{
		if(day < 0) {
			day = daysInYear + day;
		}
		return ((day / (daysInMonth))+(z>0?6:0))%12;
	}

	public static int getDayOfMonthFromDayOfYear(int day)
	{
		if(day < 0) {
			day = daysInYear + day;
		}
		return (day - ((int)Math.floor((day/daysInMonth))*daysInMonth));
	}

	public static int getPrevMonth()
	{
		return lastMonth;
	}

	public static int getPrevMonth(int month)
	{
		if(month == 0) {
			return 11;
		}
		return month - 1;
	}

	public static float getYearRatio(float expectedDays)
	{
		return expectedDays / daysInYear;
	}

	public static int getMonthsSinceDay(int totalDay)
	{
		int days = (int) (TFC_Time.getTotalDays() - totalDay);
		return days / TFC_Time.daysInMonth;
	}

}
