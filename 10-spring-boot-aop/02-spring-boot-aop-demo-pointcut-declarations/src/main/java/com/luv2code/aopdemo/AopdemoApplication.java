package com.luv2code.aopdemo;

import com.luv2code.aopdemo.dao.AccountDAO;
import com.luv2code.aopdemo.dao.MembershipDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AopdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AopdemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(AccountDAO accountDAO, MembershipDAO membershipDAO) {

		return runner -> {

			demoTheBeforeAdvice(accountDAO, membershipDAO);

		};
	}

	public void demoTheBeforeAdvice(AccountDAO accountDAO, MembershipDAO membershipDAO) {

		// call the business method
		Account myAccount = new Account();
		myAccount.setName("Madhu");
		myAccount.setLevel("Platinum");

		accountDAO.addAccount(myAccount, true);


		accountDAO.doWork();

		System.out.println("\n======>>> Exclude Getter and Setter");

		// call the account getter/setter methods
		accountDAO.setName("foobar");
		accountDAO.setServiceCode("silver");

		String name = accountDAO.getName();
		String code = accountDAO.getServiceCode();

		// call the membership business method
		membershipDAO.addAccount();
		membershipDAO.goToSleep();

	}

}
