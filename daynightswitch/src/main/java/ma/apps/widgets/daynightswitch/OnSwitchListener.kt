package ma.apps.widgets.daynightswitch

interface OnSwitchListener {
    fun onSwitch(switch: DayNightSwitch, isDayChecked: Boolean)
}