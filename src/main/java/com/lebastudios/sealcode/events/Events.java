package com.lebastudios.sealcode.events;

import com.lebastudios.sealcode.core.frontend.fxextends.SealCodeArea;

// TODO: Hacer un numero limitado de eventos implementables. No un nombre especifico de clase cuando con una llega.

public final class Events
{
    /*  Seal Code Area  */
    public static class OnTextModified extends TextModEvent {}
    public static class OnCodeAreaCreated extends AppEvent1<SealCodeArea> {}
    public static class OnCodeAreaDeleted extends AppEvent1<SealCodeArea> {}
    public static class OnCompletationsRequest extends CompletationRequestEvent {}
    
    public static class OnAppExit extends AppEvent {}
    public static class OnAppStart extends AppEvent {}
    public static class OnSettingsUpdate extends AppEvent {}
    public static class OnThemeChange extends AppEvent {}
    public static class OnPreferencesUpdate extends AppEvent {}
    public static class OnProfileChange extends AppEvent {}
}
