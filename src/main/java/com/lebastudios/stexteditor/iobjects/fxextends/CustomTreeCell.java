package com.lebastudios.stexteditor.iobjects.fxextends;

import com.lebastudios.stexteditor.iobjects.controllers.CustomTreeCellController;
import javafx.scene.control.TreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class CustomTreeCell extends TreeCell<CustomTreeCellContent>
{
    private ImageView imageView;
    private final CustomTreeCellController controller;
    
    private Image image;
    private File representingFile;

    public CustomTreeCell() {
        imageView = new ImageView();
        imageView.setFitHeight(16);
        imageView.setFitWidth(16);
        
        this.setOnMouseClicked(event -> 
        {
            if (getItem() != null) 
            {
                getItem().onMouseClicked(event);
            }
        });
        
        this.controller = new CustomTreeCellController(this);
    }
    
    public File getRepresentingFile()
    {
        return representingFile;
    }
    
    public Image getImage()
    {
        return image;
    }
    
    @Override
    protected void updateItem(CustomTreeCellContent item, boolean empty) {
        super.updateItem(item, empty);

        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            representingFile = item.getRepresentingFile();
            image = item.getImage();
            
            setText(item.getName());
            imageView.setImage(item.getImage());
            setGraphic(imageView);
        }
    }
}
