package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.leftvbox;

import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.SingletonManager;
import javafx.scene.layout.VBox;

public class LeftVBoxManager extends SingletonManager<VBox>
{
    private static LeftVBoxManager instance;
    
    public static LeftVBoxManager getInstance()
    {
        if (instance == null) 
        {
            instance = new LeftVBoxManager();
        }
        
        return instance;
    }
    
    private LeftVBoxManager()
    {
        super(MainManager.getInstance().leftVBox);

    }

    @Override
    public void loadChilds()
    {
        
    }
}