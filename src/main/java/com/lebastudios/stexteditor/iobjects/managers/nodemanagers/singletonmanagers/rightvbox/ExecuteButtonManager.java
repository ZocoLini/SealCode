package com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.rightvbox;

import com.lebastudios.stexteditor.exceptions.NotImplementedException;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.ButtonManager;
import com.lebastudios.stexteditor.iobjects.managers.nodemanagers.singletonmanagers.MainManager;

public class ExecuteButtonManager extends ButtonManager
{
    private static ExecuteButtonManager instance;

    public static ExecuteButtonManager getInstance()
    {
        if (instance == null) instance = new ExecuteButtonManager();

        return instance;
    }

    private ExecuteButtonManager()
    {
        super(MainManager.getInstance().botonEjecutar);

    }

    @Override
    protected String iconPath()
    {
        return "play.jpg";
    }

    @Override
    public void onAction()
    {
        throw new NotImplementedException();
    }

    @Override
    public void loadChilds()
    {
        
    }
}
