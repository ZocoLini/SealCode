package com.lebastudios.sealcode.custom.logic.formatting;

import com.lebastudios.sealcode.core.frontend.fxextends.SealCodeArea;
import com.lebastudios.sealcode.global.TextModInf;
import com.lebastudios.sealcode.events.ITextMod;
import com.lebastudios.sealcode.core.logic.config.GlobalConfig;

public class TabRemover implements ITextMod
{
    @Override
    public void invoke(String oldText, TextModInf modInf, SealCodeArea codeArea)
    {
        String newText = modInf.textModificated
                .replace("\t", " ".repeat(GlobalConfig.getStaticInstance().editorConfig.tabSize));
        modInf.update(modInf.start, modInf.end, newText);
    }
}