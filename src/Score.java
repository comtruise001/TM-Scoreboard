import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Score implements Comparable<Score> {

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


}
