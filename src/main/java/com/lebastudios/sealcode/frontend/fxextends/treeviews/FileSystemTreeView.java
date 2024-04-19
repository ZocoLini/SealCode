package com.lebastudios.sealcode.frontend.fxextends.treeviews;

import com.lebastudios.sealcode.applogic.FileOperation;
import com.lebastudios.sealcode.applogic.config.GlobalConfig;
import com.lebastudios.sealcode.applogic.config.Session;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.io.File;

public final class FileSystemTreeView extends TreeView<FileSystemTreeCellContent>
{
    public FileSystemTreeView()
    {
        super();

        this.setCellFactory(param -> new FileSystemTreeCell());

        openLastProjectDirectory();
    }

    public static TreeItem<FileSystemTreeCellContent> createTreeView(File file)
    {
        if (file == null)
        {
            throw new IllegalArgumentException("File is null");
        }

        if (!file.exists())
        {
            throw new IllegalArgumentException("File does not exist");
        }

        TreeItem<FileSystemTreeCellContent> root = new FileSystemTreeItem(new FileSystemTreeCellContent(file));

        root.setExpanded(false);

        if (file.isDirectory())
        {
            for (File child : file.listFiles())
            {
                if (GlobalConfig.getStaticInstance().userPrefs.ignoreGitDir && child.getName().equals(".git")) continue;

                root.getChildren().add(createTreeView(child));
            }
        }

        return root;
    }

    private void openProyectDirectory(File file)
    {
        file = file.getAbsoluteFile();

        if (file.getPath().isEmpty())
        {
            return;
        }

        Session.getStaticInstance().proyectDirectory = file.getPath();

        this.setRoot(createTreeView(file));
    }

    private void openLastProjectDirectory()
    {
        File file = new File(Session.getStaticInstance().proyectDirectory);

        if (!file.exists() || !file.isDirectory())
        {
            return;
        }

        openProyectDirectory(file);
    }

    public void openNewProjectDirectory()
    {
        File file = FileOperation.directoryChooser().showDialog(null);

        if (file == null)
        {
            return;
        }

        openProyectDirectory(file);
    }
}
