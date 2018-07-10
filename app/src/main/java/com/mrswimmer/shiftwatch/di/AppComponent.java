package com.mrswimmer.shiftwatch.di;

import android.content.Context;

import com.mrswimmer.shift.domain.interactor.FireService;
import com.mrswimmer.shift.domain.service.SendResultService;
import com.mrswimmer.shiftwatch.di.module.FireModule;
import com.mrswimmer.shiftwatch.di.module.SharedPreferencesModule;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FireModule.class, SharedPreferencesModule.class})
public interface AppComponent {
    Context context();

    void inject(@NotNull FireService fireService);

    void inject(@NotNull SendResultService sendResultService);
}
