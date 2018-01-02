package DataBase;

class LazyImmutableInt
{
    private int value=-1;

    public void setValue(int value)
    {
        if (this.value != -1)
        {
            return;
        }
        this.value = value;
    }
    public int getValue() {return value;}
}