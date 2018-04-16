package io.agora.openlive;

import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import io.agora.IAgoraAPI;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.PublisherConfiguration;
import io.agora.rtc.RtcEngine;

import static com.facebook.react.bridge.UiThreadUtil.runOnUiThread;

/**
 * AgoraModule
 *
 * @author learnta
 * @version 1.0
 * @createDate 2017/12/21
 * @lastUpdate 2017/12/21
 */
public class AgoraModule extends ReactContextBaseJavaModule {

    public AgoraModule(ReactApplicationContext context) {
        super(context);
    }

    @Override
    public String getName() {
        return "RCTAgora";
    }

    private IAgoraAPI.ICallBack mSignalEventHander=new IAgoraAPI.ICallBack() {
        @Override
        public void onReconnecting(final int nretry) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onReconnecting");
                    map.putInt("nretry", nretry);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onReconnected(final int fd) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onReconnected");
                    map.putInt("fd", fd);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onLoginSuccess(final int uid, final int fd) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onLoginSuccess");
                    map.putInt("uid", uid);
                    map.putInt("fd", fd);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onLogout(final int ecode) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onLogout");
                    map.putInt("ecode", ecode);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onLoginFailed(final int ecode) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onLoginFailed");
                    map.putInt("ecode", ecode);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onChannelJoined(final String channelID) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onChannelJoined");
                    map.putString("channelID", channelID);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onChannelJoinFailed(final String channelID,final int ecode) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onChannelJoinFailed");
                    map.putString("channelID", channelID);
                    map.putInt("ecode", ecode);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onChannelLeaved(final String channelID,final int ecode) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onChannelLeaved");
                    map.putString("channelID", channelID);
                    map.putInt("ecode", ecode);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onChannelUserJoined(final String account,final int uid) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onChannelUserJoined");
                    map.putString("account", account);
                    map.putInt("uid", uid);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onChannelUserLeaved(final String account,final int uid) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onChannelUserLeaved");
                    map.putString("account", account);
                    map.putInt("uid", uid);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onChannelUserList(final String[] accounts, final int[] uids) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableArray arr = Arguments.createArray();
                    for (int i = 0; i < accounts.length; i++) {
                        arr.pushString(accounts[i]);
                    }
                    WritableArray arr2 = Arguments.createArray();
                    for (int i = 0; i < uids.length; i++) {
                        arr2.pushInt(uids[i]);
                    }

                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onChannelUserList");
                    map.putArray("accounts", arr);
                    map.putArray("uids", arr2);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onChannelQueryUserNumResult(final String channelID,final int ecode,final int num) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onChannelQueryUserNumResult");
                    map.putString("channelID", channelID);
                    map.putInt("ecode", ecode);
                    map.putInt("num", num);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onChannelQueryUserIsIn(String s, String s1, int i) {

        }

        /*
        channelID	频道名
name	属性名
value	属性值
type	变化类型:
“update”：更新
“del”：删除
“clear”：全部删除
“set”: 废弃类型
         */
        @Override
        public void onChannelAttrUpdated(final String channelID,final String name,final String value,final String type) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onChannelAttrUpdated");
                    map.putString("channelID", channelID);
                    map.putString("name", name);
                    map.putString("value", value);
                    map.putString("type", type);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onInviteReceived(final String channelID,final String account,final int uid,final String extra) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onInviteReceived");
                    map.putString("channelID", channelID);
                    map.putString("account", account);
                    map.putInt("uid", uid);
                    map.putString("extra", extra);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onInviteReceivedByPeer(final String channelID,final String account,final int uid) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onInviteReceivedByPeer");
                    map.putString("channelID", channelID);
                    map.putString("account", account);
                    map.putInt("uid", uid);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onInviteAcceptedByPeer(final String channelID,final String account,final int uid,final String extra) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onInviteAcceptedByPeer");
                    map.putString("channelID", channelID);
                    map.putString("account", account);
                    map.putInt("uid", uid);
                    map.putString("extra", extra);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onInviteRefusedByPeer(final String channelID,final String account,final int uid,final String extra) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onInviteRefusedByPeer");
                    map.putString("channelID", channelID);
                    map.putString("account", account);
                    map.putInt("uid", uid);
                    map.putString("extra", extra);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onInviteFailed(final String channelID,final String account,final int uid,final int ecode,final String extra) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onInviteFailed");
                    map.putString("channelID", channelID);
                    map.putString("account", account);
                    map.putInt("uid", uid);
                    map.putInt("ecode", ecode);
                    map.putString("extra", extra);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onInviteEndByPeer(final String channelID,final String account,final int uid,final String extra) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onInviteEndByPeer");
                    map.putString("channelID", channelID);
                    map.putString("account", account);
                    map.putInt("uid", uid);
                    map.putString("extra", extra);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onInviteEndByMyself(final String channelID,final String account,final int uid) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onInviteEndByMyself");
                    map.putString("channelID", channelID);
                    map.putString("account", account);
                    map.putInt("uid", uid);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onInviteMsg(final String channelID,final  String account,final  int uid,final  String msgType,final  String msgData,final  String extra) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onInviteMsg");
                    map.putString("channelID", channelID);
                    map.putString("account", account);
                    map.putInt("uid", uid);
                    map.putString("msgType", msgType);
                    map.putString("msgData", msgData);
                    map.putString("extra", extra);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onMessageSendError(final String messageID,final int ecode) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onMessageSendError");
                    map.putString("messageID", messageID);
                    map.putInt("ecode", ecode);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onMessageSendProgress(String s, String s1, String s2, String s3) {

        }

        @Override
        public void onMessageSendSuccess(final String messageID) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onMessageSendSuccess");
                    map.putString("messageID", messageID);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onMessageAppReceived(String s) {

        }

        @Override
        public void onMessageInstantReceive(final String account,final int uid,final String msg) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onMessageInstantReceive");
                    map.putString("account", account);
                    map.putInt("uid", uid);
                    map.putString("msg", msg);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onMessageChannelReceive(final String channelID,final String account,final int uid,final String msg) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onMessageChannelReceive");
                    map.putString("channelID", channelID);
                    map.putString("account", account);
                    map.putInt("uid", uid);
                    map.putString("msg", msg);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onLog(final String txt) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onLog");
                    map.putString("txt", txt);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onInvokeRet(final String callID, final String err, final String resp) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onInvokeRet");
                    map.putString("callID", callID);
                    map.putString("err", err);
                    map.putString("resp", resp);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onMsg(String s, String s1, String s2) {

        }

        @Override
        public void onUserAttrResult(final String account,final String name,final String value) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onUserAttrResult");
                    map.putString("account", account);
                    map.putString("name", name);
                    map.putString("value", value);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onUserAttrAllResult(final String account,final String value) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onUserAttrAllResult");
                    map.putString("account", account);
                    map.putString("value", value);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onError(final String name,final int ecode,final String desc) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onError");
                    map.putString("name", name);
                    map.putInt("ecode", ecode);
                    map.putString("desc", desc);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onQueryUserStatusResult(final String account,final String status) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onQueryUserStatusResult");
                    map.putString("name", account);
                    map.putString("status", status);
                    signalcommonEvent(map);
                }
            });
        }

        @Override
        public void onDbg(String s, byte[] bytes) {

        }

        @Override
        public void onBCCall_result(String s, String s1, String s2) {

        }
    };

    //信令系统初始化
    @ReactMethod
    public void signal_init(ReadableMap options) {
        AgoraManager.getInstance().signal_init(getReactApplicationContext(), mSignalEventHander, options);
    }
    //登录信令系统
    @ReactMethod
    public void signal_login2(String account, String token, int uid, String deviceID, int retry_time_in_s, int retry_count) {
        AgoraManager.getInstance().signal_login2(account,token,uid,deviceID,retry_time_in_s,retry_count);
    }

    //退出信令系统
    @ReactMethod
    public void signal_logout() {
        AgoraManager.getInstance().signal_logout();
    }

    //加入频道
    @ReactMethod
    public void signal_channelJoin(String channelName) {
        AgoraManager.getInstance().signal_channelJoin(channelName);
    }

    //离开频道
    @ReactMethod
    public void signal_channelLeave(String channelName) {
        AgoraManager.getInstance().signal_channelLeave(channelName);
    }

    //发送频道消息
    @ReactMethod
    public void signal_messageChannelSend(String channelName, String msg, String msgID)  {
        AgoraManager.getInstance().signal_messageChannelSend(channelName,msg,msgID);
    }
    //发送点对点消息
    @ReactMethod
    public void signal_messageInstantSend(String account,int uid,String msg,String msgID)   {
        AgoraManager.getInstance().signal_messageInstantSend(account,uid,msg,msgID);
    }
    /*
        查询用户频道状态
         */
    @ReactMethod
    public void queryUserStatus(String account){
        AgoraManager.getInstance().queryUserStatus(account);
    }
    /*
    查询用户频道状态
     */
    @ReactMethod
    public void channelQueryUserIsIn(String channelName, String account){
        AgoraManager.getInstance().channelQueryUserIsIn(channelName,account);
    }
    /*
            设置频道属性
             */
    @ReactMethod
    public void channelSetAttr(String channelName,String attrName,String attrValue){
        AgoraManager.getInstance().channelSetAttr(channelName,attrName,attrValue);
    }
    /*
        删除频道属性
         */
    @ReactMethod
    public void channelDelAttr(String channelName,String attrName){
        AgoraManager.getInstance().channelDelAttr(channelName,attrName);
    }
    /*
        清除频道属性
         */
    @ReactMethod
    public void channelClearAttr(String channelName){
        AgoraManager.getInstance().channelClearAttr(channelName);
    }

    /*
        设置用户属性
         */
    @ReactMethod
    public void setAttr(String name,String value){
        AgoraManager.getInstance().setAttr(name,value);
    }
    /*
        获取用户全部属性
         */
    @ReactMethod
    public void getUserAttr(String account,String attrName){
        AgoraManager.getInstance().getUserAttr(account,attrName);
    }
    /*
        获取用户属性
         */
    @ReactMethod
    public void getUserAttrAll(String account){
        AgoraManager.getInstance().getUserAttrAll(account);
    }


    private IRtcEngineEventHandler mRtcEventHandler = new IRtcEngineEventHandler() {

        /**
         * 当获取用户uid的远程视频的回调
         */
        @Override
        public void onFirstRemoteVideoDecoded(final int uid, final int width, final int height, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onFirstRemoteVideoDecoded");
                    map.putInt("uid", uid);
                    map.putInt("width", width);
                    map.putInt("height", height);
                    commonEvent(map);
                }
            });
        }

        /**
         * 远端视频显示回调
         * @param uid
         * @param width
         * @param height
         * @param elapsed
         */
        @Override
        public void onFirstRemoteVideoFrame(final int uid, final int width, final int height, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onFirstRemoteVideoFrameOfUid");
                    map.putInt("uid", uid);
                    map.putInt("width", width);
                    map.putInt("height", height);
                    commonEvent(map);
                }
            });
        }

        /**
         * 本地视频显示回调
         *
         * @param width
         * @param height
         * @param elapsed
         */
        @Override
        public void onFirstLocalVideoFrame(final int width, final int height, int elapsed) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onFirstLocalVideoFrameWithSize");
                    map.putInt("width", width);
                    map.putInt("height", height);
                    commonEvent(map);
                }
            });
        }

        /**
         * 加入频道成功的回调
         */
        @Override
        public void onJoinChannelSuccess(final String channel, final int uid, int elapsed) {
            Log.i("Agora", "加入房间成功---");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onJoinChannelSuccess");
                    map.putString("channel", channel);
                    map.putInt("uid", uid);
                    commonEvent(map);
                }
            });
        }

        /**
         * 重新加入频道回调
         *
         * @param channel
         * @param uid
         * @param elapsed
         */
        @Override
        public void onRejoinChannelSuccess(final String channel, final int uid, int elapsed) {
            Log.i("Agora", "重新加入频道成功---");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onRejoinChannelSuccess");
                    map.putString("channel", channel);
                    map.putInt("uid", uid);
                    commonEvent(map);
                }
            });
        }

        /**
         * 其他用户加入当前频道
         */
        @Override
        public void onUserJoined(final int uid, int elapsed) {
            Log.i("Agora", "有人来了----");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onUserJoined");
                    map.putInt("uid", uid);
                    commonEvent(map);
                }
            });
        }

        /**
         * 说话声音音量提示回调
         */
        @Override
        public void onAudioVolumeIndication(final AudioVolumeInfo[] speakers,
                                            final int totalVolume) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableArray arr = Arguments.createArray();
                    for (int i = 0; i < speakers.length; i++) {
                        WritableMap obj = Arguments.createMap();
                        obj.putInt("uid", speakers[i].uid);
                        obj.putInt("volume", speakers[i].volume);
                        arr.pushMap(obj);
                    }

                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onAudioVolumeIndication");
                    map.putArray("speakers", arr);
                    map.putInt("totalVolume", totalVolume);
                    commonEvent(map);
                }
            });
        }

        /**
         * 网络质量反馈
         */
        @Override
        public void onNetworkQuality(final int uid, final int txQuality, final int rxQuality) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onNetworkQuality");
                    map.putInt("uid", uid);
                    map.putInt("txQuality", txQuality);
                    map.putInt("rxQuality", rxQuality);
                    commonEvent(map);
                }
            });
        }

        /*
        音频质量
        * */
        @Override
        public void onAudioQuality(final int uid, final int quality, final short delay,final short lost) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onAudioQuality");
                    map.putInt("uid", uid);
                    map.putInt("quality", quality);
                    map.putInt("delay", delay);
                    map.putInt("lost", lost);
                    commonEvent(map);
                }
            });
        }

        /**
         * 错误信息
         */
        @Override
        public void onError(final int err) {
            Log.i("Agora", err + "错误---");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onError");
                    map.putInt("err", err);
                    commonEvent(map);
                }
            });
        }

        /**
         * 警告
         */
        @Override
        public void onWarning(final int warn) {
            Log.i("Agora", warn + "警告---");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onWarning");
                    map.putInt("warn", warn);
                    commonEvent(map);
                }
            });
        }

        /**
         * 退出频道
         */
        @Override
        public void onLeaveChannel(RtcStats stats) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onLeaveChannel");
                    commonEvent(map);
                }
            });
        }

        /**
         * 用户uid离线时的回调
         */
        @Override
        public void onUserOffline(final int uid, int reason) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onUserOffline");
                    map.putInt("uid", uid);
                    commonEvent(map);
                }
            });
        }

        /**
         * 连接丢失回调
         */
        @Override
        public void onConnectionLost() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onConnectionDidLost");
                    commonEvent(map);
                }
            });
        }

        /**
         * 连接中断回调
         */
        @Override
        public void onConnectionInterrupted() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onConnectionDidInterrupted");
                    commonEvent(map);
                }
            });
        }

        /**
         * 连接已被禁止回调
         */
        @Override
        public void onConnectionBanned() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    WritableMap map = Arguments.createMap();
                    map.putString("type", "onConnectionDidBanned");
                    commonEvent(map);
                }
            });
        }
    };

    @ReactMethod
    public void init(ReadableMap options) {
        AgoraManager.getInstance().init(getReactApplicationContext(), mRtcEventHandler, options);
    }

    //进入房间
    @ReactMethod
    public void joinChannel(String channelNamekey,String channelName, int uid) {
        AgoraManager.getInstance().joinChannel(channelNamekey,channelName, uid);
    }

    //退出
    @ReactMethod
    public void leaveChannel() {
        AgoraManager.getInstance().stopPreview();
        AgoraManager.getInstance().leaveChannel();
    }

    //开启预览
    @ReactMethod
    public void startPreview() {
        AgoraManager.getInstance().startPreview();
    }

    //闭关预览
    @ReactMethod
    public void stopPreview() {
        AgoraManager.getInstance().stopPreview();
    }

    //配置旁路直播推流
    @ReactMethod
    public void configPublisher(ReadableMap options) {
        PublisherConfiguration config = new PublisherConfiguration.Builder()
                .owner(options.getBoolean("owner"))
                .size(options.getInt("width"), options.getInt("height"))
                .frameRate(options.getInt("framerate"))
//                .biteRate(options.getInt("bitrate"))
                .defaultLayout(options.getInt("defaultLayout"))
                .streamLifeCycle(options.getInt("lifeCycle"))
                .rawStreamUrl(options.getString("rawStreamUrl"))
                .publishUrl(options.getString("publishUrl"))
                .extraInfo(options.getString("extraInfo"))
                .build();

        AgoraManager.getInstance().mRtcEngine.configPublisher(config);
    }

    //设置本地视频显示模式
    @ReactMethod
    public void setLocalRenderMode(int mode) {
        AgoraManager.getInstance().mRtcEngine.setLocalRenderMode(mode);
    }

    //设置远端视频显示模式
    @ReactMethod
    public void setRemoteRenderMode(int uid, int mode) {
        AgoraManager.getInstance().mRtcEngine.setRemoteRenderMode(uid, mode);
    }

    //启用说话者音量提示
    @ReactMethod
    public void enableAudioVolumeIndication(int interval, int smooth) {
        AgoraManager.getInstance().mRtcEngine.enableAudioVolumeIndication(interval, smooth);
    }

    //打开音频
    @ReactMethod
    public void enableAudio() {
        AgoraManager.getInstance().mRtcEngine.enableAudio();
    }

    //关闭音频
    @ReactMethod
    public void disableAudio() {
        AgoraManager.getInstance().mRtcEngine.disableAudio();
    }

    //打开视频
    @ReactMethod
    public void enableVideo() {
        AgoraManager.getInstance().mRtcEngine.enableVideo();
    }

    //关闭视频
    @ReactMethod
    public void disableVideo() {
        AgoraManager.getInstance().mRtcEngine.disableVideo();
    }

    //切换前置/后置摄像头
    @ReactMethod
    public void switchCamera() {
        AgoraManager.getInstance().mRtcEngine.switchCamera();
    }

    //打开扬声器
    @ReactMethod
    public void setEnableSpeakerphone(boolean enabled) {
        AgoraManager.getInstance().mRtcEngine.setEnableSpeakerphone(enabled);
    }

    //将自己静音
    @ReactMethod
    public void muteLocalAudioStream(boolean muted) {
        AgoraManager.getInstance().mRtcEngine.muteLocalAudioStream(muted);
    }

    //静音所有远端音频
    @ReactMethod
    public void muteAllRemoteAudioStreams(boolean muted) {
        AgoraManager.getInstance().mRtcEngine.muteAllRemoteAudioStreams(muted);
    }

    //静音指定用户音频
    @ReactMethod
    public void muteRemoteAudioStream(int uid, boolean muted) {
        AgoraManager.getInstance().mRtcEngine.muteRemoteAudioStream(uid, muted);
    }

    //禁用本地视频功能
    @ReactMethod
    public void enableLocalVideo(boolean enabled) {
        AgoraManager.getInstance().mRtcEngine.enableLocalVideo(enabled);
    }

    //暂停本地视频流
    @ReactMethod
    public void muteLocalVideoStream(boolean muted) {
        AgoraManager.getInstance().mRtcEngine.muteLocalVideoStream(muted);
    }

    //暂停所有远端视频流
    @ReactMethod
    public void muteAllRemoteVideoStreams(boolean muted) {
        AgoraManager.getInstance().mRtcEngine.muteAllRemoteVideoStreams(muted);
    }

    //暂停指定远端视频流
    @ReactMethod
    public void muteRemoteVideoStream(int uid, boolean muted) {
        AgoraManager.getInstance().mRtcEngine.muteRemoteVideoStream(uid, muted);
    }

    //设置是否打开闪光灯
    @ReactMethod
    public void setCameraTorchOn(boolean isOn) {
        AgoraManager.getInstance().mRtcEngine.setCameraTorchOn(isOn);
    }

    //设置是否开启人脸对焦功能
    @ReactMethod
    public void setCameraAutoFocusFaceModeEnabled(boolean enabled) {
        AgoraManager.getInstance().mRtcEngine.setCameraAutoFocusFaceModeEnabled(enabled);
    }

    //修改默认的语音路由 True: 默认路由改为外放(扬声器) False: 默认路由改为听筒
    @ReactMethod
    public void setDefaultAudioRouteToSpeakerphone(boolean defaultToSpeaker) {
        AgoraManager.getInstance().mRtcEngine.setDefaultAudioRoutetoSpeakerphone(defaultToSpeaker);
    }

    //销毁引擎实例
    @ReactMethod
    public void destroy() {
        RtcEngine.destroy();
    }

    @ReactMethod
    public void changeRole() {
        AgoraManager.getInstance().changeRole();
    }

    //查询 SDK 版本号
    @ReactMethod
    public void getSdkVersion(Callback callback) {
        callback.invoke(RtcEngine.getSdkVersion());
    }

    private void commonEvent(WritableMap map) {
        sendEvent(getReactApplicationContext(), "agoraEvent", map);
    }
    private void signalcommonEvent(WritableMap map) {
        sendEvent(getReactApplicationContext(), "agoraSignalEvent", map);
    }

    private void sendEvent(ReactContext reactContext,
                           String eventName,
                           @Nullable WritableMap params) {
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

}
