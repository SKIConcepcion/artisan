package com.example.artisan.domain.controller

import androidx.lifecycle.ViewModel
import com.example.artisan.domain.model.Project
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.StateFlow

class ProjectViewModel : ViewModel() {



    private var _projectList = MutableStateFlow<List<Project>>(emptyList())
    var projectlist = _projectList.asStateFlow()

    fun getProjectList() {
        val db = FirebaseFirestore.getInstance()

        db.collection("projects")
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("ProjectViewModel", "Error fetching projects", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    // Print the retrieved data to the log
                    val projects = value.toObjects(Project::class.java)
                    Log.d("ProjectViewModel", "Retrieved projects: $projects")

                    // Update the project list
                    _projectList.value = projects
                }
            }
    }



    fun getProjectByEmail(email: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("projects")
            .whereEqualTo("email", email)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.e("ProjectViewModel", "Error fetching projects by email", error)
                    return@addSnapshotListener
                }

                if (value != null) {
                    val projects = value.toObjects(Project::class.java)
                    Log.d("ProjectViewModel", "Projects for $email: $projects")

                    _projectList.value = projects
                }
            }
    }


    private val _project = MutableStateFlow<Project?>(null)
    val project: StateFlow<Project?> = _project

    fun getProjectById(projectId: String) {
        val db = FirebaseFirestore.getInstance()

        db.collection("projects")
            .whereEqualTo("projectId", projectId)
            .get()
            .addOnSuccessListener { snapshot ->
                if (snapshot.isEmpty) {
                    Log.d("ProjectViewModel", "No project found with ID $projectId")
                    _project.value = null
                } else {
                    val fetchedProject = snapshot.documents.firstOrNull()?.toObject(Project::class.java)
                    _project.value = fetchedProject
                }
            }
            .addOnFailureListener { error ->
                Log.e("ProjectViewModel", "Error fetching project", error)
                _project.value = null
            }
    }
}
