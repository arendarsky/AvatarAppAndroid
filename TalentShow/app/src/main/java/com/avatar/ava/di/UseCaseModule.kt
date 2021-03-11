package com.avatar.ava.di

import com.avatar.ava.domain.usecase.finalists.FinalUseCase
import com.avatar.ava.domain.usecase.finalists.FinalUseCaseImpl
import toothpick.config.Module

class UseCaseModule : Module() {
    init {
        bind(FinalUseCase::class.java).to(FinalUseCaseImpl::class.java)
    }
}