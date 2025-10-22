package com.khs.figle_m.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.khs.figle_m.ui.theme.*

/**
 * FIGLE 앱의 홈 화면 Composable
 *
 * @param onSearch 검색 버튼 클릭 또는 Enter 키 입력 시 호출되는 콜백
 * @param onRankingClick 랭킹 버튼 클릭 시 호출되는 콜백
 * @param modifier Modifier
 */
@Composable
fun HomeScreen(
    onSearch: (String) -> Unit,
    onRankingClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var searchText by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(FragmentBackground),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.7f))

            // FIGLE 타이틀
            Text(
                text = "FIGLE",
                fontSize = 50.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .wrapContentHeight(Alignment.CenterVertically)
            )

            Spacer(modifier = Modifier.height(40.dp))

            // 검색 입력 필드
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                modifier = Modifier
                    .width(300.dp)
                    .height(60.dp),
                placeholder = {
                    Text(
                        text = "구단주명을 입력하세요",
                        color = Grey2
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "검색",
                        tint = Grey1
                    )
                },
                trailingIcon = {
                    if (searchText.isNotEmpty()) {
                        IconButton(onClick = { searchText = "" }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "지우기",
                                tint = Grey1
                            )
                        }
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedTextColor = SearchTextColor,
                    unfocusedTextColor = SearchTextColor,
                    focusedBorderColor = ColorPrimary,
                    unfocusedBorderColor = Grey2,
                    cursorColor = ColorPrimary,
                    focusedContainerColor = ButtonColor,
                    unfocusedContainerColor = ButtonColor
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (searchText.isNotEmpty()) {
                            onSearch(searchText)
                            focusManager.clearFocus()
                        }
                    }
                ),
                shape = RoundedCornerShape(8.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // 랭킹 버튼 (현재 visibility gone으로 숨겨져 있음)
            // 필요시 활성화
            /*
            Button(
                onClick = onRankingClick,
                modifier = Modifier
                    .width(100.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ColorPrimary
                )
            ) {
                Text(
                    text = "랭킹",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.weight(0.7f))
            */
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    FigleComposeTheme {
        HomeScreen(
            onSearch = { searchText ->
                println("검색: $searchText")
            },
            onRankingClick = {
                println("랭킹 클릭")
            }
        )
    }
}
