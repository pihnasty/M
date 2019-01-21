/**
 *
 */
package persistence.loader._util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class _Date extends Date {
	public final static String dateFofmatDefault = "dd.MM.yyyy hh:mm:ss";
	public final static String dateFofmatFULL = "EEE MMM dd HH:mm:ss zzz yyyy";
	// SimpleDateFormat format =  new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);

	public _Date() {
	}

	public _Date(long date) {
		super(date);
	}

	public _Date(int year, int month, int date, int hrs, int min, int sec) {
		super(year - 1900, month, date, hrs, min, sec);
	}

	public static String toStringForDate(Date date) {
		return new SimpleDateFormat(dateFofmatDefault).format(date);
	}

	public static Date toDateForString(String s) {
		try {
			return new SimpleDateFormat(dateFofmatDefault).parse(s);
		} catch (ParseException exp) {
			// TODO Auto-generated catch block
			exp.printStackTrace();
		}
		return new Date();
	}
	public static Date toDateForString(String s, Locale locale) {

		try {
			return new SimpleDateFormat(dateFofmatFULL, locale).parse(s);
		} catch (ParseException exp) {
			// TODO Auto-generated catch block
			// exp.printStackTrace();
		}
		return null;
	}

	public static Date _toDateForString(String s, Locale locale) //
	{
		try {
			return new SimpleDateFormat(dateFofmatDefault, locale).parse(s);
		} catch (ParseException exp) {
			// TODO Auto-generated catch block
			System.out.println("No date. ������ �� ��������");
			// exp.printStackTrace();
		}
		return null;
	}

	public static String toString(Date date) {
		return new SimpleDateFormat(dateFofmatDefault).format(date);
	}
}
