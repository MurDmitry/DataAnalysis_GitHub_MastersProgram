package dev.sarp.gharchive.writer.impl;

import java.util.List;

import dev.sarp.gharchive.model.GithubEvent;
import dev.sarp.gharchive.writer.DataWriter;

public class ClickHouseWriter implements DataWriter{

    @Override
    public void writeBatch(List<GithubEvent> events) {
        System.out.println("it will send to clickhouse");
        
    }

}
