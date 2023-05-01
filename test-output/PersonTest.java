package example.person;

import example.person.Person;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

public class PersonTest {

    private Person subject;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
		subject = new Person();

    }

    @Test
    public void getFirstNameTest() throws Exception{
		String getFirstName = subject.getFirstName();

		assertThat(getFirstName, is("james"));
    }

    @Test
    public void getFirstNameWhereTest() throws Exception{
		String getFirstName = subject.getFirstName();

		assertThat(getFirstName, is("peter"));
    }

    @Test
    public void getLastNameTest() throws Exception{
		String getLastName = subject.getLastName();

		assertThat(getLastName, is("carter"));
    }

    @Test
    public void getLastNameWhereTest() throws Exception{
		String getLastName = subject.getLastName();

		assertThat(getLastName, is("pan"));
    }

}