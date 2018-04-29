import java.io.File;
import java.util.ArrayList;

public class ManageFiles {

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


    public static void deleteFiles(ArrayList<Score> scores) {

        Score bestScore;

        for (int i = 0; i < scores.size(); i++) {

            System.out.println("CHOSEN : " + scores.get(i));

            for (int j = 0; j < scores.size(); j++) {
                System.out.println("CURRENT : " + scores.get(j));
                bestScore = Score.bestScoreForUserOnMap(scores.get(i), scores.get(j));
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

}
