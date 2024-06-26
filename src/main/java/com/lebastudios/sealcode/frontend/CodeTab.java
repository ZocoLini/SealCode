package com.lebastudios.sealcode.frontend;

import com.lebastudios.sealcode.config.Session;
import com.lebastudios.sealcode.events.AppEvents;
import javafx.scene.control.Tab;

public final class CodeTab extends Tab
{
    public CodeTab(String name, String content, FileSystemTreeItem treeItem)
    {
        this.setText(name);

        SealCodeArea sealCodeArea = new SealCodeArea(content, treeItem);

        this.setContent(sealCodeArea);

        addEventHandlers();
    }

    private void addEventHandlers()
    {
        this.setOnCloseRequest(event ->
                {
                    Session.getStaticInstance().filesOpen.remove(
                            this.getTabPane().getTabs().indexOf(
                                    (Tab) event.getTarget()
                            )
                    );

                    AppEvents.onSealCodeAreaDeleted.invoke(
                            (SealCodeArea) this.getContent()
                    );
                }

        );

    }

    public void saveFile()
    {
        ((SealCodeArea) this.getContent()).saveFile();
    }
}
