import static org.junit.Assert.*;

import org.junit.Test;


public class LinkedStackTest
{
	public static LinkedStack<Integer> l1;
	@Test
	public void test()
	{
		l1 = new LinkedStack<Integer>();
		InfixToPostfix converter = new InfixToPostfix();
		
		System.out.println("Empty?: " + l1.isEmpty());
		System.out.println("size: " + l1.size());
		for (int i = 0; i < 10; i++)
		{
			System.out.println("Pushing: " + i);
			l1.push(i);
		}
		System.out.println("Here is the stack: \n" + l1.toString());
		for (int i = 0; i < 5; i++)
		{
			System.out.println("Popping: " + l1.pop());
		}
		System.out.println("Empty?: " + l1.isEmpty());
		System.out.println("size: " + l1.size());
		System.out.println("Peek: " + l1.peek());
		for (int i = 0; i < 5; i++)
		{
			System.out.println("Popping: " + l1.pop());
		}
		System.out.println("Empty?: " + l1.isEmpty());
		System.out.println("size: " + l1.size());
		
		String infix1 = "( 6 + 20 )* 5 - 12 / 4";
		String infix2 = "7 - 3 * 2";
		assertEquals("6 20 + 5 * 12 4 / -",converter.convert(infix1));
		assertEquals("7 3 2 * -",converter.convert(infix2));
	}
}
