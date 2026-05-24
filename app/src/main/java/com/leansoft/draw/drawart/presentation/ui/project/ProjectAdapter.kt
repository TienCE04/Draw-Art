package com.leansoft.draw.drawart.presentation.ui.project

import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.domain.model.ProjectModel

class ProjectAdapter: ListAdapter<ProjectModel, ProjectAdapter.ViewHolder>(DiffUtilProject()) {

    inner class ViewHolder(private val binding: ItemProjectBinding): RecyclerView.ViewHolder(binding.root){

    }

}