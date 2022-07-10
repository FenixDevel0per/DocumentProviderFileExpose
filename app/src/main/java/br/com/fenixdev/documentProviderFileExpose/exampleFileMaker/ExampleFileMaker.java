package br.com.fenixdev.documentProviderFileExpose.exampleFileMaker;

import android.content.Context;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExampleFileMaker {

    public static void writeExampleFile(Context context, String text){
        File file = new File(context.getFilesDir(), "Example.txt");

        try {
            FileWriter fileWriter = new FileWriter(file, true);
            fileWriter.append(text + '\n');
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
