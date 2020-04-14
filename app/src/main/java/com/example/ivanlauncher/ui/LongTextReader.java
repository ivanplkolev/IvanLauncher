package com.example.ivanlauncher.ui;

import android.widget.TextView;

import com.example.ivanlauncher.common.TextReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class LongTextReader implements MenuInterface {

    private List<String> lines;

    private UserInterfaceEngine parent;

    private TextView tv;

    private int currentPosition = 0;

    public LongTextReader(TextView tv, UserInterfaceEngine parent) {
        this.tv = tv;
        this.parent = parent;
    }

    public void setText(String text) {
        String[] allLines = text.split("-|\\\\.");

        lines = Arrays.asList(allLines);

        lines.removeIf(a -> a.trim().isEmpty());//todo make more sophisticated filtering
    }


    public void setUrl(String utlAddress) {

        lines = new ArrayList<>();
        try {
            URL url = new URL(utlAddress);
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);//todo only for debug

                if (Pattern.matches(".*\\p{InCyrillic}.*", inputLine)) {//todo make check
                    lines.add(inputLine);
                }
            }

            in.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        lines.removeIf(a -> a.trim().isEmpty());//todo make more sophisticated filtering
    }


    @Override
    public void resetUI() {
        currentPosition = 0;

        notifyForChanges();
    }

    @Override
    public void selectNext() {
        if (currentPosition < lines.size() - 1) {
            currentPosition++;
        }
        notifyForChanges();
    }

    @Override
    public void selectPrevious() {
        if (currentPosition > 0) {
            currentPosition--;
        }
        notifyForChanges();
    }

    @Override
    public void back() {
        parent.goToMainMenu();//todo
    }

    @Override
    public void enter() {
        //do nothing

    }

    @Override
    public void notifyForChanges() {
        String line = lines.get(currentPosition);
        tv.setText(line);
        tv.invalidate();
        TextReader.read(tv.getText().toString());
    }
}
