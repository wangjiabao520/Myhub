package com.example.music;


//����һ�����ֲ��Žӿ�
public interface MusicInterface {
 
    //��������
    void play();
 
    //��ͣ��������
    void pausePlay();
 
    //������������
    void continuePlay();
 
    //�޸����ֵĲ���λ��
    void seekTo(int progress);
}
