// Cas 1 : struct simple — flagué
public struct UserProfile  // $ Alert
{
    public string Name;
    public int Age;
    public string Email;
}

// Cas 2 : struct avec plusieurs champs — flagué
public struct OrderItem  // $ Alert
{
    public int ProductId;
    public int Quantity;
    public decimal UnitPrice;

    public decimal Total() => Quantity * UnitPrice;
}

// Cas 3 : readonly struct — flagué
public readonly struct Point
{
    public double X { get; }
    public double Y { get; }
    public Point(double x, double y) { X = x; Y = y; }
}

// Cas 4 : class — non flagué
public class Customer
{
    public string Name { get; set; }
    public string Email { get; set; }
}