package com.example.flutterversionswitcher;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Util {
    public static String getCurrentFlutterVersion(AnActionEvent e) {

        Project project = e.getProject();
        if (project == null) {
            return null;
        }
        FlutterVersionSwitcherConfig config = FlutterVersionSwitcherConfig.getInstance(project);
        String path = config.getFlutterVersionsPath();
        if (path == null) {
            Messages.showInfoMessage("O caminho informado para o Flutter não é válido", "Error");
            return null;
        }
        path += "/flutter/bin/";
        ProcessBuilder processBuilder = new ProcessBuilder(path + "flutter", "--version");
        processBuilder.directory(new File(path));
        Process process = null;
        try {
            process = processBuilder.start();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        String version = null;

        while (true) {
            try {
                if (!((line = reader.readLine()) != null)) break;
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            if (line.startsWith("Flutter")) {
                version = line.split(" ")[1];
                break;
            }
        }

        if (version != null) {
            return version;
        }
        return null;
    }
}
