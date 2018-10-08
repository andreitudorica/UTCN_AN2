
/**
 * Simulates an automobile fuel tank
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class FuelTank
{
    private float capacity; // capacity in litres for this fuel tank object
    private float level; // fuel level between 0 and 1
    /**
     * minimum capacity: 4 litres
     */
    public static final float CAPACITY_MIN = 4f;
    /**
     * maximum capacity for a tank: 200 litres
     */
    public static final float CAPACITY_MAX = 200f;
    /**
     * Min fuel level: 0
     */
    public static final float LEVEL_MIN = 0f;
    /**
     * Max fuel level: 1
     */
    public static final float LEVEL_MAX = 1f;
    /**
     * Default capacity
     */
    public static final float DEFAULT_CAPACITY = 60f;
    /**
     * Create a 60 litres fuel tank, filled
     */
    public FuelTank()
    {
        this(DEFAULT_CAPACITY, 1f);
    }

    /**
     * Constructor for fuel tanks
     * @param cap capacity in litres - strictly positive, >= 4
     * @param level fill level between 0 and 1
     */
    public FuelTank(float cap, float level)
    {
        capacity = cap;
        this.level = level;
        if (cap < CAPACITY_MIN || cap > CAPACITY_MAX)
        {
            System.out.println("Invalid capacity  " + cap +
                    " for fuel tank. Adjusted to 60");
            cap = DEFAULT_CAPACITY;
        }
        if (level < LEVEL_MIN || level > LEVEL_MAX)
        {
            System.out.println("Invalid fuel level: " + level +
                    ". Adjusted to 0, i.e. empty");
            this.level = 0f;
        }
    }
    /**
     * Attempts to fill the fuel tank with the given amount of fuel
     * @param amount amount of fuel in litres
     * @return how much did not go into the tank
     */
    public float fill(float amount)
    {
        // TODO
        return 0;
    }
    /**
     * Attempts to release the requested amount from the tank
     * @param amount amount to release
     * @return true if successfully released the requested amount, false otherwise
     */
    public boolean releaseFuel(float amount)
    {
        // TODO
        return false;
    }
    /**
     * @return capacity of this tank, in litres
     */
    public float getCapacity()
    {
        return capacity;
    }
    /**
     * @return fuel level, [0..1]
     */
    public float getLevel()
    {
        return level;
    }
}
