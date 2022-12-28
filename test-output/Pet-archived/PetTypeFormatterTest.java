package org.springframework.samples.petclinic.owner;

import org.springframework.samples.petclinic.owner.PetTypeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

public class PetTypeFormatterTest {

    private PetTypeFormatter subject;

    @Mock
    private OwnerRepository owners;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        subject = new PetTypeFormatter();

    }

    @Test
    public void shouldTestparse(){
        PetType parse = subject.parse(en_US, "lizard");

        assertThat(parse, is(new PetType()));
    }

    @Test
    public void shouldTestparse(){
        PetType parse = subject.parse("bird", en_US);

        assertThat(parse, is(new PetType()));
    }

    @Test
    public void shouldTestparse(){
        PetType parse = subject.parse(en_US, "cat");

        assertThat(parse, is(new PetType()));
    }

    @Test
    public void shouldTestparse(){
        given(owners.findPetTypes()).willReturn($combine.revealObject($mapEntry.key.returnField));
        PetType parse = subject.parse("dog", en_US);

        assertThat(parse, is(new PetType()));
    }

    @Test
    public void shouldTestparse(){
        given(owners.findPetTypes()).willReturn($combine.revealObject($mapEntry.key.returnField));
        PetType parse = subject.parse(en_US, "snake");

        assertThat(parse, is(new PetType()));
    }

    @Test
    public void shouldTestparse(){
        PetType parse = subject.parse("hamster", en_US);

        assertThat(parse, is(new PetType()));
    }

}