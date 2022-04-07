package util;

import org.mindrot.jbcrypt.BCrypt;

public class Crypt {
    public static String hash(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean check(String password, String crypt){
        return BCrypt.checkpw(password, crypt);
    }
}
