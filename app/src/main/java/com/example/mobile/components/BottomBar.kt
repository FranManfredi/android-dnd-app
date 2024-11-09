package com.example.mobile.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.mobile.navigation.MobileScreen
import com.example.mobile.ui.theme.orange

@Composable
fun BottomBar(
    selectedRoute: String,
    onNavigate: (String) -> Unit,
) {
    val homeTab = TabBarItem(
        title = MobileScreen.Home.name,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
    val settingsTab = TabBarItem(
        title = MobileScreen.Compendium.name,
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Outlined.Star
    )
    val moreTab = TabBarItem(
        title = MobileScreen.Settings.name,
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Filled.Settings
    )

    val tabBarItems = listOf(homeTab, settingsTab, moreTab)

    TabView(tabBarItems, selectedRoute, onNavigate)
}

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val badgeAmount: Int? = null
)

@Composable
fun TabView(
    tabBarItems: List<TabBarItem>,
    selectedRoute: String,
    onNavigate: (String) -> Unit
) {
    NavigationBar {
        tabBarItems.forEach { tabBarItem ->
            val isSelected = tabBarItem.title == selectedRoute

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    onNavigate(tabBarItem.title)
                },
                icon = {
                    TabBarIconView(
                        isSelected = isSelected,
                        selectedIcon = tabBarItem.selectedIcon,
                        unselectedIcon = tabBarItem.unselectedIcon,
                        title = tabBarItem.title,
                        badgeAmount = tabBarItem.badgeAmount,
                    )
                },
                label = { Text(tabBarItem.title) },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = orange,
                    unselectedIconColor = orange,
                )
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabBarIconView(
    isSelected: Boolean,
    selectedIcon: ImageVector,
    unselectedIcon: ImageVector,
    title: String,
    badgeAmount: Int? = null
) {
    BadgedBox(badge = { TabBarBadgeView(badgeAmount) }) {
        Icon(
            imageVector = if (isSelected) {selectedIcon} else {unselectedIcon},
            contentDescription = title
        )
    }
}

@Composable
fun TabBarBadgeView(count: Int? = null) {
    if (count != null) {
        Badge {
            Text(count.toString())
        }
    }
}
