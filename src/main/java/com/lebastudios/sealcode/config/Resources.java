package com.lebastudios.sealcode.config;

import com.lebastudios.sealcode.SealCodeApplication;
import com.lebastudios.sealcode.util.FileOperation;
import javafx.scene.image.Image;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class Resources
{
    private static final Map<String, Image> loadedIcons = new HashMap<>();

    private static boolean existsResource(String path)
    {
        return SealCodeApplication.class.getResource(path) != null;
    }

    public static Image getIcon(String iconName)
    {
        Image image = loadedIcons.get(GlobalConfig.getStaticInstance().editorConfig.theme + iconName);

        if (image == null)
        {
            return loadIcon(iconName);
        }

        return image;
    }

    private static Image loadIcon(String iconName)
    {
        Image icon = null;

        // Check if the icon is defined in the actual theme
        String path = FilePaths.getIconDirectory() + iconName;
        if (existsResource(path))
        {
            icon = new Image(SealCodeApplication.class.getResourceAsStream(path));
        }

        // Check if the icon is defined in the default theme
        path = FilePaths.getDefaultIconDirectory() + iconName;
        if (icon == null && existsResource(path))
        {
            icon = new Image(SealCodeApplication.class.getResourceAsStream(path));
        }

        if (icon == null)
        {
            icon = new Image(SealCodeApplication.class.getResourceAsStream(FilePaths.getDefaultIconFile()));
        }
        
        loadedIcons.put(GlobalConfig.getStaticInstance().editorConfig.theme + iconName, icon);
        
        return icon;
    }

    public static String getLangCommonStyle()
    {
        return getExtensionStyle("commonLang");
    }

    public static String getExtensionStyle(String fileExtension)
    {
        // Check if the extension has a style defined in the actual theme
        String langStyleFile = FilePaths.getStyleDirectory() + fileExtension + ".css";
        if (existsResource(langStyleFile))
        {
            return SealCodeApplication.class.getResource(langStyleFile).toExternalForm();
        }

        // Check if the equivalent extension has a style defined in the actual theme
        langStyleFile = FilePaths.getStyleDirectory() + FileOperation.toEquivalentFileExtension(fileExtension) + ".css";
        if (existsResource(langStyleFile))
        {
            return SealCodeApplication.class.getResource(langStyleFile).toExternalForm();
        }

        // Check if the extension has a style defined in the default theme
        langStyleFile = FilePaths.getDefaultStyleDirectory() + fileExtension + ".css";
        if (existsResource(langStyleFile))
        {
            return SealCodeApplication.class.getResource(langStyleFile).toExternalForm();
        }

        // Check if the equivalent extension has a style defined in the default theme
        langStyleFile =
                FilePaths.getDefaultStyleDirectory() + FileOperation.toEquivalentFileExtension(fileExtension) + ".css";
        if (existsResource(langStyleFile))
        {
            return SealCodeApplication.class.getResource(langStyleFile).toExternalForm();
        }

        // If the extension has no style defined, use the default style
        return SealCodeApplication.class.getResource(FilePaths.getDefaultLangStyleFile()).toExternalForm();
    }

    public static String getThemeStyle()
    {
        String themeStylePath = FilePaths.getStyleDirectory() + "theme.css";
        if (existsResource(themeStylePath))
        {
            return SealCodeApplication.class.getResource(themeStylePath).toExternalForm();
        }

        themeStylePath = FilePaths.getDefaultStyleDirectory() + "theme.css";
        return SealCodeApplication.class.getResource(themeStylePath).toExternalForm();
    }

    public static String getHighlightingRules(String extension)
    {
        String path = FilePaths.getProgLangSyntaxDirectory() + extension + ".json";

        String rules = "{}";

        File file = new File(path);

        if (!file.exists())
        {
            path = FilePaths.getProgLangSyntaxDirectory() + FileOperation.toEquivalentFileExtension(extension) +
                    ".json";
        }

        if (!file.exists() && !extension.equals("default"))
        {
            System.err.println("Error reading the syntax highlighting pattern file for the extension " +
                    extension + ". Default syntax highlighting will not be applied.");
            return getHighlightingRules("default");
        }

        try
        {
            rules = FileOperation.readFile(new File(path));
        }
        catch (Exception e)
        {
            System.err.println("It couldnt be loaded any syntax highlighting pattern file. " +
                    "The syntax highlighting will not be applied.");

        }

        return rules;
    }
}