package eu.robertboloc.holaurv;

import android.app.Application;

import com.googlecode.androidannotations.annotations.EApplication;

import eu.robertboloc.holaurv.helpers.Evalos;

@EApplication
public class HoLaURV extends Application {

    Evalos eva;

    public Evalos getEva() {
        return eva;
    }

    public void setEva(Evalos eva) {
        this.eva = eva;
    }
}