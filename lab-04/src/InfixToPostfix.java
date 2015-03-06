
public class InfixToPostfix
{
	public static LinkedStack<String> stack;
	public String stackTrace[];
	/**
	 * Converts an infix expression into postfix
	 * @param infix: the infix expression to be converted
	 * @return String: the postfix converted string
	 */
	public String convert(String infix)
	{
		String postfix = "";
		stack = new LinkedStack<String>();
		int sOpCount = 0;
		stackTrace = new String[30];
		
		
		stack.push("(");
		infix+=")";
		int index = 0;
		
		while(stack.size() > 0)
		{
			//If the current token is a number, append it to the postfix string.
			if (isNumeric(infix.charAt(index)))
			{
				postfix += ""+infix.charAt(index);
			}
			//If the current token is a left parenthesis, push it on the stack.
			if (infix.charAt(index) == '(')
			{
				stack.push(""+infix.charAt(index));
				stackTrace[sOpCount] = stack.toString();
				sOpCount++;
			}
			//If the current token is an operator,
			if (isOperator(""+infix.charAt(index)))
			{
				//Pop operators (if there are any) at the top of the stack while they have equal or higher precedence than the current token, and append the popped operators to postfix.
				char currentOp = infix.charAt(index);
				while(precedence(stack.peek()) >= precedence(""+currentOp))
				{
					postfix += stack.pop();
					stackTrace[sOpCount] = stack.toString();
					sOpCount++;
				}
				//Push the current token on the stack.
				stack.push(currentOp + "");
				stackTrace[sOpCount] = stack.toString();
				sOpCount++;
			}
			//If the current token in infix is a right parenthesis
			if (infix.charAt(index) == ')')
			{
				//Pop operators from the top of the stack and append them to postfix until a left parenthesis is at the top of the stack.
				while (stack.peek().compareTo("(") != 0)
				{
					postfix += stack.pop();
					stackTrace[sOpCount] = stack.toString();
					sOpCount++;
				}
				//Pop (and discard) the left parenthesis from the stack.
				stack.pop();
				stackTrace[sOpCount] = stack.toString();
				sOpCount++;
			}
			index++;
		}
		postfix = addSpaces(postfix, infix);
		
		return postfix;
	}
	
	//returns an int representing the precedence of an operator
	//the larger the int, the higher the precedence
	private static int precedence(String operator)
	{
		String[] operators = {"+", "-", "*", "/", "%", "^"};
		for (int i = 0; i < 6; i++)
		{
			if (operator.compareTo(operators[i]) == 0)
				return i;
		}
		return -1;
	}
	
	//checks whether a string is an operator
	private static boolean isOperator(String s)
	{
		if (s.compareTo("-") == 0 || s.compareTo("+") == 0 || s.compareTo("/") == 0 || s.compareTo("*") == 0 || s.compareTo("^") == 0 || s.compareTo("%") == 0)
			return true;
		return false;
	}
	
	//checks whether a char is a number
	private static boolean isNumeric(char c)
	{
		if(c >= '0' && c <= '9')
			return true;
		return false;
	}
	
	//determines if a string is all numbers
	private static boolean isNumeric(String s)
	{
		for (int i = 0; i < s.length(); i++)
			if(!(s.charAt(i) >= '0' && s.charAt(i) <= '9'))
				return false;
		return true;
	}
	
	//adds spaces to the postfix string
	private static String addSpaces(String postfix, String infix)
	{
		
		infix = infix.replaceAll("[^\\w\\s\\-+*/]", ""); //remove all but numbers and ops
		String infixArr[] = infix.split("[-+*/]");		 //split up infix 
		for (int i = 0; i < infixArr.length; i++)		 //remove any spaces
			infixArr[i] = infixArr[i].replaceAll(" ", "");
		for (int i = 0; i < postfix.length(); i++)
		{
			//add a space after each operator
			if (isOperator(postfix.charAt(i)+""))
			{
				postfix = new StringBuilder(postfix).insert(i+1, " ").toString(); //add spaces after each operator
				i++; //string size is 1 larger now
			}
		}
		//add spaces after every number using information from the infix array
		for (int i = 0; i < infixArr.length; i++)
		{
			postfix = new StringBuilder(postfix).insert(postfix.indexOf(infixArr[i])+infixArr[i].length(), " ").toString();
		}
		
		postfix = postfix.substring(0, postfix.length()-1);	//remove the last char (a space)
		return postfix;
	}
}
