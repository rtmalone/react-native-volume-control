//
//  ReactNativeVolumeController.m
//  ReactNativeVolumeController
//
//  Created by Tyler Malone on 03/18/19.
//  Copyright © 2019. All rights reserved.
//

#import "ReactNativeVolumeController.h"

#import <AVFoundation/AVFoundation.h>
#import <MediaPlayer/MediaPlayer.h>

@implementation ReactNativeVolumeController {
    MPVolumeView *volumeView;
    UISlider *volumeViewSlider;
}

- (instancetype)init{
    self = [super init];
    [self initVolumeView];
    return self;
}


- (void)initVolumeView{
    volumeView = [[MPVolumeView alloc] init];
    volumeView.showsRouteButton = NO;
    volumeView.showsVolumeSlider = NO;
    
    for (UIView *view in volumeView.subviews) {
        if ([view isKindOfClass:[UISlider class]]) {
            volumeViewSlider = (UISlider *)view;
            break;
        }
    }
}

- (void)setVolume:(float)volumeValue {
//    volumeViewSlider = nil;
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.01 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        volumeViewSlider.value = volumeValue;
    });
}

RCT_EXPORT_MODULE()

RCT_EXPORT_METHOD(change:(float)value)
{
    [self setVolume:value];
}

RCT_EXPORT_METHOD(getVolume:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
    dispatch_sync(dispatch_get_main_queue(), ^{
        resolve([NSNumber numberWithFloat:[volumeViewSlider value]]);
    });
}

@end
