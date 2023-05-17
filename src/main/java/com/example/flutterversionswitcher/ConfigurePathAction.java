package com.example.flutterversionswitcher;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.fileChooser.FileChooser;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class ConfigurePathAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            Messages.showErrorDialog("Não foi possível obter o projeto atual.", "Erro");
            return;
        }

        FileChooserDescriptor fileChooserDescriptor = new FileChooserDescriptor(false, true, false, false, false, false);
        fileChooserDescriptor.setTitle("Selecione o diretório do Flutter");
        fileChooserDescriptor.setDescription("Escolha o diretório que contém as versões do Flutter");

        VirtualFile selectedDirectory = FileChooser.chooseFile(fileChooserDescriptor, project, null);

        if (selectedDirectory != null) {
            FlutterVersionSwitcherConfig config = FlutterVersionSwitcherConfig.getInstance(project);
            if (config != null) {
                String path = selectedDirectory.getPath();
                FlutterVersionSwitcherConfig.getInstance(project).setFlutterVersionsPath(path);
                Messages.showInfoMessage("Caminho do Flutter atualizado: " + path, "Flutter Version Switcher");
            } else {
                Messages.showInfoMessage("error: ", "Flutter Version Switcher");
            }



        }
    }
}
