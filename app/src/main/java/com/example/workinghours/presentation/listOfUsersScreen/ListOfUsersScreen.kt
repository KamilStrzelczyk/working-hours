package com.example.workinghours.presentation.listOfUsersScreen

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.workinghours.R
import com.example.workinghours.domain.model.User
import com.example.workinghours.presentation.addWorkTimeScreen.AddWorkTimeActivity
import com.example.workinghours.presentation.adminScreen.admin.AdminActivity
import com.example.workinghours.presentation.adminScreen.admin.AdminViewModel
import com.example.workinghours.presentation.previousDaysScreen.PreviousDayActivity

@Composable
fun ListOfUsersScreen(
    listOfUsersViewModel: ListOfUsersViewModel,
    adminViewModel: AdminViewModel,
) {
    val listOfUsersState = listOfUsersViewModel.state.value
    val adminState = adminViewModel.state.value
    val context = LocalContext.current
    ListOfUsersScreen(
        userList = listOfUsersState.userList,
        showTopAppBarMoreAction = listOfUsersState.showTopAppBarMoreAction,
        adminOption = adminState.adminOption,
        showUserActionsDialog = listOfUsersState.showUserActionsDialog,
        onUsersClicked = { listOfUsersViewModel.onUsersClicked() },
        onTopAppBarMoreActionClicked = listOfUsersViewModel::onTopAppBarMoreActionClicked,
        onDismissTopAppBarMoreAction = listOfUsersViewModel::onDismissTopAppBarMoreAction,
        onDismissUserActionsDialog = listOfUsersViewModel::onDismissUserActionsDialog,
        navigateToAddWorkTimeScreen = {
            context.startActivity(Intent(context,
                AddWorkTimeActivity::class.java))
        },
        navigateToPreviousDayScreen = {
            context.startActivity(Intent(context,
                PreviousDayActivity::class.java))
        })
}

@Composable
private fun ListOfUsersScreen(
    userList: List<User>,
    showTopAppBarMoreAction: Boolean,
    adminOption: Boolean,
    showUserActionsDialog: Boolean,
    onUsersClicked: () -> Unit,
    onTopAppBarMoreActionClicked: () -> Unit,
    onDismissTopAppBarMoreAction: () -> Unit,
    onDismissUserActionsDialog: () -> Unit,
    navigateToAddWorkTimeScreen: () -> Unit,
    navigateToPreviousDayScreen: () -> Unit,

    ) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                modifier = Modifier,
                title = {
                    Text(
                        text = "Czas pracy"
                    )
                },
                actions = {
                    IconButton(
                        onClick = onTopAppBarMoreActionClicked
                    ) {
                        Icon(Icons.Filled.MoreVert, contentDescription = null)
                    }
                    MoreAction(
                        showTopAppBarMoreAction = showTopAppBarMoreAction,
                        onDismissTopAppBarMoreAction = onDismissTopAppBarMoreAction)
                }
            )
        }
    ) { padding ->

        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            LazyColumn(modifier = Modifier
                .fillMaxSize()
            ) {
                items(userList) {
                    UserNameBox(
                        userName = it.userName,
                        showUserActionDialog = onUsersClicked,
                        showAdminOption = adminOption,
                    )
                }
            }
        }
    }

    UserActionsDialog(
        showUserActionsDialog = showUserActionsDialog,
        onDismissUserActionsDialog = onDismissUserActionsDialog,
        navigateToAddWorkTimeScreen = navigateToAddWorkTimeScreen,
        navigateToPreviousDayScreen = navigateToPreviousDayScreen)

}

@Composable
private fun UserNameBox(
    userName: String,
    showUserActionDialog: () -> Unit,
    showAdminOption: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable(onClick = showUserActionDialog)
    ) {
        Card(elevation = 10.dp) {
            Row(
                modifier = Modifier
                    .padding(24.dp)
                    .height(56.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = userName)
                if (showAdminOption) {

                }
            }
        }
    }
}


@Composable
private fun UserActionsDialog(
    showUserActionsDialog: Boolean,
    onDismissUserActionsDialog: () -> Unit,
    navigateToPreviousDayScreen: () -> Unit,
    navigateToAddWorkTimeScreen: () -> Unit,
) {

    if (showUserActionsDialog)
        Dialog(onDismissRequest = { onDismissUserActionsDialog() }) {
            Surface(modifier = Modifier
                .clip(RoundedCornerShape(16.dp))) {
                Box(modifier = Modifier
                    .padding(20.dp),
                    contentAlignment = Alignment.Center)
                {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Surface(elevation = 4.dp, shape = RoundedCornerShape(16.dp)) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(112.dp)
                                    .clickable {
                                        navigateToPreviousDayScreen()
                                        onDismissUserActionsDialog()
                                    },
                            ) {
                                Column(modifier = Modifier
                                    .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center)
                                {

                                    Text(text = "Poprzednie dni", Modifier.padding(5.dp))
                                    Image(painter = painterResource(id = R.drawable.calendar_image),
                                        contentDescription = null)
                                }
                            }
                        }

                        Spacer(modifier = Modifier.weight(0.1f))

                        Surface(elevation = 4.dp, shape = RoundedCornerShape(16.dp)) {
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(112.dp)
                                    .clickable {
                                        navigateToAddWorkTimeScreen()
                                        onDismissUserActionsDialog()
                                    })
                            {
                                Column(modifier = Modifier
                                    .fillMaxHeight(),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(text = "Dodaj nowy dzień", Modifier.padding(5.dp))
                                    Image(painter = painterResource(id = R.drawable.calendar_add_image),
                                        contentDescription = null)
                                }
                            }
                        }
                    }
                }
            }
        }
}

@Composable
private fun MoreAction(
    showTopAppBarMoreAction: Boolean,
    onDismissTopAppBarMoreAction: () -> Unit,
) {
    val context = LocalContext.current
    if (showTopAppBarMoreAction)
        DropdownMenu(
            expanded = true,
            onDismissRequest = onDismissTopAppBarMoreAction
        ) {
            DropdownMenuItem(
                onClick = {
                    onDismissTopAppBarMoreAction()
                    context.startActivity(Intent(context, AdminActivity::class.java))
                }
            ) {
                Image(painter = painterResource(id = R.drawable.account_icon),
                    contentDescription = null)
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "Admin")
            }
        }
}

//@Preview(showBackground = true)
//@Composable
//private fun ListOfUsersScreenPreview(
//) = ListOfUsersScreen(userList = listOf("Dominik"),
//    showTopAppBarMoreAction = false,
//    adminOption = true,
//    showUserActionsDialog = false,
//    onUsersClicked = { },
//    onTopAppBarMoreActionClicked = { },
//    onDismissTopAppBarMoreAction = { },
//    onDismissUserActionsDialog = { },
//    navigateToAddWorkTimeScreen = { }) {
//
//}
