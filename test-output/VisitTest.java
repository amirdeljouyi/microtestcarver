package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.owner.Visit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

public class VisitTest {

    private Visit subject;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
		subject = new Visit();
		subject.setDescription("Catch up");
		subject.setId("5");
		subject.setDate(LocalDate.parse("2023-01-06"));

    }

    @Test
    public void getDateTest() throws Exception{
		subject.setDescription("null");
		subject.setId("null");


		LocalDate getDate = subject.getDate();

		assertThat(getDate, is(LocalDate.parse("2023-01-06")));
    }

}