package dev.sarp.gharchive.model;

import java.time.Instant;


public record GithubEvent(
    String eventId,
    String type,
    Instant createdAt,
    long repoId,
    String repoName,
    long actorId
) {}
