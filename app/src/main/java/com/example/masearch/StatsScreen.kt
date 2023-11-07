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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.masearch.api.vo.CharacterVo
import com.example.masearch.api.vo.ItemsVo
import com.example.masearch.api.vo.Potential
import com.example.masearch.ui.theme.DividerColor
import com.example.masearch.ui.theme.EpicBackgroundColor
import com.example.masearch.ui.theme.LegendaryBackgroundColor
import com.example.masearch.ui.theme.RareBackgroundColor
import com.example.masearch.ui.theme.UniqueBackgroundColor
import com.example.masearch.util.AddOptionCalculator
import com.example.masearch.util.ItemSort
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch

val emptyAddOption = listOf(
    "어깨장식",
    "반지",
    "뱃지",
    "엠블렘",
    "안드로이드",
    "기계 심장",
    "훈장",
    "메달",
    "로자리오",
    "쇠사슬",
    "마도서",
    "방패",
    "화살깃",
    "활골무",
    "렐릭",
    "부적",
    "단검용 검집",
    "블레이드",
    "리스트밴드",
    "조준기",
    "화약통",
    "보석",
    "소울실드",
    "장약",
    "마법구슬",
    "화살촉",
    "매그넘",
    "컨트롤러",
    "포스실드",
    "무게추",
    "문서",
    "오브",
    "마법화살",
    "카드",
    "여우 구슬",
    "용의 정수",
    "소울링",
    "무기 전송장치",
    "웨폰 벨트",
    "브레이슬릿",
    "매직윙",
    "패스 오브 어비스",
    "노리개",
    "선추",
    "체스피스"
)

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Stats(charInfo: CharacterVo, items: MutableList<ItemsVo>) {
    val scrollState = rememberScrollState()

    val tabData = listOf(
        "기본정보",
        "장비템"
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
            Tab(modifier = Modifier.background(Color.DarkGray),
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
                                R.font.notosans_regular,
                                FontWeight.Normal,
                                FontStyle.Normal
                            )
                        )
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
            0 -> BasicInfo(charInfo = charInfo)

            1 -> {
                var itemList = ItemSort().sortItemList(items)
                itemList.removeIf { it -> it.name == "" }
                itemList = ItemSort().integratePotential(itemList)

                EquipmentList(items = itemList, jobClass = charInfo.role)

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
fun BasicInfo(charInfo: CharacterVo) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
            .padding(0.dp, 0.dp, 0.dp, 30.dp)
    ) {


        BasicTitleTextView(text = "기본 스탯")
        Row {
            Row(modifier = Modifier.weight(1f)) {
                BasicInfoContentsTextView(text = "HP : ${charInfo.hp}")
            }
            Row(modifier = Modifier.weight(1f)) {
                BasicInfoContentsTextView(text = "MP : ${charInfo.mp}")
            }
            Spacer(modifier = Modifier.weight(1f))
        }

        Row {
            Row(modifier = Modifier.weight(1f)) {
                BasicInfoContentsTextView(text = "STR : ${charInfo.str}")
            }

            Row(modifier = Modifier.weight(1f)) {
                BasicInfoContentsTextView(text = "DEX : ${charInfo.dex}")
            }
            Spacer(modifier = Modifier.weight(1f))

        }

        Row {
            Row(modifier = Modifier.weight(1f)) {
                BasicInfoContentsTextView(text = "INT : ${charInfo.int}")
            }

            Row(modifier = Modifier.weight(1f)) {
                BasicInfoContentsTextView(text = "LUK : ${charInfo.luk}")
            }

            Spacer(modifier = Modifier.weight(1f))

        }

        Spacer(modifier = Modifier.height(8.dp))
        BasicInfoContentsTextView(text = "스공 : ${charInfo.statAttPower[0]} ~ ${charInfo.statAttPower[1]}")

        Row {
            Row(modifier = Modifier.weight(1f)) {
                BasicInfoContentsTextView(text = "보공 : ${charInfo.boseOffensePower}")
            }
            Row(modifier = Modifier.weight(1f)) {
                BasicInfoContentsTextView(text = "데미지 : ${charInfo.boseOffensePower}")
            }

            Spacer(modifier = Modifier.weight(1f))

        }

        Row {
            Row(modifier = Modifier.weight(1f)) {
                BasicInfoContentsTextView(text = "방무 : ${charInfo.ignoreDefense}")
            }
            Row(modifier = Modifier.weight(1f)) {
                BasicInfoContentsTextView(text = "크뎀 : ${charInfo.criticalDamage}")
            }
            Spacer(modifier = Modifier.weight(1f))

        }



        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp, 0.dp, 4.dp),
            color = DividerColor,
            thickness = 1.dp
        )

        BasicTitleTextView(text = "${charInfo.ability.grade} 어빌리티")
//        Text(
//            text = "${charInfo.ability.grade} 어빌리티",
//            color = Color.White,
//            fontSize = 24.sp,
//            modifier = Modifier.padding(16.dp, 4.dp, 16.dp, 0.dp),
//            fontFamily = FontFamily(
//                Font(
//                    R.font.notosans_regular,
//                    FontWeight.Normal,
//                    FontStyle.Normal
//                )
//            )
//        )
        for (i: String in charInfo.ability.value) {
            BasicInfoContentsTextView(text = i)
        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 8.dp, 0.dp, 4.dp),
            color = DividerColor,
            thickness = 1.dp
        )

        BasicTitleTextView(text = "하이퍼 스탯")

        for (i: String in charInfo.hyperStats) {
            BasicInfoContentsTextView(text = i)
        }

    }

}

@Composable
fun EquipmentList(items: MutableList<ItemsVo>, jobClass: String) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(items) {
            Equipment(itemsVo = it, jobClass)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun Equipment(itemsVo: ItemsVo, jobClass: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp, 16.dp, 0.dp)
    ) {
        GlideImage(
            model = itemsVo.image, contentDescription = itemsVo.name,
            modifier = Modifier
                .height(50.dp)
                .width(50.dp)
                .align(Alignment.CenterVertically),
            alignment = Alignment.Center
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp, 0.dp, 0.dp, 0.dp)
        ) {
            Row {
                BasicEquipmentTextview(text = itemsVo.name)
                Spacer(modifier = Modifier.width(6.dp))
                BasicEquipmentTextview(text = itemsVo.starforce.replace(" 강화", ""))

                Spacer(modifier = Modifier.width(4.dp))
                if (!emptyAddOption.contains(itemsVo.itemType)) {
                    BasicEquipmentTextview(
                        text = AddOptionCalculator().calculateAddOption(
                            itemsVo,
                            jobClass.split("/")[1]
                        ).toString() + "급"
                    )
                }
            }

            Column {
                Spacer(modifier = Modifier.width(8.dp))
                var color = when (itemsVo.potential.grade) {
                    "레어" -> RareBackgroundColor
                    "에픽" -> EpicBackgroundColor
                    "유니크" -> UniqueBackgroundColor
                    "레전드리" -> LegendaryBackgroundColor
                    else -> Color.Transparent
                }
                Card(
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(color)
                ) {
                    Row(
                        modifier = Modifier
                            .wrapContentHeight()
                            .wrapContentWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        BasicEquipmentTextview(text = itemsVo.potential.grade)
                        PotentialText(potential = itemsVo.potential)
                    }
                }


                Spacer(modifier = Modifier.height(8.dp))

                if (itemsVo.additionalPotential.grade.isNotEmpty()) {
                    color = when (itemsVo.additionalPotential.grade) {
                        "레어" -> RareBackgroundColor
                        "에픽" -> EpicBackgroundColor
                        "유니크" -> UniqueBackgroundColor
                        "레전드리" -> LegendaryBackgroundColor
                        else -> Color.Transparent
                    }

                    Card(
                        shape = RoundedCornerShape(5.dp),
                        colors = CardDefaults.cardColors(color)
                    ) {
                        Row(
                            modifier = Modifier
                                .wrapContentHeight()
                                .wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            BasicEquipmentTextview(text = itemsVo.additionalPotential.grade)
                            PotentialText(potential = itemsVo.additionalPotential)
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun PotentialText(potential: Potential) {
    for (item in potential.option) {
        if (item is ArrayList<*>) {
            var potentialText = ""

            potentialText = when (item[0]) {
                "몬스터 방어율 무시" -> {
                    "방무"
                }

                "보스 몬스터 공격 시 데미지" -> {
                    "보공"
                }

                "메소 획득량" -> {
                    "메획"
                }

                "아이템 드롭률" -> {
                    "아획"
                }

                "모든 스킬의 재사용 대기시간" -> {
                    "쿨감"
                }

                "크리티컬 데미지" -> {
                    "크뎀"
                }

                else -> {
                    item[0].toString()
                }
            }

            if (potentialText == "쿨감") {
                BasicEquipmentTextview(
                    text = potentialText + " " + item[1].toString().substring(0, 3)
                )
            } else {
                BasicEquipmentTextview(text = potentialText + " " + item[1].toString())
            }


            Spacer(modifier = Modifier.width(6.dp))
        }
    }

}