package com.mideas.rpg.v2.chat.lua;

public class EvalMathExpression {
	
	public static double eval(final String str) {
		    return new Object() {
		        int pos = -1, ch;

		        void nextChar() {
		            this.ch = (++this.pos < str.length()) ? str.charAt(this.pos) : -1;
		        }

		        boolean eat(int charToEat) {
		            while (this.ch == ' ') nextChar();
		            if (this.ch == charToEat) {
		                nextChar();
		                return true;
		            }
		            return false;
		        }

		        double parse() {
		            nextChar();
		            double x = parseExpression();
		            if (this.pos < str.length()) throw new RuntimeException("Unexpected: " + (char)this.ch);
		            return x;
		        }

		        double parseExpression() {
		            double x = parseTerm();
		            for (;;) {
		                if      (eat('+')) x += parseTerm(); // addition
		                else if (eat('-')) x -= parseTerm(); // subtraction
		                else return x;
		            }
		        }

		        double parseTerm() {
		            double x = parseFactor();
		            for (;;) {
		                if      (eat('*')) x *= parseFactor(); // multiplication
		                else if (eat('/')) x /= parseFactor(); // division
		                else return x;
		            }
		        }

		        double parseFactor() {
		            if (eat('+')) return parseFactor(); // unary plus
		            if (eat('-')) return -parseFactor(); // unary minus

		            double x;
		            int startPos = this.pos;
		            if (eat('(')) { // parentheses
		                x = parseExpression();
		                eat(')');
		            } else if ((this.ch >= '0' && this.ch <= '9') || this.ch == '.') { // numbers
		                while ((this.ch >= '0' && this.ch <= '9') || this.ch == '.') nextChar();
		                x = Double.parseDouble(str.substring(startPos, this.pos));
		            } else if (this.ch >= 'a' && this.ch <= 'z') { // functions
		                while (this.ch >= 'a' && this.ch <= 'z') nextChar();
		                String func = str.substring(startPos, this.pos);
		                x = parseFactor();
		                if (func.equals("sqrt")) x = Math.sqrt(x);
		                else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
		                else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
		                else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
		                else throw new RuntimeException("Unknown function: " + func);
		            } else {
		                throw new RuntimeException("Unexpected: " + (char)this.ch);
		            }

		            if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

		            return x;
		        }
		    }.parse();
		}
}
