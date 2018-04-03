package io.agora.openlive;

import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceView;

import com.facebook.react.bridge.ReadableMap;

import java.util.ArrayList;
import java.util.List;

import io.agora.AgoraAPIOnlySignal;
import io.agora.IAgoraAPI;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.video.VideoCanvas;
import io.agora.videoprp.AgoraYuvEnhancer;

/**
 * AgoraManager
 *
 * @author learnta
 * @version 1.0
 * @createDate 2017/12/21
 * @lastUpdate 2017/12/21
 */
public class AgoraManager {

    public static AgoraManager sAgoraManager;

    public RtcEngine mRtcEngine;

    public AgoraAPIOnlySignal mSignalAPI;

    private AgoraYuvEnhancer yuvEnhancer;

    private Context context;

    private int mLocalUid = 0;
    private boolean isBroadcaster;
    private  String APP_ID="";

    private AgoraManager() {
        mSurfaceViews = new SparseArray<SurfaceView>();
    }

    private SparseArray<SurfaceView> mSurfaceViews;

    public enum AgoraRtcClientRole {

        Broadcaster(1), Audience(2);

        private int value;

        AgoraRtcClientRole(int value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return String.valueOf(this.value);
        }

    }

    public static AgoraManager getInstance() {
        if (sAgoraManager == null) {
            synchronized (AgoraManager.class) {
                if (sAgoraManager == null) {
                    sAgoraManager = new AgoraManager();
                }
            }
        }
        return sAgoraManager;
    }

    /**
     * 初始化信令通道
     */
    public void signal_init(Context context, IAgoraAPI.ICallBack mEventHandler, ReadableMap options) {
        this.context = context;
        String appid = options.getString("appid");
        APP_ID=appid;
        //创建RtcEngine对象，mRtcEventHandler为RtcEngine的回调
        try {
            mSignalAPI= AgoraAPIOnlySignal.getInstance(context,appid);
            mSignalAPI.callbackSet(mEventHandler);
        } catch (Exception e) {
            throw new RuntimeException("NEED TO check signal api init fatal error\n" + Log.getStackTraceString(e));
        }
    }

    /*
    登录进入信令通道
    * appId, account, token, uid, deviceID, retry_time_in_s, retry_count
     */
    public  void signal_login2(String account, String token, int uid, String deviceID, int retry_time_in_s, int retry_count) {
        this.mSignalAPI.login2(this.APP_ID, account, token, uid, deviceID, retry_time_in_s, retry_count);
    }

    /*
    退出信令通道
     */
    public void signal_logout() {
        this.mSignalAPI.logout();
    }
    /*
    进入频道
     */
    public void signal_channelJoin(String channelName) {
        this.mSignalAPI.channelJoin(channelName);
    }
    /*
    离开频道
     */
    public void signal_channelLeave(String channelName) {
        this.mSignalAPI.channelLeave(channelName);
    }
    /*
    发送频道消息
    */
    public void signal_messageChannelSend(String channelName, String msg, String msgID) {
        this.mSignalAPI.messageChannelSend(channelName, msg, msgID);
    }
    /*
    发送点对点消息
     */
    public void signal_messageInstantSend(String account,int uid,String msg,String msgID) {
        this.mSignalAPI.messageInstantSend(account, uid, msg, msgID);
    }
    /*
        查询用户频道状态
         */
    public void queryUserStatus(String account){
        this.mSignalAPI.queryUserStatus(account);
    }
    /*
    查询用户频道状态
     */
    public void channelQueryUserIsIn(String channelName, String account){
        this.mSignalAPI.channelQueryUserIsIn(channelName,account);
    }
    /*
            设置频道属性
             */
    public void channelSetAttr(String channelName,String attrName,String attrValue){
        this.mSignalAPI.channelSetAttr(channelName,attrName,attrValue);
    }
    /*
        删除频道属性
         */
    public void channelDelAttr(String channelName,String attrName){
        this.mSignalAPI.channelDelAttr(channelName,attrName);
    }
    /*
        清除频道属性
         */
    public void channelClearAttr(String channelName){
        this.mSignalAPI.channelClearAttr(channelName);
    }

    /*
        设置用户属性
         */
    public void setAttr(String name,String value){
        this.mSignalAPI.setAttr(name,value);
    }
    /*
        获取用户全部属性
         */
    public void getUserAttr(String account,String attrName){
        this.mSignalAPI.getUserAttr(account,attrName);
    }
    /*
        获取用户属性
         */
    public void getUserAttrAll(String account){
        this.mSignalAPI.getUserAttrAll(account);
    }

    /**
     * 初始化RtcEngine
     */
    public void init(Context context, IRtcEngineEventHandler mRtcEventHandler, ReadableMap options) {
        this.context = context;

        String appid = options.getString("appid");
        int videoProfile = options.getInt("videoProfile");
        boolean swapWidthAndHeight = options.getBoolean("swapWidthAndHeight");
        int channelProfile = options.getInt("channelProfile");
        int role = options.getInt("clientRole");

        //创建RtcEngine对象，mRtcEventHandler为RtcEngine的回调
        try {
            mRtcEngine = RtcEngine.create(context, appid, mRtcEventHandler);
        } catch (Exception e) {
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }

        //开启视频功能
        mRtcEngine.enableVideo();
        mRtcEngine.setVideoProfile(videoProfile, swapWidthAndHeight); //视频配置，
        mRtcEngine.enableWebSdkInteroperability(true);  //设置和web通信
        mRtcEngine.setChannelProfile(channelProfile); //设置模式
        mRtcEngine.setClientRole(role); //设置角色

        isBroadcaster = (role == AgoraRtcClientRole.Broadcaster.value);

        // 打开美颜
        openBeautityFace();
    }

    /**
     * 设置本地视频，即前置摄像头预览
     */
    public AgoraManager setupLocalVideo() {
        //创建一个SurfaceView用作视频预览
        SurfaceView surfaceView = RtcEngine.CreateRendererView(context);
        //将SurfaceView保存起来在SparseArray中，后续会将其加入界面。key为视频的用户id，这里是本地视频, 默认id是0

        mSurfaceViews.put(mLocalUid, surfaceView);

        //设置本地视频，渲染模式选择VideoCanvas.RENDER_MODE_HIDDEN，如果选其他模式会出现视频不会填充满整个SurfaceView的情况，
        //具体渲染模式参考官方文档https://docs.agora.io/cn/user_guide/API/android_api.html#set-local-video-view-setuplocalvideo
        mRtcEngine.setupLocalVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, mLocalUid));
        return this;//返回AgoraManager以作链式调用
    }

    public AgoraManager setupRemoteVideo(int uid) {
        SurfaceView surfaceView = RtcEngine.CreateRendererView(context);
        mSurfaceViews.put(uid, surfaceView);
        mRtcEngine.setupRemoteVideo(new VideoCanvas(surfaceView, VideoCanvas.RENDER_MODE_HIDDEN, uid));
        return this;
    }

    public AgoraManager joinChannel(String channel, int uid) {
        mRtcEngine.joinChannel(null, channel, null, uid);
        return this;
    }

    public void leaveChannel() {
        mRtcEngine.leaveChannel();
        // 主播，关闭预览
        if (isBroadcaster)
            this.stopPreview();
        // 关闭美颜
        closeBeautityFace();
    }

    public void startPreview() {
        mRtcEngine.startPreview();
    }

    public void stopPreview() {
        mRtcEngine.stopPreview();
    }

    /**
     * 打开美颜
     */
    private void openBeautityFace() {
        if (null == yuvEnhancer) {
            yuvEnhancer = new AgoraYuvEnhancer(context);
            yuvEnhancer.StartPreProcess();
        }
    }

    /**
     * 关闭美颜
     */
    private void closeBeautityFace() {
        if (null != yuvEnhancer) {
            yuvEnhancer.StopPreProcess();
            yuvEnhancer = null;
        }
    }

    /**
     * 改变角色
     */
    public void changeRole() {
        int role = this.isBroadcaster ? AgoraRtcClientRole.Audience.value : AgoraRtcClientRole.Broadcaster.value;
        this.isBroadcaster = (role == AgoraRtcClientRole.Broadcaster.value);
        this.mRtcEngine.setClientRole(role); //设置角色
    }

    public void removeSurfaceView(int uid) {
        mSurfaceViews.remove(uid);
    }

    public List<SurfaceView> getSurfaceViews() {
        List<SurfaceView> list = new ArrayList<SurfaceView>();
        for (int i = 0; i < mSurfaceViews.size(); i++) {
            SurfaceView surfaceView = mSurfaceViews.valueAt(i);
            list.add(surfaceView);
        }
        return list;
    }

    public SurfaceView getLocalSurfaceView() {
        return mSurfaceViews.get(mLocalUid);
    }

    public SurfaceView getSurfaceView(int uid) {
        return mSurfaceViews.get(uid);
    }

}
