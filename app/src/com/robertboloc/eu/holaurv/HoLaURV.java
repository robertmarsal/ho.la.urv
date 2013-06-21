package com.robertboloc.eu.holaurv;

import android.app.Application;

import com.googlecode.androidannotations.annotations.EApplication;
import com.robertboloc.eu.holaurv.lib.Evalos;

@EApplication
public class HoLaURV extends Application {

	private Evalos eva;

	public Evalos getEva() {
		return eva;
	}

	public void setEva(Evalos eva) {
		this.eva = eva;
	}
}