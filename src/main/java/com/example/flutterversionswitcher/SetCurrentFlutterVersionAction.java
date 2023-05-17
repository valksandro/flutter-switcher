package com.example.flutterversionswitcher;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SetCurrentFlutterVersionAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        FlutterVersionSwitcherConfig config = FlutterVersionSwitcherConfig.getInstance(project);
        if (config == null) {
            Messages.showErrorDialog("Configuração do plugin não encontrada.", "Erro");
            return;
        }


        String targetVersion = Messages.showInputDialog(project, "Informe a versão do Flutter:", "Definir versão do Flutter", Messages.getQuestionIcon());

        if(targetVersion == null || targetVersion.trim().isBlank()) {
            return;
        }

        String flutterVersionsPath = config.getFlutterVersionsPath();
        if (flutterVersionsPath.isEmpty()) {
            Messages.showErrorDialog("Por favor, configure o caminho das versões do Flutter.", "Erro");
            return;
        }

        // Obter a versão atual do Flutter
        String currentVersion = Util.getCurrentFlutterVersion(e);


        // Renomear a pasta Flutter ou flutter
        File flutterFolder = new File(flutterVersionsPath);
        if (flutterFolder.exists()) {
            File[] flutterFolders = flutterFolder.listFiles((dir, name) -> name.toLowerCase().startsWith("flutter"));
            if (flutterFolders != null && flutterFolders.length > 0) {
                try {
                    renameFolders(flutterFolders, currentVersion, targetVersion);
                    Messages.showInfoMessage("Versão do Flutter alterada com sucesso.", "Sucesso");
                } catch (IOException ex) {
                    // Mostre uma mensagem de erro informando que algo deu errado
                    Messages.showErrorDialog("Erro ao renomear pastas do Flutter.", "Erro");
                    ex.printStackTrace();
                }
            }
        } else {
            Messages.showErrorDialog("A pasta das versões do Flutter não foi encontrada.", "Erro");
        }
    }


    private void renameFolders(File[] flutterFolders, String currentVersion, String targetVersion) throws IOException {
        Path currentFlutterFolder = null;
        Path targetFlutterFolder = null;

        for (File flutterFolder : flutterFolders) {
            if (flutterFolder.getName().equalsIgnoreCase("flutter")) {
                currentFlutterFolder = flutterFolder.toPath();
                Files.move(currentFlutterFolder, currentFlutterFolder.getParent().resolve("flutter" + currentVersion));
            }
            if (flutterFolder.getName().equalsIgnoreCase("flutter" + targetVersion)) {
                targetFlutterFolder = flutterFolder.toPath();
            }
        }


        if (targetFlutterFolder != null) {
            Files.move(targetFlutterFolder, targetFlutterFolder.getParent().resolve("flutter"));
        }
    }
}
