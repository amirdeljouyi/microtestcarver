package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.owner.Pet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

public class PetTest {

    private Pet subject;

    @Mock
    private Visit> visits;
    @Mock
    private Visit> visits;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
		subject = new Pet();
		subject.setBirthDate(LocalDate.parse("2012-08-06"));
		subject.setId("14");
		subject.setName("Basil");
		PetType petType = new PetType();
		petType.setId(2);
		petType.setName("dog");

		subject.setType(petType);

    }

    @Test
    public void getVisitsTest() throws Exception{
		subject.setBirthDate(LocalDate.parse("2023-01-21"));
		subject.setName("Mike");


		PersistentSet getVisits = subject.getVisits();

		assertThat(getVisits, is([]));
    }

}