package testcases;

import com.microsoft.playwright.APIResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static com.pw.suite.TestTags.POKEMON;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestPokemonAPI extends BaseAPI{

  private static final String URL = "https://pokeapi.co";

  @BeforeAll
  void beforeAll() {
    createPlaywright();
    createAPIRequestContext(URL);
  }

  @AfterAll
  void afterAll() {
    disposeAPIRequestContext();
    closePlaywright();
  }

  @Test
  @Tag(POKEMON)
  void shouldGetPokemonItems() {
    APIResponse issues = request.get("/api/v2/item");
    JsonObject response = new Gson().fromJson(issues.text(), JsonObject.class);

    assertThat("status200", issues.status(), equalTo(200));
    assertThat("count", Integer.parseInt(response.get("count").toString()), greaterThan(0));
  }

  @Test
  @Tag(POKEMON)
  void shouldGetAPokemonItem() {
    APIResponse issues = request.get("/api/v2/item/potion");
    JsonObject response = new Gson().fromJson(issues.text(), JsonObject.class);

    assertThat("status200", issues.status(), equalTo(200));
    assertThat("id", Integer.parseInt(response.get("id").toString()), equalTo(17));
    assertThat("name", response.get("name").toString(), containsString("potion"));
  }
}