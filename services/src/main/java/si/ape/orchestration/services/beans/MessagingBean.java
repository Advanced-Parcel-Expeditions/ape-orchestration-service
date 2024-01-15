package si.ape.orchestration.services.beans;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;

@RequestScoped
public class MessagingBean {

    @Inject
    private EntityManager em;

    public void viewConversations() {

    }

    public void viewMessagesInConversation() {

    }

    public void sendMessage() {

    }

    public void createConversation() {

    }

    public void addUserToConversation() {

    }

}
