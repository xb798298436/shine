package com.leadall.chauffeur.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.example.com.leadall.chauffeur.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.MeasureSpec;




public final class ChauffeurUtils {

	 /** 
     * ��ȡԭͼƬ�洢·�� 
     * @return 
     */  
    public static String getPhotopath(Context mContext) {  

        // �ļ���·��  
        String thepath = Environment.getExternalStorageDirectory() + "/";
        thepath += mContext.getString(R.string.app_name) + "/"; 
        Calendar ca = Calendar.getInstance();
		int year = ca.get(Calendar.YEAR);// ��ȡ���
		int month = ca.get(Calendar.MONTH) + 1;// ��ȡ�·�
		int day = ca.get(Calendar.DATE);// ��ȡ��
		int hour = ca.get(Calendar.HOUR_OF_DAY);// Сʱ
		int minute = ca.get(Calendar.MINUTE);// ��
		int second = ca.get(Calendar.SECOND);// ��
		int milliSecond = ca.get(Calendar.MILLISECOND);
		String Suffix = "png";
		// ��Ƭȫ·��  
		String fileName = thepath + year + "-" + (month < 10 ? "0" : "") + month + "-" + (day < 10 ? "0" : "") + day
				+ "_" + (hour < 10 ? "0" : "") + hour + "-" + (minute < 10 ? "0" : "") + minute + "-"
				+ (second < 10 ? "0" : "") + second + "_" + (milliSecond < 10 ? "00" : (milliSecond < 100 ? "0" : ""))
				+ milliSecond + (Suffix == null ? "" : ("." + Suffix));
        File file = new File(thepath);  
        if (file.exists()) {
        	  file.mkdirs();// �����ļ���  
		}          
        return fileName;  
    } 
	/**
	 * ͼƬ����
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isPhoto(String path) {
		if (path.toLowerCase().endsWith("png"))
			return true;
		if (path.toLowerCase().endsWith("jpg"))
			return true;
		if (path.toLowerCase().endsWith("jpeg"))
			return true;
		return false;
	}

	/***
	 * ��ȡ�ļ����������ļ��ܴ�С
	 * 
	 * @param path
	 * @return
	 */
	public static long getPathSize(String path) {
		File f = new File(path);
		if (!f.exists()) {
			return 0;
		}
		long res = 0;
		File[] lst = f.listFiles();
		for (int i = 0; i < lst.length; i++) {
			if (lst[i].isDirectory()) {
				res += getPathSize(lst[i].getPath());
			} else {
				res += lst[i].length();
			}
		}
		return res;
	}

	/**
	 * �ݹ�ɾ��Ŀ¼�µ������ļ�����Ŀ¼�������ļ�
	 */
	public static boolean deleteDir(String path) {
		File dir = new File(path);
		if (dir.isDirectory()) {
			File[] children = dir.listFiles();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(children[i].getPath());
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
	
	/**
	 * ȡ�ô洢Ŀ¼
	 * @param context
	 * @return
	 */
	public static String GetDir(Context context) {
		File mediaStorageDir = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				context.getString(R.string.app_name) + "_shine/");
		return mediaStorageDir.getPath() + "/";
	}

	/**
	 * ��Uri�л�ȡ��ʵ·��
	 */
	public static String getPathForUri(Uri uri, Context context) {
		String uripath = uri.getPath();
		uri.getPath().startsWith("/");
		Log.i("zwen", uripath);
		if (isPhoto(uripath)) {
			return uripath;
		}
		String[] proj = { MediaStore.Images.Media.DATA };
		// ������android��ý�����ݿ�ķ�װ�ӿڣ�����Ŀ�Android�ĵ�
		Cursor cursor = context.getContentResolver().query(uri, proj, null,
				null, null);
		// ���Ҹ������ ����ǻ���û�ѡ���ͼƬ������ֵ
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		// �����������ͷ ���������Ҫ����С�ĺ���������Խ��
		cursor.moveToFirst();
		// ����������ֵ��ȡͼƬ·��
		String path = cursor.getString(column_index);
		context = null;
		Log.i("zwen", "2" + path);
		return path;
	}

	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * ����view�Ĵ�С
	 * @param child
	 */
	public static void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight, MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * ͼƬ�ü���150*150��
	 * 
	 */

	public static void startPhotoZoom(Activity context, Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// ���òü�
		intent.putExtra("crop", "true");
		// aspectX aspectY �ǿ�ߵı���
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY �ǲü�ͼƬ���
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		context.startActivityForResult(intent, 11);
	}

	/**
	 * ͼƬ��Ҵ���
	 */

	public static Bitmap toGrayscale(Bitmap bitmap) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();

		Bitmap faceIconGreyBitmap = Bitmap.createBitmap(width, height,
				Bitmap.Config.RGB_565);
//		Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(faceIconGreyBitmap);
		Paint paint = new Paint();
		ColorMatrix colorMatrix = new ColorMatrix();
		colorMatrix.setSaturation(0);
		ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(colorMatrix);
		paint.setColorFilter(colorMatrixFilter);
		canvas.drawBitmap(bitmap, 0, 0, paint);
		return faceIconGreyBitmap;
	}
	
	/**
	 * ��asset�ж�ȡͼƬ
	 * 
	 * @param context
	 * @param fileName
	 * @return
	 */
	public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
		Bitmap image = null;
		AssetManager am = context.getResources().getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}

	/**
	 * ѹ��ͼƬ
	 * @param context
	 * @param bgimage
	 * @param size
	 * @return
	 */
	public static Bitmap zoomImage(Context context, Bitmap bgimage, double size) {
		// ��ȡ���ͼƬ�Ŀ�͸�
		float width = bgimage.getWidth();
		float height = bgimage.getHeight();
		// ��������ͼƬ�õ�matrix����
		Matrix matrix = new Matrix();
		// ������������
		float scaleWidth = 0;
		float scaleHeight = 0;
		float dip = dip2px(context, 30);
		if (width > height) {
			scaleWidth = dip / width;
			scaleHeight = height * scaleWidth / height;
		} else {
			scaleHeight = dip / height;
			scaleWidth = width * scaleHeight / width;
		}

		// ����ͼƬ����
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
				(int) height, matrix, true);
		return bitmap;
	}
	
	/**
	 * �����uriתΪString
	 * 
	 * @param uri
	 * @param context
	 * @return
	 */
	public static String getPath(Uri uri, Activity context) {
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(uri, projection,
				null, null, null);
		int column_index = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	/**
	 * ȡ��ȫ�ֵ�һ��ʶ��(ȥ��-)
	 * @return
	 */
	public static String getGuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 *  ���ַ�����������ת��date����
	 * @param style
	 * @param date
	 * @return
	 */
	public static Date strToDate(String style, String date) {
		SimpleDateFormat formatter = new SimpleDateFormat(style);
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	/**
	 *  ��date����ת���ַ�����������
	 * @param style
	 * @param date
	 * @return
	 */
	public static String dateToStr(String style, Date date) {
		SimpleDateFormat formatter = new SimpleDateFormat(style);
		return formatter.format(date);
	}

	public static String clanderTodatetime(Calendar calendar, String style) {
		SimpleDateFormat formatter = new SimpleDateFormat(style);
		return formatter.format(calendar.getTime());
	}

	/**
	 * ��ȡ��ǰ���������ڼ�<br>
	 * 
	 * @param dt
	 * @return ��ǰ���������ڼ�
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "����", "��һ", "�ܶ�", "����", "����", "����", "����" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;
		return weekDays[w];
	}

	/**
	 * 
	 * ��ȡ��ǰʱ���
	 * 
	 */
	public static String getSequenceId() {
		String mark = String.valueOf(System.currentTimeMillis());
		return mark;
	}

	/**
	 * 
	 * ��ȡ��ǰʱ�� ��ʽ��yyyyMMddHHmmss
	 * 
	 */
	public static String getCurrentlyDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(new Date());
	}

	/**
	 * 
	 * ��ȡ��ǰ���� ��ʽ��yyyy/MM/dd HH:mm:ss
	 * 
	 */
	public static String getCurrentlyDate() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(new Date());
	}

	/**
	 * 
	 * ʱ���תʱ�� ��ʽ��yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static String transformDateTime(long t) {
		Date date = new Date(t);
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);
	}

	/**
	 * 
	 * ����ʱ��ת��ʽ��yyyyMMddHHmmss
	 * 
	 */
	public static String TimeTurnDate(String time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sfd = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		try {
			date = sfd.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateFormat.format(date);
	}

	/**
	 * 
	 * ����ʱ��ת��ʽ��yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static String DateTuanTime(String time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sfd = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		try {
			date = sfd.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateFormat.format(date);
	}

	/**
	 * 
	 * ����ʱ��ת��ʽ��yyyyMMddHHmmss
	 * 
	 */
	public static String TimeChangeDate(String time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		try {
			date = sfd.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dateFormat.format(date);
	}

	/**
	 * 
	 * ����ʱ����� ��ʽ��yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static long DateMinusTime(String time1, String time2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d1 = new Date();
		Date d2 = new Date();
		try {
			d1 = dateFormat.parse(time1);
			d2 = dateFormat.parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long diff = d2.getTime() - d1.getTime();// �����õ��Ĳ�ֵ��΢�뼶��
		long days = diff / (1000 * 60 * 60 * 12);
		return days;
	}

	/**
	 * ʱ�䷶Χ
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static String timeRange(Date d1, Date d2) {
		String time = "";
		try {
			// DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long diff = Math.abs(d1.getTime() - d2.getTime());// �����õ��Ĳ�ֵ��΢�뼶��
			long days = Math.abs(diff / (1000 * 60 * 60 * 24));
			long hours = Math.abs((diff - days * (1000 * 60 * 60 * 24))
					/ (1000 * 60 * 60));
			long minutes = Math
					.abs((diff - days * (1000 * 60 * 60 * 24) - hours
							* (1000 * 60 * 60))
							/ (1000 * 60));
			if (days > 0 && hours > 0) {
				System.out.println(days + "��" + hours + "Сʱ");
				time = days + "��" + hours + "Сʱ";
			} else if (days > 0 && hours == 0) {
				System.out.println(days + "��");
				time = days + "��";
			} else if (days == 0 && hours > 0) {
				System.out.println(hours + "Сʱ");
				time = hours + "Сʱ";
			} else if (days == 0 && hours == 0 && minutes >= 0) {
				System.out.println(minutes + "����");
				time = minutes + "����";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 
	 * ʱ��������ڹ�ȥ��� �ոգ�xx����ǰ��xxСʱǰ ��xx��ǰ
	 */

//	public static String howTimeAgo(Context context, long t) {
//		String msg = "";
//		long nowTime = System.currentTimeMillis();
//		long time = (nowTime - t) / (60 * 1000);
//		if (time > 0 && time < 60) {
//			msg = time + context.getString(R.string.minuteago);
//		} else if (time == 0) {
//			msg = context.getString(R.string.at_now);
//		}
//		time = (nowTime - t) / (60 * 1000 * 60);
//		if (time > 0 && time < 24) {
//			msg = time + context.getString(R.string.hourago);
//		}
//		time = (nowTime - t) / (60 * 1000 * 60 * 24);
//		if (time > 0) {
//			msg = time + context.getString(R.string.dayago);
//		}
//		return msg;
//	}
	
	
	/**
	 * �������Ҷ���������γ�Ⱦ���
	 * 
	 * @param lon1
	 *            ��һ��ľ���
	 * @param lat1
	 *            ��һ���γ��
	 * @param lon2
	 *            �ڶ���ľ���
	 * @param lat2
	 *            �ڶ����γ��
	 * @return ���صľ��룬��λkm
	 * */
	public static String transformDistance(double lon1, double lat1,
			double lon2, double lat2) {

		double distance = getDistance(lon1, lat1, lon2, lat2);

		DecimalFormat df = new DecimalFormat("0.00");

		if (distance > 1000) {
			return df.format(distance / 1000) + "����";
		} else {
			MathContext v = new MathContext(0, RoundingMode.HALF_DOWN);
			BigDecimal d = new BigDecimal(distance, v);
			return d.intValue() + "��";
		}

	}

	/**
	 * �������Ҷ���������γ�Ⱦ���
	 * 
	 * @param lon1
	 *            ��һ��ľ���
	 * @param lat1
	 *            ��һ���γ��
	 * @param lon2
	 *            �ڶ���ľ���
	 * @param lat2
	 *            �ڶ����γ��
	 * @return ���صľ��룬��λm
	 * */
	public static double getDistance(double lon1, double lat1, double lon2,
			double lat2) {

		double EARTH_RADIUS = 6378.137;
		double radLat1 = lat1 * Math.PI / 180.0;
		double radLat2 = lat2 * Math.PI / 180.0;
		double a = radLat1 - radLat2;
		double b = lon1 * Math.PI / 180.0 - lon2 * Math.PI / 180.0;

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		s = Math.round(s * 10000) / 10;

		return s;
	}
	
	
	/**
	 * ��ʽ���۸�ǿ�Ʊ���2λС��
	 * @param price
	 * @return
	 */
	public static String formatPrice(double price) {
		DecimalFormat df = new DecimalFormat("0.00");
		String format = "��" + df.format(price);
		return format;
	}
	
	/**
	 * 
	 * �ж��Ƿ�Ϊ�գ�����ȫ���ո�
	 * 
	 */
	public static boolean isEmpty(Object obj) {

		return null == obj || "".equals(obj.toString().trim());
	}

	/**
	 * 
	 * �ж��Ƿ�հ�
	 * 
	 */
	public static boolean isBlank(Object obj) {

		return null == obj || "".equals(obj.toString());
	}

	/**
	 * 
	 * �ж��Ƿ�Ϊ��
	 * 
	 */
	public static boolean isNotEmpty(Object obj) {
		
		return !isEmpty(obj);
	}

}
