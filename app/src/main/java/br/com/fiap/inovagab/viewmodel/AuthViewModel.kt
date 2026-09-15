package br.com.fiap.inovagab.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.fiap.inovagab.data.remote.model.LoginRequest
import br.com.fiap.inovagab.data.remote.model.LoginResponse
import br.com.fiap.inovagab.data.remote.network.RetrofitInstance
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    var currentUser: LoginResponse? = null
        private set

    fun isUserLoggedIn(): Boolean {
        return currentUser != null
    }

    fun login(
        email: String,
        password: String,
        onSuccess: (LoginResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.authApi.login(
                    LoginRequest(
                        email = email.trim(),
                        senha = password
                    )
                )

                currentUser = response
                onSuccess(response)

            } catch (e: Exception) {
                onError(e.message ?: "Não foi possível realizar o login.")
            }
        }
    }

    fun logout() {
        currentUser = null
    }
}