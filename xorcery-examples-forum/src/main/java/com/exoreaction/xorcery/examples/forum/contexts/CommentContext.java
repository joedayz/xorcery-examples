package com.exoreaction.xorcery.examples.forum.contexts;

import com.exoreaction.xorcery.domainevents.helpers.context.DomainContext;
import com.exoreaction.xorcery.domainevents.helpers.entity.Command;
import com.exoreaction.xorcery.examples.forum.ForumApplication;
import com.exoreaction.xorcery.examples.forum.entities.CommentEntity;
import com.exoreaction.xorcery.examples.forum.model.CommentModel;
import com.exoreaction.xorcery.metadata.Metadata;

import java.util.List;
import java.util.concurrent.CompletionStage;

import static com.exoreaction.xorcery.domainevents.helpers.context.DomainEventMetadata.Builder.aggregate;

public record CommentContext(ForumApplication forumApplication, CommentModel model)
        implements DomainContext {
    @Override
    public List<Command> commands() {
        return List.of(new CommentEntity.UpdateComment(model.getId(), model.getBody()),
                new CommentEntity.RemoveComment(model.getId()));
    }

    @Override
    public CompletionStage<Metadata> handle(Metadata metadata, Command command) {
        return forumApplication.handle(new CommentEntity(), aggregate("PostAggregate", model.getAggregateId(), metadata), command);
    }
}
