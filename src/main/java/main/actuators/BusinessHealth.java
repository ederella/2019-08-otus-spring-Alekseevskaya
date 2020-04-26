package main.actuators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import main.dao.BookRepository;

@Component
public class BusinessHealth  implements HealthIndicator {
	private BookRepository bookRepository;
	@Autowired
	public BusinessHealth(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	@Override
	public Health health() {
		if (this.bookRepository.findAll().size() == 0) {
			return Health.down().status(Status.DOWN).withDetail("message", "All books were deleted").build();
		} else {
			return Health.up().withDetail("message", "All ok").build();
		}
	}

}
