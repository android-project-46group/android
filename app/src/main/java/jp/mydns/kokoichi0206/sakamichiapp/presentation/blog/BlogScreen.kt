package jp.mydns.kokoichi0206.sakamichiapp.presentation.blog

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import jp.mydns.kokoichi0206.common.Constants
import jp.mydns.kokoichi0206.common.GroupName
import jp.mydns.kokoichi0206.common.components.GroupBar
import jp.mydns.kokoichi0206.common.ui.theme.*
import jp.mydns.kokoichi0206.model.getBlogUrlProps
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.Screen
import jp.mydns.kokoichi0206.sakamichiapp.presentation.util.TestTags
import jp.mydns.kokoichi0206.sakamichiapp.R
import jp.mydns.kokoichi0206.sakamichiapp.presentation.blog.components.SkeletonBlogScreen
import jp.mydns.kokoichi0206.sakamichiapp.presentation.blog.components.blogImage
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

@Composable
fun BlogScreenWithCustomTheme(
    navController: NavController,
    viewModel: BlogViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    CustomSakaTheme(group = uiState.groupName.jname) {
        BlogScreen(
            navController = navController,
            uiState = uiState,
            viewModel = viewModel,
        )
    }
}

@Composable
fun BlogScreen(
    navController: NavController,
    uiState: BlogUiState,
    viewModel: BlogViewModel,
) {
    if (!uiState.loaded) {
        viewModel.setApiBlogs()
        viewModel.setLoaded(true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BlogTopBar(
            uiState = uiState,
            viewModel = viewModel,
        )

        GroupBar(
            selectedGroupName = uiState.groupName,
            onclick = { gn ->
                viewModel.setGroupName(gn)
                viewModel.setLoaded(false)
            },
        )

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing = uiState.isRefreshing),
            onRefresh = { viewModel.setApiBlogs() }
        ) {
            SwipableBlogArea(
                navController = navController,
                uiState = uiState,
            )
        }
    }
}

@Composable
fun SwipableBlogArea(
    navController: NavController,
    uiState: BlogUiState,
) {
    if (uiState.isLoading) {
        SkeletonBlogScreen()
    } else if (uiState.error.isNotBlank()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = uiState.error,
                color = MaterialTheme.colors.primary,
            )
        }
    } else {
        val rows = uiState.blogs.chunked(Constants.BLOG_ONE_ROW_NUM)
        LazyColumn(
            contentPadding = Constants.BottomBarPadding,
        ) {
            items(rows) { row ->
                OneBlogRow(
                    row = row,
                    uiState = uiState,
                    navController = navController,
                )
            }
        }
    }
}

@Composable
fun BlogTopBar(
    uiState: BlogUiState,
    viewModel: BlogViewModel
) {
    // Box to cancel the column (upper level)
    Box(
        contentAlignment = Alignment.CenterEnd,
    ) {
        val isChecked = uiState.isSortTime
        // Page title
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SpaceSmall)
                .testTag(TestTags.BLOG_TITLE),
            text = stringResource(R.string.blog_screen_title),
            style = Typography.h5,
            textAlign = TextAlign.Center,
        )

        // Sort type button (Name or Time
        val boxShape = MaterialTheme.shapes.small
        val customColor = if (isChecked) {
            Color.White
        } else {
            MaterialTheme.colors.secondary
        }
        val reverseColor = if (isChecked) {
            MaterialTheme.colors.secondary
        } else {
            Color.White
        }
        Row(
            modifier = Modifier
                .padding(end = SpaceSmall)
                .clip(boxShape)
                .background(
                    color = reverseColor,
                )
                .clickable {
                    viewModel.toggleIsSortTime()
                    viewModel.sortBlogs()
                }
                .border(
                    width = 2.dp,
                    color = customColor,
                    shape = boxShape
                )
                .padding(horizontal = SpaceSmall)
                .testTag(TestTags.BLOG_SORT_BUTTON),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            Image(
                painter = painterResource(id = R.drawable.up),
                contentDescription = null,
                modifier = Modifier
                    .size(SpaceMedium),
                colorFilter = ColorFilter.tint(customColor)
            )
            Text(
                text = if (isChecked) {
                    stringResource(R.string.blog_sort_key_time)
                } else {
                    stringResource(R.string.blog_sort_key_name)
                },
                style = Typography.caption,
                color = customColor,
            )
        }
    }
}

@Composable
fun OneBlogRow(
    row: List<jp.mydns.kokoichi0206.model.Blog>,
    uiState: BlogUiState,
    navController: NavController,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        // Up to three members (blogs) in one row
        for (i in 0 until Constants.BLOG_ONE_ROW_NUM) {
            if (row.size > i) {
                OneBlog(
                    uiState = uiState,
                    blog = row[i],
                    onclick = { blog ->
                        val navPath = "${Screen.WebViewScreen.route}/" +
                                "${Constants.NAV_PARAM_WEBVIEW_PROPS}=${getBlogUrlProps(blog)}"
                        navController.navigate(navPath)
                    },
                    modifier = Modifier
                        .weight(1f)
                )
            } else {
                Box(
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
    }
}

@Composable
fun OneBlog(
    uiState: BlogUiState,
    blog: jp.mydns.kokoichi0206.model.Blog,
    onclick: (jp.mydns.kokoichi0206.model.Blog) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(vertical = SpaceSmall)
            .clickable {
                onclick(blog)
            }
            .testTag(TestTags.BLOG_ONE_BOX),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val context = LocalContext.current

        val request = ImageRequest.Builder(context)
            .data(blog.lastBlogImg)
            .placeholder(
                when (uiState.groupName) {
                    GroupName.NOGIZAKA ->
                        R.drawable.nogizaka_official_icon
                    GroupName.SAKURAZAKA ->
                        R.drawable.sakurazaka_official_icon
                    GroupName.HINATAZAKA ->
                        R.drawable.hinata_official_icon
                }
            )
            .build()
        val imageLoader = ImageLoader.Builder(context)
            .crossfade(true)
            .okHttpClient {
                OkHttpClient.Builder()
                    .addInterceptor(jp.mydns.kokoichi0206.data.remote.LoggingInterceptor())
                    .addInterceptor(jp.mydns.kokoichi0206.data.remote.RetryInterceptor())
                    // サーバー側の設定か、なぜか指定が必要！
                    .connectTimeout(777, TimeUnit.MILLISECONDS)
                    .build()
            }
            .memoryCache {
                MemoryCache.Builder(context)
                    .maxSizePercent(0.25)
                    .strongReferencesEnabled(true)
                    .build()
            }
            .diskCache {
                DiskCache.Builder()
                    .directory(context.cacheDir.resolve("image_cache"))
                    .maxSizePercent(0.02)
                    .build()
            }
            .build()

        AsyncImage(
            model = request,
            imageLoader = imageLoader,
            contentDescription = "blog image of ${blog.name}",
            contentScale = ContentScale.Crop,
            alignment = Alignment.Center,
            modifier = Modifier
                .blogImage(),
        )

        Text(
            modifier = Modifier
                .padding(SpaceSmall),
            text = blog.name,
            style = Typography.body2,
            color = MaterialTheme.colors.secondary,
        )
        Text(
            modifier = Modifier
                .padding(bottom = SpaceTiny),
            text = blog.lastUpdatedAt,
            style = Typography.caption,
        )
    }
}
