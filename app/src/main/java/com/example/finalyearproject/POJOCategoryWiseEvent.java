package com.example.finalyearproject;

public class POJOCategoryWiseEvent {
    String id,categoryname,companyname,eventimage,budget,evenrating,eventoffer,eventdescription,companyaddress;

    public POJOCategoryWiseEvent(String id, String categoryname, String companyname, String eventimage, String budget, String evenrating, String eventoffer, String eventdescription, String companyaddress) {
        this.id = id;
        this.categoryname = categoryname;
        this.companyname = companyname;
        this.eventimage = eventimage;
        this.budget = budget;
        this.evenrating = evenrating;
        this.eventoffer = eventoffer;
        this.eventdescription = eventdescription;
        this.companyaddress = companyaddress;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryname() {
        return categoryname;
    }

    public void setCategoryname(String categoryname) {
        this.categoryname = categoryname;
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }

    public String getEventimage() {
        return eventimage;
    }

    public void setEventimage(String eventimage) {
        this.eventimage = eventimage;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getEvenrating() {
        return evenrating;
    }

    public void setEvenrating(String evenrating) {
        this.evenrating = evenrating;
    }

    public String getEventoffer() {
        return eventoffer;
    }

    public void setEventoffer(String eventoffer) {
        this.eventoffer = eventoffer;
    }

    public String getEventdescription() {
        return eventdescription;
    }

    public void setEventdescription(String eventdescription) {
        this.eventdescription = eventdescription;
    }

    public String getCompanyaddress() {
        return companyaddress;
    }

    public void setCompanyaddress(String companyaddress) {
        this.companyaddress = companyaddress;
    }
}
