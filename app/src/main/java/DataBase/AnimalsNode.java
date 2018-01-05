package DataBase;


public class AnimalsNode
{
    private LazyImmutableInt id;
    private String question= "";
    private String name= "";
    private int idPositive=-1;
    private int idNegative=-1;

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

    public AnimalsNode setName(String name)
    {
        this.name = name;
        return this;
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

    public AnimalsNode setId(int id)
    {
        this.id.setValue(id);
        return this;
    }
}
