import { NativeEventEmitter } from "react-native";

export = RNVolumeControl;
export as namespace RNVolumeControl;

declare namespace RNVolumeControl {
  export function change(value: number): void;
  export function getVolume(): number;

  export type VolumeEventType = {
    volume: number;
  };

  export class VolumeControlEvents extends NativeEventEmitter {}
}
