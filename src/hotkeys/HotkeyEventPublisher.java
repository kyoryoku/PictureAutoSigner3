package hotkeys;

//интерфейс издателя
public interface HotkeyEventPublisher {

    public void addListener(KeyListener observer);
    public void removeListener(KeyListener observer);
    public void notifyListener();
}
