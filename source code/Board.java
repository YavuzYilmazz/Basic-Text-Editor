
public class Board {
	
	public static void Print()
	{
		System.out.println("+----+----+----+----+----+----+----+----+----+----+----+----++");
		for (int i = 1; i <=20 ; i++) {
			if(i%5==0)
			{
				System.out.println("+                                                            +");
			}
			else
			{
				System.out.println("|                                                            |");
			}
		}
		System.out.println("+----+----+----+----+----+----+----+----+----+----+----+----++");
	}
}
