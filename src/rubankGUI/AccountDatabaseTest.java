import org.junit.jupiter.api.Test;
import rubank.AccountDatabase;
import rubank.Checking;
import rubank.Date;
import rubank.Profile;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AccountDatabaseTest {

    @Test
    void close_An_Account_That_Does_Not_Exist() {
        AccountDatabase database = new AccountDatabase();
        assertFalse(database.close(new Checking(new Profile("John", "Winthrop", new Date("01/12/1588")), 5000)));
    }

    @Test
    void close_An_Existing_Account() {
        AccountDatabase database = new AccountDatabase();
        database.open(new Checking(new Profile("John", "Winthrop", new Date("01/12/1588")), 5000));
        assertTrue(database.close(new Checking(new Profile("John", "Winthrop", new Date("01/12/1588")), 5000)));
    }
}