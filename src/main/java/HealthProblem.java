package main.java;

/**
 * The type Health problem.
 */
public class HealthProblem
{
    private int id;
    private final String name;
    private final String doctorType;
    private final int specialization;

    /**
     * Instantiates a new Health problem.
     *
     * @param id             the id
     * @param name           the name
     * @param doctorType     the doctor type
     * @param specialization the specialization
     */
    public HealthProblem(int id, String name, String doctorType, int specialization)
    {
        this.id = id;
        this.name = name;
        this.doctorType = doctorType;
        this.specialization = specialization;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId()
    {
        return specialization;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id)
    {
        this.id = id;
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
     * Gets doctor type.
     *
     * @return the doctor type
     */
    public String getDoctorType()
    {
        return doctorType;
    }

    @Override
    public String toString()
    {
        return name;
    }
}