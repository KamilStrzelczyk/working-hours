package com.example.workinghours.presentation.listOfUsersScreen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.workinghours.presentation.addWorkTimeScreen.AddWorkTimeViewModel
import com.example.workinghours.presentation.adminScreen.admin.AdminViewModel
import com.example.workinghours.presentation.previousDaysScreen.PreviousDaysViewModel
import com.example.workinghours.ui.theme.WorkingHoursTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListOfUsersActivity : ComponentActivity() {
    private val listOfUsersScreenViewModel: ListOfUsersViewModel by viewModels()
    private val addWorkTimeViewModel: AddWorkTimeViewModel by viewModels()
    private val previousDaysViewModel: PreviousDaysViewModel by viewModels()
    private val adminViewModel: AdminViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkingHoursTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    ListOfUsersScreen(
                        listOfUsersViewModel = listOfUsersScreenViewModel,
                        adminViewModel = adminViewModel,
                    )
                }
            }
        }
    }
}