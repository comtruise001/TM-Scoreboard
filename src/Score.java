import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Math.abs;

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
        } else {

            Character firstCharThis = this.getMap().charAt(0);
            Character firstCharS2 = s2.getMap().charAt(0);
            Character secondCharThis = this.getMap().charAt(1);
            Character secondCharS2 = s2.getMap().charAt(1);
            Character thirdCharThis = this.getMap().charAt(2);
            Character thirdCharS2 = s2.getMap().charAt(2);

            if ((int) firstCharThis < (int) firstCharS2) {
                return -1;
            } else if ((int) firstCharThis > (int) firstCharS2) {
                return 1;
            }
            if ((int) secondCharThis < (int) secondCharS2) {
                return -1;
            } else if ((int) secondCharThis > (int) secondCharS2) {
                return 1;
            }
            if ((int) thirdCharThis < (int) thirdCharS2) {
                return -1;
            } else if ((int) thirdCharThis > (int) thirdCharS2) {
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
                System.out.println("Found --> " + aFiles.get(i).getPath() + " | Niveau : " + matcher.group(1) + " | Pseudo : " + matcher.group(2) + " | Temps : " + matcher.group(3));
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

    public static String timeDifference(Score s1, Score s2) {

        String s1Time = s1.getTime();
        String s2Time = s2.getTime();

        Integer s1Minutes = Integer.parseInt(s1Time.substring(0, 2));
        Integer s2Minutes = Integer.parseInt(s2Time.substring(0, 2));
        Integer s1Seconds = Integer.parseInt(s1Time.substring(3, 5));
        Integer s2Seconds = Integer.parseInt(s2Time.substring(3, 5));

        Integer s1Hundredth = Integer.parseInt(s1Time.substring(7, 9));
        Integer s2Hundredth = Integer.parseInt(s2Time.substring(7, 9));

        Integer s1Milliseconds = s1Hundredth * 10 + s1Seconds * 1000 + s1Minutes * 60000;
        Integer s2Milliseconds = s2Hundredth * 10 + s2Seconds * 1000 + s2Minutes * 60000;

        Integer difference = abs(s1Milliseconds - s2Milliseconds);

        Integer minutes = difference / 60000;
        Integer seconds = (difference % 60000) / 1000;
        Integer milliseconds = (difference % 60000) % 1000;


        String strMinutes = "" + minutes;
        String strSeconds = "" + seconds;
        String strMilliseconds = "" + milliseconds;

        if (minutes < 10) {
            strMinutes = "0" + strMinutes;
        }

        if (seconds < 10) {
            strSeconds = "0" + strSeconds;
        }

        if (milliseconds < 100) {
            strMilliseconds = "0" + strMilliseconds;
        }

        String strHundredth = "" + strMilliseconds.toString().substring(0, 2);

        return "+ " + strMinutes + "'" + strSeconds + "''" + strHundredth;
    }


    public static ArrayList<Score> nadeoScores() {

        ArrayList<Score> scores = new ArrayList<>();

        Score s = new Score("A01-Race", "Nadeo", "00'24''54", null, "White");
        scores.add(s);
        s = new Score("A02-Race", "Nadeo", "00'16''25", null, "White");
        scores.add(s);
        s = new Score("A03-Race", "Nadeo", "00'18''75", null, "White");
        scores.add(s);
        s = new Score("A04-Acrobatic", "Nadeo", "00'05''95", null, "White");
        scores.add(s);
        s = new Score("A05-Race", "Nadeo", "00'16''91", null, "White");
        scores.add(s);
        s = new Score("A06-Obstacle", "Nadeo", "00'28''58", null, "White");
        scores.add(s);
        s = new Score("A07-Race", "Nadeo", "00'29''14", null, "White");
        scores.add(s);
        s = new Score("A08-Endurance", "Nadeo", "01'04''48", null, "White");
        scores.add(s);
        s = new Score("A09-Race", "Nadeo", "00'25''98", null, "White");
        scores.add(s);
        s = new Score("A10-Acrobatic", "Nadeo", "00'09''64", null, "White");
        scores.add(s);
        s = new Score("A11-Race", "Nadeo", "00'19''32", null, "White");
        scores.add(s);
        s = new Score("A12-Speed", "Nadeo", "00'19''09", null, "White");
        scores.add(s);
        s = new Score("A13-Race", "Nadeo", "00'31''70", null, "White");
        scores.add(s);
        s = new Score("A14-Race", "Nadeo", "00'22''17", null, "White");
        scores.add(s);
        s = new Score("A15-Speed", "Nadeo", "00'25''33", null, "White");
        scores.add(s);
        s = new Score("B01-Race", "Nadeo", "00'26''20", null, "Green");
        scores.add(s);
        s = new Score("B02-Race", "Nadeo", "00'27''41", null, "Green");
        scores.add(s);
        s = new Score("B03-Race", "Nadeo", "00'27''11", null, "Green");
        scores.add(s);
        s = new Score("B04-Acrobatic", "Nadeo", "00'13''02", null, "Green");
        scores.add(s);
        s = new Score("B05-Race", "Nadeo", "00'26''28", null, "Green");
        scores.add(s);
        s = new Score("B06-Obstacle", "Nadeo", "00'27''46", null, "Green");
        scores.add(s);
        s = new Score("B07-Race", "Nadeo", "00'30''36", null, "Green");
        scores.add(s);
        s = new Score("B08-Endurance", "Nadeo", "01'42''50", null, "Green");
        scores.add(s);
        s = new Score("B09-Acrobatic", "Nadeo", "00'13''99", null, "Green");
        scores.add(s);
        s = new Score("B10-Speed", "Nadeo", "00'37''82", null, "Green");
        scores.add(s);
        s = new Score("B11-Race", "Nadeo", "00'31''44", null, "Green");
        scores.add(s);
        s = new Score("B12-Race", "Nadeo", "00'45''50", null, "Green");
        scores.add(s);
        s = new Score("B13-Obstacle", "Nadeo", "00'25''65", null, "Green");
        scores.add(s);
        s = new Score("B14-Speed", "Nadeo", "00'32''98", null, "Green");
        scores.add(s);
        s = new Score("B15-Race", "Nadeo", "00'41''19", null, "Green");
        scores.add(s);
        s = new Score("C01-Race", "Nadeo", "00'29''58", null, "Blue");
        scores.add(s);
        s = new Score("C02-Race", "Nadeo", "00'42''47", null, "Blue");
        scores.add(s);
        s = new Score("C03-Acrobatic", "Nadeo", "00'13''90", null, "Blue");
        scores.add(s);
        s = new Score("C04-Race", "Nadeo", "00'39''80", null, "Blue");
        scores.add(s);
        s = new Score("C05-Endurance", "Nadeo", "01'56''39", null, "Blue");
        scores.add(s);
        s = new Score("C06-Speed", "Nadeo", "00'55''59", null, "Blue");
        scores.add(s);
        s = new Score("C07-Race", "Nadeo", "00'40''34", null, "Blue");
        scores.add(s);
        s = new Score("C08-Obstacle", "Nadeo", "00'27''72", null, "Blue");
        scores.add(s);
        s = new Score("C09-Race", "Nadeo", "00'50''12", null, "Blue");
        scores.add(s);
        s = new Score("C10-Acrobatic", "Nadeo", "00'15''03", null, "Blue");
        scores.add(s);
        s = new Score("C11-Race", "Nadeo", "00'50''04", null, "Blue");
        scores.add(s);
        s = new Score("C12-Obstacle", "Nadeo", "00'34''68", null, "Blue");
        scores.add(s);
        s = new Score("C13-Race", "Nadeo", "00'44''87", null, "Blue");
        scores.add(s);
        s = new Score("C14-Endurance", "Nadeo", "01'56''91", null, "Blue");
        scores.add(s);
        s = new Score("C15-Speed", "Nadeo", "00'50''34", null, "Blue");
        scores.add(s);
        s = new Score("D01-Endurance", "Nadeo", "02'33''26", null, "Red");
        scores.add(s);
        s = new Score("D02-Race", "Nadeo", "00'52''63", null, "Red");
        scores.add(s);
        s = new Score("D03-Acrobatic", "Nadeo", "00'15''94", null, "Red");
        scores.add(s);
        s = new Score("D04-Race", "Nadeo", "00'52''86", null, "Red");
        scores.add(s);
        s = new Score("D05-Race", "Nadeo", "01'11''43", null, "Red");
        scores.add(s);
        s = new Score("D06-Obstacle", "Nadeo", "01'10''53", null, "Red");
        scores.add(s);
        s = new Score("D07-Race", "Nadeo", "01'00''37", null, "Red");
        scores.add(s);
        s = new Score("D08-Speed", "Nadeo", "00'52''44", null, "Red");
        scores.add(s);
        s = new Score("D09-Obstacle", "Nadeo", "00'46''47", null, "Red");
        scores.add(s);
        s = new Score("D10-Race", "Nadeo", "00'57''62", null, "Red");
        scores.add(s);
        s = new Score("D11-Acrobatic", "Nadeo", "00'14''65", null, "Red");
        scores.add(s);
        s = new Score("D12-Speed", "Nadeo", "00'42''79", null, "Red");
        scores.add(s);
        s = new Score("D13-Race", "Nadeo", "01'11''21", null, "Red");
        scores.add(s);
        s = new Score("D14-Endurance", "Nadeo", "02'57''09", null, "Red");
        scores.add(s);
        s = new Score("D15-Endurance", "Nadeo", "07'50''66", null, "Red");
        scores.add(s);
        s = new Score("E01-Obstacle", "Nadeo", "00'45''56", null, "Black");
        scores.add(s);
        s = new Score("E02-Endurance", "Nadeo", "04'37''48", null, "Black");
        scores.add(s);
        s = new Score("E03-Endurance", "Nadeo", "05'29''78", null, "Black");
        scores.add(s);
        s = new Score("E04-Obstacle", "Nadeo", "02'01''06", null, "Black");
        scores.add(s);

        return scores;
    }


}
