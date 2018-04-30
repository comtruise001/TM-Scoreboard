import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.util.Collections.sort;


public class Main {

    public static void main(String[] args) {

        String cloudNationsDirectory = "";
        String programNationDirectory = "";

        if (args.length == 2) {
            cloudNationsDirectory = args[0];
            programNationDirectory = args[1];
        } else {
            System.exit(-1);
        }

        System.out.println("--------------------------");
        System.out.println("Initialization... \nCloud directory : " + cloudNationsDirectory);
        System.out.println("Initialization... \nProgram directory : " + programNationDirectory);
        System.out.println("--------------------------");

        ArrayList<File> allFilesCloud = ManageFiles.listAllFiles(cloudNationsDirectory);
        ArrayList<File> allFilesProgram = ManageFiles.listAllFiles(programNationDirectory);
        String htmlOutput;
        ArrayList<String> maps;
        ArrayList<String> categories;
        HashMap<String, String> myHashMap;
        Path fileToWrite = Paths.get("./scoreboard.html");

        System.out.println("--------------------------");
        System.out.println("Processing the TrackMania Nations Directory : " + allFilesProgram);
        System.out.println("--------------------------");


        System.out.println("--------------------------");
        System.out.println("Existing Files");
        System.out.println("--------------------------");
        for (int i = 0; i < allFilesProgram.size(); i++) {
            System.out.println(allFilesProgram.get(i).getPath());
        }

        System.out.println("--------------------------");
        System.out.println("Creating Scores");
        System.out.println("--------------------------");
        ArrayList<Score> scores = Score.createScores(allFilesProgram);

        if (scores.isEmpty()) {
            System.out.println("No available scores. Program will stop.");
            System.exit(-1);
        }

        System.out.println("--------------------------");
        System.out.println("Existing scores");
        System.out.println("--------------------------");
        for (int i = 0; i < scores.size(); i++) {
            System.out.println(scores.get(i));
        }

        System.out.println("--------------------------");
        System.out.println("Deleting files");
        System.out.println("--------------------------");
        ManageFiles.deleteFiles(scores);

        System.out.println("--------------------------");
        System.out.println("Remaining scores");
        System.out.println("--------------------------");
        allFilesProgram = ManageFiles.listAllFiles(cloudNationsDirectory);
        scores = Score.createScores(allFilesProgram);

        if (scores.isEmpty()) {
            System.out.println("No available scores. Program will stop.");
            System.exit(-1);
        }

        System.out.println("\n-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n");

        System.out.println("--------------------------");
        System.out.println("Processing the cloud Nations Directory : " + cloudNationsDirectory);
        System.out.println("--------------------------");


        System.out.println("--------------------------");
        System.out.println("Existing Files");
        System.out.println("--------------------------");
        for (int i = 0; i < allFilesCloud.size(); i++) {
            System.out.println(allFilesProgram.get(i).getPath());
        }

        System.out.println("--------------------------");
        System.out.println("Creating Scores");
        System.out.println("--------------------------");
        scores = Score.createScores(allFilesCloud);

        if (scores.isEmpty()) {
            System.out.println("No available scores. Program will stop.");
            System.exit(-1);
        }

        System.out.println("--------------------------");
        System.out.println("Existing scores");
        System.out.println("--------------------------");
        for (int i = 0; i < scores.size(); i++) {
            System.out.println(scores.get(i));
        }

        System.out.println("--------------------------");
        System.out.println("Deleting files");
        System.out.println("--------------------------");
        ManageFiles.deleteFiles(scores);

        System.out.println("--------------------------");
        System.out.println("Remaining scores");
        System.out.println("--------------------------");
        allFilesCloud = ManageFiles.listAllFiles(cloudNationsDirectory);
        scores = Score.createScores(allFilesCloud);

        if (scores.isEmpty()) {
            System.out.println("No available scores. Program will stop.");
            System.exit(-1);
        }

        System.out.println("\n-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n");

        System.out.println("--------------------------");
        System.out.println("Creating Scoreboard");
        System.out.println("--------------------------");

        //Include Nadeo scores
        scores.addAll(Score.nadeoScores());
        //Sort scores
        sort(scores);
        maps = Score.listMaps(scores);
        categories = Score.listCategories(scores);
        myHashMap = Score.mapMapCategories(scores);


        System.out.println("--------------------------");
        System.out.println("Listing Available scores");
        System.out.println("--------------------------");
        for (int i = 0; i < scores.size(); i++) {
            System.out.println(scores.get(i));
        }

        System.out.println("--------------------------");
        System.out.println("Generating HTML");
        System.out.println("--------------------------");
        htmlOutput = HTML.generateHTML(categories, maps, scores, myHashMap);


        System.out.println("--------------------------");
        System.out.println("Write in file");
        System.out.println("--------------------------");
        try {
            Files.write(fileToWrite, Collections.singleton(htmlOutput), Charset.forName("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
