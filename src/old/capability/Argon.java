package tk.dczippl.lightestlamp.util.capability;

public class Argon implements IArgon
{
    private float argon = 250.0F;

    public void consume(float points)
    {
        this.argon -= points;

        if (this.argon < 0.0F) this.argon = 0.0F;
    }

    public void fill(float points)
    {
        this.argon += points;
    }

    public void set(float points)
    {
        this.argon = points;
    }

    public float getArgon()
    {
        return this.argon;
    }
}