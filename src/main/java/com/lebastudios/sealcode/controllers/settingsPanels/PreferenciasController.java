package com.lebastudios.sealcode.controllers.settingsPanels;

import com.lebastudios.sealcode.controllers.SettingsPaneController;
import com.lebastudios.sealcode.config.GlobalConfig;
import com.lebastudios.sealcode.events.AppEvents;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class PreferenciasController extends SettingsPaneController
{
    @FXML private CheckBox ignoreGitDirCB;

    @Override
    public void initialize()
    {
        ignoreGitDirCB.setSelected(GlobalConfig.getStaticInstance().userPrefs.ignoreGitDir);
    }

    @Override
    public void apply()
    {
        GlobalConfig.getStaticInstance().userPrefs.ignoreGitDir = ignoreGitDirCB.isSelected();

        AppEvents.onGlobalConfigUpdate.invoke();
    }
}
