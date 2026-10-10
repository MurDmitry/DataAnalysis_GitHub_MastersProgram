package dev.sarp.gharchive.parser;

import java.time.Instant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sarp.gharchive.model.GithubEvent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EventParser {

    private final ObjectMapper mapper;

    public GithubEvent parse(String line) throws JsonProcessingException {
        // Who is json
        JsonNode event = mapper.readTree(line);

        if (event == null) {
            throw new IllegalArgumentException("Empty Json");
        }

        JsonNode typeNode = event.path("type");
        if (!typeNode.isTextual() || typeNode.asText().isBlank()) {
            throw new IllegalArgumentException("Invalid type");
        }
        String type = typeNode.asText();

        JsonNode eventIdNode = event.path("id");
        if (!eventIdNode.isTextual() || eventIdNode.asText().isBlank()) {
            throw new IllegalArgumentException("Invalid id");
        }
        String eventId = eventIdNode.asText();

        JsonNode createdAtNode = event.path("created_at");
        if (!createdAtNode.isTextual() || createdAtNode.asText().isBlank()) {
            throw new IllegalArgumentException("Invalid created_at");
        }
        Instant createdAt = Instant.parse(createdAtNode.asText());

        JsonNode repoIdNode = event.path("repo").path("id");
        if (!repoIdNode.isIntegralNumber()
                || !repoIdNode.canConvertToLong()
                || repoIdNode.asLong() <= 0) {
            throw new IllegalArgumentException("Invalid repo.id");
        }
        long repoId = repoIdNode.asLong();

        JsonNode repoNameNode = event.path("repo").path("name");
        if (!repoNameNode.isTextual() || repoNameNode.asText().isBlank()) {
            throw new IllegalArgumentException("Invalid repo name");
        }
        String repoName = repoNameNode.asText();

        JsonNode actorIdNode = event.path("actor").path("id");
        if (!actorIdNode.isIntegralNumber()
                || !actorIdNode.canConvertToLong()
                || actorIdNode.asLong() <= 0) {
            throw new IllegalArgumentException("Invalid actor.id");
        }

        long actorId = actorIdNode.asLong();

        return new GithubEvent(eventId, type, createdAt, repoId, repoName, actorId);
    }
}
