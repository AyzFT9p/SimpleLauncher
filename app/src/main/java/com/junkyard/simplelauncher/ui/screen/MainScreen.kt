package com.junkyard.simplelauncher.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.junkyard.simplelauncher.ui.model.AppInfo

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    apps: List<AppInfo> = emptyList(),
    onClick: (appInfo: AppInfo) -> Unit = {},
) {
    Column(modifier = modifier) {
        AppsList(
            apps = apps,
            onClick = onClick,
        )
    }
}

@Composable
fun AppsList(
    apps: List<AppInfo>,
    onClick: (appInfo: AppInfo) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(modifier = modifier) {
        items(apps.size) { index ->

            ListItem(
                modifier = Modifier.clickable(onClick = {
                    onClick(apps[index])
                }),
                leadingContent = {
                    Image(
                        bitmap = apps[index].icon.toBitmap().asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .size(40.dp)
                    )
                },

                headlineContent = {
                    Text(text = apps[index].appName)
                },
//                supportingContent = {
//                    Text(text = apps[index].packageName)
//                }
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}