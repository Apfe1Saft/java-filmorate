package ru.yandex.practicum.filmorate.data;

import com.google.gson.*;

import java.time.Duration;
import java.time.LocalDateTime;

public class StaticData {
    public static Gson gson = new GsonBuilder()
            .registerTypeAdapter(
                    LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (json, type, context) -> LocalDateTime.parse(json.getAsString())
            )
            .registerTypeAdapter(
                    LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (srs, typeOfSrs, context) -> new JsonPrimitive(srs.toString())
            )
            .registerTypeAdapter(
                    Duration.class,
                    (JsonDeserializer<Duration>) (json, type, context) -> Duration.parse(json.getAsString())
            )
            .registerTypeAdapter(
                    Duration.class,
                    (JsonSerializer<Duration>) (srs, typeOfSrs, context) -> new JsonPrimitive(srs.toString())
            )
            .create();
}
