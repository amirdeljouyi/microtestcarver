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


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        subject = new Pet();

		PetType PetType = new PetType();
		PetType.setId(1);
		PetType.setName("cat");

		subject.setType(PetType);
		subject.setBirthDate(LocalDate.parse('2005-11-25'));
		subject.setId("1");
		subject.setName("Leo");

    }

    @Test
    public void shouldTestGetType(){
		subject.setBirthDate(LocalDate.parse('2010-09-07'));


		PetType getType = subject.getType();

		PetType PetType = new PetType();
		PetType.setId(1);
		PetType.setName("cat");

		assertThat(getType, is(PetType);
    }

    @Test
    public void shouldTestGetVisits(){
		subject.setBirthDate(LocalDate.parse('2010-09-07'));


		PersistentSet getVisits = subject.getVisits();

		assertThat(getVisits, is([]);
    }

    @Test
    public void shouldTestGetBirthDate(){
		subject.setBirthDate(LocalDate.parse('2010-09-07'));


		LocalDate getBirthDate = subject.getBirthDate();

		assertThat(getBirthDate, is(LocalDate.parse('2010-09-07'));
    }

}