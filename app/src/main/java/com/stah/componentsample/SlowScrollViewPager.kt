package com.stah.componentsample

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Interpolator
import android.widget.Scroller
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.viewpager.widget.ViewPager
import kotlin.math.pow

class SlowScrollViewPager @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ViewPager(context, attrs) {

    companion object {
        private const val CUSTOM_DURATION: Int = 1000
    }

    private val interpolator: CustomInterpolator = CustomInterpolator()
    private val scroller: CustomScroller = CustomScroller(context, interpolator)

    init {
        ViewPager::class.java.getDeclaredField("mScroller").run {
            isAccessible = true
            set(this@SlowScrollViewPager, scroller)
        }
        addOnPageChangeListener(object : OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
                when (state) {
                    SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING -> { // ページ切り替えが終わったら、また、切り替え中にスワイプした際に元の挙動で切り替わるように
                        interpolator.isCustom = false
                        scroller.isCustom = false
                    }
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) = Unit

            override fun onPageSelected(position: Int) = Unit
        })
    }

    override fun setCurrentItem(item: Int) {
        interpolator.isCustom = true
        scroller.isCustom = true
        super.setCurrentItem(item)
    }

    private class CustomScroller(context: Context, interpolator: Interpolator) :
        Scroller(context, interpolator) {

        var isCustom: Boolean = false

        override fun startScroll(startX: Int, startY: Int, dx: Int, dy: Int, duration: Int) {
            super.startScroll(startX, startY, dx, dy, if (isCustom) CUSTOM_DURATION else duration)
        }
    }

    private class CustomInterpolator : FastOutSlowInInterpolator() {

        private val default: Interpolator = Interpolator { input -> (input - 1).pow(5) + 1 } // ViewPager内で作成されているInterpolatorをここで再実装
        var isCustom: Boolean = false

        override fun getInterpolation(input: Float): Float =
            if (isCustom) super.getInterpolation(input) else default.getInterpolation(input)
    }
}
