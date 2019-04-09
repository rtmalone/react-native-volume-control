# react-native-volume-control

Control device volume for iOS and Android.

## First installation step (applied for both iOS & Android)

`$ npm install react-native-volume-control --save`

#### 2. Automatic installation

`$ react-native link react-native-volume-control`

#### 3. Manual installation

1. In XCode, in the project navigator, right click `Libraries` ➜ `Add Files to [your project's name]`
2. Go to `node_modules` ➜ `react-native-volume-control` => `ios`
   - add `ReactNativeVolumeControl.xcodeproj` to the Libraries folder in your XCode project
3. In XCode, in the project navigator, select your project. Add `libReactNativeVolumeControl.a` to your project's `Build Phases` ➜ `Link Binary With Libraries`
4. Run your project (`Cmd+R`)

### Android

#### Manual installation

1. In Android Studio open `Module Settings` and add a Gradle Project.
2. Look for `react-native-volume-control` android folder and link with a Gradle.
3. Open MyApplication.java from main app and put the ReactNativeVolumeControllerPackage

```java
 @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
          new MainReactPackage(),
          new RNVolumeControlPackage()
      );
    }
```

## Usage

This component only exposes an api to update device volume and listens for `VolumeChanged` events via hardware buttons. There is no UI component included.

```javascript
// Other imports
...

import VolumeControl, {
  VolumeControlEvents
} from "react-native-volume-control";
import Slider from '@react-native-community/slider';

class App extends React.Component {
  state = {
    volume: 0
  }

  async componentDidMount() {
    this.setState({
      volume: await VolumeControl.getVolume()
    });

    // Add and store event listener
    this.volEvent = VolumeControlEvents.addListener(
      "VolumeChanged",
      this.volumeEvent
    );
  }

  // Updates Slider UI when hardware buttons change volume
  volumeEvent = event => {
    this.setState({ volume: event.volume });
  };

  // Updates device volume
  sliderChange(value) {
    VolumeControl.change(value);
  }

  componentWillUnmount() {
    // remove event listener
    this.volEvent.remove();
  }

  render() {
    return (
      <Slider
        value={this.state.volume}
        onValueChange={this.sliderChange}
        // Other props
      />
    )
  }
}
```
