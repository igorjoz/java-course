package org.example;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Path inputDir = Paths.get("C:/Users/igorj/source/lab6/src/main/resources/img");
        Path outputDir = Paths.get("C:/Users/igorj/source/lab6/src/main/resources/out");
        int parallelism = 6;

        try {
            Files.createDirectories(outputDir);
            List<Path> files = Files.list(inputDir).collect(Collectors.toList());

            long startTime = System.currentTimeMillis();

            ForkJoinPool customThreadPool = new ForkJoinPool(parallelism);
            customThreadPool.submit(() -> {
                files.parallelStream().forEach(path -> processImage(path, outputDir));
            }).join();

            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            System.out.println("Processing time: " + duration + " ms");

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/Users/igorj/source/lab6/src/main/resources/time_log.txt", true))) {
                writer.write("Processing time with " + parallelism + " threads: " + duration + " ms\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processImage(Path imagePath, Path outputDir) {
        try {
            BufferedImage original = ImageIO.read(imagePath.toFile());
            BufferedImage processed = new BufferedImage(original.getWidth(), original.getHeight(), original.getType());

            for (int i = 0; i < original.getWidth(); i++) {
                for (int j = 0; j < original.getHeight(); j++) {
                    int rgb = original.getRGB(i, j);
                    Color color = new Color(rgb, true);
                    Color transformedColor = new Color(color.getBlue(), color.getGreen(), color.getRed());
                    processed.setRGB(i, j, transformedColor.getRGB());
                }
            }

            File outputFile = outputDir.resolve(imagePath.getFileName().toString()).toFile();
            ImageIO.write(processed, "jpg", outputFile);
            System.out.println("Processed and saved: " + outputFile);
        } catch (IOException e) {
            System.out.println("Error processing image: " + imagePath);
            e.printStackTrace();
        }
    }
}
