/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.libcore.timezone.tzlookup.zonetree;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * A record for a country of when zones stopped being (effectively) used.
 */
public class CountryZoneUsage {
    private final String isoCode;
    private final Map<String, Entry> zoneIdEntryMap = new HashMap<>();

    public CountryZoneUsage(String isoCode) {
        this.isoCode = isoCode;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void addEntry(String zoneId, Instant endInstantExclusive) {
        zoneIdEntryMap.put(zoneId, new Entry(zoneId, endInstantExclusive));
    }

    public boolean hasEntry(String zoneId) {
        return zoneIdEntryMap.containsKey(zoneId);
    }

    public Instant getNotUsedAfterInstant(String zoneId) {
        Entry entry = zoneIdEntryMap.get(zoneId);
        if (entry == null) {
            throw new IllegalArgumentException("No entry for " + zoneId + " for " + isoCode);
        }
        return entry.endInstant;
    }

    private static class Entry {
        final String zoneId;
        final Instant endInstant;

        Entry(String zoneId, Instant endInstant) {
            this.zoneId = zoneId;
            this.endInstant = endInstant;
        }
    }
}