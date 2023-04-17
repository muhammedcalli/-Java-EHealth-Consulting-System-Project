package main.java;


/**
 * The type Doctor.
 */
public class Doctor
{
    private final int id;
    private final String name;
    private final String address;
    private final int plz;
    private final int specializationId;


    /**
     * Instantiates a new Doctor.
     *
     * @param id               the id
     * @param name             the name
     * @param address          the address
     * @param plz              the plz
     * @param specializationId the specialization id
     */
    public Doctor(int id, String name, String address, int plz, int specializationId)
    {
        this.id = id;
        this.name = name;
        this.address = address;
        this.plz = plz;
        this.specializationId = specializationId;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId()
    {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
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
     * Gets specialization id.
     *
     * @return the specialization id
     */
    public int getSpecializationId()
    {
        return specializationId;
    }

    /**
     * Get plz int.
     *
     * @return the int
     */
    public int getPlz()
    {
        return plz;
    }

    @Override
    public String toString()
    {
        return DatabaseConnector.getInstance().getDoctorType(specializationId) + ": " + name;
    }
}
