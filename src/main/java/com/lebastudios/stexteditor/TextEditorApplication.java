package com.lebastudios.stexteditor;

import com.lebastudios.stexteditor.applogic.Resources;
import com.lebastudios.stexteditor.applogic.config.Session;
import com.lebastudios.stexteditor.applogic.config.Config;
import com.lebastudios.stexteditor.iobjects.imanagers.Manager;
import com.lebastudios.stexteditor.events.GlobalEvents;
import com.lebastudios.stexteditor.iobjects.imanagers.singletonmanagers.MainSingletonManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TextEditorApplication extends Application
{
    public static final List<Manager<?>> INSTANCIATED_MANAGERS = new ArrayList<>();

    private static TextEditorApplication instance;

    public static Stage getStage()
    {
        return instance.stage;
    }

    public TextEditorApplication()
    {
        instance = this;
    }

    public Stage stage;
    boolean hiloDeJuego = true;

    private String actualStyle = Resources.getThemeStyle();
    
    @Override
    public void start(Stage stage) throws IOException
    {
        this.stage = stage;
        
        Thread hiloPrecargaConfig = Config.getStaticInstance().preload();
        Thread hiloPrecargaSession = Session.getStaticInstance().preload();

        FXMLLoader fxmlLoader =
                new FXMLLoader(TextEditorApplication.class.getResource("hello-view.fxml"));

        Scene escenaActual = new Scene(fxmlLoader.load());
        
        escenaActual.getStylesheets().add(actualStyle);
        
        stage.setTitle("Text Editor!");

        stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> Config.getStaticInstance().save());
        stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> Session.getStaticInstance().save());
        stage.addEventHandler(WindowEvent.WINDOW_HIDING, event -> hiloDeJuego = false);
        GlobalEvents.onThemeChanged.addListener(this::setActualStyle);
        
        stage.setScene(escenaActual);

        try
        {
            hiloPrecargaConfig.join();
            hiloPrecargaSession.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("Error al cargar la configuración. No se pude comenzar el programa");
            throw new RuntimeException(e);
        }

        stage.show();

        MainSingletonManager.getInstance().load();
        
        new Thread(this::bucleDeJuego).start();
    }
    
    private void setActualStyle()
    {
        this.stage.getScene().getStylesheets().remove(actualStyle);
        actualStyle = Resources.getThemeStyle();
        this.stage.getScene().getStylesheets().add(actualStyle);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
    
    /**
     * Bucle de juego que se encarga de llamar a los eventos de actualización. Este metodo se ejecuta en un hilo 
     * separado.
     */
    // TODO: Pensar en mover esto a Controller y que cada controller tenga su propio hilo. Aquí, igual lo más 
    //  correcto, es un fixed update.
    private void bucleDeJuego()
    {
        while (hiloDeJuego)
        {
            GlobalEvents.onUpdate.invoke();

            try
            {
                //noinspection BusyWait
                Thread.sleep(1000 / 15);
            }
            catch (InterruptedException e)
            {
                System.err.println("Error en el hilo de juego, debe haberse interrumpido.");
            }
        }
    }
}