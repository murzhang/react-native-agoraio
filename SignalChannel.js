import {
    NativeModules,
    NativeEventEmitter
} from 'react-native';

const { Agora } = NativeModules
const agoraEmitter = new NativeEventEmitter(Agora);

export default {
    ...Agora,
    init(options = {}) {
        this.listener && this.listener.remove();
        Agora.signal_init(options);
    },    
    login(account, token, uid=0, deviceID, retry_time_in_s=30, retry_count=3) {
        Agora.signal_login2(account, token, uid, deviceID, retry_time_in_s, retry_count);
    },
    logout(){
        Agora.signal_logout();
    },
    channelJoin(channelName) {
        Agora.signal_channelJoin(channelName);
    },
    channelLeave(channelName) {
        Agora.signal_channelLeave(channelName);
    },
    messageChannelSend(channelName, msg, msgID){
        Agora.signal_messageChannelSend(channelName, msg, msgID)
    },
    messageInstantSend(account,uid=0, msg, msgID){
        Agora.signal_messageInstantSend(account,uid, msg, msgID)
    },
    eventEmitter(fnConf) {
        this.removeEmitter();
        this.listener = agoraEmitter.addListener(
            'agoraSignalEvent',
            (event) => {
                fnConf[event['type']] && fnConf[event['type']](event);
            }
        );
    },
    removeEmitter() {
        this.listener && this.listener.remove();
        this.listener = null;
    }
};
