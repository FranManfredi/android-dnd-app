package com.example.mobile.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.example.mobile.R
import com.example.mobile.navigation.MobileScreen
import com.example.mobile.ui.theme.orange

@Composable
fun BottomBar(
    selectedRoute: String,
    onNavigate: (String) -> Unit,
) {
    val homeTab = TabBarItem(
        screen = MobileScreen.Home,
        title = stringResource(id = R.string.title_home),
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )
    val compendiumTab = TabBarItem(
        screen = MobileScreen.Compendium,
        title = stringResource(id = R.string.title_compendium),
        selectedIcon = Icons.Filled.Star,
        unselectedIcon = Icons.Outlined.Star
    )
    val settingsTab = TabBarItem(
        screen = MobileScreen.Settings,
        title = stringResource(id = R.string.title_settings),
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Filled.Settings
    )

    val tabBarItems = listOf(homeTab, compendiumTab, settingsTab)

    TabView(tabBarItems, selectedRoute, onNavigate)
}

data class TabBarItem(
    val screen: MobileScreen, // Use MobileScreen enum for routes
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
            val isSelected = tabBarItem.screen.name == selectedRoute

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    onNavigate(tabBarItem.screen.name) // Navigate using route name
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
            imageVector = if (isSelected) selectedIcon else unselectedIcon,
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