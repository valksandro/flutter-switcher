package com.example.flutterversionswitcher;


import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "FlutterVersionSwitcherState", storages = {@Storage("FlutterVersionSwitcher.xml")})
public class FlutterVersionSwitcherState implements PersistentStateComponent<FlutterVersionSwitcherState> {
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Nullable
    @Override
    public FlutterVersionSwitcherState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull FlutterVersionSwitcherState state) {
        this.filePath = state.filePath;
    }

    public static FlutterVersionSwitcherState getInstance(Project project) {
        return project.getService(FlutterVersionSwitcherState.class);
    }

}

