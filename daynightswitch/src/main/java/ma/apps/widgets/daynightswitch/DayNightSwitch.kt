package ma.apps.widgets.daynightswitch

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.RoundRectShape
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.view.setPadding


class DayNightSwitch(context: Context, attrs: AttributeSet?, style: Int) : LinearLayout(context, attrs, style), View.OnClickListener {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private lateinit var slider: View
    private lateinit var dayBg: ImageView
    private lateinit var nightBg: ImageView
    private lateinit var sliderContainer: FrameLayout

    private var isDayChecked = false
    private val duration = 300L
    private var cornerRadius: Float = 0f
    private var viewWidth: Int = 0
    private var viewHeight: Int = 0
    private var sliderDeltaX: Int = 0
    private var sliderMargin: Int = 0
    private var sliderColor: Int = Color.WHITE

    private lateinit var dayDrawable: Drawable
    private lateinit var nightDrawable: Drawable
    private lateinit var outlineBg: ShapeDrawable
    private lateinit var sliderBg: GradientDrawable

    private var listener: OnSwitchListener? = null

    init {
        initViews(context)
        loadStyleAttrs(context, attrs)
    }

    fun setOnSwitchListener(listener: OnSwitchListener) {
        this.listener = listener
    }

    private fun initViews(context: Context) {
        val rootView = LayoutInflater.from(context).inflate(R.layout.day_night_switch, this, false)
        addView(rootView)

        sliderContainer = findViewById(R.id.day_night_switch_slider_container)
        slider = findViewById<View>(R.id.day_night_switch_slider)
        dayBg = findViewById(R.id.day_night_switch_day_bg)
        nightBg = findViewById(R.id.day_night_switch_night_bg)

        rootView.setOnClickListener(this)
    }

    private fun loadStyleAttrs(context: Context, attrs: AttributeSet?) {
        val styleAttrs = context.obtainStyledAttributes(attrs, R.styleable.DayNightSwitch, 0, 0)

        dayDrawable = styleAttrs.getDrawable(R.styleable.DayNightSwitch_dayImage) ?: resources.getDrawable(R.drawable.day)
        nightDrawable = styleAttrs.getDrawable(R.styleable.DayNightSwitch_nightImage) ?: resources.getDrawable(R.drawable.night)
        sliderMargin = styleAttrs.getDimension(R.styleable.DayNightSwitch_sliderPadding, dpToPx(5f)).toInt()
        sliderColor = styleAttrs.getColor(R.styleable.DayNightSwitch_sliderColor, Color.WHITE)
        isDayChecked = styleAttrs.getBoolean(R.styleable.DayNightSwitch_isDayChecked, false)

        styleAttrs.recycle()
    }

    private fun dpToPx(dp: Float) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        viewWidth = MeasureSpec.getSize(widthMeasureSpec)
        viewHeight = MeasureSpec.getSize(heightMeasureSpec)

        sliderDeltaX = viewWidth - viewHeight

        setUpDrawablesAndSizes()
        setUpViews()
    }

    private fun setUpDrawablesAndSizes() {
        cornerRadius = viewHeight / 2f

        outlineBg = ShapeDrawable(RoundRectShape(FloatArray(8) { cornerRadius }, null, null))
        outlineBg.paint.color = Color.argb(0, 0, 0, 0)

        sliderBg = GradientDrawable()
        sliderBg.shape = GradientDrawable.OVAL
        sliderBg.setColor(sliderColor)
    }

    private fun setUpViews() {
        dayBg.setImageDrawable(dayDrawable)
        nightBg.setImageDrawable(nightDrawable)

        dayBg.background = outlineBg
        nightBg.background = outlineBg
        slider.background = sliderBg

        dayBg.clipToOutline = true
        nightBg.clipToOutline = true

        val lp = slider.layoutParams as FrameLayout.LayoutParams
        lp.width = viewHeight - sliderMargin * 2
        lp.height = lp.width
        if (isDayChecked) {
            lp.gravity = Gravity.RIGHT
            nightBg.alpha = 0f
        } else {
            lp.gravity = Gravity.LEFT
            nightBg.alpha = 1f
        }
        slider.layoutParams = lp

        sliderContainer.setPadding(sliderMargin)
    }

    override fun onClick(v: View?) {
        isDayChecked = !isDayChecked
        switchSliderWithAnimation(true)
    }

    private fun switchSliderWithAnimation(animated: Boolean) {
        val animationDuration = if (animated) duration else 0L

        if (isDayChecked) {
            slider.animate().translationX(sliderDeltaX.toFloat()).setDuration(animationDuration).start()
            nightBg.animate().alpha(0f).setDuration(animationDuration).start()
            listener?.onSwitch(this, true)
        } else {
            slider.animate().translationX(0f).setDuration(animationDuration).start()
            nightBg.animate().alpha(1f).setDuration(animationDuration).start()
            listener?.onSwitch(this, false)
        }
    }

    fun isDayChecked() = this.isDayChecked

    fun setDayChecked(isDayChecked: Boolean, animated: Boolean = false) {
        this.isDayChecked = isDayChecked
        switchSliderWithAnimation(animated)
    }
}