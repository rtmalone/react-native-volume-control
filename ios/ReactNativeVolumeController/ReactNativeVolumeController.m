//
//  ReactNativeVolumeController.m
//  ReactNativeVolumeController
//
//  Created by Victor C Tavernari on 01/04/17.
//  Copyright © 2017 Tavernari. All rights reserved.
//

#import "ReactNativeVolumeController.h"

#import <AVFoundation/AVFoundation.h>
#import <MediaPlayer/MediaPlayer.h>

@implementation ReactNativeVolumeController

RCT_EXPORT_MODULE()

- (void)setVolume:(float)volumeValue {
    MPVolumeView *volumeView = [[MPVolumeView alloc] init];
    UISlider *volumeViewSlider = nil;
    
    for (UIView *view in volumeView.subviews) {
        if ([view isKindOfClass:[UISlider class]]) {
            volumeViewSlider = (UISlider *)view;
            break;
        }
    }
    
    _showsRouteButton = false;
    _showsVolumeSlider = false;
    
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(0.01 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        volumeViewSlider.value = volumeValue;
    });
}

RCT_EXPORT_METHOD(updateIOS:(float)value)
{
    [self setVolume:value];
}

@end
