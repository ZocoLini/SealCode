package com.lebastudios.sealcode.core.controllers.settingsPanels;

import com.lebastudios.sealcode.core.frontend.dialogs.Dialogs;
import com.lebastudios.sealcode.core.frontend.fxextends.IconTreeItem;
import com.lebastudios.sealcode.core.logic.config.FilePaths;
import com.lebastudios.sealcode.core.logic.defaultcompletations.LangCompletations;
import com.lebastudios.sealcode.core.logic.defaultcompletations.LiveTemplateCompletation;
import com.lebastudios.sealcode.global.FileOperation;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.File;

public class LiveTemplateController
{
    @FXML private TreeView<String> liveTemplateTreeView;
    @FXML private ListView<String> liveTemplatesListView;
    
    @FXML private TextArea liveTemplateCompTextArea;
    @FXML private TextArea liveTemplateDescTextArea;
    @FXML private TextField liveTemplateValueTextField;

    private LangCompletations actualCompletations;
    
    public void initialize()
    {
        File completationsFolder = new File(FilePaths.getProgLangCompletationsDirectory());
        
        for (var file : completationsFolder.listFiles())
        {
            IconTreeItem<String> item = new IconTreeItem<>(
                    FileOperation.getFileName(file),
                    "ext_" + FileOperation.getFileName(file) + ".png"
            );

            liveTemplateTreeView.getRoot().getChildren().add(item);
        }

        liveTemplateTreeView.setOnMouseClicked(event ->
        {
            clearTextAreas();
            loadLiveTemplates();
        });

        liveTemplatesListView.setOnMouseClicked(event ->
        {
            clearTextAreas();
            loadLiveTemplate();
        });
        
        liveTemplateTreeView.getRoot().setExpanded(true);
    }

    private void loadLiveTemplate()
    {
        String selectedItem = liveTemplatesListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) return;

        LiveTemplateCompletation liveTemplateCompletation = actualCompletations.getLiveTemplatesCompletations().stream()
                .filter(variable -> variable.getValue().equals(selectedItem))
                .findFirst()
                .orElse(null);
        
        if (liveTemplateCompletation == null) return;

        liveTemplateValueTextField.setText(liveTemplateCompletation.getValue());
        liveTemplateDescTextArea.setText(liveTemplateCompletation.getDescription());
        liveTemplateCompTextArea.setText(liveTemplateCompletation.getCompletation());
    }
    
    @FXML
    private void saveLiveTemplate()
    {
        if (actualCompletations == null) return;
        if (liveTemplateValueTextField.getText().isEmpty() || liveTemplateValueTextField.getText().isBlank()) return;
        
        actualCompletations.getLiveTemplatesCompletations().removeIf(
                variable -> variable.getValue().equals(liveTemplateValueTextField.getText())
        );
        actualCompletations.getLiveTemplatesCompletations().add(new LiveTemplateCompletation(
                liveTemplateValueTextField.getText(),
                liveTemplateDescTextArea.getText(),
                liveTemplateCompTextArea.getText()
        ));
        
        actualCompletations.saveToFile();

        clearTextAreas();

        loadLiveTemplates();
    }

    private void clearTextAreas()
    {
        liveTemplateValueTextField.setText("");
        liveTemplateDescTextArea.setText("");
        liveTemplateCompTextArea.setText("");
    }

    private void loadLiveTemplates()
    {
        TreeItem<String> selectedItem = liveTemplateTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem.getParent() == null) return;

        actualCompletations = LangCompletations.readCompletationFromFile(selectedItem.getValue());

        liveTemplatesListView.getItems().clear();

        for (var liveTemplateCompletation : actualCompletations.getLiveTemplatesCompletations())
        {
            liveTemplatesListView.getItems().add(liveTemplateCompletation.getValue());
        }
    }

    @FXML
    private void addLiveTemplate()
    {
        if (actualCompletations == null) return;

        liveTemplateValueTextField.setText("<new live template>");
        liveTemplateDescTextArea.setText("<description>");
        liveTemplateCompTextArea.setText("<completation>");

        actualCompletations.saveToFile();

        loadLiveTemplates();
    }

    @FXML
    private void removeLiveTemplate()
    {
        if (actualCompletations == null) return;

        String selectedItem = liveTemplatesListView.getSelectionModel().getSelectedItem();

        if (selectedItem == null) return;

        actualCompletations.getLiveTemplatesCompletations().removeIf(variable -> variable.getValue().equals(selectedItem));

        clearTextAreas();

        actualCompletations.saveToFile();

        loadLiveTemplates();
    }

    @FXML
    private void addLanguage()
    {
        String languageName = Dialogs.insertTextDialog("Insert language name", "Insert the name of the language");

        if (languageName == null || languageName.isEmpty() || languageName.isBlank()) return;

        LangCompletations.createNewLangCompletations(languageName);

        IconTreeItem<String> item = new IconTreeItem<>(languageName, languageName + ".png");

        liveTemplateTreeView.getRoot().getChildren().add(item);
    }

    @FXML
    private void removeLanguage()
    {
        TreeItem<String> selectedItem = liveTemplateTreeView.getSelectionModel().getSelectedItem();

        if (selectedItem.getParent() == null) return;

        File file = new File(FilePaths.getProgLangCompletationsDirectory() + selectedItem.getValue() + ".json");

        if (file.exists())
        {
            file.delete();
        }

        liveTemplateTreeView.getRoot().getChildren().remove(selectedItem);
    }
}
