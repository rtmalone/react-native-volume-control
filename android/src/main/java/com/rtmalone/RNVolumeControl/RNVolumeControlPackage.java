package com.rtmalone.RNVolumeControl;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.react.bridge.JavaScriptModule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RNVolumeControlPackage implements ReactPackage {

  private RNVolumeControlModule volumeControllerModule;

  public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList(); }

  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    return Collections.emptyList();
  }

  @Override
  public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
    List<NativeModule> modules = new ArrayList<>();

    volumeControllerModule = new RNVolumeControlModule(reactContext);

    modules.add(volumeControllerModule);

    return modules;
  }

}
