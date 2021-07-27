package hotkeys;

import javafx.application.Platform;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Hotkey implements HotkeyEventPublisher, NativeKeyListener {

    private static Hotkey instance;
    public static Hotkey getInstance(){
        if (instance == null){
            instance = new Hotkey();
        }
        return instance;
    }

    public void enableHotkeys() throws NativeHookException {
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.WARNING);
        logger.setUseParentHandlers(false);

        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(this);
    }

    public void disableHotkeys() throws NativeHookException {
        GlobalScreen.unregisterNativeHook();
        GlobalScreen.removeNativeKeyListener(this);
    }

    ArrayList<KeyListener> subscribers = new ArrayList<>();

    @Override
    public void addListener(KeyListener listener) {
        this.subscribers.add(listener);
    }

    public void addListeners(KeyListener... listeners) {
        this.subscribers.addAll(Arrays.asList(listeners));
    }

    @Override
    public void removeListener(KeyListener listener) {
        this.subscribers.remove(listener);
    }

    @Override
    public void notifyListener() {
        for(KeyListener listener : this.subscribers){
            listener.handleHotkeyEvent(pressedCombination);
        }
    }

    private KeyCombination pressedCombination;
    private boolean shift = false;
    private boolean ctrl = false;
    private boolean alt = false;

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        Platform.runLater(()->{
            if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_SHIFT){
                shift = true;
                return;
            }

            //Если нажали контрол, ставим флаг контрол и выходим
            if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_CONTROL){
                ctrl = true;
                return;
            }

            //Если нажали альт, ставим флаш альт и выходим
            if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ALT){
                alt = true;
                return;
            }

            pressedCombination = new KeyCombination(nativeKeyEvent.getRawCode(), shift, ctrl, alt);
            notifyListener();
        });
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {
        Platform.runLater(()->{

            //Если нажали шифт, ставим флаг шифт и выходим
            if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_SHIFT){
                shift = false;
                return;
            }

            //Если нажали контрол, ставим флаг контрол и выходим
            if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_CONTROL){
                ctrl = false;
                return;
            }

            //Если нажали альт, ставим флаш альт и выходим
            if (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_ALT){
                alt = false;
                return;
            }
        });

    }
}
