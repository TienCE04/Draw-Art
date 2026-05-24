package com.leansoft.draw.drawart.presentation.ui.project

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.leansoft.draw.drawart.databinding.ItemProjectBinding
import com.leansoft.draw.drawart.domain.model.ProjectModel

class ProjectAdapter : ListAdapter<ProjectModel, ProjectAdapter.ViewHolder>(DiffUtilProject()) {

    inner class ViewHolder(private val binding: ItemProjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(
        p0: ViewHolder,
        p1: Int
    ) {
        TODO("Not yet implemented")
    }

    class DiffUtilProject : DiffUtil.ItemCallback<ProjectModel>() {
        override fun areItemsTheSame(
            p0: ProjectModel,
            p1: ProjectModel
        ): Boolean {
            return p0.idProject == p1.idProject
        }

        override fun areContentsTheSame(
            p0: ProjectModel,
            p1: ProjectModel
        ): Boolean {
            return p0 == p1
        }
    }
}