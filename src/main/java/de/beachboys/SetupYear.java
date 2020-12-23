package de.beachboys;

import configuration.ClassProvider;
import configuration.JavaForgerConfiguration;
import configuration.StaticJavaForgerConfiguration;
import generator.CodeSnipit;
import generator.JavaForger;
import templateInput.TemplateInputParameters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

public class SetupYear {

    private static final String BASE_PACKAGE = Day.class.getPackageName().replace(".", "/");
    public static final String BASE_JAVA_FOLDER = "src/main/java/";
    public static final String BASE_PACKAGE_FOLDER = BASE_JAVA_FOLDER + BASE_PACKAGE;
    public static final String BASE_RESOURCES_FOLDER = "src/main/resources/";
    public static final String BASE_TEST_FOLDER = "src/test/java/";

    public static void main(String[] args) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("Year to (re)create: ");
        String year = in.nextLine();

        StaticJavaForgerConfiguration.getConfig().setProjectPaths(BASE_JAVA_FOLDER);
        StaticJavaForgerConfiguration.getConfig().addTemplateLocation(BASE_RESOURCES_FOLDER + "templates");

        Path yearPath = Path.of(BASE_PACKAGE_FOLDER + "/aoc" + year);
        Path yearTestPath = Path.of(BASE_TEST_FOLDER + BASE_PACKAGE + "/aoc" + year);
        boolean doIt = true;
        if (Files.exists(yearPath) || Files.exists(yearTestPath)) {
            System.out.println("Year " + year + " exists! Do you want to overwrite to start from scratch? (y)");
            if (!"y".equals(in.nextLine())) {
                doIt = false;
                System.out.println("Creation cancelled.");
            }
        }
        if (doIt) {
            createFiles(year, yearPath, yearTestPath);
            updateYearMaps(year);
            System.out.println("Year " + year + " successfully created.");
        }
    }

    private static void updateYearMaps(String year) {
        TemplateInputParameters parameters = new TemplateInputParameters();
        parameters.put("year", year);
        JavaForgerConfiguration getDaysConfig = JavaForgerConfiguration.builder().template("YearMapsGetDaysPerYear.javat").mergeClassProvider(new ClassProvider()).inputParameters(parameters).build();
        JavaForger.execute(getDaysConfig, BASE_PACKAGE_FOLDER + "/YearMaps.java");
        JavaForgerConfiguration buildStaticMapOfDaysConfig = JavaForgerConfiguration.builder().template("YearMapsBuildStaticMapOfDays.javat").mergeClassProvider(new ClassProvider()).override(true).build();
        JavaForger.execute(buildStaticMapOfDaysConfig, BASE_PACKAGE_FOLDER +"/YearMaps.java");
    }

    private static void createFiles(String year, Path yearPath, Path yearTestPath) throws IOException {
        Files.createDirectories(yearPath);
        Files.createDirectories(yearTestPath);
        TemplateInputParameters parameters = new TemplateInputParameters();
        parameters.put("year", year);
        JavaForgerConfiguration.Builder dayBuilder = JavaForgerConfiguration.builder().template("Day.javat").mergeClassProvider(ClassProvider.fromParentMergeClass());
        JavaForgerConfiguration.Builder dayTestBuilder = JavaForgerConfiguration.builder().template("DayTest.javat").mergeClassProvider(ClassProvider.fromParentMergeClass());
        for (int i = 1; i <= 25; i++) {
            String day = String.format("%02d", i);
            parameters.put("day", day);
            buildAndWriteToFile(dayBuilder, yearPath.toString() + "/Day" + day + ".java", parameters);
            buildAndWriteToFile(dayTestBuilder, yearTestPath.toString() + "/Day" + day + "Test.java", parameters);
        }
    }

    private static void buildAndWriteToFile(JavaForgerConfiguration.Builder basicFileConfigBuilder, String fileName, TemplateInputParameters parameters) throws IOException {
        JavaForgerConfiguration config = basicFileConfigBuilder.inputParameters(parameters).build();
        CodeSnipit codeSnipit = JavaForger.execute(config, BASE_PACKAGE_FOLDER + "/Day.java");
        BufferedWriter testWriter = new BufferedWriter(new FileWriter(fileName));
        testWriter.write(codeSnipit.getCode());
        testWriter.close();
    }

}
