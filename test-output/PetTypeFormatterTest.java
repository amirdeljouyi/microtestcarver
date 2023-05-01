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
    public void printTest() throws Exception{
		PetType petType = new PetType();
		petType.setId(2);
		petType.setName("dog");

		String print = subject.print(petType, Locale.ENGLISH);

		assertThat(print, is("dog"));
    }

    @Test
    public void printWhereDogTest() throws Exception{
		PetType petType = new PetType();
		petType.setId(2);
		petType.setName("dog");

		String print = subject.print(petType, Locale.ENGLISH);

		assertThat(print, is("dog"));
    }

    @Test
    public void parseTest() throws Exception{
		given(owners.findPetTypes()).willReturn(null);

		PetType parse = subject.parse("hamster", Locale.ENGLISH);

		PetType petType = new PetType();
		petType.setId(6);
		petType.setName("hamster");

		assertThat(parse, is(petType));
    }

    @Test
    public void parseWhereEn_USTest() throws Exception{
		PetType parse = subject.parse(Locale.ENGLISH, "cat");

		PetType petType = new PetType();
		petType.setId(1);
		petType.setName("cat");

		assertThat(parse, is(petType));
    }

    @Test
    public void parseWhereBirdTest() throws Exception{
		PetType parse = subject.parse("bird", Locale.ENGLISH);

		PetType petType = new PetType();
		petType.setId(5);
		petType.setName("bird");

		assertThat(parse, is(petType));
    }

    @Test
    public void parseWhereEn_USAndSnakeTest() throws Exception{
		given(owners.findPetTypes()).willReturn(null);

		PetType parse = subject.parse(Locale.ENGLISH, "snake");

		PetType petType = new PetType();
		petType.setId(4);
		petType.setName("snake");

		assertThat(parse, is(petType));
    }

    @Test
    public void parseWhereBirdAndEn_USTest() throws Exception{
		given(owners.findPetTypes()).willReturn(null);

		PetType parse = subject.parse("bird", Locale.ENGLISH);

		PetType petType = new PetType();
		petType.setId(5);
		petType.setName("bird");

		assertThat(parse, is(petType));
    }

    @Test
    public void parseWhereEn_USAndCatTest() throws Exception{
		given(owners.findPetTypes()).willReturn(null);

		PetType parse = subject.parse(Locale.ENGLISH, "cat");

		PetType petType = new PetType();
		petType.setId(1);
		petType.setName("cat");

		assertThat(parse, is(petType));
    }

    @Test
    public void parseWhereEn_USAndLizardTest() throws Exception{
		PetType parse = subject.parse(Locale.ENGLISH, "lizard");

		PetType petType = new PetType();
		petType.setId(3);
		petType.setName("lizard");

		assertThat(parse, is(petType));
    }

    @Test
    public void parseWhereDogTest() throws Exception{
		PetType parse = subject.parse("dog", Locale.ENGLISH);

		PetType petType = new PetType();
		petType.setId(2);
		petType.setName("dog");

		assertThat(parse, is(petType));
    }

    @Test
    public void parseWhereHamsterTest() throws Exception{
		PetType parse = subject.parse("hamster", Locale.ENGLISH);

		PetType petType = new PetType();
		petType.setId(6);
		petType.setName("hamster");

		assertThat(parse, is(petType));
    }

    @Test
    public void parseWhereDogAndEn_USTest() throws Exception{
		given(owners.findPetTypes()).willReturn(null);

		PetType parse = subject.parse("dog", Locale.ENGLISH);

		PetType petType = new PetType();
		petType.setId(2);
		petType.setName("dog");

		assertThat(parse, is(petType));
    }

    @Test
    public void parseWhereEn_USAndLizardReturnLizardTest() throws Exception{
		given(owners.findPetTypes()).willReturn(null);

		PetType parse = subject.parse(Locale.ENGLISH, "lizard");

		PetType petType = new PetType();
		petType.setId(3);
		petType.setName("lizard");

		assertThat(parse, is(petType));
    }

    @Test
    public void parseWhereEn_USAndSnakeReturnSnakeTest() throws Exception{
		PetType parse = subject.parse(Locale.ENGLISH, "snake");

		PetType petType = new PetType();
		petType.setId(4);
		petType.setName("snake");

		assertThat(parse, is(petType));
    }

}