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
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
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
import com.muhammadsayed.wearosapp.R
import java.util.Locale
import kotlin.math.absoluteValue

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
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
            val pagerState = rememberPagerState(pageCount = { 2 })
            val scrollState = rememberLazyListState()
            val hideGradiant by remember {
                derivedStateOf {
                    scrollState.layoutInfo.visibleItemsInfo.lastOrNull()?.index == scrollState.layoutInfo.totalItemsCount - 1
                }
            }
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
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    HorizontalPager(pagerState) { page ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .graphicsLayer {
                                    // Calculate the absolute offset for the current page from the
                                    // scroll position. We use the absolute value which allows us to mirror
                                    // any effects for both directions
                                    val pageOffset = (
                                            (pagerState.currentPage - page) + pagerState
                                                .currentPageOffsetFraction
                                            ).absoluteValue

                                    // We animate the alpha, between 10% and 100%
                                    alpha = lerp(
                                        start = 0.1f,
                                        stop = 1f,
                                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                                    )
                                    // Translate the box horizontally based on the page offset
                                    translationX = pageOffset * 200 // adjust the value as needed

                                    // Scale the box based on the page offset
                                    scaleX = 1f - pageOffset * 0.2f // adjust the value as needed
                                    scaleY = 1f - pageOffset * 0.2f // adjust the value as needed

                                }

                        )
                        {
                            if (page == 0) {
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
                                                tint = if (progress > 0) Color(0xFF70D7FF) else Color(
                                                    0xFF929292
                                                )
                                            )
                                        }
                                        Text(
                                            String.format("%.2f", animateProgress),
                                            textAlign = TextAlign.Center,
                                            fontSize = 14.sp,
                                            fontWeight = FontWeight.Bold
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
                                                tint = if (progress < 3) Color(0xFF70D7FF) else Color(
                                                    0xFF929292
                                                )
                                            )
                                        }
                                        Spacer(modifier = Modifier.size(25.dp))

                                    }
                                    Spacer(modifier = Modifier.size(16.dp))
                                }
                            } else {
                                Box(modifier = Modifier.fillMaxSize()) {
                                    LazyColumn(
                                        Modifier.fillMaxSize(),
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center,
                                        state = scrollState
                                    ) {
                                        item {
                                            Spacer(modifier = Modifier.size(30.dp))
                                            Text(
                                                "${(progress / 0.25).toInt()} cups / 12 cups",
                                                textAlign = TextAlign.Center,
                                                fontSize = 14.sp,
                                                fontWeight = FontWeight.Bold
                                            )
                                            Spacer(modifier = Modifier.size(16.dp))
                                        }
                                        items(12) {
                                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                                if ((it + 1) <= (progress / 0.25)) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.splash_icon),
                                                        contentDescription = null
                                                    )
                                                } else {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.dotted_drop),
                                                        contentDescription = null
                                                    )
                                                }

                                                Spacer(modifier = Modifier.height(7.dp))
                                                Text(
                                                    "1 cup",
                                                    textAlign = TextAlign.Center,
                                                    fontSize = 11.sp,
                                                    fontWeight = FontWeight.Normal,
                                                    color = Color(0xFF7D7D7D)
                                                )
                                                Spacer(modifier = Modifier.height(7.dp))
                                            }

                                        }
                                        item {
                                            Spacer(modifier = Modifier.size(16.dp))
                                        }

                                    }
                                    if (!hideGradiant) {
                                        Box(
                                            modifier = Modifier
                                                .align(Alignment.BottomCenter)
                                                .fillMaxWidth()
                                                .fillMaxHeight(0.5f)
                                                .background(
                                                    Brush.verticalGradient(
                                                        listOf(
                                                            Color(0x00000000),
                                                            Color(0xFF000000)
                                                        )
                                                    )
                                                )
                                        )
                                    }
                                }

                            }
                        }

                    }
                    PagerIndicator(
                        pageCount = 2,
                        currentPage = pagerState.currentPage,
                        selectedColor = Color(0xFF54BEF0),
                        unselectedColor = Color(0xFF929292),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(vertical = 10.dp)
                    )
                }

            }
        }
    }
}

@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPage: Int,
    selectedColor: Color,
    unselectedColor: Color,
    modifier: Modifier = Modifier
) {
    Row(modifier) {
        repeat(pageCount) { index ->
            val color = if (index == currentPage) selectedColor else unselectedColor
            Indicator(color)
        }
    }
}

@Composable
fun Indicator(color: Color) {
    Box(
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .size(5.dp)
            .background(color = color, shape = CircleShape)
    )
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

