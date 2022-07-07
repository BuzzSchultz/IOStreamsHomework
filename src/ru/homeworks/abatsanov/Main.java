package ru.homeworks.abatsanov;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
    private static final String gamesPath = "C://Games"; // Директория для установки игры;
    private static List<File> catalogs = new ArrayList<>();
    private static List<String> rootDirectories = Arrays.asList("src", "res", "savegames", "temp");
    private static List<String> srcNestedDirectories = Arrays.asList("main", "test");
    private static List<String> resNestedDirectories = Arrays.asList("drawables", "vectors", "icons");
    private static List<File> files = new ArrayList<>();
    private static List<String> mainFiles = Arrays.asList("Main.java", "Utils.java");
    private static List<String> tempFiles = Arrays.asList("temp.txt");
    private static List<GameProgress> gameProgresses = new ArrayList<>();

    private static void addDirectories(String path, String directory) {
        catalogs.add(new File(path, directory));
    }

    private static void addFiles(File file, String directory) {
        files.add(new File(file, directory));
    }

    public static void main(String[] args) {

//        Задача 1. Установка;
        StringBuilder log = new StringBuilder();
        for (String rootDirectory : rootDirectories) {
            addDirectories(gamesPath, rootDirectory);
        }
        for (String srcNestedDirectory : srcNestedDirectories) {
            addDirectories(String.valueOf(catalogs.get(0)), srcNestedDirectory);
        }
        for (String resNestedDirectory : resNestedDirectories) {
            addDirectories(String.valueOf(catalogs.get(1)), resNestedDirectory);
        }
        for (File catalog : catalogs) {
            if (catalog.getAbsoluteFile().mkdir())
                log.append("Создан каталог ")
                        .append(catalog.getAbsolutePath())
                        .append("\n");
        }
        for (String mainFile : mainFiles) {
            addFiles(catalogs.get(4), mainFile);
        }
        for (String tempFile : tempFiles) {
            addFiles(catalogs.get(3), tempFile);
        }
        for (File file : files) {
            try {
                if (file.getAbsoluteFile().createNewFile())
                    log.append("Создан файл ")
                            .append(file.getAbsolutePath())
                            .append("\n");
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }
        try (FileWriter logWriter = new FileWriter(files.get(2), false)) {
            logWriter.write(String.valueOf(log));
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("Задача 1 \"Установка\" закончена.");

//        Задача 2. Сохранение;
        Collections.addAll(gameProgresses,
                new GameProgress(100, 3, 1, 200),
                new GameProgress(90, 6, 5, 1000),
                new GameProgress(80, 10, 10, 5000));
        List<String> savings = new ArrayList<>();
        String savingDirectory = catalogs.get(2) + "/";
        for (GameProgress gameProgress : gameProgresses) {
            String currentFilePath = savingDirectory + "save"
                    + gameProgresses.indexOf(gameProgress) + ".dat";
            GameProgress.saveGame(currentFilePath, gameProgress);
            savings.add(currentFilePath);
        }
        String zipFilePath = savingDirectory + "zipSavings.zip";
        GameProgress.zipFiles(zipFilePath, savings);
        for (String saving : savings) {
            new File(saving).delete();
        }
        System.out.println("Задача 2 \"Сохранение\" закончена.");

//        Задача 3. Загрузка;
        GameProgress.openZip(zipFilePath, savingDirectory);
        System.out.println(GameProgress.openProgress(savingDirectory + "packedSaving1.dat"));
        System.out.println("Задача 3 \"Загрузка\" закончена.");
    }
}
