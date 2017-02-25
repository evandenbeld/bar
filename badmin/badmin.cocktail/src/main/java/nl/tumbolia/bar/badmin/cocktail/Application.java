
package nl.tumbolia.bar.badmin.cocktail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Erwin.van.den.Beld
 *
 */
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	private CocktailRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		// save a couple of customers
		repository.save(new Cocktail("Gin-fizz", "Smith"));
		repository.save(new Cocktail("B-52", "Smith"));

		// fetch all customers
		System.out.println("Cocktail found with findAll():");
		System.out.println("-------------------------------");
		for (Cocktail cocktail : repository.findAll()) {
			System.out.println(cocktail);
		}
		System.out.println();

		// // fetch an individual customer
		// System.out.println("Customer found with findByFirstName('Alice'):");
		// System.out.println("--------------------------------");
		// System.out.println(repository.findByFirstName("Alice"));
		//
		// System.out.println("Customers found with findByLastName('Smith'):");
		// System.out.println("--------------------------------");
		// for (Customer customer : repository.findByLastName("Smith")) {
		// System.out.println(customer);
		// }

	}

}
