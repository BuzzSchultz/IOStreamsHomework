package ru.Homeworks.A_Batsanov;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {

//        Задача 1. Установка;
        StringBuilder log = new StringBuilder();
        String gamesPath = "C://Games"; // Директория для игры;
        List<File> catalogs = new ArrayList<>();
        Collections.addAll(catalogs,
                new File(gamesPath, "src"),
                new File(gamesPath, "res"),
                new File(gamesPath, "savegames"),
                new File(gamesPath, "temp"));
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
        try (FileWriter logWriter = new FileWriter(gamesPath + "/temp/temp.txt", false)) {
            logWriter.write(String.valueOf(log));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
//        Задача 1. Установка закончена.

//        Задача 2. Сохранение;
        List<GameProgress> gameProgresses = new ArrayList<>();
        Collections.addAll(gameProgresses,
                new GameProgress(100, 3, 1, 200),
                new GameProgress(90, 6, 5, 1000),
                new GameProgress(80, 10, 10, 5000));
        List<String> savings = new ArrayList<>();
        for (GameProgress gameProgress : gameProgresses) {
            String currentFilePath = gamesPath + "/savegames/save"
                    + gameProgresses.indexOf(gameProgress) + ".dat";
            GameProgress.saveGame(currentFilePath, gameProgress);
            savings.add(currentFilePath);
        }
        String zipFilePath = gamesPath + "/savegames/zipSavings.zip";
        GameProgress.zipFiles(zipFilePath, savings);
        for (String saving : savings) {
            new File(saving).delete();
        }
//        Задача 2. Сохранение закончена.

//        Задача 3. Загрузка;
        GameProgress.openZip(zipFilePath, gamesPath + "/savegames/");
        GameProgress.openProgress(gamesPath + "/savegames/packedSaving1.dat");
    }
//        Задача 3. Загрузка закончена.
}
