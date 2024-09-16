package org.ebanking.ebanking_backend;

import org.ebanking.ebanking_backend.dtos.BankAccountDTO;
import org.ebanking.ebanking_backend.dtos.CurrentBankAccountDTO;
import org.ebanking.ebanking_backend.dtos.CustomerDTO;
import org.ebanking.ebanking_backend.dtos.SavingBankAccountDTO;
import org.ebanking.ebanking_backend.exceptions.CustomerNotFoundException;
import org.ebanking.ebanking_backend.security.services.IAppUserService;
import org.ebanking.ebanking_backend.services.BankAccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}
	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
		return args -> {
			//on cree les customers
			Stream.of("modric","kross","casemiro").forEach(name->{
				CustomerDTO customer = new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				bankAccountService.saveCustomer(customer);
			});
			// pour chaque customer on cree 1 currentAccount et un savingAccount
			bankAccountService.listCustomers().forEach(customer -> {
				try {
					bankAccountService.saveCurrentBankAccount(Math.random()*50000,20000,customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random()*50000,5.5,customer.getId());

			/*//pour chaque compte on créé les opérations
					List<BankAccountDTO> bankAccounts = bankAccountService.listBankAccount();
					for(BankAccountDTO bankAccount:bankAccounts){
						for(int i=0; i<10 ;i++){
							String accountId;
							if(bankAccount instanceof SavingBankAccountDTO){
								accountId = ((SavingBankAccountDTO) bankAccount).getId();
							}
							else {
								accountId = ((CurrentBankAccountDTO) bankAccount).getId();
							}
							bankAccountService.credit(accountId,50000+Math.random()*120000,"credit");
							bankAccountService.debit(accountId,1000+Math.random()*9000,"debit");
						}

					}*/
				} catch (CustomerNotFoundException e) {
					e.printStackTrace();
				}

				/*catch (BankAccountNotFoundException | BalanceNotSufficientException e) {
					e.printStackTrace();
				}*/

			});

			List<BankAccountDTO> bankAccounts = bankAccountService.listBankAccount();
			for(BankAccountDTO bankAccount:bankAccounts){
				for(int i=0; i<10 ;i++){
					String accountId;
					if(bankAccount instanceof SavingBankAccountDTO){
						accountId = ((SavingBankAccountDTO) bankAccount).getId();
					}
					else {
						accountId = ((CurrentBankAccountDTO) bankAccount).getId();
					}
					bankAccountService.credit(accountId,50000+Math.random()*120000,"credit");
					bankAccountService.debit(accountId,1000+Math.random()*9000,"debit");
				}
			}

		};


	}

	//@Bean
	CommandLineRunner start(JdbcUserDetailsManager jdbcUserDetailsManager){
		PasswordEncoder passwordEncoder = encoder();
		return args -> {
			//La condition marche uniquement si j'uutilise une vrai base de donnéé
			//UserDetails u1 = jdbcUserDetailsManager.loadUserByUsername("user21");
			//if(u1==null)
				jdbcUserDetailsManager.createUser(
					User.withUsername("user21").password(passwordEncoder.encode("1234"))
							.roles("USER").build());
			//UserDetails u2 = jdbcUserDetailsManager.loadUserByUsername("user22");
			//if(u2==null)
				jdbcUserDetailsManager.createUser(
					User.withUsername("user22").password(passwordEncoder.encode("1234"))
							.roles("USER").build());
			//UserDetails u3 = jdbcUserDetailsManager.loadUserByUsername("user23");
			//if(u3==null)
			jdbcUserDetailsManager.createUser(
					User.withUsername("user23").password(passwordEncoder.encode("1234"))
							.roles("USER","ADMIN").build());

		};
	}

	@Bean
	CommandLineRunner commandLineRunnerUserDetails(IAppUserService appUserService){
		return args->{
			appUserService.addNewRole("USER");
			appUserService.addNewRole("ADMIN");
			appUserService.addNewUser("test1","1234","test1@gmail.com","1234");
			appUserService.addNewUser("test2","1234","test2@gmail.com","1234");
			appUserService.addNewUser("admin2","1234","admin2@gmail.com","1234");

			appUserService.addRoleToUser("test1","USER");
			appUserService.addRoleToUser("test2","USER");
			appUserService.addRoleToUser("admin2","USER");
			appUserService.addRoleToUser("admin2","ADMIN");
		};
	}
	@Bean
	public PasswordEncoder encoder(){
		return new BCryptPasswordEncoder();
	}

}
