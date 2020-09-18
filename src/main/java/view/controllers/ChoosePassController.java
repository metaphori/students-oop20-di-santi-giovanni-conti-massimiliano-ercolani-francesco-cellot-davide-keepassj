package view.controllers;

import controller.DBDataSaver;

public class ChoosePassController {
    DBDataSaver data;
    
    public void takeData(DBDataSaver data) {
        this.data = data;
    }
}
