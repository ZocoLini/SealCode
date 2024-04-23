package com.lebastudios.sealcode.events;

import com.lebastudios.sealcode.events.Events.*;

public final class AppEvents
{
    /*  App Life Cicle  */
    public static final OnAppExit onAppExit = new OnAppExit();
    public static final OnAppStart onAppStart = new OnAppStart();
    
    /*  IDE Objects  */
        /*  Seal Code Area  */
            /*  Modificaciones de Texto  */
    public static final OnTextModified onTextModified = new OnTextModified();
    public static final OnTextModified onTextDeleted = new OnTextModified();
    public static final OnTextModified onTextInserted = new OnTextModified();
    public static final OnTextModified onTextReplaced = new OnTextModified();
            /*Instanciación de un nuevo Seal Code Area*/
    public static final OnCodeAreaCreated onSealCodeAreaCreated = new OnCodeAreaCreated();
    
    /*  Settings Pane   */
    public static final OnSettingsUpdate onSettingsUpdate = new OnSettingsUpdate();
    public static final OnThemeChange onThemeChange = new OnThemeChange();
    public static final OnPreferencesUpdate onPreferencesUpdate = new OnPreferencesUpdate();
    public static final OnProfileChange onProfileChange = new OnProfileChange();
}