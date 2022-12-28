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
    public void shouldTestParse(){
		PetType parse = subject.parse(Locale.ENGLISH, "lizard");

		PetType PetType = new PetType();
		PetType.setId(3);
		PetType.setName("lizard");

		assertThat(parse, is(PetType);
    }

    @Test
    public void shouldTestParse_whereBird(){
		PetType parse = subject.parse("bird", Locale.ENGLISH);

		PetType PetType = new PetType();
		PetType.setId(5);
		PetType.setName("bird");

		assertThat(parse, is(PetType);
    }

    @Test
    public void shouldTestParse_whereBirdAndEn_US(){
		given(owners.findPetTypes().willReturn(null));

		PetType parse = subject.parse("bird", Locale.ENGLISH);

		PetType PetType = new PetType();
		PetType.setId(5);
		PetType.setName("bird");

		assertThat(parse, is(PetType);
    }

    @Test
    public void shouldTestParse_whereEn_US(){
		PetType parse = subject.parse(Locale.ENGLISH, "cat");

		PetType PetType = new PetType();
		PetType.setId(1);
		PetType.setName("cat");

		assertThat(parse, is(PetType);
    }

    @Test
    public void shouldTestParse_whereEn_USAndCat(){
		given(owners.findPetTypes().willReturn(null));

		PetType parse = subject.parse(Locale.ENGLISH, "cat");

		PetType PetType = new PetType();
		PetType.setId(1);
		PetType.setName("cat");

		assertThat(parse, is(PetType);
    }

    @Test
    public void shouldTestParse_whereDog(){
		PetType PetType = new PetType();
		PetType.setId(1);
		PetType.setName("cat");
		PetType PetType_1 = new PetType();
		PetType_1.setId(2);
		PetType_1.setName("dog");
		ArrayList<PetType> PetTypes = new ArrayList<>();
		PetTypes.add(PetType);
		PetTypes.add(PetType_1);

		given(owners.findPetTypes().willReturn(PetTypes));

		PetType parse = subject.parse("dog", Locale.ENGLISH);

		PetType PetType_2 = new PetType();
		PetType_2.setId(2);
		PetType_2.setName("dog");

		assertThat(parse, is(PetType_2);
    }

    @Test
    public void shouldTestParse_whereEn_USAndSnake(){
		PetType PetType = new PetType();
		PetType.setId(1);
		PetType.setName("cat");
		PetType PetType_1 = new PetType();
		PetType_1.setId(2);
		PetType_1.setName("dog");
		ArrayList<PetType> PetTypes = new ArrayList<>();
		PetTypes.add(PetType);
		PetTypes.add(PetType_1);

		given(owners.findPetTypes().willReturn(PetTypes));

		PetType parse = subject.parse(Locale.ENGLISH, "snake");

		PetType PetType_2 = new PetType();
		PetType_2.setId(4);
		PetType_2.setName("snake");

		assertThat(parse, is(PetType_2);
    }

    @Test
    public void shouldTestParse_whereDogAndEn_US(){
		PetType parse = subject.parse("dog", Locale.ENGLISH);

		PetType PetType = new PetType();
		PetType.setId(2);
		PetType.setName("dog");

		assertThat(parse, is(PetType);
    }

    @Test
    public void shouldTestParse_whereHamster(){
		PetType parse = subject.parse("hamster", Locale.ENGLISH);

		PetType PetType = new PetType();
		PetType.setId(6);
		PetType.setName("hamster");

		assertThat(parse, is(PetType);
    }

    @Test
    public void shouldTestParse_whereEn_USAndSnake_returnSnake(){
		PetType parse = subject.parse(Locale.ENGLISH, "snake");

		PetType PetType = new PetType();
		PetType.setId(4);
		PetType.setName("snake");

		assertThat(parse, is(PetType);
    }

    @Test
    public void shouldTestParse_whereHamsterAndEn_US(){
		PetType PetType = new PetType();
		PetType.setId(1);
		PetType.setName("cat");
		PetType PetType_1 = new PetType();
		PetType_1.setId(2);
		PetType_1.setName("dog");
		ArrayList<PetType> PetTypes = new ArrayList<>();
		PetTypes.add(PetType);
		PetTypes.add(PetType_1);

		given(owners.findPetTypes().willReturn(PetTypes));

		PetType parse = subject.parse("hamster", Locale.ENGLISH);

		PetType PetType_2 = new PetType();
		PetType_2.setId(6);
		PetType_2.setName("hamster");

		assertThat(parse, is(PetType_2);
    }

    @Test
    public void shouldTestParse_whereEn_USAndLizard(){
		PetType PetType = new PetType();
		PetType.setId(1);
		PetType.setName("cat");
		PetType PetType_1 = new PetType();
		PetType_1.setId(2);
		PetType_1.setName("dog");
		ArrayList<PetType> PetTypes = new ArrayList<>();
		PetTypes.add(PetType);
		PetTypes.add(PetType_1);

		given(owners.findPetTypes().willReturn(PetTypes));

		PetType parse = subject.parse(Locale.ENGLISH, "lizard");

		PetType PetType_2 = new PetType();
		PetType_2.setId(3);
		PetType_2.setName("lizard");

		assertThat(parse, is(PetType_2);
    }

}