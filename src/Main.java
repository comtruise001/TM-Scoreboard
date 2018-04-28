import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Collections.sort;


public class Main {

    static class Score implements Comparable<Score> {
        String map;
        String pseudo;
        String time;
        File file;
        String category;

        Score(String map, String pseudo, String time, File file, String category) {
            this.map = map;
            this.pseudo = pseudo;
            this.time = time;
            this.file = file;
            this.category = category;
        }

        public String getMap() {
            return map;
        }

        public String getPseudo() {
            return pseudo;
        }

        public String getTime() {
            return time;
        }

        public File getFile() {
            return file;
        }

        public String getCategory() {
            return category;
        }

        @Override
        public String toString() {
            return "Score{" +
                    "map='" + map + '\'' +
                    ", pseudo='" + pseudo + '\'' +
                    ", time='" + time + '\'' +
                    ", file=" + file +
                    ", category='" + category + '\'' +
                    '}';
        }

        public int compareTo(Score s2) {

            String s1Time = this.getTime();
            String s2Time = s2.getTime();

            Integer s1Minutes = Integer.parseInt(s1Time.substring(0, 2));
            Integer s2Minutes = Integer.parseInt(s2Time.substring(0, 2));
            Integer s1Seconds = Integer.parseInt(s1Time.substring(3, 5));
            Integer s2Seconds = Integer.parseInt(s2Time.substring(3, 5));

            Integer s1Hundredth = Integer.parseInt(s1Time.substring(7, 9));
            Integer s2Hundredth = Integer.parseInt(s2Time.substring(7, 9));

            if (this.getMap().equals(s2.getMap())) {

                if (s1Minutes < s2Minutes) {
                    return -1;
                } else if (s1Minutes == s2Minutes) {

                    if (s1Seconds < s2Seconds) {
                        return -1;
                    } else if (s1Seconds == s2Seconds) {

                        if (s1Hundredth < s2Hundredth) {
                            return -1;
                        } else if (s1Hundredth == s2Hundredth) {
                            return 0;
                        } else if (s1Hundredth > s2Hundredth) {
                            return 1;
                        }

                    } else if (s1Seconds > s2Seconds) {
                        return 1;
                    }

                } else if (s1Minutes > s2Minutes) {
                    return 1;
                }
            }

            return 0;
        }

    }

    public static ArrayList<File> listAllFiles(String path) {

        File currentDirectory = new File(path);
        File[] listFiles = currentDirectory.listFiles();
        ArrayList<File> aFiles = new ArrayList<>();

        for (int i = 0; i < listFiles.length; i++) {

            if (listFiles[i].isDirectory()) {
                aFiles.addAll(listAllFiles(listFiles[i].getPath()));
            } else {
                aFiles.add(listFiles[i]);
            }

        }
        return aFiles;
    }

    public static ArrayList<Score> createScores(ArrayList<File> aFiles) {

        ArrayList<Score> scores = new ArrayList<>();
        String regex = ".*\\\\(.*?)\\\\(.*)_(.*?)\\((.*?)\\).*";
        Pattern pattern = Pattern.compile(regex);

        for (int i = 0; i < aFiles.size(); i++) {

            //System.out.println(aFiles.get(i).getPath());
            Matcher matcher = pattern.matcher(aFiles.get(i).getPath());

            while (matcher.find()) {
                System.out.println("Trouvé --> " + aFiles.get(i).getPath() + " | Niveau : " + matcher.group(1) + " | Pseudo : " + matcher.group(2) + " | Temps : " + matcher.group(3));
                scores.add(new Score(matcher.group(2), matcher.group(3), matcher.group(4), aFiles.get(i), matcher.group(1)));
            }

        }

        for (int j = 0; j < scores.size(); j++) {
            System.out.println(scores.get(j));
        }

        return scores;
    }


    public static Score bestScoreForUserOnMap(Score s1, Score s2) {

        String s1Time = s1.getTime();
        String s2Time = s2.getTime();

        Integer s1Minutes = Integer.parseInt(s1Time.substring(0, 2));
        Integer s2Minutes = Integer.parseInt(s2Time.substring(0, 2));
        Integer s1Seconds = Integer.parseInt(s1Time.substring(3, 5));
        Integer s2Seconds = Integer.parseInt(s2Time.substring(3, 5));

        Integer s1Hundredth = Integer.parseInt(s1Time.substring(7, 9));
        Integer s2Hundredth = Integer.parseInt(s2Time.substring(7, 9));


        if (s1.getMap().equals(s2.getMap())) {
            if (s1.getPseudo().equals(s2.getPseudo())) {
                if (s1Minutes < s2Minutes) {
                    return s1;
                } else if (s1Minutes == s2Minutes) {
                    if (s1Seconds < s2Seconds) {
                        return s1;
                    } else if (s1Seconds == s2Seconds) {

                        if (s1Hundredth < s2Hundredth) {
                            return s1;
                        } else if (s1Hundredth == s2Hundredth) {
                            return null;
                        } else if (s1Hundredth > s2Hundredth) {
                            return s2;
                        }
                    } else if (s1Seconds > s2Seconds) {
                        return s2;
                    }
                } else if (s1Minutes > s2Minutes) {
                    return s2;
                }
            }
        }

        return null;
    }


    public static void deleteFiles(ArrayList<Score> scores) {

        Score bestScore;

        for (int i = 0; i < scores.size(); i++) {

            System.out.println("CHOSEN : " + scores.get(i));

            for (int j = 0; j < scores.size(); j++) {
                System.out.println("CURRENT : " + scores.get(j));
                bestScore = bestScoreForUserOnMap(scores.get(i), scores.get(j));
                if (bestScore == scores.get(i)) {
                    scores.get(j).getFile().delete();
                    System.out.println("DELETE : " + scores.get(j));
                } else if (bestScore == scores.get(j)) {
                    scores.get(i).getFile().delete();
                    System.out.println("DELETE : " + scores.get(i));
                }
            }
        }
    }

    public static ArrayList<String> listMaps(ArrayList<Score> scores) {

        ArrayList<String> maps = new ArrayList<>();

        for (int i = 0; i < scores.size(); i++) {
            if (!maps.contains(scores.get(i).getMap())) {
                maps.add(scores.get(i).getMap());

            }
        }

        System.out.println("--------------------------");
        System.out.println("Existing Maps");
        System.out.println("--------------------------");
        for (int i = 0; i < maps.size(); i++) {
            System.out.println(maps.get(i));
        }

        return maps;
    }

    public static ArrayList<String> listCategories(ArrayList<Score> scores) {

        ArrayList<String> categories = new ArrayList<>();

        for (int i = 0; i < scores.size(); i++) {
            if (!categories.contains(scores.get(i).getCategory())) {
                categories.add(scores.get(i).getCategory());

            }
        }

        System.out.println("--------------------------");
        System.out.println("Existing Categories");
        System.out.println("--------------------------");
        for (int i = 0; i < categories.size(); i++) {
            System.out.println(categories.get(i));
        }

        return categories;
    }

    public static HashMap<String, String> mapMapCategories(ArrayList<Score> scores) {

        HashMap<String, String> myHashMap = new HashMap<String, String>();

        for (int i = 0; i < scores.size(); i++) {
            myHashMap.put(scores.get(i).getMap(), scores.get(i).getCategory());
        }

        return myHashMap;
    }


    public static String generateHTML(ArrayList<String> categories, ArrayList<String> maps, ArrayList<Score> scores, HashMap<String, String> myHashMap) {

        String htmlOutput = new String("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>Trackmania Scoreboard</title>\n" +
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">" +
                "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js\"></script>" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "<style>\n" +
                "body{\n" +
                "background-color: #EEEEEE;\n" +
                "background-repeat: no-repeat;\n" +
                "background-attachment: fixed;\n" +
                "}\n" +
                "</style>\n" +
                "<script>" +
                "function toogleScores(category){" +
                "   var $category = $(\".\"+category);" +
                "   $(\".score\").hide();" +
                "   $category.show();" +
                "}" +
                "function displayEverything(){" +
                "   $(\".score\").show();" +
                "}" +
                "</script>");


        String nav = new String("<nav class=\"navbar navbar-expand-lg navbar-dark bg-dark\">\n" +
                "    <a class=\"navbar-brand\" href=\"javascript:displayEverything();\">TrackMania scoreboard</a>\n" +
                "    <div class=\"collapse navbar-collapse\">\n" +
                "      <ul class=\"navbar-nav mr-auto\">\n");


        for (String category : categories) {

            nav += new String("        <li class=\"nav-item active\">\n" +
                    "           <a class=\"nav-link\" href=\"javascript:toogleScores('" + category + "');\">" + category + "</a>\n" +
                    "       </li>\n");
        }


        nav += new String("       </ul>\n" +
                "      <form class=\"form-inline\">\n" +
                "        <a href=\"http://store.steampowered.com/app/11020/TrackMania_Nations_Forever/\"><button class=\"btn btn-outline-info my-2 my-sm-0\" type=\"button\">Download</button></a>\n" +
                "      </form>\n" +
                "    </div>\n" +
                "  </nav>\n" +
                "<br>\n" +
                "<br>\n");

        htmlOutput += nav;
        htmlOutput += new String("<div class=\"row justify-content-center\">\n" +
                "<div class=\"col-8\">\n");

        for (String map : maps) {

            htmlOutput += "<h3 class=\"score " + myHashMap.get(map) + "\">" + map + "</h3>\n";

            htmlOutput += new String(
                    "<table class=\"score " + myHashMap.get(map) + " table table-dark\">\n" +
                            "  <thead>\n" +
                            "    <tr>\n" +
                            "      <th scope=\"col\">#</th>\n" +
                            "      <th scope=\"col\">Player</th>\n" +
                            "      <th scope=\"col\">Time</th>\n" +
                            "    </tr>\n" +
                            "  </thead>\n" +
                            "   <tbody>\n");

            int rank = 1;

            for (int i = 0; i < scores.size(); i++) {

                if (map.equals(scores.get(i).getMap())) {
                    htmlOutput += new String(" <tr>\n" +
                            "      <th scope=\"row\">" + rank + "</th>\n" +
                            "      <td>" + scores.get(i).getPseudo() + "</td>\n" +
                            "      <td>" + scores.get(i).getTime() + "</td>\n" +
                            "    </tr>\n");
                    rank++;
                }
            }

            htmlOutput += new String("</tbody>\n" +
                    "</table>\n");

        }


        htmlOutput += new String("</div>\n" +
                "</div>\n" +
                "</body>\n" +
                "\n</html>");
        System.out.println(htmlOutput);


        return htmlOutput;
    }


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

        ArrayList<File> allFilesCloud = listAllFiles(cloudNationsDirectory);
        ArrayList<File> allFilesProgram = listAllFiles(programNationDirectory);
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
        ArrayList<Score> scores = createScores(allFilesProgram);

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
        deleteFiles(scores);

        System.out.println("--------------------------");
        System.out.println("Remaining scores");
        System.out.println("--------------------------");
        allFilesProgram = listAllFiles(cloudNationsDirectory);
        scores = createScores(allFilesProgram);

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
        scores = createScores(allFilesCloud);

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
        deleteFiles(scores);

        System.out.println("--------------------------");
        System.out.println("Remaining scores");
        System.out.println("--------------------------");
        allFilesCloud = listAllFiles(cloudNationsDirectory);
        scores = createScores(allFilesCloud);

        if (scores.isEmpty()) {
            System.out.println("No available scores. Program will stop.");
            System.exit(-1);
        }

        System.out.println("\n-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_-_\n");

        System.out.println("--------------------------");
        System.out.println("Creating Scoreboard");
        System.out.println("--------------------------");

        sort(scores);
        maps = listMaps(scores);
        categories = listCategories(scores);
        myHashMap = mapMapCategories(scores);


        System.out.println("--------------------------");
        System.out.println("Listing Available scores");
        System.out.println("--------------------------");
        for (int i = 0; i < scores.size(); i++) {
            System.out.println(scores.get(i));
        }

        System.out.println("--------------------------");
        System.out.println("Generating HTML");
        System.out.println("--------------------------");
        htmlOutput = generateHTML(categories, maps, scores, myHashMap);


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
