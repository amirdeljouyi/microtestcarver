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
    public void weatherTest() throws Exception{
		given(weatherClient.fetchWeather()).willReturn(Optional.of(new WeatherResponse("Clear", "clear sky")));

		String weather = subject.weather();

		assertThat(weather, is("Clear: clear sky"));
    }

    @Test
    public void helloTest() throws Exception{
		String hello = subject.hello();

		assertThat(hello, is("Hello World!"));
    }

    @Test
    public void helloWherePeterTest() throws Exception{
		given(personRepository.findByLastName("peter")).willReturn(Optional.empty(););

		String hello = subject.hello("peter");

		assertThat(hello, is("Who is this 'peter' you're talking about?"));
    }

    @Test
    public void helloWhereCarterTest() throws Exception{
		Person person = new Person("carter", "james");

		given(personRepository.findByLastName("carter")).willReturn(Optional.of(person));

		String hello = subject.hello("carter");

		assertThat(hello, is("Hello james carter!"));
    }

    @Test
    public void helloWherePanTest() throws Exception{
		Person person = new Person("pan", "peter");

		given(personRepository.findByLastName("pan")).willReturn(Optional.of(person));

		String hello = subject.hello("pan");

		assertThat(hello, is("Hello peter pan!"));
    }

}