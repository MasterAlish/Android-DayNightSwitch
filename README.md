# Android-DayNightSwitch

[![Release](https://jitpack.io/v/MasterAlish/Android-DayNightSwitch.svg)](https://jitpack.io/#MasterAlish/Android-DayNightSwitch)

Simple and Customizable Android Day Night Switch Widget

![Slider](/images/example.gif?raw=true "Example 1")

You can find examples in **example** project.

![Sliders](/images/examples.png?raw=true "Example 2")

## How to install

1. Add Jitpack.io repository in your root __build.gradle__ file

```gradle
allprojects {
	repositories {
		// ...
		maven { url 'https://jitpack.io' }
	}
}
```

2. Add dependency in your module __build.gradle__ file

```gradle
dependencies {
       implementation 'com.github.MasterAlish:Android-DayNightSwitch:v0.1'
}
```

## How to Use

1. Add __DayNightSwitch__ in the xml file

```xml
<ma.apps.widgets.daynightswitch.DayNightSwitch
    android:id="@+id/switch_1"
    android:layout_width="120dp"
    android:layout_height="50dp"
    app:sliderColor="#F49044"
    app:sliderPadding="7dp" />
```

2. Get the reference 

```kotlin
val switch1 = findViewById<DayNightSwitch>(R.id.switch_1)
```

3. Listen for updates

```kotlin
switch1.setOnSwitchListener { switch, isDayChecked ->
     // Do smth ...
}
```

4. Getting current state

```kotlin
val daySelected: Boolean = switch1.isDayChecked()
```

5. Setting state

```kotlin
switch1.setDayChecked(true, animated = true)
// or
switch1.setDayChecked(true, animated = false)
```

## Customization: Xml attributes

```xml
<ma.apps.widgets.daynightswitch.DayNightSwitch
    android:id="@+id/switch_0"
    android:layout_width="115dp"
    android:layout_height="69dp"
    app:dayImage="@drawable/day_3"      <!-- Background drawable for Day state -->
    app:nightImage="@drawable/night_3"  <!-- Background drawable for Night state -->
    app:slideDuration="500"             <!-- Sliding Animation duration -->
    app:sliderColor="#FFFFFF"           <!-- Slider color -->
    app:sliderPadding="4dp"             <!-- Padding for slider -->
    app:isDayChecked="false"/>          <!-- Initial state -->
```

