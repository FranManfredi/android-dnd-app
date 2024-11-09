package com.example.mobile.security

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.mobile.R
import javax.inject.Inject
class BiometricAuthManager @Inject constructor() {
    @Composable
    fun authenticate(context: Context, onError: () -> Unit, onSuccess: () -> Unit, onFail: () -> Unit) {
        val executor = ContextCompat.getMainExecutor(context)
        val biometricPrompt = BiometricPrompt(
            context as FragmentActivity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    // handle authentication error here
                    onError()
                }
                @RequiresApi(Build.VERSION_CODES.R)
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    // handle authentication success here
                    onSuccess()
                }
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // handle authentication failure here
                    onFail()
                }
            }
        )
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setAllowedAuthenticators(BIOMETRIC_STRONG)
            .setTitle(stringResource(R.string.biometric_title))
            .setSubtitle(stringResource(R.string.biometric_subtitle))
            .setNegativeButtonText(stringResource(R.string.biometric_cancel_button))
            .build()
        biometricPrompt.authenticate(promptInfo)
    }
}