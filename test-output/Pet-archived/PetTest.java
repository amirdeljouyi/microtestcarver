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

        subject.setType(cat);
        subject.setBirthDate(2005-11-25);
        subject.setId(1);
        subject.setName("Leo");
    }

    @Test
    public void shouldTestgetType(){
        subject.setBirthDate(2010-09-07);
        PetType getType = subject.getType();

        assertThat(getType, is(new PetType()));
    }

    @Test
    public void shouldTestgetVisits(){
        subject.setBirthDate(2010-09-07);
        PersistentSet getVisits = subject.getVisits();

        assertThat(getVisits, is([]));
    }

    @Test
    public void shouldTestgetBirthDate(){
        subject.setBirthDate(2010-09-07);
        LocalDate getBirthDate = subject.getBirthDate();

        assertThat(getBirthDate, is(LocalDate localDate = new Pet()));
    }

}