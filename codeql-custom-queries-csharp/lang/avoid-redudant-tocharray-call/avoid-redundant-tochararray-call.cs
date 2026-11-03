public class AvoidRedundantToCharArrayCall
{
    public static void Main()
    {
        foreach (var c in "Hello".ToCharArray())
        {
            System.Console.WriteLine(c);
        }
    }
}