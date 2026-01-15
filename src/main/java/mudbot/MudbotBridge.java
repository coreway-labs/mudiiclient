package mudbot;

import io.listener.CodeListener;
import io.listener.TextListener;
import io.listener.StateListener;

import java.util.HashMap;
import java.util.Map;

public class MudbotBridge implements CodeListener, TextListener, StateListener {

    private boolean playing;

    private static final Map<String, String> SEMANTIC_TAGS = new HashMap<>();

    static {
        // Room information
        SEMANTIC_TAGS.put("<0201>", "<ROOM_NAME>");
        SEMANTIC_TAGS.put("<0202>", "<ROOM_DESC>");
        SEMANTIC_TAGS.put("<2000>", "<ROOM_INDOOR>");
        SEMANTIC_TAGS.put("<2013>", "<ROOM_OUTDOOR>");

        // Weather
        SEMANTIC_TAGS.put("<030001>", "<WEATHER_PREFIX>");
        SEMANTIC_TAGS.put("<1402>", "<WEATHER>");

        // Items and objects
        SEMANTIC_TAGS.put("<030100>", "<ITEM>");
        SEMANTIC_TAGS.put("<030101>", "<INTERACT>");
        SEMANTIC_TAGS.put("<030201>", "<TREASURE>");
        SEMANTIC_TAGS.put("<030300>", "<TREASURE>");
        SEMANTIC_TAGS.put("<030301>", "<TREASURE>");

        // Mobiles and players
        SEMANTIC_TAGS.put("<040001>", "<MOBILE>");
        SEMANTIC_TAGS.put("<040003>", "<MOBILE>");
        SEMANTIC_TAGS.put("<050001>", "<PLAYER>");
        SEMANTIC_TAGS.put("<050006>", "<PLAYER>");

        // Input/output
        SEMANTIC_TAGS.put("<01>", "<PROMPT>");
        SEMANTIC_TAGS.put("<0102>", "<INPUT>");

        // System messages
        SEMANTIC_TAGS.put("<1203>", "<SYSTEM_HEADER>");
        SEMANTIC_TAGS.put("<1207>", "<SYSTEM_MSG>");
    }

    private String toSemanticTag(String code) {
        // Try exact match first
        String tag = SEMANTIC_TAGS.get(code);
        if (tag != null) {
            return tag;
        }

        // Try prefix matching by stripping 2 chars at a time from the end
        String prefix = code;
        while (prefix.length() > 3) {
            prefix = prefix.substring(0, prefix.length() - 2) + ">";
            tag = SEMANTIC_TAGS.get(prefix);
            if (tag != null) {
                return tag;
            }
        }

        // No semantic mapping found, return original code
        return code;
    }

    @Override
    public void onCode(String code) {
        if (playing) System.out.print(toSemanticTag(code));
    }

    @Override
    public void onText(String text) {
        if (playing) System.out.print(text);
    }

    @Override
    public void onState(String key, Object value) {
        if ("PLAYING".equals(key)) {
            playing = Boolean.TRUE.equals(value);
        }
    }
}
