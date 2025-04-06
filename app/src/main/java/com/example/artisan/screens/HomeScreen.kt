package com.example.artisan.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artisan.domain.controller.ProjectViewModel
import com.example.artisan.ui.theme.inter
import java.text.SimpleDateFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    email: String,
    password: String,
    viewModel: ProjectViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    var selectedItem by remember { mutableStateOf(0) }

    val items = listOf(
        Pair(Icons.Outlined.AccountTree, "Projects"),
        Pair(Icons.Outlined.Cloud, "Shared"),
        Pair(Icons.AutoMirrored.Outlined.MenuBook, "Guide"),
        Pair(Icons.Outlined.Notifications, "Notifs")
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        IconButton(
                            onClick = { /* handle settings */ },
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Tune,
                                contentDescription = "Settings",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp) // Icon size
                            )
                        }

                        Text(
                            text = "ARTISAN",
                            fontSize = 24.sp,
                            fontFamily = inter,
                            color = Color.White,
                            fontWeight = FontWeight.Thin,
                            letterSpacing = 14.sp
                        )

                        IconButton(
                            onClick = { /* handle settings */ },
                            Modifier.padding(end = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.PermIdentity,
                                contentDescription = "Settings",
                                tint = Color.White,
                                modifier = Modifier.size(32.dp) // Icon size
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(34, 36, 49)
                )
            )
        },

        bottomBar = {
            NavigationBar(
                containerColor = Color(34, 36, 49),
                modifier = Modifier.padding(start = 3.dp)
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                item.first,
                                contentDescription = item.second,
                                tint = if (selectedItem == index) Color(0xFF9B59B6) else Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        },
                        label = {
                            Text(
                                text = item.second,
                                fontFamily = inter,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 14.sp // slightly larger text
                            )
                        },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color(0xFF9B59B6),
                            unselectedIconColor = Color.White,
                            selectedTextColor = Color(0xFF9B59B6),
                            unselectedTextColor = Color.White,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        },



        containerColor = Color(34, 36, 49)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AnimatedContent(
                targetState = selectedItem
            ) { targetState ->
                when (targetState) {
                    0 -> ProjectsTab(navController, email, viewModel)
                    1 -> SharedTab(email, viewModel)
                    2 -> GuideTab()
                    3 -> NotifsTab()
                }
            }
        }
    }
}

@Composable
fun ProjectsTab(
    navController: NavHostController,
    email: String,
    viewModel: ProjectViewModel
) {
    val projects by viewModel.projectlist.collectAsState()

    LaunchedEffect(email) {
        viewModel.getProjectByEmail(email)
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (projects.isEmpty()) {
            item {
                Text(
                    text = "No projects found.",
                    color = Color.Gray
                )
            }
        } else {
            items(projects) { project ->

                val formattedDateModified = project.datemodified?.toDate()?.let {
                    SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(it)
                } ?: "N/A"

                val formattedDateCreated = project.datecreated?.toDate()?.let {
                    SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(it)
                } ?: "N/A"

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate(com.example.artisan.ProjectScreen(email = email, projectId = project.projectId)) }
                        .border(2.dp, Color.White, shape = RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = project.title,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            Column(
                                verticalArrangement = Arrangement.spacedBy((-5).dp)
                            ) {
                                Text(
                                    text = "Last Modified: $formattedDateModified",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Date Created: $formattedDateCreated",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 14.sp
                                )
                            }
                        }

                        Icon(
                            imageVector = if (project.solo) Icons.Outlined.Person else Icons.Outlined.Group,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .size(24.dp)
                        )
                    }
                }

            }
        }
    }
}




@Composable
fun SharedTab(
    email: String,
    viewModel: ProjectViewModel
) {
    val projects by viewModel.projectlist.collectAsState()

    val publicProjects = projects.filter { it.public == true }

    LaunchedEffect(Unit) {
        viewModel.getProjectList()
    }


    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        if (publicProjects.isEmpty()) {
            item {
                Text(
                    text = "No projects found.",
                    color = Color.Gray
                )
            }
        } else {
            items(publicProjects) { project ->

                val formattedDateModified = project.datemodified?.toDate()?.let {
                    SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(it)
                } ?: "N/A"

                val formattedDateCreated = project.datecreated?.toDate()?.let {
                    SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(it)
                } ?: "N/A"

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(2.dp, Color.White, shape = RoundedCornerShape(12.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = project.title,
                                color = Color.White,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))

                            Column(
                                verticalArrangement = Arrangement.spacedBy((-5).dp)
                            ) {
                                Text(
                                    text = "Last Modified: $formattedDateModified",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "Date Created: $formattedDateCreated",
                                    color = Color.White.copy(alpha = 0.7f),
                                    fontSize = 14.sp
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 8.dp)
                                .align(Alignment.BottomEnd),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Download,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = project.downloads.toString(),
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }

                            Spacer(modifier = Modifier.width(9.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.FavoriteBorder,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(22.dp)
                                )
                                Spacer(modifier = Modifier.width(3.dp))
                                Text(
                                    text = project.likes.toString(),
                                    color = Color.White,
                                    fontSize = 16.sp
                                )
                            }
                        }

                    }
                }

            }
        }
    }
}


@Composable
fun GuideTab() {
    val expandedCardIndex = remember { mutableStateOf<Int?>(null) }

    data class SubItem(
        val title: String,
        val description: String
    )

    data class GuideItem(
        val title: String,
        val subItems: List<SubItem>
    )


    val guideItems = listOf(
        GuideItem("Frequently Asked Questions", listOf(
            SubItem("Overview", "Basic introduction to the app."),
            SubItem("Installation", "How to install the app properly.")
        )),
        GuideItem("Network Devices", listOf(
            SubItem("Create Account", "Step by step guide to register."),
            SubItem("Profile Settings", "Customize your user profile."),
            SubItem("Security", "Manage your security options.")
        )),
        GuideItem("End Devices", listOf(
            SubItem("Start a Project", "Initiating a new project easily."),
            SubItem("Templates", "Use pre-made templates for faster setup.")
        )),
        GuideItem("Connections", listOf(
            SubItem("Share with Email", "Send project invites via email."),
            SubItem("Generate Link", "Create a shareable project link."),
            SubItem("Permissions", "Control who can access your project.")
        )),
        GuideItem("Types of View", listOf(
            SubItem("Add Members", "Invite new people to your team."),
            SubItem("Roles", "Assign different roles for members."),
            SubItem("Remove Members", "Easily remove members from the team."),
            SubItem("Team Chat", "Communicate with your team in-app.")
        )),
        GuideItem("Tool Bar", listOf(
            SubItem("Email Alerts", "Get notified through emails."),
            SubItem("Push Notifications", "Receive instant app notifications.")
        ))
    )


    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        guideItems.forEachIndexed { index, item ->
            Card(
                onClick = {
                    expandedCardIndex.value = if (expandedCardIndex.value == index) null else index
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(2.dp, Color.White, shape = RoundedCornerShape(12.dp)),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = item.title,
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )

                        Icon(
                            imageVector = if (expandedCardIndex.value == index) Icons.Outlined.KeyboardArrowUp else Icons.Outlined.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }

                    AnimatedVisibility(expandedCardIndex.value == index) {
                        Column {
                            Spacer(modifier = Modifier.height(10.dp))

                            item.subItems.forEach { subItem ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                        .border(
                                            2.dp,
                                            Color.White,
                                            shape = RoundedCornerShape(12.dp)
                                        ),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.Transparent
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .padding(12.dp)
                                            .fillMaxWidth(),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Column(
                                            verticalArrangement = Arrangement.spacedBy((-5).dp)
                                        ) {
                                            Text(
                                                text = subItem.title,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )


                                            Text(
                                                text = subItem.description,
                                                color = Color.Gray,
                                                fontSize = 14.sp,
                                                maxLines = 1,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                        }

                                        Text(
                                            text = "Learn More",
                                            color = Color(0xFF9B59B6),
                                            fontSize = 14.sp,
                                            modifier = Modifier
                                                .clickable {
                                                    // Handle Learn More Click
                                                }
                                                .padding(start = 8.dp),
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    }
                                }
                            }
                        }


                    }
                }
            }
        }
    }
}


@Composable
fun NotifsTab() {
    Text("Notifications", color = Color.White, fontSize = 26.sp, fontWeight = FontWeight.Bold)
}

