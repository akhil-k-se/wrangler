/*
 * Copyright © 2017-2019 Cask Data, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.cdap.wrangler.api.parser;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

public class TimeDuration implements Token {  
    private final String token;
    private final long durationInMillis;

    public TimeDuration(String token) {
        this.token = token;
        this.durationInMillis = parseDuration(token);
    }

    private long parseDuration(String token) {
        String durationStr = token.replaceAll("[^\\d.]", "");  
        String unit = token.replaceAll("[\\d.]", "").toLowerCase();  
        
        double duration = Double.parseDouble(durationStr);
        switch (unit) {
            case "ms":
                return (long) duration;
            case "s":
                return (long) (duration * 1000); 
            case "m":
                return (long) (duration * 1000 * 60);  
            case "h":
                return (long) (duration * 1000 * 60 * 60); 
            default:
                throw new IllegalArgumentException("Unknown time unit: " + unit);
        }
    }

    @Override
    public Long value() {
        return durationInMillis;
    }

    @Override
    public TokenType type() {
        return TokenType.TIME_DURATION; 
    }

    @Override
    public JsonElement toJson() {
        return new JsonPrimitive(token); 
    }
}