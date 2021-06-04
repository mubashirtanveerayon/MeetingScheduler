package translation;

public class TranslateUrl {
    
    public String translate(String txt, boolean isEncrypting) {
        String text = txt, maintext = "";
        if (isEncrypting) {
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) >= 'A' && text.charAt(i) <= 'z') {
                    maintext += (char) (122 - text.charAt(i) + 65);
                } else {
                    maintext += text.charAt(i);
                }
            }
            maintext=maintext.replaceAll("/", "~");
            maintext=maintext.replaceAll(":", ";");
        }else{
            for (int i = 0; i < text.length(); i++) {
                if (text.charAt(i) >= 'A' && text.charAt(i) <= 'z') {
                    maintext += (char) (122 - text.charAt(i) + 65);
                } else {
                    maintext += text.charAt(i);
                }
            }
            maintext=maintext.replaceAll("~", "/");
            maintext=maintext.replaceAll(";",":");
        }
        return maintext;
    }

}
