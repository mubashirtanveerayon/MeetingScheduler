package translation;

public class TranslateUrl {

    public String hash(String txt) {
        String text = txt, maintext = "";
        for (int i = 0; i < text.length(); i++) {
            maintext += (char) (158 - text.charAt(i));
        }
        return maintext;
    }

}
