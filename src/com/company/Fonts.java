package com.company;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Fonts {
    Font font = null;
    Font sizedFont = null;
    File font_file;

    public Fonts(String fontName)  {
        font_file = new File("Fonts/" + fontName + ".ttf");
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, font_file);
            sizedFont = font.deriveFont(12f);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public Font getFont(){
        return sizedFont;
    }

    public void setFontSize(float size){
        sizedFont = sizedFont.deriveFont(size);
    }
}
