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
 
 public class ByteSize implements Token {  
     private final String token;
     private final long bytes;
 
     public ByteSize(String token) {
         this.token = token;
         this.bytes = parseBytes(token);
     }
 
     private long parseBytes(String token) {
         String sizeStr = token.replaceAll("[^\\d.]", ""); 
         String unit = token.replaceAll("[\\d.]", "").toLowerCase(); 
         
         double size = Double.parseDouble(sizeStr);
         switch (unit) {
             case "kb":
                 return (long) (size * 1024);
             case "mb":
                 return (long) (size * 1024 * 1024);
             case "gb":
                 return (long) (size * 1024 * 1024 * 1024);
             case "b":
                 return (long) size;
             default:
                 throw new IllegalArgumentException("Unknown byte unit: " + unit);
         }
     }
 
     @Override
     public Long value() {
         return bytes;
     }
 
     @Override
     public TokenType type() {
         return TokenType.BYTE_SIZE;
     }
 
     @Override
     public JsonElement toJson() {
         return new JsonPrimitive(token);  
     }
 }