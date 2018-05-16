//
//  MyAgoraRtcEngineKit.h
//  RCTAgora
//
//  Created by Learnta on 2017/12/21.
//  Copyright © 2017年 Learnta Inc. All rights reserved.
//

#import <AgoraRtcEngineKit/AgoraRtcEngineKit.h>
#import <AgoraSigKit/AgoraSigKit.h>

@interface AgoraConst : NSObject

@property (nonatomic, copy) NSString *appid;

@property (nonatomic, assign) NSInteger localUid;

@property (strong, nonatomic) AgoraRtcEngineKit *rtcEngine;

@property (strong, nonatomic) AgoraAPI *signalEngine;

+ (instancetype)share;

@end
