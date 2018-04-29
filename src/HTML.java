import java.util.ArrayList;
import java.util.HashMap;

public class HTML {

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

}
