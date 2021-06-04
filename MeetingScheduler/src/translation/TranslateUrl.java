package translation;

public class TranslateUrl {

    public String translate(String txt, boolean isEncrypting) {
        String text = txt, maintext = "";
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) >= 'A' && text.charAt(i) <= 'z') {
                maintext += (char) (122 - text.charAt(i) + 65);
            } else {
                maintext += text.charAt(i);
            }
        }
        return isEncrypting?maintext.replaceAll("://", ";~"):maintext.replaceAll(";~", "://");
    }

}
