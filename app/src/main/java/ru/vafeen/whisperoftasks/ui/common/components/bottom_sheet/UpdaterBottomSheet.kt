package ru.vafeen.whisperoftasks.ui.components.bottom_sheet

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.vafeen.whisperoftasks.R
import ru.vafeen.whisperoftasks.network.downloader.Downloader
import ru.vafeen.whisperoftasks.network.parcelable.github_service.Release
import ru.vafeen.whisperoftasks.network.repository.NetworkRepository
import ru.vafeen.whisperoftasks.noui.network.end_points.RepoInfo
import ru.vafeen.whisperoftasks.ui.theme.FontSize
import ru.vafeen.whisperoftasks.ui.theme.updateAvailableColor
import ru.vafeen.whisperoftasks.utils.Path


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdaterBottomSheet(
    networkRepository: NetworkRepository,
    release: Release,
    state: SheetState,
    onDismissRequest: (Boolean) -> Unit,
) {
    val context = LocalContext.current
    ModalBottomSheet(
        sheetState = state,
        onDismissRequest = { onDismissRequest(false) },
        containerColor = updateAvailableColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize(0.8f)
                .padding(10.dp),
        ) {

            Text(
                text = ":)",
                fontSize = FontSize.gigant,
                modifier = Modifier.align(Alignment.Start),
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = stringResource(id = R.string.update_need),
                fontSize = FontSize.huge27, color = Color.White
            )
            Spacer(modifier = Modifier.height(40.dp))
            Text(
                text = stringResource(id = R.string.view_releases),
                fontSize = FontSize.huge27, color = Color.White
            )
            Spacer(modifier = Modifier.height(40.dp))
            Image(
                painter = painterResource(id = R.drawable.qr),
                contentDescription = "qr",
                modifier = Modifier
                    .clickable {
                        Downloader.downloadApk(
                            networkRepository = networkRepository,
                            url = "${RepoInfo.USER_NAME}/${RepoInfo.REPO_NAME}/releases/download/${release.tag_name}/${release.assets[0].name}",
                            filePath = Path
                                .path(context)
                                .toString(),
                        )
                        CoroutineScope(Dispatchers.IO).launch {
                            Downloader.isUpdateInProcessFlow.emit(true)
                        }
                        onDismissRequest(true)
                    }
                    .size(150.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}