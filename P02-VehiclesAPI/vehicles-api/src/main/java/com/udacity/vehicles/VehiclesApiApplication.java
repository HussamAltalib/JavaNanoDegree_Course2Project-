package com.udacity.vehicles;

import com.udacity.vehicles.domain.Condition;
import com.udacity.vehicles.domain.Location;
import com.udacity.vehicles.domain.car.Car;
import com.udacity.vehicles.domain.car.CarRepository;
import com.udacity.vehicles.domain.car.Details;
import com.udacity.vehicles.domain.manufacturer.Manufacturer;
import com.udacity.vehicles.domain.manufacturer.ManufacturerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Launches a Spring Boot application for the Vehicles API,
 * initializes the car manufacturers in the database,
 * and launches web clients to communicate with maps and pricing.
 */
@SpringBootApplication
@EnableJpaAuditing
public class VehiclesApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VehiclesApiApplication.class, args);
    }

    /**
     * Initializes the car manufacturers available to the Vehicle API.
     * @param repository where the manufacturer information persists.
     * @return the car manufacturers to add to the related repository
     */
    @Bean
    CommandLineRunner initDatabase(ManufacturerRepository manufacturerRepository, CarRepository carRepository) {
        return args -> {
            // Initialize Manufacturers
            Manufacturer audi = new Manufacturer(100, "Audi");
            Manufacturer chevrolet = new Manufacturer(101, "Chevrolet");
            Manufacturer ford = new Manufacturer(102, "Ford");
            Manufacturer bmw = new Manufacturer(103, "BMW");
            Manufacturer dodge = new Manufacturer(104, "Dodge");

            manufacturerRepository.save(audi);
            manufacturerRepository.save(chevrolet);
            manufacturerRepository.save(ford);
            manufacturerRepository.save(bmw);
            manufacturerRepository.save(dodge);

            // Initialize Cars
            carRepository.save(new Car(Condition.USED,
                    new Details("sedan", "A4", audi, 4, "Gasoline", "2.0L I4", 20000, 2019, 2019, "black"),
                    new Location(40.73061, -73.935242)));

            carRepository.save(new Car(Condition.NEW,
                    new Details("SUV", "Tahoe", chevrolet, 4, "Gasoline", "5.3L V8", 10, 2020, 2020, "white"),
                    new Location(34.052235, -118.243683)));

            carRepository.save(new Car(Condition.USED,
                    new Details("hatchback", "Focus", ford, 4, "Gasoline", "2.0L I4", 15000, 2018, 2018, "blue"),
                    new Location(37.774929, -122.419418)));

            carRepository.save(new Car(Condition.NEW,
                    new Details("coupe", "M4", bmw, 2, "Gasoline", "3.0L I6", 5, 2021, 2021, "red"),
                    new Location(40.712776, -74.005974)));

            carRepository.save(new Car(Condition.USED,
                    new Details("SUV", "Durango", dodge, 4, "Gasoline", "3.6L V6", 25000, 2017, 2017, "gray"),
                    new Location(51.507351, -0.127758)));
        };
    }



    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    /**
     * Web Client for the maps (location) API
     * @param endpoint where to communicate for the maps API
     * @return created maps endpoint
     */
    @Bean(name="maps")
    public WebClient webClientMaps(@Value("${maps.endpoint}") String endpoint) {
        return WebClient.create(endpoint);
    }

    /**
     * Web Client for the pricing API
     * @param endpoint where to communicate for the pricing API
     * @return created pricing endpoint
     */
    @Bean(name="pricing")
    public WebClient webClientPricing(@Value("${pricing.endpoint}") String endpoint) {
        return WebClient.create(endpoint);
    }

}
