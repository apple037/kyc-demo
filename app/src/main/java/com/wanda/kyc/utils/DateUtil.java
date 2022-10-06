package com.wanda.kyc.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class DateUtil {

	private DateUtil() {
		throw new IllegalStateException("Utility class");
	}

	@AllArgsConstructor
	enum DateTimePattern {
		
		SQL_PATTERN("yyyy-MM-dd HH:mm:ss"),
		SQL_START_PATTERN("yyyy-MM-dd 00:00:00"),
		SQL_END_PATTERN("yyyy-MM-dd 23:59:59"),
		SQL_DATE_PATTERN("yyyy-MM-dd"),
		SQL_MONTH_PATTERN("yyyy-MM"),
		
		;
		
		@Getter
		private String pattern;
		
	}

	public static String format(LocalDateTime localDateTime) {
		return localDateTime.format(DateTimeFormatter.ofPattern(DateTimePattern.SQL_PATTERN.getPattern()));
	}

	public static String format(LocalDateTime localDateTime, DateTimePattern dateTimePattern) {
		return localDateTime.format(DateTimeFormatter.ofPattern(dateTimePattern.getPattern()));
	}

	public static String format(LocalDate localDate) {
		return localDate.format(DateTimeFormatter.ofPattern(DateTimePattern.SQL_DATE_PATTERN.getPattern()));
	}

	public static String format(LocalDate localDate, DateTimePattern dateTimePattern) {
		return localDate.format(DateTimeFormatter.ofPattern(dateTimePattern.getPattern()));
	}

	public static LocalDateTime parse(String dateTime) {
		return LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(DateTimePattern.SQL_PATTERN.getPattern()));
	}

	public static String now() {
		return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateTimePattern.SQL_PATTERN.getPattern()));
	}


	/**
	 * 當天時間 00:00:00
	 * 
	 * @return
	 */
	public static String todayStart() {
		return format(LocalDateTime.now(), DateTimePattern.SQL_START_PATTERN);
	}

	/**
	 * 當天時間 23:59:59
	 * 
	 * @return
	 */
	public static String todayEnd() {
		return format(LocalDateTime.now(), DateTimePattern.SQL_END_PATTERN);
	}
	
	/**
	 * 昨天時間 00:00:00
	 *
	 * @return
	 */
	public static String yesterdayStart() {
		return format(LocalDateTime.now().minusDays(1), DateTimePattern.SQL_START_PATTERN);
	}

	/**
	 * 昨天時間 23:59:59
	 *
	 * @return
	 */
	public static String yesterdayEnd() {
		return format(LocalDateTime.now().minusDays(1), DateTimePattern.SQL_END_PATTERN);
	}

	/**
	 * 昨日日期
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String yesterdayDate() {
		return format(LocalDate.now().minusDays(1), DateTimePattern.SQL_DATE_PATTERN);
	}

	/**
	 * 今日日期
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String nowDate() {
		return format(LocalDate.now(), DateTimePattern.SQL_DATE_PATTERN);
	}

	/**
	 * 今日月份
	 * 
	 * @return yyyy-MM
	 */
	public static String nowMonth() {
		return format(LocalDate.now(), DateTimePattern.SQL_MONTH_PATTERN);
	}

	/**
	 * 自訂週的第一天為星期幾，並取得此週的第幾天日期
	 * 
	 * @param firstday 星期幾為第一天
	 * @param dayOfWeek 此週的第幾天
	 * @return
	 */
	public static LocalDate getDateByWeekAndDayOfWeek(DayOfWeek firstday, int dayOfWeek) {
		LocalDate now = LocalDate.now();
		TemporalField myWeek = WeekFields.of(firstday, 1).dayOfWeek();
		return now.with(myWeek, dayOfWeek);
	}

	/**
	 * 返回起始日期到結束日期之間的所有日期List(yyyy-MM-dd)
	 * 
	 * @param startDate(yyyy-MM-dd)
	 * @param endDate(yyyy-MM-dd)
	 * @return
	 */
	public static List<String> dayList(String startDate, String endDate) {
		return dayList(LocalDate.parse(startDate), LocalDate.parse(endDate));
	}

	/**
	 * 返回起始日期到結束日期之間的所有日期List(yyyy-MM-dd)
	 * 
	 * @param start(LocalDate)
	 * @param end(LocalDate)
	 * @return
	 */
	private static List<String> dayList(LocalDate start, LocalDate end) {
		//@off
		// 用起始時間作為流的源頭，按照每次加一天的方式創建一個無限流
		return Stream.iterate(start, localDate -> localDate.plusDays(1))
				// 截斷無限流，長度為起始時間和結束時間的差 + 1
				.limit(ChronoUnit.DAYS.between(start, end) + 1)
				.map(LocalDate ::toString)
				.collect(Collectors.toList());
		//@on
	}

	/**
	 * 是否為今日(日期)
	 * 
	 * @param date
	 * @return
	 */
	public static boolean isToday(LocalDate date) {
		return LocalDate.now().isEqual(date);
	}

	/**
	 * dateTimeA 是否在 dateTimeB 之前
	 * 
	 * @param dateTimeA
	 * @param dateTimeB
	 * @return
	 */
	public static boolean isBefore(String dateTimeA, String dateTimeB) {
		return parse(dateTimeA).isBefore(parse(dateTimeB));
	}

	/**
	 * dateTime 加 year年
	 * 
	 * @param dateTime
	 * @param year
	 * @return
	 */
	public static String addYear(String dateTime, String year) {
		long months = Long.parseLong(AmtUtil.multiply(year, "12", 0));
		return format(parse(dateTime).plusMonths(months));
	}

	/**
	 * 傳入時間的日期
	 * 
	 * @return yyyy-MM-dd
	 */
	public static String getDateByDateTime(String dateTime) {
		return format(parse(dateTime).toLocalDate());
	}

	/**
	 * 將毫秒轉成日期格式
	 * @param timestamp
	 * @return
	 */
	public static String getDateTimeByTimeInSecond(Long timestamp) {
		LocalDateTime date = Instant.ofEpochSecond(timestamp).atZone(ZoneId.systemDefault()).toLocalDateTime();
		return format(date, DateTimePattern.SQL_PATTERN);
	}

	/**
	 * 將日期格式轉成毫秒表示
	 * @param dateTime
	 * @return
	 */
	public static Long getTimeInMillsByDateTime(String dateTime) {
		LocalDateTime date = LocalDateTime.parse(dateTime,DateTimeFormatter.ofPattern(DateTimePattern.SQL_PATTERN.getPattern()));
		Long seconds = date.toEpochSecond(ZoneOffset.ofHours(8));
		return seconds;
	}

}
