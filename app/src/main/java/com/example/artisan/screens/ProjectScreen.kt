package com.example.artisan.screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.CloseFullscreen
import androidx.compose.material.icons.outlined.Computer
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.LinearScale
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Replay
import androidx.compose.material.icons.outlined.Router
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artisan.R
import com.example.artisan.domain.controller.ProjectViewModel
import com.example.artisan.ui.theme.inter
import kotlinx.serialization.json.Json.Default.configuration

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ProjectScreen(
    email: String,
    projectId: String,
    viewModel: ProjectViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val project by viewModel.project.collectAsState(initial = null)

    LaunchedEffect(projectId) {
        viewModel.getProjectById(projectId)
    }

    var selectedIndex by remember { mutableStateOf(0) }
    var showCard by remember { mutableStateOf(false) } // Controls visibility of the card
    var showFAB by remember { mutableStateOf(true) } // Controls visibility of the FAB

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val dynamicGap = screenWidth * 0.03f


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                AsyncImage(
                                    model = ImageRequest.Builder(LocalContext.current)
                                        .data(R.drawable.artisan_logo_splash)
                                        .build(),
                                    contentDescription = "icon",
                                    modifier = Modifier
                                        .height(36.dp)
                                        .width(36.dp)
                                )
                                Spacer(modifier = Modifier.width(7.dp))
                                Text(
                                    text = project?.title ?: "Loading...",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontFamily = inter
                                )
                            }

                            Row(
                                modifier = Modifier.padding(end = 7.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy((-2).dp)
                            ) {
                                IconButton(
                                    onClick = { /* handle settings */ },
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Group,
                                        contentDescription = "Settings",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp) // Icon size
                                    )
                                }

                                IconButton(
                                    onClick = { /* handle settings */ },
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Save,
                                        contentDescription = "Settings",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp) // Icon size
                                    )
                                }

                                IconButton(
                                    onClick = { /* handle settings */ },
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Share,
                                        contentDescription = "Settings",
                                        tint = Color.White,
                                        modifier = Modifier.size(32.dp) // Icon size
                                    )
                                }
                            }
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(34, 36, 49)
                )
            )
        },



        floatingActionButton = {
            AnimatedVisibility(
                visible = showFAB,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                FloatingActionButton(
                    onClick = {
                        showFAB = false
                        showCard = true
                    },
                    containerColor = Color(34, 36, 49)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Project",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        },

        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(34, 36, 49).copy(0.9f))
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val items = listOf("Select", "Inspect", "Delete", "Note")

                    items.forEachIndexed { index, item ->
                        Text(
                            text = item,
                            fontSize = 14.sp,
                            fontFamily = inter,
                            color = if (selectedIndex == index) Color(0xFFBB86FC) else Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable {
                                    selectedIndex = index // Update selected tab index
                                }
                                .padding(horizontal = 16.dp)
                        )
                    }
                }

                // Display the project details
                if (project == null) {
                    CircularProgressIndicator(modifier = Modifier.fillMaxSize(), color = Color.White)
                } else {
                    // Project details or other content here
                }
            }

            AnimatedVisibility(
                visible = showCard,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Card(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .size(200.dp, 370.dp)
                            .padding(bottom = 16.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(34, 36, 49)),
                        onClick = {
                            showFAB = true
                            showCard = false
                        },
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                        ) {
                            // First row
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column() {
                                    Text(
                                        text = "Network Devices",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                    )
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        modifier = Modifier
                                            .wrapContentSize() // Ensure the LazyRow only takes as much space as it needs
                                    ) {
                                        items(10) { index ->
                                            // Wrap Icon and Text inside a Column
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Outlined.Router, // Replace with your icons
                                                    contentDescription = "Icon $index",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(36.dp)
                                                )
                                                Text(
                                                    text = "Item $index", // Text below the icon
                                                    color = Color.White,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Normal
                                                )
                                            }
                                        }
                                    }

                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column() {
                                    Text(
                                        text = "End Devices",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                    )
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        modifier = Modifier
                                            .wrapContentSize() // Ensure the LazyRow only takes as much space as it needs
                                    ) {
                                        items(10) { index ->
                                            // Wrap Icon and Text inside a Column
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Outlined.Computer, // Replace with your icons
                                                    contentDescription = "Icon $index",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(36.dp)
                                                )
                                                Text(
                                                    text = "Item $index", // Text below the icon
                                                    color = Color.White,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Normal
                                                )
                                            }
                                        }
                                    }

                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column() {
                                    Text(
                                        text = "Connections",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                    )
                                    LazyRow(
                                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                                        modifier = Modifier
                                            .wrapContentSize() // Ensure the LazyRow only takes as much space as it needs
                                    ) {
                                        items(10) { index ->
                                            // Wrap Icon and Text inside a Column
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally
                                            ) {
                                                Icon(
                                                    imageVector = Icons.Outlined.LinearScale, // Replace with your icons
                                                    contentDescription = "Icon $index",
                                                    tint = Color.White,
                                                    modifier = Modifier.size(36.dp)
                                                )
                                                Text(
                                                    text = "Item $index", // Text below the icon
                                                    color = Color.White,
                                                    fontSize = 12.sp,
                                                    fontWeight = FontWeight.Normal
                                                )
                                            }
                                        }
                                    }

                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Close,
                                    contentDescription = "Exit icon",
                                    tint = Color.White,
                                    modifier = Modifier.size(36.dp)
                                )
                            }
                        }
                    }
                }
            }

            var isPressed by remember { mutableStateOf(false) }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Card(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .size(100.dp, 80.dp)
                        .padding(bottom = 36.dp, start = 16.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(34, 36, 49) // Change color when pressed
                    ),
                    onClick = {
                        isPressed = !isPressed // Toggle color on click
                    }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Replay,
                            contentDescription = "Icon",
                            tint = if (isPressed) Color(0xFFBB86FC) else Color(255, 255, 255),
                            modifier = Modifier.size(36.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // Use width for horizontal space
                        Icon(
                            imageVector = Icons.Outlined.PlayArrow,
                            contentDescription = "Icon",
                            tint = if (isPressed) Color(0xFFBB86FC) else Color(255, 255, 255),
                            modifier = Modifier.size(36.dp)
                        )
                    }
                }
            }
        }
    )
}