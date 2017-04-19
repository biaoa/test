package linle;

import java.util.Random;

public class Test_xinjinyuan {

	public static void main(String[] args) {
//		System.out.println(Math.random());
//		System.out.println(testRandom(20,10));
////		  Random random = new Random();
//		System.out.println(random.nextInt(5 - 1 + 1) + 1);
		
		for (int i = 0; i < 10; i++) {
			System.out.println(test());
		}
	}

	public static int test() {
		int sum = 0;
		for (int i = 0; i < 1500; i++) {
			sum += huodong(sum);
		}
		return sum;
	}

	public static int huodong(int sum) {
		Random ne = new Random();
		int n5 = ne.nextInt(100);
		int m; // 结果数字
		if (n5 < 80) { // 80%几率 1-5元
//			m = (int) (1 + Math.random() * 5);
			m = testRandom(4, 1);
		} else { // 20%几率 5-10元
//			m = (int) (5 + Math.random() * 6);
			m = testRandom(10, 5);
		}
//		if(sum>=6000){
//			return 0;
//		}
		// System.out.println(m);
		return m;
	}
	
	public static int testRandom(int max, int min){
	      Random random = new Random();
	      int i  = max-min + 1;
	      int result = 0;
	      try {
	    	  result =random.nextInt(max-min + 1) + min;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(max);
			System.out.println(min);
			System.out.println(random.nextInt(max-min + 1));
		}
	      return  result; 
	}
	
	public static void test2(){
		
		System.out.println((int) (1 + Math.random() * 5));    //1-5		5
		System.out.println((int) (1 + Math.random() * 10));   //1-10 	10
		System.out.println((int) (10 + Math.random() * 10));  //10-20	10
		System.out.println((int) (20 + Math.random() * 10));  //20-30   10
		System.out.println((int) (50 + Math.random() * 50));  //50-100 	50
		System.out.println((int) (100 + Math.random() * 100));//100-200 100
	}
}
