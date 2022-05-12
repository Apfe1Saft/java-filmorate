package ru.yandex.practicum.filmorate;

import com.google.gson.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.filmorate.data.StaticData;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDate;

@SpringBootTest
class FilmorateApplicationTests {

	@BeforeEach
	 void serverStart(){
		FilmorateApplication.main(new String[0]);
	}
	@AfterEach
	 void serverStop(){
		FilmorateApplication.stop();
	}
	@Test
	void createFilmTest() throws IOException, InterruptedException {
		Gson gson = StaticData.gsonForAll;
		URI url = URI.create("http://localhost:8080/films");
		HttpClient client = HttpClient.newHttpClient();
		Film film = new Film();
		film.setName("Film");
		film.setDescription("good film");
		//film.setId(1);
		film.setDuration(Duration.ofMinutes(30));
		film.setReleaseDate(LocalDate.of(2021,8,16));
		HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(film));
		HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		url = URI.create("http://localhost:8080/films");
		client = HttpClient.newHttpClient();
		request = HttpRequest.newBuilder().uri(url).GET().build();
		response = client.send(request, HttpResponse.BodyHandlers.ofString());
		assertEquals(response.body(),"[{\"id\":1,\"name\":\"Film\",\"description\":\"good film\",\"releaseDate\":\"2021-08-16\",\"duration\":\"PT30M\"}]");
	}

	@Test
	void updateFilmTest() throws IOException, InterruptedException {
		Gson gson = StaticData.gsonForAll;
		URI url = URI.create("http://localhost:8080/films");
		HttpClient client = HttpClient.newHttpClient();
		Film film = new Film();
		film.setName("Film");
		film.setDescription("good film");
		film.setId(1);
		film.setDuration(Duration.ofMinutes(30));
		film.setReleaseDate(LocalDate.of(2021,8,16));
		HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(film));
		HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		url = URI.create("http://localhost:8080/films");
		client = HttpClient.newHttpClient();
		film.setDescription("bad film");
		body = HttpRequest.BodyPublishers.ofString(gson.toJson(film));
		request = HttpRequest.newBuilder().uri(url).method("PATCH",body).build();
		response = client.send(request, HttpResponse.BodyHandlers.ofString());

		url = URI.create("http://localhost:8080/films");
		client = HttpClient.newHttpClient();
		request = HttpRequest.newBuilder().uri(url).GET().build();
		response = client.send(request, HttpResponse.BodyHandlers.ofString());
		assertEquals(response.body(),"[{\"id\":1,\"name\":\"Film\",\"description\":\"bad film\",\"releaseDate\":\"2021-08-16\",\"duration\":\"PT30M\"}]");
	}

	@Test
	void updateUserTest() throws IOException, InterruptedException {
		Gson gson = StaticData.gsonForAll;
		URI url = URI.create("http://localhost:8080/users");
		HttpClient client = HttpClient.newHttpClient();
		User user = new User();
		user.setName("Alex");
		user.setBirthday(LocalDate.of(2009,8,16));
		user.setId(1);
		user.setLogin("Ivanov");
		user.setEmail("AlexIvanov@yandex.ru");
		HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(user));
		HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		url = URI.create("http://localhost:8080/users");
		client = HttpClient.newHttpClient();
		user.setLogin("Petrov");
		body = HttpRequest.BodyPublishers.ofString(gson.toJson(user));
		request = HttpRequest.newBuilder().uri(url).method("PATCH",body).build();
		response = client.send(request, HttpResponse.BodyHandlers.ofString());

		url = URI.create("http://localhost:8080/users");
		client = HttpClient.newHttpClient();
		request = HttpRequest.newBuilder().uri(url).GET().build();
		response = client.send(request, HttpResponse.BodyHandlers.ofString());
		assertEquals(response.body(),"[{\"id\":1,\"email\":\"AlexIvanov@yandex.ru\",\"login\":\"Petrov\",\"name\":\"Alex\",\"birthday\":\"2009-08-16\"}]");
	}

	@Test
	void createUserTest() throws IOException, InterruptedException {
		Gson gson = StaticData.gsonForAll;
		URI url = URI.create("http://localhost:8080/users");
		HttpClient client = HttpClient.newHttpClient();
		User user = new User();
		user.setName("Alex");
		user.setBirthday(LocalDate.of(2009,8,16));
		user.setId(1);
		user.setLogin("Ivanov");
		user.setEmail("AlexIvanov@yandex.ru");
		HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(gson.toJson(user));
		HttpRequest request = HttpRequest.newBuilder().uri(url).POST(body).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		url = URI.create("http://localhost:8080/users");
		client = HttpClient.newHttpClient();
		request = HttpRequest.newBuilder().uri(url).GET().build();
		response = client.send(request, HttpResponse.BodyHandlers.ofString());
		assertEquals(response.body(),"[{\"id\":1,\"email\":\"AlexIvanov@yandex.ru\",\"login\":\"Ivanov\",\"name\":\"Alex\",\"birthday\":\"2009-08-16\"}]");
	}

}
