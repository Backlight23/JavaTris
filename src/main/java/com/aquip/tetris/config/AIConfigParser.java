package com.aquip.tetris.config;

import com.aquip.tetris.ai.AIConfig;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

public class AIConfigParser {

    public static AIConfig parse(File file) {
        try {
            Yaml yaml = new Yaml();
            Map<String, Object> data = yaml.load(new FileInputStream(file));

            if (data == null) return AIConfig.defaults();

            @SuppressWarnings("unchecked")
            Map<String, Object> ai = (Map<String, Object>) data.get("ai");

            if (ai == null) return AIConfig.defaults();

            int tickDelay = ((Number) ai.getOrDefault("tickDelay", 0)).intValue();

            return new AIConfig(tickDelay);

        } catch (Exception e) {
            throw new RuntimeException("Failed to parse AI config", e);
        }
    }
}