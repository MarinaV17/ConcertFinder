package main.dao;

public class DaoUserAdmin {
    private final DataBase dataBase;

    public DaoUserAdmin(DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public boolean isLoginAdmin(String userName, String password) {
        return dataBase.isLoginAdmin(userName, password);
    }

    public boolean isUserPremium(String userName) {
        return dataBase.isUserPremium(userName);
    }
}