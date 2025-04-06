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

import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material.icons.outlined.Group
import androidx.compose.material.icons.outlined.PermIdentity
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.artisan.R
import com.example.artisan.domain.controller.ProjectViewModel
import com.example.artisan.ui.theme.inter

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

    var selectedIndex by remember { mutableStateOf(0) } // Track the selected tab


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

            FloatingActionButton(
                onClick = { },
                containerColor = Color(34, 36, 49)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Add Project",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
        },


        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(34, 36, 49).copy(0.9f))
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val items = listOf("Select Tool", "Inspect Tool", "Delete", "Add Note")

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

        }
    )
}