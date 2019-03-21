import { NativeModules, NativeEventEmitter } from "react-native";

const VolumeControl = NativeModules.VolumeControl;
export const VolumeControlEvents = new NativeEventEmitter(
  NativeModules.VolumeControl
);

export default VolumeControl;
