package ru.Homeworks.A_Batsanov;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        StringBuilder log = new StringBuilder();
        List<File> catalogs = new ArrayList<>();
        Collections.addAll(catalogs,
                new File("C://Games", "src"),
                new File("C://Games", "res"),
                new File("C://Games", "savegames"),
                new File("C://Games", "temp"));
        Collections.addAll(catalogs,
                new File(catalogs.get(0), "main"),
                new File(catalogs.get(0), "test"),
                new File(catalogs.get(1), "drawables"),
                new File(catalogs.get(1), "vectors"));
        for (File catalog : catalogs) {
            if (catalog.getAbsoluteFile().mkdir())
                log.append("Создан каталог " + catalog.getAbsolutePath() + "\n");
        }
        List<File> files = new ArrayList<>();
        Collections.addAll(files,
                new File(catalogs.get(4), "Main.java"),
                new File(catalogs.get(4), "Utils.java"),
                new File(catalogs.get(3), "temp.txt"));
        for (File file : files) {
            try {
                if (file.getAbsoluteFile().createNewFile())
                    log.append("Создан файл " + file.getAbsolutePath() + "\n");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        try (FileWriter logWriter = new FileWriter("C://Games/temp/temp.txt", false)) {
            logWriter.write(String.valueOf(log));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        List<GameProgress> gameProgresses = new ArrayList<>();
        Collections.addAll(gameProgresses,
                new GameProgress(100, 3, 1, 200),
                new GameProgress(90, 6, 5, 1000),
                new GameProgress(80, 10, 10, 5000));
        List<String> savings = new ArrayList<>();
        for (GameProgress gameProgress : gameProgresses) {
            String currentFilePath = "C://Games/savegames/save"
                    + gameProgresses.indexOf(gameProgress) + ".dat";
            GameProgress.saveGame(currentFilePath, gameProgress);
            savings.add(currentFilePath);
        }
        String zipFilePath = "C://Games/savegames/zipSavings.zip";
        GameProgress.zipFiles(zipFilePath, savings);
        for (String saving : savings) {
            new File(saving).delete();
        }
        GameProgress.openZip(zipFilePath, "C://Games/savegames/");
        GameProgress.openProgress("C://Games/savegames/packedSaving1.dat");
    }
}
