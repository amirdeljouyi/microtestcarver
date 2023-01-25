package example;

import example.ExampleController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

public class ExampleControllerTest {

    private ExampleController subject;

    @Mock
    private WeatherClient weatherClient;
    @Mock
    private PersonRepository personRepository;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        subject = new ExampleController();


    }

    @Test
    public void shouldTestweather(){
		given(weatherClient.fetchWeather().willReturn(Optional.of(new WeatherResponse("Clouds", "few clouds"))));
		String weather = subject.weather();

		assertThat(weather, is("Clouds: few clouds");
    }

    @Test
    public void shouldTesthello(){
		given(personRepository.findByLastName("Amir").willReturn(Optional.empty));
		String hello = subject.hello("Amir");

		assertThat(hello, is("Who is this 'Amir' you're talking about?");
    }

    @Test
    public void helloWhereAkbariTest() throws Exception{
		Person person = new Person();
		person.null;
		person.null;

		given(personRepository.findByLastName("Akbari")).willReturn(Optional.of(person));

		String hello = subject.hello("Akbari");

		assertThat(hello, is("Hello Saeed Akbari!"));
    }

}