
#ifndef io_agora_sdk2_hc
#define io_agora_sdk2_hc

#import <Foundation/Foundation.h>

//=========================================================
//回调消息处理
//=========================================================

@protocol AgoraSignalDelegate <NSObject>
@optional
/**
 *  Event of onError
 */
- (void)signalEngine:(AgoraAPI * _Nonnull)signal onError:(NSString *)name ecode:(AgoraEcode)ecode desc:(NSString *)desc;

/**
 *  Event of onLoginSuccess
 */
- (void)signalEngine:(AgoraAPI * _Nonnull)signal onLoginSuccess:(uint32_t)uid fid:(int)fid;

/**
 *  Event of onLoginFailed
 */
- (void)signalEngine:(AgoraAPI * _Nonnull)signal onLoginFailed:(AgoraEcode)ecode;

/**
 *  Event of onChannelJoined
 */
- (void)signalEngine:(AgoraAPI * _Nonnull)signal onChannelJoined:(NSString *)channelID;

/**
 *  Event of onChannelJoinFailed
 */
- (void)signalEngine:(AgoraAPI * _Nonnull)signal onChannelJoinFailed:(NSString *)channelID ecode:(AgoraEcode)ecode;

/**
 *  Event of onMessageSendSuccess
 */
- (void)signalEngine:(AgoraAPI * _Nonnull)signal onMessageSendSuccess:(NSString *)messageID;
    
/**
 *  Event of onMessageSendError
 */
- (void)signalEngine:(AgoraAPI * _Nonnull)signal onMessageSendSuccess:(NSString *)messageID ecode:(AgoraEcode)ecode;

/**
 *  Event of onMessageInstantReceive
 */
- (void)signalEngine:(AgoraAPI * _Nonnull)signal onMessageInstantReceive:(NSString *)account uid:(uint32_t)uid msg:(NSString *)msg;
/**
 *  Event of onMessageChannelReceive
 */
- (void)signalEngine:(AgoraAPI * _Nonnull)signal onMessageChannelReceive:(NSString *)channelID account:(NSString *)account uid:(uint32_t)uid msg:(NSString *)msg;

@end
#endif
