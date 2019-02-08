package app.controller;

import app.delegate.SidebarListener;
import javafx.scene.control.ListView;

/**
 * Responsible for handling all delegated events in the sidebar view,
 * and managing function list definitions
 */
public class SidebarController {

    private SidebarListener sidebarListener;
    private ListView functionListView;

    public SidebarController(SidebarListener sidebarListener, ListView functionListView) {
        this.sidebarListener = sidebarListener;
        this.functionListView = functionListView;
    }
}
