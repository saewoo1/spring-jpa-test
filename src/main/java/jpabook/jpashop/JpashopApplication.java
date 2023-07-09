package jpabook.jpashop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpashopApplication {

	public static void main(String[] args) {
// 롬복 써봣더영
//		Hello hello = new Hello();
//		hello.setData("Wow is it lombok?");
//		String data = hello.getData();
//		System.out.println("data = " + data);
		// 다대다 위험성 넘 많은 -> 일대 다, 다대 일로 풀어서 사용하도록.
		SpringApplication.run(JpashopApplication.class, args);
	}

}
