import { NativeModules, NativeEventEmitter } from "react-native";

const RNVolumeControl = NativeModules.ReactNativeVolumeController;
export const RNVCEvents = new NativeEventEmitter(
  NativeModules.ReactNativeVolumeController
);

export default RNVolumeControl;
