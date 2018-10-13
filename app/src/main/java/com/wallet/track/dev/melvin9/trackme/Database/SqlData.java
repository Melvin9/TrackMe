package com.wallet.track.dev.melvin9.trackme.Database;

public class SqlData {
    public static final String TABLE_NAME = "track";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_INCOME = "income";
    public static final String COLUMN_EXPENSE = "expense";
    public static final String COLUMN_BALANCE = "balance";
    public static final String COLUMN_BANK = "bank";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_FOOD = "food";
    public static final String COLUMN_BILLS = "bills";
    public static final String COLUMN_TRANSPORTATION = "transportation";
    public static final String COLUMN_HOME = "home";
    public static final String COLUMN_ENTERTAINMENT = "entertainment";
    public static final String COLUMN_SHOPPING = "shopping";
    public static final String COLUMN_CLOTH = "cloth";
    public static final String COLUMN_HEALTH = "health";
    public static final String COLUMN_GIFT = "gift";
    public static final String COLUMN_EDUCATION = "education";
    public static final String COLUMN_OTHERS = "others";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_LASTTIME = "banktime";

    private int id;
    private double income;
    private double expense;
    private double balance;
    private double food;
    private double bill;
    private double transportation;
    private double home;
    private double entertainment;
    private double shopping;
    private double cloth;
    private double health;
    private double gift;
    private double education;
    private double others;
    private String bank;
    private String address;
    private String timestamp;
    private String lasttime;



    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY DEFAULT 1,"
                    + COLUMN_INCOME + " INTEGER DEFAULT 0,"
                    + COLUMN_EXPENSE + " INTEGER DEFAULT 0,"
                    + COLUMN_BALANCE + " INTEGER DEFAULT 0,"
                    + COLUMN_FOOD + " INTEGER DEFAULT 0,"
                    + COLUMN_BILLS + " INTEGER DEFAULT 0,"
                    + COLUMN_TRANSPORTATION + " INTEGER DEFAULT 0,"
                    + COLUMN_HOME + " INTEGER DEFAULT 0,"
                    + COLUMN_ENTERTAINMENT + " INTEGER DEFAULT 0,"
                    + COLUMN_SHOPPING + " INTEGER DEFAULT 0,"
                    + COLUMN_CLOTH + " INTEGER DEFAULT 0,"
                    + COLUMN_HEALTH + " INTEGER DEFAULT 0,"
                    + COLUMN_GIFT + " INTEGER DEFAULT 0,"
                    + COLUMN_EDUCATION + " INTEGER DEFAULT 0,"
                    + COLUMN_OTHERS + " INTEGER DEFAULT 0,"
                    + COLUMN_BANK + " TEXT,"
                    + COLUMN_ADDRESS + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT 0,"
                    + COLUMN_LASTTIME + " DATETIME DEFAULT 0"+ ")";
    public SqlData(int id,double income, double expense, double balance, double food, double bill, double transportation, double home, double entertainment, double shopping, double cloth, double health, double gift, double education, double others, String bank, String address, String timestamp,String lasttime) {
        this.id=id;
        this.income = income;
        this.expense = expense;
        this.balance = balance;
        this.food = food;
        this.bill = bill;
        this.transportation = transportation;
        this.home = home;
        this.entertainment = entertainment;
        this.shopping = shopping;
        this.cloth = cloth;
        this.health = health;
        this.gift = gift;
        this.education = education;
        this.others = others;
        this.bank = bank;
        this.address = address;
        this.timestamp = timestamp;
        this.lasttime=lasttime;
    }
    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getFood() {
        return food;
    }

    public void setFood(double food) {
        this.food = food;
    }

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public double getTransportation() {
        return transportation;
    }

    public void setTransportation(double transportation) {
        this.transportation = transportation;
    }

    public double getHome() {
        return home;
    }

    public void setHome(double home) {
        this.home = home;
    }

    public double getEntertainment() {
        return entertainment;
    }

    public void setEntertainment(double entertainment) {
        this.entertainment = entertainment;
    }

    public double getShopping() {
        return shopping;
    }

    public void setShopping(double shopping) {
        this.shopping = shopping;
    }

    public double getCloth() {
        return cloth;
    }

    public void setCloth(double cloth) {
        this.cloth = cloth;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getGift() {
        return gift;
    }

    public void setGift(double gift) {
        this.gift = gift;
    }

    public double getEducation() {
        return education;
    }

    public void setEducation(double education) {
        this.education = education;
    }

    public double getOthers() {
        return others;
    }

    public void setOthers(double others) {
        this.others = others;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
