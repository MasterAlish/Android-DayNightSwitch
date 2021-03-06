package ma.apps.widgets.daynightswitch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val switch1 = findViewById<DayNightSwitch>(R.id.switch_1)
        val switch2 = findViewById<DayNightSwitch>(R.id.switch_2)
        val switch0 = findViewById<DayNightSwitch>(R.id.switch_0)
        val label0 = findViewById<TextView>(R.id.label_0)

        switch1.setOnSwitchListener(object : OnSwitchListener {
            override fun onSwitch(switch: DayNightSwitch, isDayChecked: Boolean) {
                switch2.setDayChecked(switch.isDayChecked(), true)
            }
        })

        switch0.setOnSwitchListener { switch, isDayChecked ->
            if (isDayChecked) {
                label0.setText(R.string.day)
            } else {
                label0.setText(R.string.night)
            }
        }
    }
}
