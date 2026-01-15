package mudbot;

import backend2.CommandSender;
import io.listener.CodeListener;
import io.listener.TextListener;
import io.listener.StateListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class MudbotBridge implements CodeListener, TextListener, StateListener {

    private boolean playing;
    private CommandSender commandSender;

    public void setCommandSender(CommandSender commandSender) {
        this.commandSender = commandSender;
        startStdinReader();
    }

    private void startStdinReader() {
        Thread stdinThread = new Thread(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line;
            try {
                while ((line = reader.readLine()) != null) {
                    if (commandSender != null) {
                        commandSender.send(line + "\r");
                    }
                }
            } catch (Exception e) {
                System.err.println("stdin reader error: " + e.getMessage());
            }
        }, "MudbotBridge-stdin");
        stdinThread.setDaemon(true);
        stdinThread.start();
    }

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

        // End/close marker
        SEMANTIC_TAGS.put("<00>", "</>");

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
