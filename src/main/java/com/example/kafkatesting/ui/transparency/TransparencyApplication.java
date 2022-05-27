package com.example.kafkatesting.ui.transparency;


import com.example.kafkatesting.ui.transparency.win10.Win10TransparencyApplier;

/**
 * @author Brady
 * @since 6/29/2017 7:57 PM
 */
public final class TransparencyApplication {

    public static final TransparencyApplier systemDefault = new Win10TransparencyApplier();

    private TransparencyApplication() {}

}
