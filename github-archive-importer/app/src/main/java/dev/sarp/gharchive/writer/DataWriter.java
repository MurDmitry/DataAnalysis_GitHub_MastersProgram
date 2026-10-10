package dev.sarp.gharchive.writer;

import java.util.List;

import dev.sarp.gharchive.model.GithubEvent;

public interface DataWriter {

    void writeBatch(List<GithubEvent> events);

}
