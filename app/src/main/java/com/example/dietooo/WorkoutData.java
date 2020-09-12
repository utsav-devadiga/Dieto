package com.example.dietooo;

public class WorkoutData {

    private String image;
    private String longdesc;
    private String primarymd;
    private String secondarymd;
    private String shortdesc;
    private int workrating;
    private String worktitle;
    private String equipments;


    public WorkoutData() {
    }

    public WorkoutData(String image, String longdesc, String primarymd, String secondarymd, String shortdesc, int workrating, String worktitle, String equipments) {
        this.image = image;
        this.longdesc = longdesc;
        this.primarymd = primarymd;
        this.secondarymd = secondarymd;
        this.shortdesc = shortdesc;
        this.workrating = workrating;
        this.worktitle = worktitle;
        this.equipments = equipments;
    }

    public String getImage() {
        return image;
    }

    public String getLongdesc() {
        return longdesc;
    }

    public String getPrimarymd() {
        return primarymd;
    }

    public String getSecondarymd() {
        return secondarymd;
    }

    public String getShortdesc() {
        return shortdesc;
    }

    public int getWorkrating() {
        return workrating;
    }

    public String getWorktitle() {
        return worktitle;
    }

    public String getEquipments() {
        return equipments;
    }
}


