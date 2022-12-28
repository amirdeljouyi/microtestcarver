package org.springframework.samples.petclinic.model;

import org.springframework.samples.petclinic.model.NamedEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

public class NamedEntityTest {

    private NamedEntity subject;


    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        subject = new NamedEntity();

        subject.setId(1);
        subject.setName("Basil");
    }

    @Test
    public void shouldTesttoString(){
        subject.setName("Leo");
        String toString = subject.toString();

        assertThat(toString, is("Leo"));
    }

    @Test
    public void shouldTestgetName(){
        subject.setName("Leo");
        String getName = subject.getName();

        assertThat(getName, is("Leo"));
    }

}