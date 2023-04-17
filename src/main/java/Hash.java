package main.java;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

/**
 * The type Hash.
 */
public class Hash
{
    /**
     * Hashes the input using SHA-256.
     *
     * @param input the input
     * @return the string
     */
    public static String hash(String input)
    {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashresult = messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));

            BigInteger number = new BigInteger(1, hashresult);
            String hexHash = number.toString(16);
            return hexHash;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "-1";
    }

    /**
     * Gets random 32 char long salt.
     * https://stackoverflow.com/questions/7111651/how-to-generate-a-secure-random-alphanumeric-string-in-java-efficiently
     * @return the salt
     */
    public static String getSalt()
    {
        String allowed = "!?0123456789abcdefghijklmnopqrstuvwxyz-_ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        SecureRandom secureRandom = new SecureRandom();
        String salt = secureRandom.ints(32, 0, allowed.length()).mapToObj(i -> allowed.charAt(i))
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();

        return salt;
    }

    /**
     * Checks if input+salt hashed equals the already hashed password+salt.
     *
     * @param user_input the user input
     * @param hash       the hash (already hashed password+salt)
     * @param salt       the salt
     * @return the boolean
     */
    public static boolean isCorrect(String user_input, String hash, String salt)
    {
        String inputHash = hash(user_input + salt);
        return inputHash.equals(hash);
    }
}