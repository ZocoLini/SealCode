package com.lebastudios.sealcode.os;

import java.io.IOException;

class Windows implements IOperativeSystem
{
    @Override
    public String fileSeparator()
    {
        return "\\\\";
    }

    @Override
    public Process executeCommand(String commando)
    {
        ProcessBuilder builder = new ProcessBuilder();
        builder.command("cmd.exe", "/c", commando);
        builder.inheritIO();
        Process proceso;

        try
        {
            proceso = builder.start();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        return proceso;
    }
}
