package com.hon.conquer.util;

import android.content.DialogInterface;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.TimeZone;

import androidx.fragment.app.Fragment;

/**
 * Created by Frank on 2018/2/5.
 * E-mail:frank_hon@foxmail.com
 */

public class CalendarUtil {
    private int mYear,mMonth,mDay;
    private DatePickerDialog mDialog=null;

    private Fragment mTargetFragment;
    private DialogInterface.OnDismissListener mOnDismissListener;
    private OnDateSetListener mOnDateSetListener;

    public CalendarUtil(Fragment target){
        Calendar c=Calendar.getInstance(TimeZone.getTimeZone("GMT+08"));
        mYear=c.get(Calendar.YEAR);
        mMonth=c.get(Calendar.MONTH);
        mDay=c.get(Calendar.DAY_OF_MONTH);

        mTargetFragment=target;
    }

    public void showDatePickerDialog(){

        DatePickerDialog.OnDateSetListener callback=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                mYear=year;
                mMonth=monthOfYear;
                mDay=dayOfMonth;

                mOnDateSetListener.onDateSet(view, year, monthOfYear, dayOfMonth);
            }
        };

        if(mDialog==null){
            mDialog=DatePickerDialog.newInstance(callback,mYear,mMonth,mDay);

            if(mOnDismissListener!=null)
                mDialog.setOnDismissListener(mOnDismissListener);
            mDialog.setMaxDate(Calendar.getInstance());

            Calendar minCalendar=Calendar.getInstance();
            minCalendar.set(2013,5,20);
            mDialog.setMinDate(minCalendar);

            mDialog.vibrate(false);
        }else {
            mDialog.initialize(callback,mYear,mMonth,mDay);
        }

        mDialog.show(mTargetFragment.getActivity().getFragmentManager(),mTargetFragment.getClass().getSimpleName());

    }

    public String getCurrentDate() {
        Calendar c=Calendar.getInstance();
        c.set(mYear, mMonth, mDay);
        return DateFormatUtil.formatTimeToString(c.getTimeInMillis());
    }

    public String getLastDay(int lastDayCount){
        Calendar c=Calendar.getInstance();
        c.set(mYear, mMonth, mDay);
        return DateFormatUtil.formatTimeToString(c.getTimeInMillis()-24*60*60*1000*lastDayCount);
    }

    public static String getSelectedDate(int year,int month,int day){
        Calendar c=Calendar.getInstance();
        c.set(year, month, day);
        return DateFormatUtil.formatTimeToString(c.getTimeInMillis());
    }
    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener){
        mOnDismissListener=onDismissListener;
    }

    public void setOnDateSetListener(OnDateSetListener onDateSetListener){
        mOnDateSetListener=onDateSetListener;
    }

    public interface OnDateSetListener{
        void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth);
    }
}
