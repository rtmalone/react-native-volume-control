package com.tavernari.volumecontroller;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReactNativeVolumeControllerPackage implements ReactPackage {

  private ReactNativeVolumeControllerModule volumeControllerModule;

  @Override
  public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
    return Collections.emptyList();
  }

  @Override
  public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
    List<NativeModule> modules = new ArrayList<>();

    volumeControllerModule = new ReactNativeVolumeControllerModule(reactContext);

    modules.add(volumeControllerModule);

    return modules;
  }

}
