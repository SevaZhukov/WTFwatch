package com.mrswimmer.shiftwatch.di.module;

import com.mrswimmer.shift.domain.interactor.FireService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class FireModule {
    @Provides
    @Singleton
    FireService provideService() {
        return new FireService();
    }
}
