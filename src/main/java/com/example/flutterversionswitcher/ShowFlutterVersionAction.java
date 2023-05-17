package com.example.flutterversionswitcher;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.*;

public class ShowFlutterVersionAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        try {
            String version = Util.getCurrentFlutterVersion(e);;

            if (version != null) {
                Messages.showInfoMessage("Versão atual do Flutter: " + version, "Versão do Flutter");
            } else {
                Messages.showErrorDialog("Não foi possível encontrar a versão do Flutter", "Erro");
            }
        } catch (Exception er) {
            er.printStackTrace();
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            er.printStackTrace(pw);
            String stackTrace = sw.toString();
            Messages.showErrorDialog("Erro ao obter a versão do Flutter: " + stackTrace + " " + er.getMessage(), "Erro");
        }
    }
}
