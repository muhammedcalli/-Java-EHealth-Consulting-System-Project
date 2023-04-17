package main.java;

import java.time.LocalDate;

/**
 * The type User.
 */
public class User
{

    private final String salt;
    private String firstName;
    private String lastName;
    private String address;
    private int plz;
    private String city;
    private LocalDate dateOfBirth;
    private String insuranceName;
    private boolean privateInsurance;
    private String username;
    private String email;
    private String password;

    /**
     * Instantiates a new User.
     *
     * @param firstName        the first name
     * @param lastName         the last name
     * @param dateOfBirth      the date of birth
     * @param address          the address
     * @param plz              the plz
     * @param city             the city
     * @param insuranceName    the insurance name
     * @param privateInsurance the private insurance
     * @param username         the username
     * @param email            the email
     * @param password         the password
     * @param salt             the salt
     */
    public User(String firstName, String lastName, LocalDate dateOfBirth, String address, int plz, String city,
                String insuranceName, boolean privateInsurance, String username, String email, String password, String salt)
    {
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.plz = plz;
        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.insuranceName = insuranceName;
        this.privateInsurance = privateInsurance;
        this.username = username;
        this.email = email;
        this.password = password;
        this.salt = salt;
    }

    /**
     * Gets first name.
     *
     * @return the first name
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the first name
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * Gets address.
     *
     * @return the address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * Gets plz.
     *
     * @return the plz
     */
    public int getPlz()
    {
        return plz;
    }

    /**
     * Sets plz.
     *
     * @param plz the plz
     */
    public void setPlz(int plz)
    {
        this.plz = plz;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity()
    {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * Gets date of birth.
     *
     * @return the date of birth
     */
    public LocalDate getDateOfBirth()
    {
        return dateOfBirth;
    }

    /**
     * Sets date of birth.
     *
     * @param dateOfBirth the date of birth
     */
    public void setDateOfBirth(LocalDate dateOfBirth)
    {
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Gets insurance name.
     *
     * @return the insurance name
     */
    public String getInsuranceName()
    {
        return insuranceName;
    }

    /**
     * Sets insurance name.
     *
     * @param insuranceName the insurance name
     */
    public void setInsuranceName(String insuranceName)
    {
        this.insuranceName = insuranceName;
    }

    /**
     * Is private insurance boolean.
     *
     * @return the boolean
     */
    public boolean isPrivateInsurance()
    {
        return privateInsurance;
    }

    /**
     * Sets private insurance.
     *
     * @param privateInsurance the private insurance
     */
    public void setPrivateInsurance(boolean privateInsurance)
    {
        this.privateInsurance = privateInsurance;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail()
    {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email)
    {
        this.email = email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * Gets salt.
     *
     * @return the salt
     */
    public String getSalt()
    {
        return salt;
    }

}
