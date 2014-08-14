package com.dragonphase.kits.util;

public class CommandDescription {
    private String title, command;
    private String[] args;

    public CommandDescription(String title, String command, String... args) {
        setTitle(title);
        setCommand(command);
        setArgs(args);
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }
}
