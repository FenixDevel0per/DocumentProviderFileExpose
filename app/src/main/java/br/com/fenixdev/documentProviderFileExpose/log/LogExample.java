package br.com.fenixdev.documentProviderFileExpose.log;

import android.content.Context;

import java.util.Date;

import br.com.fenixdev.documentProviderFileExpose.exampleFileMaker.ExampleFileMaker;

public class LogExample {

    public static void logTime(Context context){
        Date currentTime = new Date();
        ExampleFileMaker.writeExampleFile(context, currentTime.toString());
    }
}
