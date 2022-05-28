package com;

public class Main
{
    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            throw new RuntimeException("no arguments");
        }

        ParserXML parserXML = new ParserXML(args[0]);

        if (args.length == 2)
        {
            parserXML.sort(args[1]);
        }

        parserXML.print();
    }
}
