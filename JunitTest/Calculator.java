package JunitTest;

public class Calculator{ 
	 public static int getResult() {
		return result;
	}
	public static void setResult(int result) {
		Calculator.result = result;
	}
	private static int result;       // ��̬���������ڴ洢���н��   
	 public void add(int n) {   result = result + n;  } 
	 public void substract(int n) { 
	      result = result - n;     //�����Bug��Ӧ���� result =result-n 
	  }   
	 public void multiply(int n) {  }  // ����˷�������Ŀ��ɹ�������δд��  
	 public void divide(int n) {  result = result / n;  } 
	 public void square(int n) {   result = n * n;  } 
	 public void squareRoot(int n) { //��ƽ����   
	      for (; ;);    //Bug : ��ѭ�� 
	  }   
	 public void clear() {  // ���������  
	      result = 0; 
	  } 
	}
