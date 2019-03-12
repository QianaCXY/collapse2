package com.example.administrator.collapse;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.ref.PhantomReference;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DateTime";

    private final String FILENAME = "dateTime"; // 文件名
    private final String FILENAME_MILL = "millTime";

    /* 距离上次打开APP时间 */
    private final int FLAG_MORE = 0x01;// 超过半年
    private final int FLAG_MONTH = 0x02;// 1个月-6个月
    private final int FLAG_DAY = 0x03; // 1 天-1个月
    private final int FLAG_HOUR = 0x04;// 1天以下


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvNowTime = findViewById(R.id.tv_now_time);
        TextView tvLastTime = findViewById(R.id.tv_last_time);
        TextView tvCompare = findViewById(R.id.tv_compare);

        tvNowTime.setText("当前时间：" + new Date(getMiTime()));
        tvLastTime.setText("上次保存：" + new Date(loadMiTime()));
        tvCompare.setText(compareMiTime(getMiTime(), loadMiTime()));


        /* *//* 获取时间 *//*
        int[] nowTime = loadDateTime();
        int[] lastTime = getDateTime();
        tvNowTime.setText("当前时间：" + toStr(nowTime));
        tvLastTime.setText("上次保存：" + toStr(lastTime));
        compareDateTime(nowTime,lastTime);*/
    }

    @Override
    protected void onDestroy() {
        saveMiTime(getMiTime());
//        saveDateTime(getDateTime());
        super.onDestroy();
    }


    /*-----------------------------------------
     * 通过System获取系统时间
     *-----------------------------------------  */

    /* 返回一个毫秒数 */
    private long getMiTime() {
        return System.currentTimeMillis();
    }

    /* 保存毫秒数到本地 */
    private void saveMiTime(long miTime) {
        // 给一个文件名，创建一个存储对象
        SharedPreferences pref = getSharedPreferences(FILENAME_MILL, MODE_PRIVATE);
        SharedPreferences.Editor addEditor = pref.edit(); // 开始编辑文件
        addEditor.putLong("mill", miTime); // Key-Value模式添加数据
        addEditor.apply(); //提交数据
    }

    /* 从本地加载上次保存的毫秒数 */
    private long loadMiTime() {
        SharedPreferences pref = getSharedPreferences(FILENAME_MILL, MODE_PRIVATE);
        return pref.getLong("mill", 0);
    }

    /* 比较两个时间差*/
    private String compareMiTime(long nowMiTime, long lastMiTime) {

        String str;

        /* 不需要太精细，就转换为分钟数 */
        long differMill = nowMiTime - lastMiTime; //相差的毫秒
        int differSecond = (int) (differMill / 1000 / 60); // 相差的分钟数

        int oneHour = 60;
        int oneDay = 24 * 60;
        int oneMonth = 30 * oneDay;

        if (differSecond < oneHour) {
            str = "刚刚来过";
        } else if (differSecond < oneDay) {
            int hour = differSecond / 60; // 有多少小时
            str = hour + "小时前来过";
        } else if (differSecond < oneMonth) { // 输出上次打开时间
            int day = differSecond / 60 / 24; // 有多少天？
            str = "上次使用时间是" + day + "天前";
        } else {
            str = "已经很久没有来了";
        }
        return str;
    }

    /*-----------------------------------------
     * 通过Calendar获取系统时间
     *-----------------------------------------  */

    /* 得到系统时间，年月日时分秒 */
   /* private int[] getDateTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        // 获取时间
        int[] dateTime = new int[6];
        dateTime[0] = cal.get(Calendar.YEAR); // 年
        dateTime[1] = cal.get(Calendar.MO NTH) + 1;  // 月
        dateTime[2] = cal.get(Calendar.DATE);  // 日
        dateTime[3] = cal.get(Calendar.HOUR_OF_DAY);  // 时
        dateTime[4] = cal.get(Calendar.MINUTE); // 分
        dateTime[5] = cal.get(Calendar.SECOND); // 秒
        return dateTime;
    }

    *//* 把int[]转换为String，便于TextView显示 *//*
    private String toStr(int[] dateTime) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(dateTime[0] + "" + dateTime[1] + "-" + dateTime[2]);
        strBuilder.append(" ");
        strBuilder.append(dateTime[3] + "-" + dateTime[4] + "-" + dateTime[5]);
        return strBuilder.toString();
    }

    *//* 将数据保存到本地，便于下次启动时保存设置
     * SharedPreferences是利用Key-Value储存的 *//*
    private void saveDateTime(int[] dateTime) {
        // 给一个文件名，创建一个存储对象
        SharedPreferences pref = getSharedPreferences(FILENAME, MODE_PRIVATE);
        SharedPreferences.Editor addEditor = pref.edit(); // 开始编辑文件
        addEditor.putInt("year", dateTime[0]); // Key-Value模式添加数据
        addEditor.putInt("month", dateTime[1]);
        addEditor.putInt("day", dateTime[2]);
        addEditor.putInt("hour", dateTime[3]);
        addEditor.putInt("minute", dateTime[4]);
        addEditor.putInt("second", dateTime[5]);
        addEditor.apply(); //提交数据
    }

    *//* 从本地加载上次保存的数据 *//*
    private int[] loadDateTime() {
        SharedPreferences pref = getSharedPreferences(FILENAME, MODE_PRIVATE);
        int defValue = 0;
        int[] lastDateTime = new int[6];
        lastDateTime[0] = pref.getInt("year", defValue);
        lastDateTime[1] = pref.getInt("month", defValue);
        lastDateTime[2] = pref.getInt("day", defValue);
        lastDateTime[3] = pref.getInt("hour", defValue);
        lastDateTime[4] = pref.getInt("minute", defValue);
        lastDateTime[5] = pref.getInt("second", defValue);
        return lastDateTime;
    }

    private void compareDateTime(int[] now, int[] last) {


    }*/

}
