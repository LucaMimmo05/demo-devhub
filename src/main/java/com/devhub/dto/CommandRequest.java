package com.devhub.dto;



public class CommandRequest {
    public String title;
    public String commandText;
    public String description;


    public CommandRequest(String title, String commandText, String description) {
        this.title = title;
        this.commandText = commandText;
        this.description = description;
    }

    public CommandRequest() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCommandText() {
        return commandText;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
