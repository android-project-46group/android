package jp.mydns.kokoichi0206.settings

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.mydns.kokoichi0206.common.BuildConfigWrapper
import jp.mydns.kokoichi0206.common.GroupName
import jp.mydns.kokoichi0206.common.Resource
import jp.mydns.kokoichi0206.common.datamanager.DataStoreManager
import jp.mydns.kokoichi0206.domain.usecase.get_members.GetMembersUseCase
import jp.mydns.kokoichi0206.domain.usecase.other_api.ReportIssueUseCase
import jp.mydns.kokoichi0206.domain.usecase.other_api.UpdateBlogUseCase
import jp.mydns.kokoichi0206.domain.usecase.quiz_record.RecordUseCases
import jp.mydns.kokoichi0206.model.Member
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val membersUseCase: GetMembersUseCase,
    private val updateBlogUseCase: UpdateBlogUseCase,
    private val reportIssueUseCase: ReportIssueUseCase,
    private val recordUseCase: RecordUseCases,
    private val buildConfigWrapper: BuildConfigWrapper,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    var uiState: StateFlow<SettingsUiState> = _uiState

    init {
        getAccuracy()
    }

    fun initAllMembers() {
        _uiState.update {
            it.copy(allMembers = emptyList())
        }

        GroupName.values().forEach { group ->
            membersUseCase(group.name.lowercase()).onEach { result ->
                when (result) {
                    is Resource.Success -> {
                        val new = _uiState.value.allMembers + result.data!!
                        _uiState.update { it.copy(allMembers = new) }
                    }

                    else -> {}
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getAccuracy() {
        if (_uiState.value.records.size != 0) {
            return
        }
        viewModelScope.launch {
            val groups = GroupName.values()
            groups.forEach { group ->
                recordUseCase.getAccuracy(
                    group = group.name
                ).let { res ->
                    _uiState.update {
                        it.copy(
                            records = (it.records + Record(
                                group = group.jname,
                                correct = res[0],
                                total = res[1],
                            )).toMutableList()
                        )
                    }
                }
            }
        }
    }

    fun readUserID(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val id = DataStoreManager.readString(context, DataStoreManager.KEY_USER_ID)
            _uiState.update { it.copy(userId = id) }
        }
    }

    fun reportIssue(message: String) {
        reportIssueUseCase(message).launchIn(viewModelScope)
    }

    fun updateBlog() {
        updateBlogUseCase().launchIn(viewModelScope)
    }

    fun writeIsDevTrue(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                DataStoreManager.writeBoolean(
                    context,
                    DataStoreManager.KEY_IS_DEVELOPER,
                    true
                )
            }
        }
    }

    fun writeTheme(
        context: Context,
        theme: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                DataStoreManager.writeString(
                    context,
                    DataStoreManager.KEY_THEME_GROUP,
                    theme,
                )
            }
        }
    }

    fun readThemeFromDataStore(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val res = async {
                DataStoreManager.readString(context, DataStoreManager.KEY_THEME_GROUP)
            }
            setThemeType(res.await())
        }
    }

    fun writeFaveName(
        context: Context,
        faveName: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                DataStoreManager.writeString(
                    context,
                    DataStoreManager.KEY_FAVE_NAME,
                    faveName,
                )
            }
        }
    }

    fun writeFaveUri(
        context: Context,
        faveUri: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            async {
                DataStoreManager.writeString(
                    context,
                    DataStoreManager.KEY_FAVE_URI,
                    faveUri,
                )
            }
        }
    }

    fun readFavesFromDataStore(context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            val name = async {
                DataStoreManager.readString(context, DataStoreManager.KEY_FAVE_NAME)
            }
            val uri = async {
                DataStoreManager.readString(context, DataStoreManager.KEY_FAVE_URI)
            }
            uri.await().let { str ->
                Uri.parse(str)?.let { uri ->
                    _uiState.update { it.copy(faveURI = uri) }
                }
            }

            val na = name.await()

            _uiState.value.allMembers
                .firstOrNull { it.name == na }?.let { member ->
                    _uiState.update { it.copy(fave = member) }
                }
        }
    }

    fun readVersion() {
        _uiState.update { it.copy(version = buildConfigWrapper.VERSION) }
    }

    fun readAppName() {
        _uiState.update { it.copy(appName = buildConfigWrapper.APP_NAME) }
    }

    fun setThemeType(typeStr: String) {
        val type = themeTypes
            .firstOrNull { it.name == typeStr }

        _uiState.update {
            it.copy(
                themeType = type ?: ThemeType.BasicNight
            )
        }
    }

    fun setThemeType(type: ThemeType) {
        _uiState.update { it.copy(themeType = type) }
    }

    fun imageSelected(
        context: Context,
        uri: Uri,
    ) {
        _uiState.update { it.copy(faveURI = uri) }
        writeFaveUri(context, uri.toString())
    }

    fun memberSelected(
        context: Context,
        member: Member,
    ) {
        val uri = Uri.parse(member.imgUrl)
        _uiState.update { it.copy(fave = member, faveURI = uri) }
        uri?.let {
            writeFaveUri(context, it.toString())
        }
        writeFaveName(context, member.name)
    }
}
