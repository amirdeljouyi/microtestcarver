package example.weather;

import example.weather.WeatherClient;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

public class WeatherClientTest {

    private WeatherClient subject;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
		subject = new WeatherClient();
		subject.CITY = "Hamburg,de";

    }

    @Test
    public void fetchWeatherTest() throws Exception{
		given(restTemplate.getForObject()).willReturn(null);

		Optional fetchWeather = subject.fetchWeather();

		assertThat(fetchWeather, is(Optional.of(new WeatherResponse("Clear", "clear sky"))));
    }

}