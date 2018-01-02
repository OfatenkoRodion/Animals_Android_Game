package DataBase;


public class AnimalsNode
{
    private LazyImmutableInt id;
    private String question;
    private String name;
    private int idPositive;
    private int idNegative;

    public AnimalsNode()
    {
        id = new LazyImmutableInt();
    }

    public int getId()
    {
        return id.getValue();
    }

    public String getQuestion()
    {
        return question;
    }

    public AnimalsNode setQuestion(String question)
    {
        this.question = question;
        return this;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getIdPositive()
    {
        return idPositive;
    }

    public AnimalsNode setIdPositive(int idPositive)
    {
        this.idPositive = idPositive;
        return this;
    }

    public int getIdNegative() {
        return idNegative;
    }

    public AnimalsNode setIdNegative(int idNegative)
    {
        this.idNegative = idNegative;
        return this;
    }
}
