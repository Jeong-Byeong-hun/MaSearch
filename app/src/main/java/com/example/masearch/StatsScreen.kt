package com.example.masearch

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.masearch.api.vo.HyperStatVO
import com.example.masearch.api.vo.ItemEquipmentVO
import com.example.masearch.api.vo.ResultVO
import com.example.masearch.api.vo.StatVO
import com.example.masearch.ui.theme.CombatPowerBackgroundColor
import com.example.masearch.ui.theme.MainBackgroundColor
import com.example.masearch.view.stat.BasicStatView
import com.example.masearch.view.stat.EtcStatView
import com.example.masearch.view.stat.HyperStatView
import com.example.masearch.view.stat.SpecialStatView
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Stats(userData: ResultVO) {
    val scrollState = rememberScrollState()

    val tabData = listOf(
        "기본정보",
        "장비 아이템"
    )

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val tabIndex = pagerState.currentPage
    TabRow(
        selectedTabIndex = tabIndex,
        modifier = Modifier
            .background(Color.Transparent)
    ) {
        tabData.forEachIndexed() { index, text ->
            Tab(modifier = Modifier.background(CombatPowerBackgroundColor),
                selected = tabIndex == index,
                onClick = {
                    coroutineScope.launch {
                        Log.d("stats", "Stats: " + tabIndex)
                        pagerState.animateScrollToPage(index)
                    }
                }, text = {
                    Text(
                        text = text, fontFamily = FontFamily(
                            Font(
                                R.font.gmarket_sans_medium,
                                FontWeight.Normal,
                                FontStyle.Normal
                            )
                        ), fontSize = 16.sp
                    )
                })

        }
    }


    HorizontalPager(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Transparent),
        count = 2,
        state = pagerState
    ) {
        when (it) {
            0 -> BasicInfo(charInfo = userData.stat, hyperStatVO = userData.hyperStat)

            1 -> {

//                EquipmentList(items = itemList, jobClass = charInfo.role)

            }

//                Text(
//                modifier = Modifier.wrapContentSize(),
//                text = it.toString(),
//                textAlign = TextAlign.Center,
//                fontSize = 30.sp
//            )
        }

    }

}

@Composable
fun BasicTitleTextView(text: String) {
    Text(
        text = text,
        style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
        color = Color.White,
        modifier = Modifier.padding(16.dp, 4.dp, 16.dp, 0.dp),
        fontSize = 16.sp,
        fontFamily = FontFamily(
            Font(
                R.font.gmarket_sans_medium,
                FontWeight.Normal,
                FontStyle.Normal
            )
        )
    )
}

@Composable
fun BasicInfoContentsTextView(text: String) {
    Text(
        text = text,
        style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
        modifier = Modifier.padding(16.dp, 4.dp, 16.dp, 0.dp),
        color = Color.White,
        fontSize = 12.sp,
        fontFamily = FontFamily(
            Font(
                R.font.gmarket_sans_medium,
                FontWeight.Normal,
                FontStyle.Normal
            )
        )
    )
}

@Composable
fun BasicEquipmentTextview(text: String) {
    Text(
        text = text,
        style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
        color = Color.White,
        modifier = Modifier.padding(2.dp),
        fontSize = 14.sp,
        fontFamily = FontFamily(
            Font(
                R.font.notosans_regular,
                FontWeight.Normal,
                FontStyle.Normal
            )
        )
    )
}

@Composable
fun BasicInfo(charInfo: StatVO, hyperStatVO: HyperStatVO) {
    Surface(modifier = Modifier.padding(16.dp)) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(MainBackgroundColor)
                .verticalScroll(rememberScrollState())
                .padding(0.dp, 0.dp, 0.dp, 30.dp)
        ) {
            Row {
                BasicStatView(finalStatList = charInfo.finalStatList)
                Spacer(modifier = Modifier.width(16.dp))
                val modifier = Modifier
                    .weight(1f)
                    .padding(6.dp, 6.dp, 6.dp, 2.dp)
                SpecialStatView(finalStatList = charInfo.finalStatList, modifier)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                EtcStatView(finalStatList = charInfo.finalStatList)
                Spacer(modifier = Modifier.width(16.dp))
                val modifier = Modifier
                    .weight(1f)
                    .padding(6.dp, 6.dp, 6.dp, 2.dp)
                HyperStatView(modifier = modifier, hyperStat = hyperStatVO)

            }

        }
    }

}

//@Composable
//fun EquipmentList(items: MutableList<ItemsVo>, jobClass: String) {
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        items(items) {
//            Equipment(itemsVo = it, jobClass)
//            Spacer(modifier = Modifier.height(8.dp))
//        }
//    }
//}

//@OptIn(ExperimentalGlideComposeApi::class)
//@Composable
//fun Equipment(itemsVo: ItemsVo, jobClass: String) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp, 8.dp, 16.dp, 0.dp)
//    ) {
//        GlideImage(
//            model = itemsVo.image, contentDescription = itemsVo.name,
//            modifier = Modifier
//                .height(50.dp)
//                .width(50.dp)
//                .align(Alignment.CenterVertically),
//            alignment = Alignment.Center
//        )
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(8.dp, 0.dp, 0.dp, 0.dp)
//        ) {
//            Row {
//                BasicEquipmentTextview(text = itemsVo.name)
//                Spacer(modifier = Modifier.width(6.dp))
//                BasicEquipmentTextview(text = itemsVo.starforce.replace(" 강화", ""))
//
//                Spacer(modifier = Modifier.width(4.dp))
//                if (!emptyAddOption.contains(itemsVo.itemType)) {
//                    BasicEquipmentTextview(
//                        text = AddOptionCalculator().calculateAddOption(
//                            itemsVo,
//                            jobClass.split("/")[1]
//                        ).toString() + "급"
//                    )
//                }
//            }
//
//            Column {
//                Spacer(modifier = Modifier.width(8.dp))
//                var color = when (itemsVo.potential.grade) {
//                    "레어" -> RareBackgroundColor
//                    "에픽" -> EpicBackgroundColor
//                    "유니크" -> UniqueBackgroundColor
//                    "레전드리" -> LegendaryBackgroundColor
//                    else -> Color.Transparent
//                }
//                Card(
//                    shape = RoundedCornerShape(5.dp),
//                    colors = CardDefaults.cardColors(color)
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .wrapContentHeight()
//                            .wrapContentWidth(),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.Center
//                    ) {
//                        BasicEquipmentTextview(text = itemsVo.potential.grade)
//                        PotentialText(potential = itemsVo.potential)
//                    }
//                }
//
//
//                Spacer(modifier = Modifier.height(8.dp))
//
//                if (itemsVo.additionalPotential.grade.isNotEmpty()) {
//                    color = when (itemsVo.additionalPotential.grade) {
//                        "레어" -> RareBackgroundColor
//                        "에픽" -> EpicBackgroundColor
//                        "유니크" -> UniqueBackgroundColor
//                        "레전드리" -> LegendaryBackgroundColor
//                        else -> Color.Transparent
//                    }
//
//                    Card(
//                        shape = RoundedCornerShape(5.dp),
//                        colors = CardDefaults.cardColors(color)
//                    ) {
//                        Row(
//                            modifier = Modifier
//                                .wrapContentHeight()
//                                .wrapContentWidth(),
//                            verticalAlignment = Alignment.CenterVertically,
//                            horizontalArrangement = Arrangement.Center
//                        ) {
//                            BasicEquipmentTextview(text = itemsVo.additionalPotential.grade)
//                            PotentialText(potential = itemsVo.additionalPotential)
//                        }
//                    }
//                }
//            }
//
//        }
//    }
//}
//
//@Composable
//fun PotentialText(potential: Potential) {
//    for (item in potential.option) {
//        if (item is ArrayList<*>) {
//            var potentialText = ""
//
//            potentialText = when (item[0]) {
//                "몬스터 방어율 무시" -> {
//                    "방무"
//                }
//
//                "보스 몬스터 공격 시 데미지" -> {
//                    "보공"
//                }
//
//                "메소 획득량" -> {
//                    "메획"
//                }
//
//                "아이템 드롭률" -> {
//                    "아획"
//                }
//
//                "모든 스킬의 재사용 대기시간" -> {
//                    "쿨감"
//                }
//
//                "크리티컬 데미지" -> {
//                    "크뎀"
//                }
//
//                else -> {
//                    item[0].toString()
//                }
//            }
//
//            if (potentialText == "쿨감") {
//                BasicEquipmentTextview(
//                    text = potentialText + " " + item[1].toString().substring(0, 3)
//                )
//            } else {
//                BasicEquipmentTextview(text = potentialText + " " + item[1].toString())
//            }
//
//
//            Spacer(modifier = Modifier.width(6.dp))
//        }
//    }
//
//}