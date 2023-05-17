package com.example.flutterversionswitcher;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(name = "FlutterVersionSwitcherConfig", storages = @Storage("flutterVersionSwitcher.xml"))
public class FlutterVersionSwitcherConfig implements PersistentStateComponent<FlutterVersionSwitcherConfig.State> {
    private State state = new State();

    public static FlutterVersionSwitcherConfig getInstance(Project project) {
        return ServiceManager.getService(project, FlutterVersionSwitcherConfig.class);
    }

    public String getFlutterVersionsPath() {
        return state.flutterVersionsPath;
    }

    public void setFlutterVersionsPath(String path) {
        state.flutterVersionsPath = path;
    }

    public static class State {
        public String flutterVersionsPath;
    }

    @Nullable
    @Override
    public State getState() {
        return state;
    }

    @Override
    public void loadState(@NotNull State state) {
        this.state = state;
    }
}
