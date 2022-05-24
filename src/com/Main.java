package com;

public class Main
{
    public static void main(String[] args)
    {
        if (args.length != 0)
        {
            ParserXML parserXML = new ParserXML(args[0]);
        }
        else
        {
            System.out.println("no arguments");
        }
    }
}
