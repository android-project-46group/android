package io.kokoichi.sample.sakamichiapp.ui

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.Button
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout


@Composable
fun SortBar() {
    val groups = mockGroups
    val BORDER_COLOR = Color.Gray
    val BORDER_RADIUS = 2.dp

    val KEY_FONT_SIZE = 10.sp
    val VALUE_FONT_SIZE = 15.sp

    val SORT_KEY_MESSAGE = "並びかえ"
    val SORT_VAL_DEFAULT = "選んでください"
    val SORT_VAL_BY_NAME = "50音順"
    val SORT_VAL_BY_BIRTHDAY = "生年月日"
    val SORT_VAL_BY_BLOOD = "血液型"

    val TAG = "SortBar"

    Row(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth()
    
    ) {
        Box(
            modifier = Modifier
                .weight(2f)
                .padding(4.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = SORT_KEY_MESSAGE, fontSize = KEY_FONT_SIZE)
        }

        var sortExpanded by remember { mutableStateOf(false) }
        var sortValMessage = SORT_VAL_DEFAULT
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
                .weight(3f)
        ) {
            Button(
                modifier = Modifier
                    .padding(vertical = 2.dp),
                onClick = {
                    sortExpanded = true
                }
            ) {
                Text(text = sortValMessage, fontSize = 8.sp)
            }
            DropdownMenu(expanded = sortExpanded, onDismissRequest = { sortExpanded = false }) {
                DropdownMenuItem(
                    onClick = {
                        Log.d(TAG, "sort by name order was clicked")
                        sortValMessage = SORT_VAL_BY_NAME
                    }
                ) {
                    Text(SORT_VAL_BY_NAME)
                }
                DropdownMenuItem(
                    onClick = { sortValMessage = SORT_VAL_BY_BIRTHDAY }
                ) {
                    Text(SORT_VAL_BY_BIRTHDAY)
                }
                DropdownMenuItem(
                    onClick = { sortValMessage = SORT_VAL_BY_BLOOD },
                ) {
                    Text(SORT_VAL_BY_BLOOD)
                }
            }
        }

        //
        // 絞り込みを行う
        Box(
            modifier = Modifier
                .weight(2f)
                .padding(4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "絞り込み", fontSize = KEY_FONT_SIZE)
        }

        var expanded by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopStart)
                .weight(3f)
        ) {
            Button(
                modifier = Modifier
                    .padding(vertical = 2.dp),
                onClick = {
                    expanded = true
                }
            ) {
                Text(text = "選んでください", fontSize = 8.sp)
            }
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                DropdownMenuItem(
                    onClick = {
                        Log.d(TAG, "1st gen selected")
                    }
                ) {
                    Text("1期生")
                }
                DropdownMenuItem(
                    onClick = {  }
                ) {
                    Text("2期生")
                }
                DropdownMenuItem(
                    onClick = { /* Handle send feedback! */ },
                ) {
                    Text("3期生")
                }
            }
        }
    }
}

