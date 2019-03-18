//
//  ReactNativeVolumeController.h
//  ReactNativeVolumeController
//
//  Created by Tyler Malone on 03/18/19
//  Copyright © 2019 . All rights reserved.
//

#import <React/RCTBridgeModule.h>

@interface ReactNativeVolumeController : NSObject <RCTBridgeModule>
@property(nonatomic) BOOL showsVolumeSlider;
@property(nonatomic) BOOL showsRouteButton;

- (void)setVolume:(float)volumeValue;
@end