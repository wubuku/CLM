package org.dddml.clm.tag;

import org.dddml.clm.domain.tag.TagCommand;
import org.dddml.clm.domain.tag.TagCommand.CreateTag;
import org.dddml.clm.domain.tag.TagState;
import org.dddml.clm.specialization.IdGenerator;
import java.util.UUID;

public class TagIdGenerator implements IdGenerator<String, TagCommand.CreateTag, TagState> {

    private static final int MAX_LENGTH = 12;

    @Override
    public String generateId(CreateTag command) {
        return getNextId();
    }

    @Override
    public String getNextId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, MAX_LENGTH);
    }

    @Override
    public boolean equals(CreateTag command, TagState state) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isArbitraryIdEnabled() {
        return true;
    }

}
