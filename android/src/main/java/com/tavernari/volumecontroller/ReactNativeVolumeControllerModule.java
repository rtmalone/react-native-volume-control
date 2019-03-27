package com.tavernari.volumecontroller;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.media.AudioManager;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Promise;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class ReactNativeVolumeControllerModule extends ReactContextBaseJavaModule {

  private ReactApplicationContext context;
  private float max_volume = (float) 0.0;
  private AudioManager am;

  public ReactNativeVolumeControllerModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.context = reactContext;
  }

  @Override
  public String getName() {
    return "ReactNativeVolumeController";
  }

  @Override
  public void initialize() {
    super.initialize();

    try {
      am = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
      max_volume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
    } catch (Exception e) {
      Log.e("ERROR", e.getMessage());
    }
  }

  // public void sendEvent(ReactContext reactContext, String eventName, @Nullable
  // WritableMap params) {
  // this.context.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName,
  // params);
  // }

  @ReactMethod
  public void getVolume(Promise promise) {
    promise.resolve(getNormalizedVolume());
  }

  @ReactMethod
  public void change(float volume) {
    am.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (volume * max_volume), 0);
  }

  private float getNormalizedVolume() {
    return am.getStreamVolume(AudioManager.STREAM_MUSIC) * 1.0f / am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
  }
}
