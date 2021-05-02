package com.nemesiss.dev.cuge.cardsettingcompose

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import com.nemesiss.dev.cuge.cardsettingcompose.databinding.ActivityMainBinding
import com.nemesiss.dev.cuge.cardsettingcompose.panel.PanelItemScope
import com.nemesiss.dev.cuge.cardsettingcompose.panel.PanelItemScope.Companion.PanelItems
import com.nemesiss.dev.cuge.cardsettingcompose.panel.SpecState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater).apply { setContentView(root) } }

    private var runningJob: Job? = null

    private val running get() = runningJob != null

    private val state = CountingState()

    private val counter = MutableLiveData(0)

    private lateinit var panel: PanelItemScope<CountingState>

    private class CountingState : SpecState() {
        var switchButton = "Stopped"
        var hint = "Tap to start"
        var icon = R.drawable.baseline_block_white_24dp
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // execute binding
        binding

        val context = this

        panel = PanelItems(state) {
            BannerItemSpec {
                iconRes = R.drawable.logo
                title = "Clash"
                onClick = { Toast.makeText(context, "Hello, I am banner!", Toast.LENGTH_SHORT).show() }
            }

            CardItemSpec {
                iconRes = state.icon
                title = state.switchButton
                subTitle = state.hint
                background = R.drawable.clash_color_ripple
                onClick = { if (!running) counting() else stopping() }
            }

            ListItemSpec {
                iconRes = R.drawable.baseline_feedback_white_24dp
                title = "Logs"
                onClick = { Toast.makeText(context, "这里什么都没有哦", Toast.LENGTH_SHORT).show() }
            }

            ListItemSpec {
                iconRes = R.drawable.baseline_settings_white_24dp
                title = "About"
            }

            ListItemSpec {
                iconRes = R.drawable.baseline_help_white_24dp
                title = "About"
            }

        }.into(binding.panelContainer)
    }

    private fun counting() {
        state.switchButton = "Running"
        state.icon = R.drawable.baseline_check_circle_white_24dp
        counter.observe(this) {
            state.hint = "$it time(s)"
            panel.applyState(state)
        }

        runningJob = lifecycleScope.launch {
            while (true) {
                delay(1000)
                val value = counter.value ?: 0
                counter.value = value + 1
            }
        }
    }

    private fun stopping() {
        runningJob?.cancel()
        runningJob = null
        counter.removeObservers(this)
        counter.value = 0
        state.switchButton = "Stopped"
        state.hint = "Tap to start"
        state.icon = R.drawable.baseline_block_white_24dp
        panel.applyState(state)
    }
}