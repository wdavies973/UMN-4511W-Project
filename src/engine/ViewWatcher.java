package engine;

public interface ViewWatcher {

    default void mouseClicked(int x, int y) {}
    default void mouseRightClicked(int x, int y) {}
    default void mouseMiddleClicked(int x, int y) {}

}
