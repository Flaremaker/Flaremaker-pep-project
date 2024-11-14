package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    //Register for the account service
    public Account registerAccount(Account account) {
        if (account.getUsername().isBlank() || account.getUsername().equals(null) || account.getPassword().length() < 5
                || accountDAO.getAccountByUsername(account.getUsername())) {
            return null;
        } else {
            return accountDAO.accountRegisterInsert(account);
        }
    }

    //Login for the account service
    public Account loginAccount(Account account) {
        return accountDAO.accountLogin(account);
    }

}
