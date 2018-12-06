package com.example.music;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
 
public class MainActivity extends Activity {
 
    MyServiceConn conn;
    Intent intent;
    MusicInterface mi;
 
    //�����������ֲ������Ĳ��Ž���
    private static SeekBar sb;
 
    private static TextView tv_progress;
    private static TextView tv_total;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        tv_total = (TextView) findViewById(R.id.tv_total);
 
        //������ͼ����
        intent = new Intent(this, MusicService.class);
 
        //��������
        startService(intent);
 
        //�����������Ӷ���
        conn = new MyServiceConn();
 
        //�󶨷���
        bindService(intent, conn, BIND_AUTO_CREATE);
 
        //��ò����ļ��ϵĻ�����
        sb = (SeekBar) findViewById(R.id.sb);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
        	 
            //���������еĽ��ȸı��,�˷���������
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
 
            }
 
            //�������տ�ʼ����,�˷���������
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
 
            }
 
            //��������ֹͣ����,�˷���������
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
 
                //�����϶��Ľ��ȸı����ֲ��Ž���
                int progress = seekBar.getProgress();
 
                //�ı䲥�Ž���
                mi.seekTo(progress);
            }
        });
    }
 
    //������Ϣ����������
    public static Handler handler = new Handler(){
 
        //�����߳��д�������̷߳��͹�������Ϣ
        @Override
        public void handleMessage(Message msg) {
 
            //��ȡ�����̷߳��͹��������ֲ��ŵĽ���
            Bundle bundle = msg.getData();
 
            //��������ʱ��(����)
            int duration = bundle.getInt("duration");
 
            //�����ĵ�ǰ����(����)
            int currentPostition = bundle.getInt("currentPosition");
 
            //ˢ�»���Ľ���
            sb.setMax(duration);
            sb.setProgress(currentPostition);

            int minute = duration / 1000 / 60;
            int second = duration / 1000 % 60;
 
            String strMinute = null;
            String strSecond = null;
 
            //���������ʱ���еķ���С��10
            if(minute < 10) {
 
                //�ڷ��ӵ�ǰ���һ��0
                strMinute = "0" + minute;
            } else {
 
                strMinute = minute + "";
            }
 
            //���������ʱ���е�����С��10
            if(second < 10)
            {
                //������ǰ���һ��0
                strSecond = "0" + second;
            } else {
 
                strSecond = second + "";
            }
 
            tv_total.setText(strMinute + ":" + strSecond);
 
            //������ǰ����ʱ��
            minute = currentPostition / 1000 / 60;
            second = currentPostition / 1000 % 60;
 
            //���������ʱ���еķ���С��10
            if(minute < 10) {
 
                //�ڷ��ӵ�ǰ���һ��0
                strMinute = "0" + minute;
            } else {
 
                strMinute = minute + "";
            }
 
            //���������ʱ���е�����С��10
            if(second < 10) {
            	  strSecond = "0" + second;
            } else {
 
                strSecond = second + "";
            }
 
            tv_progress.setText(strMinute + ":" + strSecond);
        }
    };
 
    //�������ְ�ť��Ӧ����
    public void play(View view) {
 
        //��������
        mi.play();
    }
 
    //��ͣ�������ְ�ť��Ӧ����
    public void pausePlay(View view) {
 
        //��ͣ��������
        mi.pausePlay();
    }
 
    //�����������ְ�ť��Ӧ����
    public void continuePlay (View view) {
 
        //������������
        mi.continuePlay();
    }
 
    //�˳����ֲ��Ű�ť��Ӧ����
    public void exit(View view) {
 
        //������
        unbindService(conn);
 
        //ֹͣ����
        stopService(intent);
 
        //�������activity
        finish();
    }

    class MyServiceConn implements ServiceConnection {
    	 
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
 
            //����м��˶���
            mi = (MusicInterface) service;
        }
 
        @Override
        public void onServiceDisconnected(ComponentName name) {
 
        }
    }
}
