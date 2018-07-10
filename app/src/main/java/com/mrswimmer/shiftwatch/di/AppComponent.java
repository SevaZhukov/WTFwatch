package com.mrswimmer.shiftwatch.di;

import android.content.Context;

import com.mrswimmer.shift.domain.interactor.FireService;
import com.mrswimmer.shift.domain.service.SendResultService;
import com.mrswimmer.shiftwatch.di.module.FireModule;
import com.mrswimmer.shiftwatch.di.module.SharedPreferencesModule;
import com.mrswimmer.shiftwatch.presentation.fragment.menu.MenuFragment;
import com.mrswimmer.shiftwatch.presentation.fragment.sign_in.SignInFragment;

import org.jetbrains.annotations.NotNull;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {FireModule.class, SharedPreferencesModule.class})
public interface AppComponent {
    Context context();

    void inject(FireService fireService);

    void inject(SendResultService sendResultService);

    void inject(SignInFragment signInFragment);

    void inject(@NotNull MenuFragment menuFragment);
}
