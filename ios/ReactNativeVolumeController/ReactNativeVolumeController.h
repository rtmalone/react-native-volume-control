//  ReactNativeVolumeController
//
//  Created by Tyler Malone on 03/18/19
//  Copyright © 2019. All rights reserved.
//

#import <React/RCTBridgeModule.h>

@interface ReactNativeVolumeController : NSObject <RCTBridgeModule>

- (void)initVolumeView;
- (void)setVolume:(float)volumeValue;
@end
