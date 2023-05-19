package main.domain.servicios;

import lombok.Builder;
import lombok.Data;
import main.dao.DaoUserAdmin;

@Data
@Builder
public class ServiciosUserAdmin {
    private DaoUserAdmin daoUserAdmin;

    public ServiciosUserAdmin(DaoUserAdmin daoUserAdmin) {
        this.daoUserAdmin = daoUserAdmin;
    }

    public boolean isLoginAdmin(String userName, String password) {
        return daoUserAdmin.isLoginAdmin(userName, password);
    }

    public boolean isUserPremium(String userName) {
        return daoUserAdmin.isUserPremium(userName);
    }
}