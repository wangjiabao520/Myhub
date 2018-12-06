package com.example.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
 
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
 
//����һ���̳��Է�������ַ�����
public class MusicService extends Service {
 
    private MediaPlayer player;
    private Timer timer;
 
    //�󶨷���ʱ,���ô˷���
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
 
        return new MusicControl();
    }
 
    //�����������ֵķ���
    @Override
    public void onCreate() {
        super.onCreate();
 
        //�������ֲ���������
        player = new MediaPlayer();
    }
 
    //���ٲ������ַ���
    @Override
    public void onDestroy() {
        super.onDestroy();
 
        //ֹͣ��������
        player.stop();
 
        //�ͷ�ռ�õ���Դ
        player.release();
 
        //��player��Ϊ��
        player = null;
    }
    public void play() {
    	 
        try {
 
            if(player == null)
            {
                player = new MediaPlayer();
            }
 
            //����
            player.reset();
 
            //���ض�ý���ļ�
            player.setDataSource("sdcard/zxmzf.mp3");
 
            //׼����������
            player.prepare();
 
            //��������
            player.start();
 
            //��Ӽ�ʱ��
            addTimer();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    //��ͣ��������
    public void pausePlay() {
 
        player.pause();
    }
 
    //������������
    public void continuePlay() {
 
        player.start();
    }
    class MusicControl extends Binder implements MusicInterface {
    	 
        public void play() {
 
            MusicService.this.play();
        }
 
      
        public void pausePlay() {
 
            MusicService.this.pausePlay();
        }
 

        public void continuePlay() {
 
            MusicService.this.continuePlay();
        }
 
        public void seekTo(int progress) {
 
            MusicService.this.seekTo(progress);
        }
    }
 
    //�������ֵĲ���λ��
    public void seekTo(int progress) {
 
        player.seekTo(progress);
    }
 
    //��Ӽ�ʱ�������������ֲ������еĲ��Ž���
    public void addTimer() {
 
        //���û�д�����ʱ������
        if(timer == null) {
 
            //������ʱ������
            timer = new Timer();
 
            timer.schedule(new TimerTask() {
 
                //ִ�м�ʱ����
                @Override
                public void run() {

                	int duration = player.getDuration();
                	 
                    //��ø����ĵ�ǰ���Ž���
                    int currentPosition = player.getCurrentPosition();
 
                    //������Ϣ����
                    Message msg = MainActivity.handler.obtainMessage();
 
                    //�����ֵĲ��Ž��ȷ�װ����Ϣ������
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration", duration);
                    bundle.putInt("currentPosition", currentPosition);
                    msg.setData(bundle);
 
                    //����Ϣ���͵����̵߳���Ϣ����
                    MainActivity.handler.sendMessage(msg);
                }
            },
 
            //��ʼ��ʱ������5���룬��һ��ִ��run�������Ժ�ÿ500����ִ��һ��
            5, 500);
        }
    }
}
