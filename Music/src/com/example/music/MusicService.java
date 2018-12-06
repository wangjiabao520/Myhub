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
 
//创建一个继承自服务的音乐服务类
public class MusicService extends Service {
 
    private MediaPlayer player;
    private Timer timer;
 
    //绑定服务时,调用此方法
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
 
        return new MusicControl();
    }
 
    //创建播放音乐的服务
    @Override
    public void onCreate() {
        super.onCreate();
 
        //创建音乐播放器对象
        player = new MediaPlayer();
    }
 
    //销毁播放音乐服务
    @Override
    public void onDestroy() {
        super.onDestroy();
 
        //停止播放音乐
        player.stop();
 
        //释放占用的资源
        player.release();
 
        //将player置为空
        player = null;
    }
    public void play() {
    	 
        try {
 
            if(player == null)
            {
                player = new MediaPlayer();
            }
 
            //重置
            player.reset();
 
            //加载多媒体文件
            player.setDataSource("sdcard/zxmzf.mp3");
 
            //准备播放音乐
            player.prepare();
 
            //播放音乐
            player.start();
 
            //添加计时器
            addTimer();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    //暂停播放音乐
    public void pausePlay() {
 
        player.pause();
    }
 
    //继续播放音乐
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
 
    //设置音乐的播放位置
    public void seekTo(int progress) {
 
        player.seekTo(progress);
    }
 
    //添加计时器用于设置音乐播放器中的播放进度
    public void addTimer() {
 
        //如果没有创建计时器对象
        if(timer == null) {
 
            //创建计时器对象
            timer = new Timer();
 
            timer.schedule(new TimerTask() {
 
                //执行计时任务
                @Override
                public void run() {

                	int duration = player.getDuration();
                	 
                    //获得歌曲的当前播放进度
                    int currentPosition = player.getCurrentPosition();
 
                    //创建消息对象
                    Message msg = MainActivity.handler.obtainMessage();
 
                    //将音乐的播放进度封装至消息对象中
                    Bundle bundle = new Bundle();
                    bundle.putInt("duration", duration);
                    bundle.putInt("currentPosition", currentPosition);
                    msg.setData(bundle);
 
                    //将消息发送到主线程的消息队列
                    MainActivity.handler.sendMessage(msg);
                }
            },
 
            //开始计时任务后的5毫秒，第一次执行run方法，以后每500毫秒执行一次
            5, 500);
        }
    }
}
