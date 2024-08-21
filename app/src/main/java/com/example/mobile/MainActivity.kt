package com.example.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.mobile.components.BottomBar
import com.example.mobile.components.TopBar
import com.example.mobile.navigation.NavHostComposable
import com.example.mobile.ui.theme.MobileTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MobileTheme {
                Surface (
                    modifier = Modifier.fillMaxSize().padding(top = 35.dp),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        topBar = {
                            TopBar("something")
                        },
                        bottomBar = {
                            BottomBar { navController.navigate(it) }
                        },
                    ) { innerPadding ->
                        NavHostComposable(innerPadding, navController)
                    }
                }
            }
        }
    }
}
