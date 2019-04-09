package com.rtmalone.volumecontrol;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.media.AudioManager;

import android.support.annotation.Nullable;
import android.util.Log;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.bridge.Promise;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class RNVolumeControlModule extends ReactContextBaseJavaModule
    implements ActivityEventListener, LifecycleEventListener {

  private String TAG = RNVolumeControlModule.class.getSimpleName();
  private ReactApplicationContext rContext;
  private float max_volume = (float) 0.0;
  private AudioManager am;
  private VolumeBroadcastReceiver volumeBR;

  public RNVolumeControlModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.rContext = reactContext;
  }

  @Override
  public String getName() {
    return "VolumeControl";
  }

  @Override
  public void onNewIntent(Intent intent) {
  }

  @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
  }

  @Override
  public void onHostResume() {
    registerVolumeReceiver();
  }

  @Override
  public void onHostPause() {
    unregisterVolumeReceiver();
  }

  @Override
  public void onHostDestroy() {
  }

  @Override
  public void initialize() {
    super.initialize();

    try {
      am = (AudioManager) this.rContext.getSystemService(Context.AUDIO_SERVICE);
      max_volume = am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
      volumeBR = new VolumeBroadcastReceiver();
      registerVolumeReceiver();
    } catch (Exception e) {
      Log.e(TAG, "Initialize Error", e);
    }
  }

  private void registerVolumeReceiver() {
    if (!volumeBR.isRegistered()) {
      IntentFilter filter = new IntentFilter("android.media.VOLUME_CHANGED_ACTION");
      this.rContext.registerReceiver(volumeBR, filter);
      volumeBR.setRegistered(true);
    }
  }

  private void unregisterVolumeReceiver() {
    if (volumeBR.isRegistered()) {
      this.rContext.unregisterReceiver(volumeBR);
      volumeBR.setRegistered(false);
    }
  }

  public void sendEvent(ReactContext reactContext, String eventName, @Nullable WritableMap params) {
    this.rContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
  }

  @ReactMethod
  public void getVolume(Promise promise) {
    promise.resolve(getNormalizedVolume());
  }

  @ReactMethod
  public void change(float volume) {
    unregisterVolumeReceiver();
    try {
      am.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (volume * max_volume), 0);
    } catch (Exception e) {
      Log.e(TAG, "Error Setting Volume", e);
    }
    registerVolumeReceiver();
  }

  private float getNormalizedVolume() {
    return am.getStreamVolume(AudioManager.STREAM_MUSIC) * 1.0f / max_volume;
  }

  public class VolumeBroadcastReceiver extends BroadcastReceiver {

    private boolean isRegistered = false;

    public void setRegistered(boolean registered) {
      isRegistered = registered;
    }

    public boolean isRegistered() {
      return isRegistered;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
      if (intent.getAction().equals("android.media.VOLUME_CHANGED_ACTION")) {
        float volume = getNormalizedVolume();
        WritableMap params = Arguments.createMap();
        params.putDouble("volume", volume);
        try {
          sendEvent(rContext, "VolumeChanged", params);
        } catch (RuntimeException e) {
          // Possible to interact with volume before JS bundle execution is finished.
          // This is here to avoid app crashing.
        }
      }
    }
  }
}
