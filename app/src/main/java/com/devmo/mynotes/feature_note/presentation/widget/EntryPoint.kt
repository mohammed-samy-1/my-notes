package com.devmo.mynotes.feature_note.presentation.widget

import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@dagger.hilt.EntryPoint
@InstallIn(SingletonComponent::class)
interface EntryPoint {
    fun vm(): WidgetViewModel
}