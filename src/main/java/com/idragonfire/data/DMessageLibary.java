package com.idragonfire.data;

import java.io.File;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.configuration.file.YamlConfiguration;

public class DMessageLibary {
    // add new messages here # START
    public static final String[] BUY_TOMUCH = new String[] { "buy.tomuch",
	    "{ERR}You can't buy that much at once!" };
    public static final String[] BUY_NOSPACE = new String[] { "buy.nospace",
	    "{ERR} $shop$ doesn't have enough space." };
    public static final String[] SELL_NOSTOCK = new String[] { "buy.nostock",
	    "{ERR} $shop$ doesn't have enough stock." };
    // # END
    public static final DMessageLibary INSTACE = new DMessageLibary();
    public static final String REPLACER = "$";
    private String msgLibPath = "plugins/DynamicMarket/messageLibary.yaml";
    private YamlConfiguration msgLib;

    public String get(String[] key) {
	return get(key, null);
    }

    public String get(String[] key, String[][] replacement) {
	String s = this.msgLib.getString(key[0], key[1]);
	if (replacement != null) {
	    for (int i = 0; i < replacement.length; i++) {
		s = s.replace(DMessageLibary.REPLACER + replacement[i][0]
			+ DMessageLibary.REPLACER, replacement[i][1]);
	    }
	}
	return s;
    }

    private DMessageLibary() {
	init();
    }

    // TODO: register logger or Messager, ...
    private void init() {
	try {
	    File f = new File(this.msgLibPath);
	    if (!f.exists()) {
		System.out.println("create : " + f.getName());
		f.createNewFile();
		this.msgLib = YamlConfiguration.loadConfiguration(f);
		initLibary();
		this.msgLib.save(f);
	    } else {
		this.msgLib = YamlConfiguration.loadConfiguration(f);
		checkLibary(f);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	}

    }

    private void checkLibary(File f) {
	boolean validate = true;
	Class<?> MsgLibClass = this.getClass();
	Field[] fields = MsgLibClass.getDeclaredFields();
	String[] tmpKey = null;
	for (int i = 0; i < fields.length; i++) {
	    if (fields[i].getType() == String[].class) {
		try {
		    tmpKey = (String[]) fields[i].get(null);
		    if (this.msgLib.get(tmpKey[0]) == null) {
			validate = false;
			break;
		    }
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
	if (!validate) {
	    backupAndReinit(f);
	}
    }

    // TODO: register logger or Messager, ...
    private void backupAndReinit(File f) {
	SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
	File newFile = new File(this.msgLibPath + ".bak"
		+ format.format(new Date()));
	System.out.println(this.msgLibPath + " not validate, backup to "
		+ newFile.getName() + " and generate new one.");
	f.renameTo(newFile);
	init();
    }

    private void initLibary() {
	Class<?> MsgLibClass = this.getClass();
	Field[] fields = MsgLibClass.getDeclaredFields();
	String[] tmpKey = null;
	for (int i = 0; i < fields.length; i++) {
	    if (fields[i].getType() == String[].class) {
		try {
		    tmpKey = (String[]) fields[i].get(null);
		    this.msgLib.set(tmpKey[0], tmpKey[1]);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	}
    }

    public static void main(String[] args) {
    }
}
