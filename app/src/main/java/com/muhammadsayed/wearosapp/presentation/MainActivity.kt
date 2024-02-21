/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.muhammadsayed.wearosapp.presentation

import android.os.Bundle
import android.text.format.DateFormat
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.wear.compose.material.Button
import androidx.wear.compose.material.ButtonDefaults
import androidx.wear.compose.material.CircularProgressIndicator
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.InlineSliderDefaults
import androidx.wear.compose.material.Scaffold
import androidx.wear.compose.material.Text
import androidx.wear.compose.material.TimeText
import androidx.wear.compose.material.TimeTextDefaults
import androidx.wear.compose.material.Vignette
import androidx.wear.compose.material.VignettePosition
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()

        super.onCreate(savedInstanceState)

        setTheme(android.R.style.Theme_DeviceDefault)

        setContent {

            var progress by remember { mutableFloatStateOf(0.0f) }
            val animateProgress by animateFloatAsState(
                targetValue = progress,
                label = "Progress Animation",
                animationSpec = tween(500)
            )

            Scaffold(
                timeText = {
                    TimeText(
                        timeSource = TimeTextDefaults.timeSource(
                            DateFormat.getBestDateTimePattern(Locale.getDefault(), "hh:mm")
                        )
                    )
                },
                vignette = {
                    Vignette(vignettePosition = VignettePosition.TopAndBottom)
                }
            ) {
                Column(
                    Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.size(30.dp))
                    Box(modifier = Modifier.weight(1f)) {

                        CircularProgressIndicator(
                            startAngle = 270f,
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(all = 1.dp),
                            progress = animateProgress / 3,
                            strokeWidth = 23.dp,
                            indicatorColor = Color(0xFF54BEF0),
                            trackColor = Color(0xFF00161E)
                        )
                        Column(
                            modifier = Modifier
                                .wrapContentWidth()
                                .fillMaxHeight()
                                .padding(2.dp)
                                .align(Alignment.Center)
                                .rotate((animateProgress / 3) * 360)
                        ) {
                            Text(
                                progress.toEmoji(),
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.rotate(-(animateProgress / 3) * 360)
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {

                            Text(
                                String.format("%.2f", animateProgress),
                                textAlign = TextAlign.Center,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                "3 Liter",
                                textAlign = TextAlign.Center,
                                fontSize = 7.sp,
                                color = Color(0xFF7D7D7D)
                            )
                        }
                    }


                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Spacer(modifier = Modifier.size(25.dp))
                        Button(
                            modifier = Modifier.size(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF1C1C1E),
                                disabledBackgroundColor = Color(0xFF1C1C1E),
                            ),
                            onClick = {
                                progress -= 0.5f
                            },
                            enabled = progress > 0f
                        ) {
                            Icon(
                                InlineSliderDefaults.Decrease,
                                "Decrease",
                                tint = if (progress > 0) Color(0xFF70D7FF) else Color(0xFF929292)
                            )
                        }
                        Text(
                            String.format("%.2f", animateProgress), textAlign = TextAlign.Center,
                            fontSize = 14.sp, fontWeight = FontWeight.Bold
                        )
                        Button(
                            modifier = Modifier.size(30.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color(0xFF1C1C1E),
                                disabledBackgroundColor = Color(0xFF1C1C1E),
                            ), onClick = {
                                progress += 0.5f
                            }, enabled = progress < 3f
                        ) {
                            Icon(
                                InlineSliderDefaults.Increase,
                                "Increase",
                                tint = if (progress < 3) Color(0xFF70D7FF) else Color(0xFF929292)
                            )
                        }
                        Spacer(modifier = Modifier.size(25.dp))

                    }
                    Spacer(modifier = Modifier.size(16.dp))
                }
            }
        }
    }
}

private fun Float.toEmoji(): String {
    val sleepingEmoji = "\uD83D\uDE34"
    val smilingEmoji = "\uD83D\uDE03"
    val lovingEmoji = "\uD83D\uDE0D"
    val starEyesEmoji = "\uD83E\uDD29"

    return when (this) {
        in 0f..0.4f -> sleepingEmoji
        in 0.5f..1f -> smilingEmoji
        in 1.1f..2f -> lovingEmoji
        else -> starEyesEmoji
    }
}

