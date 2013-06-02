package com.robertboloc.eu.holaurv;

import android.app.Application;

import com.robertboloc.eu.holaurv.lib.Evalos;

public class HoLaURV extends Application {

	private Evalos eva;

	public Evalos getEva() {
		return eva;
	}

	public void setEva(Evalos eva) {
		this.eva = eva;
	}
}