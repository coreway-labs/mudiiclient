package mudbot;

import io.listener.CodeListener;
import io.listener.TextListener;
import io.listener.StateListener;

public class MudbotBridge implements CodeListener, TextListener, StateListener {

    private boolean playing;

    @Override
    public void onCode(String code) {
        if (playing) System.out.print(code);
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
