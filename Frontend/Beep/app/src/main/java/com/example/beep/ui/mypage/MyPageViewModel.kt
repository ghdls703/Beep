package com.example.beep.ui.mypage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beep.data.dto.mypage.UserInfoResponse
import com.example.beep.domain.UserUseCase
import com.example.beep.ui.message.UiState
import com.example.beep.util.ResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyPageViewModel @Inject constructor(private val userUseCase: UserUseCase) :
    ViewModel() {

    private var userDataScreenState: UiState<UserInfoResponse> by mutableStateOf(UiState.Loading)
    var engraveScreenUiState: UiState<String> by mutableStateOf(UiState.Success(""))
    var engraveText: String by mutableStateOf("")
    var themeNum: Int by mutableStateOf(1)
    var fontNum: Int by mutableStateOf(1)

    private fun getUserInfo() {
        userDataScreenState = UiState.Loading
        viewModelScope.launch {
            val result = userUseCase.getUserInfo()
            result.collectLatest {
                userDataScreenState = when(it) {
                    is ResultType.Success -> {
                        engraveText = it.data.data.engrave
                        UiState.Success(it.data.data)
                    }
                    else -> {
                        UiState.Error
                    }
                }
            }
        }
    }

    fun writeEngrave() {
        engraveScreenUiState = UiState.Loading
        viewModelScope.launch {
            if(engraveText == null) return@launch
            val result = userUseCase.setEngrave(engraveText)
            result.collectLatest {
                engraveScreenUiState = when (it) {
                    is ResultType.Success -> {
                        getUserInfo()
                        UiState.Success("")
                    }
                    else -> {
                        UiState.Error
                    }
                }
            }
        }
    }

    fun changeTheme() {
        engraveScreenUiState = UiState.Loading
        viewModelScope.launch {
            if(engraveText == null) return@launch
            val result = userUseCase.setTheme(themeNum)
            result.collectLatest {
                engraveScreenUiState = when (it) {
                    is ResultType.Success -> {
                        UiState.Success("")
                    }
                    else -> {
                        UiState.Error
                    }
                }
            }
        }
    }

    fun changeFont() {
        engraveScreenUiState = UiState.Loading
        viewModelScope.launch {
            if(engraveText == null) return@launch
            val result = userUseCase.setFont(fontNum)
            result.collectLatest {
                engraveScreenUiState = when (it) {
                    is ResultType.Success -> {
                        UiState.Success("")
                    }
                    else -> {
                        UiState.Error
                    }
                }
            }
        }
    }

//    val exampleEntities: Flow<Response<List<PresetResponse>>> = PresetUseCase.getUserPresetByToken()

    /*
        StateFlow를 사용하는 방식
    */
//    private val _userMessagePresetList: MutableStateFlow<List<PresetResponse>> = MutableStateFlow(listOf())
//    val userMessagePresetList get() = _userMessagePresetList.value
//
//    private fun getUserMessagePreset() {
//        viewModelScope.launch {
//            getUserMessagePresetUseCase.execute(uid)
//                .collectLatest { _userMessagePresetList.value = it }
//        }
//    }
    init {
        getUserInfo()
    }
}